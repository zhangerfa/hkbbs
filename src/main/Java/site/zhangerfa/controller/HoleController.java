package site.zhangerfa.controller;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.pojo.Hole;
import site.zhangerfa.service.HoleService;
import site.zhangerfa.util.HostHolder;

@Controller
@RequestMapping("/holes")
public class HoleController {
    @Resource
    private HoleService holeService;
    @Resource
    private HostHolder hostHolder;

    @PostMapping
    @ResponseBody
    public Result addHole(Hole hole){
        hole.setPosterId(hostHolder.getUser().getStuId());
        holeService.addHole(hole);
        return new Result(Code.SAVE_OK, null, "发布成功");
    }

    @RequestMapping("/details")
    @ResponseBody
    public String details(){
        return "";
    }
}
