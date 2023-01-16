package site.zhangerfa.controller;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Hole;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.service.HoleService;
import site.zhangerfa.util.CardUtil;
import site.zhangerfa.util.Constant;
import site.zhangerfa.util.HostHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/holes")
public class HoleController {
    @Resource
    private HoleService holeService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private CommentService commentService;
    @Resource
    private CardUtil cardUtil;

    @PostMapping
    @ResponseBody
    public Result addHole(@RequestBody Hole hole){
        hole.setPosterId(hostHolder.getUser().getStuId());
        holeService.addHole(hole);
        return new Result(Code.SAVE_OK, null, "发布成功");
    }

    @DeleteMapping("/delete/{holeId}")
    @ResponseBody
    public Result delete(@PathVariable int holeId){
        holeService.deleteHoleById(holeId);
        return new Result(Code.DELETE_OK, true, "删除成功");
    }

    @RequestMapping("/details/{holeId}")
    public String details(Model model, Page page, @PathVariable int holeId){
        // 用户信息
        User user = hostHolder.getUser();
        model.addAttribute("user", user);
        // 树洞信息
        Hole hole = holeService.getHoleById(holeId);
        ArrayList<Hole> holes = new ArrayList<>();
        holes.add(hole);
        hole = cardUtil.completeHoles(holes).get(0);
        model.addAttribute("hole", hole);
        // 分页信息
        page.setRows(hole.getCommentNum());
        page.setPath("/details/" + holeId);
        // 评论集合
        List<Comment> comments = commentService.getCommentsForEntity(Constant.ENTITY_TYPE_HOLE, holeId,
                page.getOffset(), page.getLimit());
        List<Map> commentsList = cardUtil.serializeComments(hole.getId(), comments);
        model.addAttribute("comments", commentsList);
        // 评论次数（包含评论的评论）
        model.addAttribute("commentsNum", commentsList.size());

        return "site/hole-detail";
    }
}
