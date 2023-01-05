package site.zhangerfa.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.pojo.LoginTicket;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.service.CardService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.util.CardUtil;
import site.zhangerfa.util.HostHolder;

import java.util.List;
import java.util.Map;


/**
 * 用于响应异步请求卡片数据的控制器
 */
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

    @GetMapping
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
    public Result getOnePageCardsByStuId(Page page,
                                         @CookieValue("ticket") String ticket){
        String stuId = userService.getStuIdByTicket(ticket);
        List<Card> cards = cardService.getOnePageCards(stuId,
                page.getOffset(), page.getLimit());
        Integer code = cards != null? Code.GET_OK: Code.GET_ERR;
        return new Result(code, cards);
    }

    // 新增一个卡片
    @PostMapping
    public Result addCard(@RequestBody Card card){
        String stuId = hostHolder.getUser().getStuId();
        card.setPosterId(stuId);
        boolean flag = cardService.add(card);
        return new Result(flag? Code.SAVE_OK: Code.SAVE_ERR, flag);
    }
}
