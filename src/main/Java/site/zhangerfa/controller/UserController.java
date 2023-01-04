package site.zhangerfa.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.dao.LoginTicketMapper;
import site.zhangerfa.pojo.LoginTicket;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.UserService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/users")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private DefaultKaptcha defaultKaptcha;

    @Resource
    private UserService userService;

    @Resource
    private LoginTicketMapper loginTicketMapper;

    /**
     * 判断用户是否已经注册
     * @param stuId
     * @return
     */
    @RequestMapping("/isExist")
    public Result isExist(String stuId) {
        boolean flag = userService.isExist(stuId);
        return  new Result(flag? Code.GET_OK: Code.GET_ERR, flag);
    }

    /**
     * 检查指定学号的用户的输入密码、验证码是否正确
     *      如果正确生成登录凭证以cookie返回给用户
     * @param rememberMe 是否勾选记住密码
     * @return
     */
    @PostMapping("/login")
    public Result login(User user, boolean rememberMe, HttpServletResponse response){
        Map<String, Object> map = userService.login(user, rememberMe);
        if (!(boolean) map.get("result")){
            return new Result(Code.SAVE_ERR, null, (String) map.get("msg"));
        }
        // 登录凭证作为cookie凭证发送给客户端
        LoginTicket ticket = (LoginTicket) map.get("ticket");
        Cookie cookie = new Cookie("ticket", ticket.getTicket());
        // 计算登录凭证有效时间（秒）
        long expired = (ticket.getExpired().getTime() - new Date(System.currentTimeMillis()).getTime()) / 1000;
        cookie.setMaxAge((int) expired);
        cookie.setPath("/"); // 访问所有页面需要携带登录凭证
        response.addCookie(cookie);
        return new Result(Code.SAVE_OK, ticket.getTicket(), (String) map.get("msg"));
    }

    /**
     * 注销登录，发送请求后将登录凭证状态修改为不可用，并重定向到登录页面
     * @param ticket 登录凭证
     * @return
     */
    @RequestMapping("/logout")
    public Result logout(String ticket, HttpServletRequest request,
                         HttpServletResponse response) {
        loginTicketMapper.updateStatus(ticket, 0);
        try {
            request.getRequestDispatcher("/login").forward(request, response);
        } catch (Exception e) {
            logger.error("注销登录后重定向错误-->" + e.getMessage());
        }
        return new Result(Code.DELETE_OK, null);
    }

    /**
     * 注册新用户
     * @param user
     * @param code 用户输入的验证码
     * @return
     */
    @PostMapping("/register")
    public Result register(User user, @RequestParam("code") String code){
        if (!userService.checkCode(user.getStuId(), code)){
            return new Result(Code.GET_ERR, false, "验证码错误");
        }
        boolean flag = userService.add(user);
        return new Result(flag? Code.SAVE_OK: Code.SAVE_ERR, flag, "注册成功");
    }

    /**
     * 修改用户信息，请求可以传入用户名和密码，传入即修改
     * @return 当用户名或密码至少一项修改成功则返回true ，否则返回 “请输入有效信息”
     */
    @PutMapping
    public Result updateUsername(@RequestBody User user){
        String stuId = user.getStuId();
        boolean flag = false;
        if (user.getPassword() != null){
            flag &= userService.updatePassword(stuId, user.getPassword());
        }
        if (user.getUsername() != null){
            flag |= userService.updateUsername(stuId, user.getUsername());
        }
        return new Result(flag? Code.UPDATE_OK: Code.UPDATE_ERR, flag);
    }

    /**
     * 获取指定学号的用户信息
     * @param stuId
     * @return 返回用户的学号、用户名、注册时间的map,输入学号有误则返回null
     */
    @GetMapping("/{stuId}")
    public Result getUser(@PathVariable String stuId){
        Map<String, String> map = userService.getUsernameAndCreateTimeByStuId(stuId);
        return new Result(map != null? Code.GET_OK: Code.GET_ERR, map);
    }

    /**
     * 删除指定学号的用户
     * @param stuId
     * @return
     */
    @DeleteMapping("/{stuId}")
    public Result delete(@PathVariable String stuId){
        boolean flag = userService.deleteByStuId(stuId);
        return new Result(flag? Code.SAVE_OK: Code.SAVE_ERR, flag);
    }

    /**
     * 向指定学号的邮箱发送验证码
     * @return 返回验证码
     */
    @RequestMapping("/sendCode")
    public Result sendCode(String stuId){
        boolean flag = userService.sendCode(stuId);
        int code = flag? Code.GET_OK: Code.GET_ERR;
        String msg = flag? "验证码已发送": "请检查您的学号后重试";
        return new Result(code, flag, msg);
    }

    /**
     * 发送随机图片验证码
     * @return
     */
    @RequestMapping("/sendImageCode")
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
}
