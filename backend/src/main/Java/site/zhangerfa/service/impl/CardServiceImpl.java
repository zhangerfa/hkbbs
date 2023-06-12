package site.zhangerfa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.controller.vo.CardVo;
import site.zhangerfa.controller.vo.UserVo;
import site.zhangerfa.dao.CardMapper;
import site.zhangerfa.entity.User;
import site.zhangerfa.service.ImageService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.entity.Card;
import site.zhangerfa.entity.Image;
import site.zhangerfa.service.CardService;
import site.zhangerfa.Constant.Constant;
import site.zhangerfa.util.EntityUtil;
import site.zhangerfa.util.UserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CardServiceImpl implements CardService {
    @Resource
    private CardMapper cardMapper;
    @Resource
    private UserService userService;
    @Resource
    private ImageService imageService;

    @Override
    public boolean add(Card card) {
        // 发布卡片
        cardMapper.insert(card);
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
        return cardMapper.updateById(card) > 0;
    }

    @Override
    public CardVo getById(int id) {
        // 查询卡片基本信息
        Card card = cardMapper.selectById(id);
        if (card == null) return null;
        // 发布者信息
        User poster = userService.getUserByStuId(card.getPosterId());
        UserVo posterVo = EntityUtil.getUserVo(poster);
        // 补充卡片中的图片url
        card.setImageUrls(imageService.getImagesForEntity(Constant.ENTITY_TYPE_CARD, id));
        return new CardVo(card, posterVo, UserUtil.getAge(poster));
    }

    @Override
    public List<CardVo> getOnePageCards(String posterId, int goal, int currentPage, int pageSize) {
        // 当传入”0“时获取最新的一页卡片，否则获取该学号用户的一页卡片
        // 当传入-1时查询所有类型的卡片，否则查询指定类型的卡片
        LambdaQueryWrapper<Card> wrapper = new LambdaQueryWrapper<Card>().
                eq(goal != -1,Card::getGoal, goal).
                eq(!Objects.equals(posterId, "0"), Card::getPosterId, posterId);
        // 获取一页卡片
        Page<Card> cardPage = cardMapper.selectPage(new Page<>(currentPage, pageSize), wrapper);
        // 将卡片信息和发布者信息封装到CardInfo中
        List<CardVo> cardVos = new ArrayList<>();
        for (Card card : cardPage.getRecords()) {
            // 补充卡片中的图片url
            card.setImageUrls(imageService.getImagesForEntity(Constant.ENTITY_TYPE_CARD, card.getId()));
            User poster = userService.getUserByStuId(card.getPosterId());
            UserVo posterVo = EntityUtil.getUserVo(poster);
            cardVos.add(new CardVo(card, posterVo, UserUtil.getAge(poster)));
        }
        return cardVos;
    }

    @Override
    public int getNumOfCards(String posterId, int goal) {
        return cardMapper.selectCount(new LambdaQueryWrapper<Card>().
                eq(goal != -1, Card::getGoal, goal).
                eq(!Objects.equals(posterId, "0"), Card::getPosterId, posterId)).intValue();
    }
}
