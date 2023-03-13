package site.zhangerfa.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.event.EventProducer;
import site.zhangerfa.event.EventUtil;
import site.zhangerfa.pojo.*;
import site.zhangerfa.service.CardService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.util.CardUtil;
import site.zhangerfa.util.Constant;
import site.zhangerfa.util.HostHolder;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/cards")
public class CardController {
    @Resource
    private CardService cardService;
    @Resource
    private UserService userService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private CardUtil cardUtil;
    @Resource
    private EventProducer eventProducer;
    @Resource
    private EventUtil eventUtil;

    @Operation(summary = "新增卡片")
    @PostMapping
    public Result<Boolean> addCard(@RequestBody Card card){
        String stuId = hostHolder.getUser().getStuId();
        card.setPosterId(stuId);
        boolean flag = cardService.add(card);
        return new Result<>(flag? Code.SAVE_OK: Code.SAVE_ERR, flag);
    }

    /**
     * 返回帖子详情界面
     * @param cardId
     * @return 向模板引擎发送数据 card、user、comments、commentsNum
     *         分别是卡片信息、用户信息、评论集合、评论数量
     *            其中，comments是一个list，每个值是一个map，包含一个评论的信息
     *               每个map包含，username、content、createTime、comments、commentNum
     *                  分别是谁评论的、评论了什么、什么时候评论的，评论的评论集合、评论数量
     *                  comments是该评论的评论集合，是一个list，每个值为map
     */
    @GetMapping("/details/{cardId}")
    public String getDetails(Model model, @PathVariable int cardId, Page<Card> page){
        // 登录用户的信息
        model.addAttribute("user", hostHolder.getUser());
        // 帖子信息
        Card card = cardService.getCardById(cardId);
        model.addAttribute("card", card);
        // 作者信息
        User user = userService.getUserByStuId(card.getPosterId());
        model.addAttribute("poster", user);
        // 分页信息
        page.setNumOfPosts(card.getCommentNum());
        page.setPath("/details/" + cardId);
        // 评论集合
        List<Comment> comments = cardService.getComments(cardId, page.getOffset(), page.getLimit());
        List<Map> res = cardUtil.serializeComments(cardUtil.completeComments(comments));
        model.addAttribute("comments", res);
        // 评论数量
        model.addAttribute("commentsNum", res.size());

        return "site/card-detail";
    }

    @DeleteMapping("/delete/{cardId}")
    @ResponseBody
    public Result deleteCard(@PathVariable int cardId){
        Map<String, Object> map = cardService.deleteCard(cardId);
        return new Result(Code.DELETE_OK, map.get("result"), (String) map.get("msg"));
    }

    /**
     * 为卡片增加评论,加成功后重定向到当前卡片的详情页面
     * @param comment
     * @param cardId
     * @return
     */
    @PostMapping("/comment")
    public String addComment(Comment comment, int cardId){
        // 增加评论
        cardService.addComment(comment);
        // 发布评论通知
        Notice notice = eventUtil.getNotice(comment, cardId); // 将评论数据包装为notice对象
        eventProducer.addNotice(Constant.TOPIC_COMMENT, notice); // 传入消息队列
        return "redirect:/cards/details/" + cardId;
    }

    @DeleteMapping("/comment/{commentId}")
    @ResponseBody
    public Result<Map> deleteComment(@PathVariable int commentId){
        Map<String, Object> map = cardService.deleteComment(commentId);
        int code = (boolean)map.get("result")? Code.DELETE_OK: Code.DELETE_ERR;
        return new Result(code, map.get("result"), (String)map.get("msg"));
    }
}
