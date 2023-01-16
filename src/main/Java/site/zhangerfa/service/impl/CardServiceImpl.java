package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import site.zhangerfa.dao.CardMapper;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.service.CardService;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.util.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CardServiceImpl implements CardService {
    @Resource
    private CardMapper cardMapper;

    @Override
    public List<Card> getOnePageCards(String stuId, int offset, int limit) {
        if (stuId == null){
            throw new IllegalArgumentException("未输入学号");
        }
        return cardMapper.selectOnePageCards(stuId, offset, limit);
    }

    @Override
    public int getNumOfCards() {
        return cardMapper.getNumOfCards();
    }

    @Override
    public boolean add(Card card) {
        if (card == null){
            throw new IllegalArgumentException("输入为空");
        }
        // 转义HTML标记，防止HTML注入
        card.setTitle(HtmlUtils.htmlEscape(card.getTitle()));
        card.setContent(HtmlUtils.htmlEscape(card.getContent()));
        int addNum = cardMapper.addCard(card);
        return addNum != 0;
    }

    @Override
    public Card getCardById(int id) {
        return cardMapper.selectCardById(id);
    }

    @Override
    public boolean commentNumPlusOne(int id) {
        int flag = cardMapper.commentNumPlusOne(id);
        return flag > 0;
    }

    @Override
    public boolean commentNumMinusOne(int id) {
        int flag = cardMapper.commentNumMinusOne(id);
        return flag > 0;
    }

    @Override
    public Map<String, Object> deleteCard(int id, String stuId,CommentService commentService) {
        // 权限验证 只有发帖者可以删除自己发的帖子
        Card card = cardMapper.selectCardById(id);
        if (!stuId.equals(card.getPosterId())){
            Map<String, Object> map = new HashMap<>();
            map.put("result", false);
            map.put("msg", "您没有权限删除");
            return map;
        }
        // 删除卡片的评论
        List<Comment> comments = commentService.getCommentsForEntity(Constant.ENTITY_TYPE_CARD, id, 0, Integer.MAX_VALUE);
        for (Comment comment : comments) {
            commentService.deleteComment(comment.getId(), stuId);
        }
        // 删除卡片
        cardMapper.deleteCardById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        map.put("msg", "删除成功");
        return map;
    }
}
