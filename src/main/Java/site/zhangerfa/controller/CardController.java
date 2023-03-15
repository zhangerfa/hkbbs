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
import site.zhangerfa.service.UserService;
import site.zhangerfa.util.PostUtil;
import site.zhangerfa.util.Constant;
import site.zhangerfa.util.HostHolder;

import java.util.List;
import java.util.Map;


@RestController
@Tag(name = "卡片")
@RequestMapping("/cards")
public class CardController {
    @Resource
    private CardService cardService;
    @Resource
    private UserService userService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private PostUtil postUtil;
    @Resource
    private EventProducer eventProducer;
    @Resource
    private EventUtil eventUtil;

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
    public Result<PostDetails<Card>> getDetails(@PathVariable @Parameter(description = "帖子id") int cardId,
                                                @Parameter(hidden = true) Page page){
        PostDetails<Card> cardDetails = new PostDetails<>();
        // 帖子信息
        Card card = cardService.getCardById(cardId);
        if (card == null) return new Result<>(Code.GET_ERR, null, "您访问的帖子已被删除");
        cardDetails.setPost(card);
        // 作者信息
        User poster = userService.getUserByStuId(card.getPosterId());
        cardDetails.setPoster(poster);
        // 分页信息
        page.completePage(cardService.getTotalNums());
        cardDetails.setPage(page);
        // 评论集合
        List<Comment> comments = cardService.getComments(cardId, page.getOffset(), page.getLimit());
        // 获取每个评论的详细信息
        List<CommentDetails> commentsDetails = postUtil.getCommentsDetails(comments, page.getPageSize());
        cardDetails.setCommentDetails(commentsDetails);
        return new Result<>(Code.GET_OK, cardDetails, "查询成功");
    }

    @DeleteMapping("/delete/{cardId}")
    @Operation(summary = "删除卡片", description = "删除卡片及卡片中所有评论")
    @ResponseBody
    public Result<Boolean> deleteCard(@PathVariable int cardId){
        Map<String, Object> map = cardService.deleteById(cardId);
        return new Result<>(Code.DELETE_OK, (Boolean) map.get("result"), (String) map.get("msg"));
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
        cardService.addComment(comment);
        // 发布评论通知
        Notice notice = eventUtil.getNotice(comment, cardId); // 将评论数据包装为notice对象
        eventProducer.addNotice(Constant.TOPIC_COMMENT, notice); // 传入消息队列
        return new Result<>(Code.SAVE_OK, true, "发布成功");
    }

    @DeleteMapping("/comment/{commentId}")
    @Operation(summary = "删除评论", description = "删除评论及评论中的子评论")
    @ResponseBody
    public Result<Boolean> deleteComment(@PathVariable int commentId){
        Map<String, Object> map = cardService.deleteComment(commentId);
        int code = (boolean)map.get("result")? Code.DELETE_OK: Code.DELETE_ERR;
        return new Result<>(code, (Boolean) map.get("result"), (String)map.get("msg"));
    }
}
