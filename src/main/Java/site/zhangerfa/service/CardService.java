package site.zhangerfa.service;

import site.zhangerfa.pojo.Card;

import java.util.List;

public interface CardService {
    /**
     * 获取所有该学号用户发的卡片中的某一页
     *
     * @param stuId
     * @param page 要查询的页数
     * @return
     */
    public List<Card> getOnePageCardsByStuId(String stuId, int page);

    /**
     * 添加卡片
     *
     * @param card
     * @return
     */
    public boolean add(Card card);

    /**
     * 获取指定一页卡片，一页十张，按发布时间顺序排列
     *
     * @param page 要查询的页数
     * @return
     */
    public List<Card> getOnePageCards(int page);
}
