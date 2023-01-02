package site.zhangerfa.controller;

import jakarta.annotation.Resource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.service.CardService;

import java.util.List;


@RestController
@RequestMapping("/cards")
public class CardController {
    @Resource
    private CardService cardService;

    @GetMapping("/{page}")
    public Result getOnePageCards(@PathVariable int page){
        List<Card> cards = cardService.getOnePageCards("0", page);
        Integer code = cards != null? Code.GET_OK: Code.GET_ERR;
        return new Result(code, cards);
    }

    @GetMapping("my/{page}")
    public Result getOnePageCardsByStuId(@PathVariable("page") int page,
                                         @SessionAttribute("stuId") String stuId){
        List<Card> cards = cardService.getOnePageCards(stuId, page);
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
