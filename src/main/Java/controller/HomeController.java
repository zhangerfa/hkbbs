package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// 实现访问首页时资源跳转
@Controller
public class HomeController {
    @RequestMapping
    public String homeForward(){
        return "forward:/html/wall.html";
    }
}
