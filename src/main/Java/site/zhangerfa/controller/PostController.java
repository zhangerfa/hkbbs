package site.zhangerfa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.service.CardService;

import java.util.Map;

@RestController
@Tag(name = "删除帖子和评论")
@RequestMapping("/delete")
public class PostController {
    @Resource
    private CardService postService;

    @DeleteMapping("/post/{postId}")
    @Operation(summary = "删除帖子", description = "删除卡片及卡片中所有评论")
    public Result<Boolean> delete(@PathVariable int postId){
        Map<String, Object> map = postService.deleteById(postId);
        Boolean result = (Boolean) map.get("result");
        return new Result<>(result? Code.DELETE_OK: Code.DELETE_ERR, result, (String) map.get("msg"));
    }

    @DeleteMapping("/comment/{commentId}")
    @Operation(summary = "删除评论", description = "删除评论及评论中的子评论")
    @ResponseBody
    public Result<Boolean> deleteComment(@PathVariable int commentId){
        Map<String, Object> map = postService.deleteComment(commentId);
        int code = (boolean)map.get("result")? Code.DELETE_OK: Code.DELETE_ERR;
        return new Result<>(code, (Boolean) map.get("result"), (String)map.get("msg"));
    }
}
