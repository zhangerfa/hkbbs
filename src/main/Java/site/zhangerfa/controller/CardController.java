package site.zhangerfa.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.service.CardService;

import java.util.List;


/**
 * 用于响应异步请求卡片数据的控制器
 */
@RestController
@RequestMapping("/cards")
public class CardController {
    @Resource
    private CardService cardService;

    @GetMapping
    public Result getOnePageCards(Page page){
        List<Card> cards = cardService.getOnePageCards("0", page.getOffset(),
                page.getLimit());
        Integer code = cards != null? Code.GET_OK: Code.GET_ERR;
        return new Result(code, cards);
    }

    @GetMapping("/my")
    public Result getOnePageCardsByStuId(Page page,
                                         @SessionAttribute("stuId") String stuId){
        List<Card> cards = cardService.getOnePageCards(stuId,
                page.getOffset(), page.getLimit());
        Integer code = cards != null? Code.GET_OK: Code.GET_ERR;
        return new Result(code, cards);
    }

    // 新增一个卡片
    @PostMapping
    public Result addCard(@RequestBody Card card,
                          @SessionAttribute("stuId") String stuId){
        card.setPosterId(stuId);
        boolean flag = cardService.add(card);
        return new Result(flag? Code.SAVE_OK: Code.SAVE_ERR, flag);
    }
}
