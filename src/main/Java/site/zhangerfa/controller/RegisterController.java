package site.zhangerfa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @RequestMapping
    public String register(){
        return "site/register.html";
    }
}
