package site.zhangerfa.controller.tool;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import site.zhangerfa.service.HoleNicknameService;
import site.zhangerfa.service.HoleService;

@Controller
@RequestMapping("/hole")
public class HoleController {
    @Resource
    private HoleService holeService;


//    @PostMapping
//    public String addHole(){
//
//    }
}
