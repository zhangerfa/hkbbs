package site.zhangerfa.service.impl;

import site.zhangerfa.dao.CardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.service.CardService;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    static final int NumOfPageOnPage = 10; // 一页卡片的数量
    @Autowired
    private CardMapper cardMapper;

    @Override
    public List<Card> getOnePageCards(String stuId, int page) {
        int offset = (page - 1) * NumOfPageOnPage;
        int limit = offset + NumOfPageOnPage;
        return cardMapper.selectOnePageCards(stuId, offset, limit);
    }

    @Override
    public boolean add(Card card) {
        int addNum = cardMapper.addCard(card);
        System.out.println(card);
        return addNum != 0;
    }
}
