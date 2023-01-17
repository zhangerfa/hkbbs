package site.zhangerfa.controller;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.CardService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.util.CardUtil;
import site.zhangerfa.util.HostHolder;

import java.util.List;
import java.util.Map;


@Controller
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

    @GetMapping
    @ResponseBody
    public Result getOnePageCards(Page page){
        List<Card> cards = cardService.getOnePageCards("0", page.getOffset(),
                page.getLimit());
        List<Map> res;
        Integer code;
        if (cards == null){
            res = null;
            code = Code.GET_ERR;
        }else {
            res = cardUtil.completeCard(cards);
            code = Code.GET_OK;
        }
        return new Result(code, res);
    }

    @GetMapping("/my")
    @ResponseBody
    public Result getOnePageCardsByStuId(Page page,
                                         @CookieValue("ticket") String ticket) throws IllegalAccessException {
        String stuId = hostHolder.getUser().getStuId();
        List<Card> cards = cardService.getOnePageCards(stuId,
                page.getOffset(), page.getLimit());
        Integer code = cards != null? Code.GET_OK: Code.GET_ERR;
        return new Result(code, cards);
    }

    // 新增一个卡片
    @PostMapping
    @ResponseBody
    public Result addCard(@RequestBody Card card){
        String stuId = hostHolder.getUser().getStuId();
        card.setPosterId(stuId);
        boolean flag = cardService.add(card);
        return new Result(flag? Code.SAVE_OK: Code.SAVE_ERR, flag);
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
    public String getDetails(@PathVariable int cardId, Model model, Page page){
        // 登录用户的信息
        model.addAttribute("user", hostHolder.getUser());
        // 帖子信息
        Card card = cardService.getCardById(cardId);
        model.addAttribute("card", card);
        // 作者信息
        User user = userService.getUserByStuId(card.getPosterId());
        model.addAttribute("poster", user);
        // 分页信息
        page.setRows(card.getCommentNum());
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
        cardService.addComment(comment);
        return "redirect:/cards/details/" + cardId;
    }

    @DeleteMapping("/comment/{commentId}")
    @ResponseBody
    public Result deleteComment(@PathVariable int commentId){
        Map<String, Object> map = cardService.deleteComment(commentId);
        int code = (boolean)map.get("result")? Code.DELETE_OK: Code.DELETE_ERR;
        return new Result(code, map.get("result"), (String)map.get("msg"));
    }
}
