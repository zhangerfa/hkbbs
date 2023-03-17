package site.zhangerfa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.event.EventProducer;
import site.zhangerfa.event.EventUtil;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Hole;
import site.zhangerfa.pojo.Notice;
import site.zhangerfa.service.PostService;
import site.zhangerfa.service.impl.HoleServiceImpl;
import site.zhangerfa.util.Constant;
import site.zhangerfa.util.HostHolder;

import java.util.Map;


@RestController
@Tag(name = "树洞")
@RequestMapping("/holes")
public class HoleController{
    @Resource(type = HoleServiceImpl.class)
    private PostService holeService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private EventProducer eventProducer;
    @Resource
    private EventUtil eventUtil;

    @PostMapping
    @Operation(summary = "发布树洞")
    @Parameters({
            @Parameter(name = "title", description = "标题", required = true),
            @Parameter(name = "content", description = "内容", required = true)})
    public Result<Boolean> addHole(@RequestBody @Parameter(hidden = true) Hole hole){
        if (hostHolder.getUser() == null) return new Result<>(Code.SAVE_ERR, false, "用户未登录");
        hole.setPosterId(hostHolder.getUser().getStuId());
        holeService.add(hole, Constant.ENTITY_TYPE_HOLE);
        return new Result<>(Code.SAVE_OK, true, "发布成功");
    }

    @Operation(summary = "发布评论（包括对评论评论）", description = "需要传入被评论实体的类型和id，以及被评论实体所属的帖子id")
    @PostMapping("/comment")
    @Parameters({
            @Parameter(name = "entityType", description = "被评论实体的类型", required = true),
            @Parameter(name = "entityId", description = "被评论实体的id", required = true),
            @Parameter(name = "content", description = "评论内容", required = true),
            @Parameter(name = "cardId", description = "被评论实体所属帖子的id", required = true)
    })
    public Result<Boolean> addComment(@Parameter(hidden = true) Comment comment, int cardId){
        // 增加评论
        holeService.addComment(comment, cardId);
        // 发布评论通知
        Notice notice = eventUtil.getNotice(comment, cardId); // 将评论数据包装为notice对象
        eventProducer.addNotice(Constant.TOPIC_COMMENT, notice); // 传入消息队列
        return new Result<>(Code.SAVE_OK, true, "发布成功");
    }

    @DeleteMapping("/delete/post/{postId}")
    @Operation(summary = "删除树洞", description = "删除卡片及卡片中所有评论")
    public Result<Boolean> delete(@PathVariable int postId){
        Map<String, Object> map = holeService.deleteById(postId);
        Boolean result = (Boolean) map.get("result");
        return new Result<>(result? Code.DELETE_OK: Code.DELETE_ERR, result, (String) map.get("msg"));
    }
}
