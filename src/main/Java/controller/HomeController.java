package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// 实现访问首页时资源跳转
@Controller
public class HomeController {
    @RequestMapping
    public String go(){
        return "forward:/html/login.html";
    }
}
