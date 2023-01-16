package site.zhangerfa.controller;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.util.Constant;
import site.zhangerfa.util.HostHolder;

import java.util.Map;

@Controller
@RequestMapping("/comments")
public class CommentController {
    @Resource
    private HostHolder hostHolder;

    @Resource
    private CommentService commentService;

    /**
     * 添加评论，添加成功后重定向到当前卡片的详情页面
     * @param comment
     * @return
     */
    @PostMapping
    public String add(Comment comment, int cardId){
        comment.setStuId(hostHolder.getUser().getStuId());

        boolean flag = commentService.addComment(comment);
        if (comment.getEntityType() == Constant.ENTITY_TYPE_CARD){
            return "redirect:/cards/details/" + cardId;
        }
        return "redirect:/holes/details/" + cardId;
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/delete/{commentId}")
    @ResponseBody
    public Result delete(@PathVariable int commentId){
        String stuId = hostHolder.getUser().getStuId();
        Map<String, Object> map = commentService.deleteComment(commentId, stuId);
        return new Result(Code.DELETE_OK, map.get("result"), (String) map.get("msg"));
    }
}
