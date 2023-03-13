package site.zhangerfa.controller;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.event.EventProducer;
import site.zhangerfa.event.EventUtil;
import site.zhangerfa.pojo.*;
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
    private CardUtil cardUtil;
    @Resource
    private EventProducer eventProducer;
    @Resource
    private EventUtil eventUtil;

    @PostMapping
    @ResponseBody
    public Result addHole(@RequestBody Hole hole){
        hole.setPosterId(hostHolder.getUser().getStuId());
        holeService.add(hole);
        return new Result(Code.SAVE_OK, null, "发布成功");
    }

    @DeleteMapping("/delete/{holeId}")
    @ResponseBody
    public Result deleteHole(@PathVariable int holeId){
        holeService.deleteById(holeId);
        return new Result(Code.DELETE_OK, true, "删除成功");
    }

    @RequestMapping("/details/{holeId}")
    public String getDetails(Model model, Page page, @PathVariable int holeId){
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
        page.setNumOfPosts(hole.getCommentNum());
        page.setPath("/details/" + holeId);
        // 评论集合
        List<Comment> comments = holeService.getComments(holeId, page.getOffset(), page.getLimit());
        List<Map> commentsList = cardUtil.serializeComments(hole.getId(), comments);
        model.addAttribute("comments", commentsList);
        // 评论次数（包含评论的评论）
        model.addAttribute("commentsNum", commentsList.size());

        return "site/hole-detail";
    }

    /**
     * 为树洞增加评论,加成功后重定向到当前卡片的详情页面
     * @param comment
     * @param holeId
     * @return
     */
    @PostMapping("/comment")
    public String addComment(Comment comment, int holeId){
        // 发布评论
        holeService.addComment(comment, holeId);
        // 发布通知
        Notice notice = eventUtil.getNotice(comment, holeId); // 将评论数据包装为notice对象
        eventProducer.addNotice(Constant.TOPIC_COMMENT, notice); // 传入消息队列
        return "redirect:/holes/details/" + holeId;
    }

    @DeleteMapping("/comment/{commentId}")
    @ResponseBody
    public Result deleteComment(@PathVariable int commentId){
        Map<String, Object> map = holeService.deleteComment(commentId);
        int code = (boolean)map.get("result")? Code.DELETE_OK: Code.DELETE_ERR;
        return new Result(code, map.get("result"), (String)map.get("msg"));
    }
}
