package site.zhangerfa.service;

import site.zhangerfa.pojo.Card;

import java.util.List;

public interface CardService {
    /**
     * 学号为0时获取一页卡片
     * 学号不为0时获取所有该学号用户发的卡片中的某一页
     *
     * @param stuId
     * @param page 要查询的页数
     * @return
     */
    public List<Card> getOnePageCards(String stuId, int page);

    /**
     * 添加卡片
     *
     * @param card
     * @return
     */
    public boolean add(Card card);
}
