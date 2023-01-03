package site.zhangerfa.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.UserService;

import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    private UserService userService;

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
     * 如果正确将用户stuId存储session中
     * @param stuId
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Result login(User user, HttpSession session){
        if (!userService.checkPassword(user.getStuId(), user.getPassword())){
            return new Result(Code.GET_ERR, false, "密码错误");
        }
        // session中存储该用户的学号
        session.setAttribute("stuId", user.getStuId());
        return new Result(Code.GET_OK, true, "登录成功");
    }

    /**
     * 注销登录，发送请求后删除session，并重定向到网站首页
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public Result logout(HttpSession session,
                         HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        session.removeAttribute("stuId");
        request.getRequestDispatcher("/").forward(request, response);
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
        System.out.println(code);
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
}
