package site.zhangerfa.util;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CardUtil {
    @Resource
    private UserService userService;

    // 补全卡片信息
    public List<Map> completeCard(List<Card> cards){
        List<Map> res = new ArrayList<>();
        for (Card card : cards) {
            String username = userService.getUsernameByStuId(card.getPosterId());
            Map<String, Object> map = new HashMap<>();
            map.put("card", card);
            map.put("username", username);
            res.add(map);
        }
        return res;
    }
}
