package site.zhangerfa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @RequestMapping
    public String register(){
        System.out.println("111");
        return "site/register.html";
    }
}
