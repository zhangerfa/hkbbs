package site.zhangerfa.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.pojo.LoginTicket;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.LoginTicketService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.util.HostHolder;
import site.zhangerfa.util.ImgShackUtil;
import site.zhangerfa.util.UserUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@Tag(name = "用户")
@RequestMapping("/users")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;
    @Resource
    private LoginTicketService loginTicketService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private ImgShackUtil imgShackUtil;


    @Operation(summary = "判断用户是否注册")
    @GetMapping ("/isExist")
    @Parameter(name = "stuId", description = "用户学号")
    public Result<Boolean> isExist(String stuId) {
        boolean flag = userService.isExist(stuId);
        return new Result<>(flag ? Code.GET_OK : Code.GET_ERR, flag, flag? "已注册": "未注册");
    }

    @Operation(summary = "登录", description = "检查用户输入的密码是否正确，如果正确生成登录凭证以cookie返回给用户")
    @Parameters({
            @Parameter(name = "rememberMe", description = "用户是否勾选记住密码"),
            @Parameter(name = "stuId", description = "学号", required = true),
            @Parameter(name = "password", description = "用户输入密码", required = true)})
    @PostMapping("/login")
    public Result<Boolean> login(@Parameter(hidden = true) User user,
                                 @RequestParam(required = false, defaultValue = "false") boolean rememberMe,
                                 HttpServletResponse response){
        Map<String, Object> map = userService.login(user, rememberMe);
        if (!(boolean) map.get("result")){
            return new Result<>(Code.SAVE_ERR, false, (String) map.get("msg"));
        }
        // 登录凭证作为cookie凭证发送给客户端
        LoginTicket ticket = (LoginTicket) map.get("ticket");
        Cookie cookie = new Cookie("ticket", ticket.getTicket());
        // 计算登录凭证有效时间（秒）
        long expired = (ticket.getExpired().getTime() - new Date(System.currentTimeMillis()).getTime()) / 1000;
        cookie.setMaxAge((int) expired);
        cookie.setPath("/"); // 访问所有页面需要携带登录凭证
        response.addCookie(cookie);
        hostHolder.setUser(user);
        return new Result<>(Code.SAVE_OK, true, (String) map.get("msg"));
    }

    @Operation(summary = "退出登录", description = "将用户登录凭证的有效状态设置为不可用，并重定向到首页")
    @PutMapping("/logout")
    public Result<Boolean> logout(HttpServletResponse response) {
        User user = hostHolder.getUser();
        if (user == null) return new Result<>(Code.UPDATE_ERR, false, "用户未登录");
        loginTicketService.updateStatus(user.getStuId(), 0);
        try {
            response.sendRedirect("/");
        } catch (Exception e) {
            logger.error("注销登录后重定向错误-->" + e.getMessage());
        }
        return new Result<>(Code.DELETE_OK, true);
    }

    @Operation(summary = "注册新用户")
    @Parameters({
            @Parameter(name = "code", description = "用户输入验证码"),
            @Parameter(name = "stuId", description = "学号", required = true,
                    schema = @Schema(pattern = "[UMD][0-9]{9}")),
            @Parameter(name = "username", description = "用户名", required = true),
            @Parameter(name = "password", description = "密码", required = true,
                    schema = @Schema(pattern = "[a-zA-Z0-9]{6,16}")),
            @Parameter(name = "gender", description = "性别：0-男，1-女", required = true)})
    @PostMapping("/register")
    public Result<Boolean> register(@Parameter(hidden = true) User user, String code, HttpSession session){
        // 判断学号是否合法
        if (!UserUtil.isStuIdValid(user.getStuId()))
            return new Result<>(Code.SAVE_ERR, "学号错误");
        // 判断用户是否已注册
        if (isExist(user.getStuId()).getData())
            return new Result<>(Code.SAVE_ERR, "该学号已注册");
        // 判断密码是否符合格式
        if (!UserUtil.isMatchRegex("[a-zA-Z0-9]{6,16}", user.getPassword()))
            return new Result<>(Code.SAVE_ERR, "密码长度为6~16位，只能包含字母和数字");
        // 判断验证码是否正确
        if (!userService.checkCode(code, session)){
            return new Result<>(Code.SAVE_ERR, false, "验证码错误");
        }
        // 添加用户
        boolean flag = userService.add(user);
        return new Result<>(flag? Code.SAVE_OK: Code.SAVE_ERR, flag, "注册成功");
    }

    @Operation(summary = "修改用户信息", description = "用户名、密码、头像传入非空则进行修改")
    @PutMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Parameters({@Parameter(name = "newPassword", description = "新密码", schema = @Schema(pattern = "[a-zA-Z0-9]{6,16}")),
            @Parameter(name = "username",description = "新用户名")})
    public Result<Boolean> updateUser(@RequestParam(required = false) String newPassword,
                                      @RequestParam(required = false) String username,
                                      @Parameter(description = "新头像") @RequestPart(required = false)
                                          MultipartFile headerImage){
        User user = hostHolder.getUser();
        if (user == null) return new Result<>(Code.UPDATE_ERR, false, "用户未登录");
        String stuId = user.getStuId();
        user.setUsername(username);
        user.setPassword(newPassword);
        if (headerImage != null){
            // 头像文件上传到图床中
            // 文件命名为 学号_header
            String imageName = stuId + "_header";
            user.setHeaderUrl(imgShackUtil.add(headerImage, imageName));
        }
        return new Result<>(Code.UPDATE_OK, true);
    }

    @Operation(summary = "向指定学号的邮箱发送验证码")
    @GetMapping("/sendCode")
    public Result<Boolean> sendCode(String stuId, HttpSession session){
        // 验证学号是否合法
        if (!UserUtil.isStuIdValid(stuId))
            return new Result<>(Code.GET_ERR, "学号错误");
        // 判断用户是否已注册
        if (isExist(stuId).getData())
            return new Result<>(Code.GET_ERR, "学号已注册");
        // 发送验证码
        boolean flag = userService.sendCode(stuId, session);
        int code = flag? Code.GET_OK: Code.GET_ERR;
        String msg = flag? "验证码已发送": "请检查您的学号后重试";
        return new Result<>(code, flag, msg);
    }

//    @Operation(summary = "发送图片验证码")
//    @GetMapping("/sendImageCode")
//    public void getKaptcha(HttpSession session, HttpServletResponse response){
//        // 生成图片验证码
//        String text = defaultKaptcha.createText();
//        // 将验证码存储到session中
//        session.setAttribute("imageCode", text);
//        // 生成验证码图片
//        BufferedImage image = defaultKaptcha.createImage(text);
//        // 设置响应体格式
//        response.setContentType("image/png");
//        // 将验证码图片写入响应中
//        try {
//            ServletOutputStream outputStream = response.getOutputStream();
//            ImageIO.write(image, "png", outputStream);
//        } catch (IOException e) {
//            logger.error("向响应中写入图片验证码失败");
//            throw new RuntimeException(e);
//        }
//    }

    @Operation(summary = "获取用户信息", description = "获取指定用户信息，当传入用户学号为‘0’时表示获取当前用户的信息")
    @GetMapping("/{stuId}")
    public Result<User> getUser(@PathVariable String stuId){
        if (stuId.equals("0")) {
            // 返回当前登录用户的信息
            if (hostHolder.getUser() != null) return new Result<>(Code.GET_OK, hostHolder.getUser());
            else return new Result<>(Code.GET_ERR, "用户未登录");
        }
        // 查询指定用户的信息
        User user = userService.getUserByStuId(stuId);
        String msg = user != null? "查询成功": "用户不存在";
        return new Result<>(user != null? Code.GET_OK: Code.GET_ERR, user, msg);
    }

    // ########################################## 以下为管理员权限才可以调用的接口

    /**
     * 先删除账号所有发帖，登录凭证等以学号为外键的内容
     * @param stuId
     * @return
     */
    @Hidden
    @Operation(summary = "删除指定学号的用户")
    @DeleteMapping("/{stuId}")
    public Result<Boolean> delete(@PathVariable String stuId){
        if (!isExist(stuId).getData()) return new Result<>(Code.DELETE_ERR, false, "用户不存在");
        boolean flag = userService.deleteByStuId(stuId);
        return new Result<>(flag? Code.DELETE_OK: Code.SAVE_ERR, flag);
    }
}
