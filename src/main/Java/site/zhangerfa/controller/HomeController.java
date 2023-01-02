package site.zhangerfa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// 实现访问首页时资源跳转
@Controller
public class HomeController {
    @RequestMapping("/")
    public String homeRedirect(){
        System.out.println(111);
        return "redirect:/wall";
    }
}
