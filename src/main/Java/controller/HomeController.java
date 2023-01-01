package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

// 实现访问首页时资源跳转
@Controller
public class HomeController {
    @RequestMapping
    public String homeForward(){
        return "redirect:/html/wall.html";
    }
}
