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
import site.zhangerfa.service.CardService;
import site.zhangerfa.util.Constant;
import site.zhangerfa.util.HostHolder;
import site.zhangerfa.util.PostUtil;


@RestController
@Tag(name = "卡片：发布、评论、获取详情")
@RequestMapping("/cards")
public class CardController{
    @Resource
    private CardService cardService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private EventProducer eventProducer;
    @Resource
    private EventUtil eventUtil;
    @Resource
    private PostUtil postUtil;

    @Operation(summary = "新增卡片")
    @Parameters({
            @Parameter(name = "title", description = "标题", required = true),
            @Parameter(name = "content", description = "内容", required = true)})
    @PostMapping
    public Result<Boolean> addCard(@RequestBody @Parameter(hidden = true) Card card){
        if (hostHolder.getUser() == null) return new Result<>(Code.SAVE_ERR, false, "用户未登录");
        String stuId = hostHolder.getUser().getStuId();
        card.setPosterId(stuId);
        boolean flag = cardService.add(card);
        return new Result<>(flag? Code.SAVE_OK: Code.SAVE_ERR, flag);
    }

    @Operation(summary = "帖子详情", description = "返回帖子详细数据，包括帖子内容，发布者信息，评论信息，评论的分页信息")
    @Parameters({
            @Parameter(name = "currentPage", description = "当前页码", required = true),
            @Parameter(name = "pageSize", description = "当前页要展示的卡片数量", required = true),
    })
    @GetMapping("/details/{cardId}")
    public Result<PostDetails<Post>> getDetails(@PathVariable @Parameter(description = "帖子id") int cardId,
                                                @Parameter(hidden = true) Page page){
        Result<PostDetails<Post>> result = postUtil.getPostAndPosterDetails(cardId, page, Constant.ENTITY_TYPE_CARD);
        if (result.getCode() == Code.GET_ERR) return result;
        // 分页信息
        page.completePage(cardService.getTotalNums());
        result.getData().setPage(page);
        return result;
    }

    @Operation(summary = "为卡片增加评论", description = "加成功后重定向到当前卡片的详情页面")
    @PostMapping("/comment")
    @Parameters({
            @Parameter(name = "entityType", description = "被评论实体的类型", required = true),
            @Parameter(name = "entityId", description = "被评论实体的id", required = true),
            @Parameter(name = "content", description = "评论内容", required = true),
            @Parameter(name = "cardId", description = "被评论实体所属帖子的id", required = true)
    })
    public Result<Boolean> addComment(@Parameter(hidden = true) Comment comment, int cardId){
        // 增加评论
        cardService.addComment(comment, cardId);
        // 发布评论通知
        Notice notice = eventUtil.getNotice(comment, cardId); // 将评论数据包装为notice对象
        eventProducer.addNotice(Constant.TOPIC_COMMENT, notice); // 传入消息队列
        return new Result<>(Code.SAVE_OK, true, "发布成功");
    }
}
