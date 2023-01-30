package site.zhangerfa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LoginController {
    /**
     * 登录页面
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "site/login";
    }

    /**
     * 注册页面
     * @return
     */
    @RequestMapping("/register")
    public String register(){
        return "site/register";
    }

    /**
     * 访问域名时资源跳转
     * @return
     */
    @RequestMapping("/")
    public String homeRedirect(){
        return "forward:/wall";
    }
}
