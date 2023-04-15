package site.zhangerfa.dao;

import org.apache.ibatis.annotations.Mapper;
import site.zhangerfa.controller.tool.CardInfo;
import site.zhangerfa.pojo.Card;

import java.util.List;

@Mapper
public interface CardMapper {
    /**
     * 发布卡片
     *
     * @param posterId
     * @param card
     * @return
     */
    int add(String posterId, Card card);

    int deleteById(int id);

    CardInfo selectById(int id);

    /**
     * 获取指定用户发布的指定交友目标的一页卡片
     * @param goal 当输入为 -1时获取最新的一页卡片
     * @param posterId 当输入为 ’0‘时获取最新发布的一页卡片
     * @param offset 当前页第一张卡片的排序数
     * @param limit 当前页最后一张卡片的排序数
     * @return
     */
    List<CardInfo> selectOnePageCards(int goal, String posterId, int offset, int limit);

    /**
     * 将卡片改为传入内容，当卡片不存在时返回 0
     * @param card
     * @return
     */
    int updateCard(Card card);

    /**
     * 获取总卡片数
     * @param goal 当输入为 -1时统计所有类型的卡片，否则统计指定类型
     * @param posterId 当传入”0“时统计所有用户卡片，否则统计指定用户
     * @return
     */
    int getTotalNums(int goal, String posterId);
}
