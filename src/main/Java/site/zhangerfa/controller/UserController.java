package site.zhangerfa.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.pojo.LoginTicket;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.LoginTicketService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.util.HostHolder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RestController
@Tag(name = "用户信息")
@RequestMapping("/users")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private DefaultKaptcha defaultKaptcha;
    @Resource
    private UserService userService;
    @Resource
    private LoginTicketService loginTicketService;
    @Resource
    private HostHolder hostHolder;


    @Operation(summary = "判断用户是否注册")
    @GetMapping ("/isExist")
    @Parameter(name = "stuId", description = "用户学号", required = true)
    public Result isExist( String stuId) {
        boolean flag = userService.isExist(stuId);
        return  new Result(flag? Code.GET_OK: Code.GET_ERR, flag);
    }

    // 如果正确生成登录凭证以cookie返回给用户
    @Operation(summary = "登录：检查用户输入的密码是否正确")
    @Parameter(name = "rememberMe", description = "用户是否勾选记住密码")
    @PostMapping("/login")
    public Result login(User user, boolean rememberMe, HttpServletResponse response){
        Map<String, Object> map = userService.login(user, rememberMe);
        if (!(boolean) map.get("result")){
            return new Result(Code.SAVE_ERR, false, (String) map.get("msg"));
        }
        // 登录凭证作为cookie凭证发送给客户端
        LoginTicket ticket = (LoginTicket) map.get("ticket");
        Cookie cookie = new Cookie("ticket", ticket.getTicket());
        // 计算登录凭证有效时间（秒）
        long expired = (ticket.getExpired().getTime() - new Date(System.currentTimeMillis()).getTime()) / 1000;
        cookie.setMaxAge((int) expired);
        cookie.setPath("/"); // 访问所有页面需要携带登录凭证
        response.addCookie(cookie);
        return new Result(Code.SAVE_OK, true, (String) map.get("msg"));
    }

    @Operation(summary = "退出登录，重定向到登录页面")
    @PutMapping("/logout")
    public Result logout(HttpServletResponse response) {
        loginTicketService.updateStatus(hostHolder.getUser().getStuId(), 0);
        try {
            response.sendRedirect("/login");
        } catch (Exception e) {
            logger.error("注销登录后重定向错误-->" + e.getMessage());
        }
        return new Result(Code.DELETE_OK, null);
    }

    @Operation(summary = "注册新用户")
    @Parameter(name = "code", description = "用户输入验证码")
    @PostMapping("/register")
    public Result register(User user, String code, HttpSession session){
        if (!userService.checkCode(code, session)){
            return new Result(Code.GET_ERR, false, "验证码错误");
        }
        boolean flag = userService.add(user);
        return new Result(flag? Code.SAVE_OK: Code.SAVE_ERR, flag, "注册成功");
    }

    @Operation(summary = "修改用户信息：用户名、密码、头像传入非空则进行修改")
    @Parameters({@Parameter(name = "newPassword", description = "新密码"),
            @Parameter(name = "username", description = "新用户名"),
            @Parameter(name = "headerImage", description = "新头像")})
    @PutMapping
    public Result updateUser(String newPassword, String username, MultipartFile headerImage){
        User user = hostHolder.getUser();
        String stuId = user.getStuId();
        String msg = "";
        if (newPassword != null){
            userService.updatePassword(stuId, newPassword);
            msg += "密码修改成功!";
        }
        if (username != null){
            userService.updateUsername(stuId, username);
            if (msg.length() == 0) msg += "用户名修改成功！";
            else msg = "用户名、" + msg;
        }
        if (headerImage != null){
            userService.updateHeader(user.getStuId(), headerImage);
            userService.updateHeader(user.getStuId(), headerImage);
            if (msg.length() == 0) msg += "头像修改成功！";
            else msg = "头像、" + msg;
        }
        return new Result(Code.UPDATE_OK, true, msg);
    }

    @Operation(summary = "向指定学号的邮箱发送验证码")
    @GetMapping("/sendCode")
    public Result sendCode(String stuId, HttpSession session){
        boolean flag = userService.sendCode(stuId, session);
        int code = flag? Code.GET_OK: Code.GET_ERR;
        String msg = flag? "验证码已发送": "请检查您的学号后重试";
        return new Result(code, flag, msg);
    }

    @Operation(summary = "发送图片验证码")
    @GetMapping("/sendImageCode")
    public void getKaptcha(HttpSession session, HttpServletResponse response){
        // 生成图片验证码
        String text = defaultKaptcha.createText();
        // 将验证码存储到session中
        session.setAttribute("imageCode", text);
        // 生成验证码图片
        BufferedImage image = defaultKaptcha.createImage(text);
        // 设置响应体格式
        response.setContentType("image/png");
        // 将验证码图片写入响应中
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            logger.error("向响应中写入图片验证码失败");
            throw new RuntimeException(e);
        }
    }

    // ########################################## 以下为管理员权限才可以调用的接口
    @Operation(summary = "获取指定学号的用户信息")
    @GetMapping("/{stuId}")
    public Result getUser(@PathVariable String stuId){
        Map<String, String> map = userService.getUsernameAndCreateTimeByStuId(stuId);
        return new Result(map != null? Code.GET_OK: Code.GET_ERR, map);
    }

    @Operation(summary = "删除指定学号的用户")
    @DeleteMapping("/{stuId}")
    public Result delete(@PathVariable String stuId){
        boolean flag = userService.deleteByStuId(stuId);
        return new Result(flag? Code.SAVE_OK: Code.SAVE_ERR, flag);
    }
}
