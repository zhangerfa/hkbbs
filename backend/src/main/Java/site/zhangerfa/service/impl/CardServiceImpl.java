package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.controller.tool.CardContainStuId;
import site.zhangerfa.controller.tool.CardContainsPoster;
import site.zhangerfa.dao.CardMapper;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.service.CardService;
import site.zhangerfa.service.UserService;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    @Resource
    private CardMapper cardMapper;
    @Resource
    private UserService userService;

    @Override
    public boolean add(CardContainStuId card) {
        return cardMapper.add(card) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return cardMapper.deleteById(id) > 0;
    }

    @Override
    public boolean update(CardContainStuId card) {
        CardContainsPoster oldCard = cardMapper.selectById(card.getId());
        if (card.getAboutMe() == null) card.setAboutMe(oldCard.getAboutMe());
        if (card.getGoal() == -1) card.setGoal(oldCard.getGoal());
        if (card.getExpected() == null) card.setExpected(oldCard.getExpected());
        return cardMapper.updateCard(card) > 0;
    }

    @Override
    public CardContainsPoster getById(int id) {
        CardContainsPoster card = cardMapper.selectById(id);
        card.setPoster(userService.getUserByStuId(card.getPoster().getStuId()));
        return card;
    }

    @Override
    public List<CardContainsPoster> getOnePageCards(String posterId, Page page, int goal) {
        page.completePage(cardMapper.getTotalNums(goal, posterId));
        List<CardContainsPoster> cards = cardMapper.selectOnePageCards(goal, posterId, page.getOffset(), page.getLimit());
        for (CardContainsPoster card : cards) {
            card.setPoster(userService.getUserByStuId(card.getPoster().getStuId()));
        }
        return cards;
    }
}
