package site.zhangerfa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.service.CardService;
import site.zhangerfa.service.UserService;

import java.util.List;

@Controller
public class WallController {
    @Autowired
    private CardService cardService;

    @Autowired
    private UserService userService;

    /**
     * 跳转到我的主页
     */
    @RequestMapping("/my")
    public String my(@SessionAttribute("stuId") String stuId, Model model){
        String username = userService.getUsernameByStuId(stuId);
        model.addAttribute("username", username);
        List<Card> cards = cardService.getOnePageCards(stuId, 1);// 获取一页卡片
        model.addAttribute("cards", cards);
        return "site/myWall.html";
    }

    /**
     * 访问卡片墙
     * @param stuId
     * @param model
     * @return
     */
    @GetMapping("/wall")
    public String getIndexPage(@SessionAttribute("stuId") String stuId, Model model){
        String username = userService.getUsernameByStuId(stuId);
        model.addAttribute("username", username);
        List<Card> cards = cardService.getOnePageCards("0", 1);// 获取一页卡片
        model.addAttribute("cards", cards);
        return "site/wall.html";
    }
}
