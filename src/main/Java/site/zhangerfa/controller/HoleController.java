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
import site.zhangerfa.pojo.*;
import site.zhangerfa.service.HoleService;
import site.zhangerfa.util.Constant;
import site.zhangerfa.util.HostHolder;
import site.zhangerfa.util.PostUtil;

@RestController
@Tag(name = "树洞：发布、评论、获取详情")
@RequestMapping("/holes")
public class HoleController{
    @Resource
    private HoleService holeService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private EventProducer eventProducer;
    @Resource
    private EventUtil eventUtil;
    @Resource
    private PostUtil postUtil;

    @PostMapping
    @Operation(summary = "发布树洞")
    @Parameters({
            @Parameter(name = "title", description = "标题", required = true),
            @Parameter(name = "content", description = "内容", required = true)})
    public Result<Boolean> addHole(@RequestBody @Parameter(hidden = true) Hole hole){
        hole.setPosterId(hostHolder.getUser().getStuId());
        holeService.add(hole);
        return new Result<>(Code.SAVE_OK, true, "发布成功");
    }

    @GetMapping("/details/{holeId}")
    @Parameters({
            @Parameter(name = "currentPage", description = "当前页码", required = true),
            @Parameter(name = "pageSize", description = "当前页要展示的卡片数量", required = true),
    })
    @Operation(summary = "树洞详情", description = "返回树洞详细数据，包括树洞内容，发布者信息，评论信息，评论的分页信息")
    public Result<PostDetails<Post>> getDetails(@Parameter(hidden = true) Page page, @PathVariable int holeId){
        Result<PostDetails<Post>> result = postUtil.getPostAndPosterDetails(holeId, page, Constant.ENTITY_TYPE_CARD);
        if (result.getCode() == Code.GET_ERR) return result;
        // 分页信息
        page.completePage(holeService.getTotalNums());
        result.getData().setPage(page);
        return result;
    }

    @PostMapping("/comment")
    @Operation(summary = "评论树洞")
    @Parameters({
            @Parameter(name = "entityType", description = "被评论实体的类型", required = true),
            @Parameter(name = "entityId", description = "被评论实体的id", required = true),
            @Parameter(name = "content", description = "评论内容", required = true),
            @Parameter(name = "holeId", description = "被评论实体所属树洞的id", required = true)
    })
    public Result<Boolean> addComment(@Parameter(hidden = true) Comment comment, int holeId){
        // 发布评论
        holeService.addComment(comment, holeId);
        // 发布通知
        Notice notice = eventUtil.getNotice(comment, holeId); // 将评论数据包装为notice对象
        eventProducer.addNotice(Constant.TOPIC_COMMENT, notice); // 传入消息队列
        return new Result<>(Code.SAVE_OK, true, "评论成功");
    }
}
