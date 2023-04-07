package site.zhangerfa.service;

import site.zhangerfa.controller.tool.CardContainStuId;
import site.zhangerfa.controller.tool.CardContainsPoster;
import site.zhangerfa.pojo.Page;

import java.util.List;

public interface CardService {
    /**
     * 发布卡片
     * @param card
     * @return
     */
    boolean add(CardContainStuId card);

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
    boolean update(CardContainStuId card);

    /**
     * 获取指定id的卡片
     * @param id
     * @return
     */
    CardContainsPoster getById(int id);

    /**
     * 获取一页卡片
     * @param posterId 当传入”0“时获取最新的一页卡片，否则获取该学号用户的一页卡片
     * @param page 存储当前页的分页信息
     * @param goal 当传入-1时查询所有类型的卡片，否则查询指定类型的卡片
     * @return
     */
    List<CardContainsPoster> getOnePageCards(String posterId, Page page, int goal);
}
