package site.zhangerfa.service;

import site.zhangerfa.pojo.Card;

import java.util.List;
import java.util.Map;

public interface CardService {
    /**
     * 学号为 0时获取一页卡片
     * 学号不为 0时获取所有该学号用户发的卡片中的某一页
     * 一页卡片是指 从第 offset 张到第 limit 张
     *
     * @param stuId
     * @param offset 起始卡片
     * @param limit 终止卡片
     * @return
     */
    List<Card> getOnePageCards(String stuId, int offset, int limit);

    int getNumOfCards();

    /**
     * 添加卡片
     *
     * @param card
     * @return
     */
    boolean add(Card card);

    Card getCardById(int id);

    /**
     * 将指定id卡片的评论数量加一
     * @param id
     * @return
     */
    boolean commentNumPlusOne(int id);

    /**
     * 删除卡片，并所有该卡片的评论
     * @param id
     * @return
     */
    Map<String, Object> deleteCard(int id, String stuId);
}
