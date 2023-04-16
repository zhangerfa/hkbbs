package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.controller.tool.CardInfo;
import site.zhangerfa.dao.CardMapper;
import site.zhangerfa.pojo.Card;
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
    public boolean add(String posterId, Card card) {
        // 发布卡片
        cardMapper.add(posterId, card);
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
    public boolean update(Card card) {
        CardInfo oldCard = cardMapper.selectById(card.getId());
        if (card.getAboutMe() == null) card.setAboutMe(oldCard.getAboutMe());
        if (card.getGoal() == -1) card.setGoal(oldCard.getGoal());
        if (card.getExpected() == null) card.setExpected(oldCard.getExpected());
        return cardMapper.updateCard(card) > 0;
    }

    @Override
    public CardInfo getById(int id) {
        // 查询卡片基本信息
        CardInfo card = cardMapper.selectById(id);
        if (card == null) return null;
        // 补充发布者信息
        card.setPoster(userService.getUserByStuId(card.getPoster().getStuId()));
        // 补充卡片中的图片url
        card.setImageUrls(imageService.getImagesForEntity(Constant.ENTITY_TYPE_CARD, id));
        return card;
    }

    @Override
    public List<CardInfo> getOnePageCards(String posterId, Page page, int goal) {
        page.completePage(cardMapper.getTotalNums(goal, posterId));
        List<CardInfo> cards = cardMapper.selectOnePageCards(goal, posterId, page.getOffset(), page.getLimit());
        for (CardInfo card : cards) {
            card.setPoster(userService.getUserByStuId(card.getPoster().getStuId()));
        }
        return cards;
    }
}
