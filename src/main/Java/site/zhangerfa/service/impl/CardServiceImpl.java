package site.zhangerfa.service.impl;

import org.springframework.web.util.HtmlUtils;
import site.zhangerfa.dao.CardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.service.CardService;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
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
}
