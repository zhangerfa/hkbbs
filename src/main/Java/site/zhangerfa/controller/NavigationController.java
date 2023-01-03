package site.zhangerfa.controller;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.service.CardService;
import site.zhangerfa.service.UserService;

import java.util.List;

/**
 * 导航栏跳转控制器
 */
@Controller
public class NavigationController {
    @Resource
    private CardService cardService;

    @Resource
    private UserService userService;

    /**
     * 跳转到我的主页
     */
    @RequestMapping("/my")
    public String my(@SessionAttribute("stuId") String stuId, Page page,
                     Model model){
        // page对象被自动注入到model对象中
        page.setRows(cardService.getNumOfCards());
        page.setPath("/my");

        String username = userService.getUsernameByStuId(stuId);
        model.addAttribute("username", username);
        List<Card> cards = cardService.getOnePageCards(stuId,
                page.getOffset(), page.getLimit());// 获取一页卡片
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
    public String wall(@SessionAttribute("stuId") String stuId,
                       Page page, Model model){
        page.setRows(cardService.getNumOfCards());
        page.setPath("/wall");

        String username = userService.getUsernameByStuId(stuId);
        model.addAttribute("username", username);
        List<Card> cards = cardService.getOnePageCards("0",
                page.getOffset(), page.getLimit());// 获取一页卡片
        model.addAttribute("cards", cards);
        return "site/wall.html";
    }

    /**
     * 访问树洞
     */
    @RequestMapping("/hole")
    public String hole(){
        return "site/hole.html";
    }

    /**
     * 访问域名时资源跳转
     * @return
     */
    @RequestMapping("/")
    public String homeRedirect(){
        return "forward:/wall";
    }

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "site/login.html";
    }

    /**
     * 跳转到注册页面
     * @return
     */
    @RequestMapping("/register")
    public String register(){
        return "site/register.html";
    }

}
