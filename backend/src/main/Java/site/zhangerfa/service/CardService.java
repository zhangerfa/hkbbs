package site.zhangerfa.service;

import site.zhangerfa.controller.tool.CardInfo;
import site.zhangerfa.pojo.Card;

import java.util.List;

public interface CardService {
    /**
     * 发布卡片
     * @param card
     * @return
     */
    boolean add(Card card);

    /**
     * 删除卡片
     * @param id
     * @return
     */
    boolean deleteById(int id);

    /**
     * 将需要修改的内容封装在CardContainStuId对象中，不需要修改的字段不设置值
     * @param card
     * @return
     */
    boolean update(Card card);

    /**
     * 获取指定id的卡片
     * @param id
     * @return
     */
    CardInfo getById(int id);

    /**
     * 获取一页卡片
     * @param posterId 当传入”0“时获取最新的一页卡片，否则获取该学号用户的一页卡片
     * @param goal 当传入-1时查询所有类型的卡片，否则查询指定类型的卡片
     * @return
     */
    List<CardInfo> getOnePageCards(String posterId, int goal, int currentPage, int pageSize);

    /**
     * 获取卡片总数
     *
     * @param posterId 当传入”0“时获取所有卡片数量，否则获取该学号用户的卡片数量
     * @param goal     当传入-1时查询所有类型的卡片的数量，否则查询指定类型的卡片的数量
     * @return
     */
    int getNumOfCards(String posterId, int goal);
}
