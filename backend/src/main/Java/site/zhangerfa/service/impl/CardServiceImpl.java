package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.Constant.Goal;
import site.zhangerfa.controller.tool.CardContainStuId;
import site.zhangerfa.controller.tool.CardContainsPoster;
import site.zhangerfa.dao.CardMapper;
import site.zhangerfa.pojo.Image;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.service.CardService;
import site.zhangerfa.service.ImageService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.Constant.Constant;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    @Resource
    private CardMapper cardMapper;
    @Resource
    private UserService userService;
    @Resource
    private ImageService imageService;

    @Override
    public boolean add(CardContainStuId card) {
        // 发布卡片
        cardMapper.add(card);
        // 将帖子中图片放入image表中
        for (String imageUrl : card.getImageUrls()) {
            imageService.add(new Image(Constant.ENTITY_TYPE_CARD, card.getId(), imageUrl));
        }
        return true;
    }

    @Override
    public boolean deleteById(int id) {
        return cardMapper.deleteById(id) > 0;
    }

    @Override
    public boolean update(CardContainStuId card) {
        CardContainsPoster oldCard = cardMapper.selectById(card.getId());
        if (card.getAboutMe() == null) card.setAboutMe(oldCard.getAboutMe());
        if (card.getGoal() == Goal.DEFAULT) card.setGoal(oldCard.getGoal());
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
