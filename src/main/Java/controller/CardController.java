package controller;

import controller.support.Code;
import controller.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.Card;
import service.CardService;

import java.util.List;


@RestController
@RequestMapping("/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    // 前端发送需要第几页（一页10张卡片），后端传回第几页的所有卡片
    @GetMapping("/{page}")
    public Result getOnePageCards(@PathVariable int page){
        List<Card> cards = cardService.getOnePageCards(page);
        Integer code = cards != null? Code.GET_OK: Code.GET_ERR;
        return new Result(code, cards);
    }

    @GetMapping("my/{page}")
    public Result getOnePageCardsByStuId(@PathVariable("page") int page,
                                         @SessionAttribute("stuId") String stuId){
        List<Card> cards = cardService.getOnePageCardsByStuId(stuId, page);
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
