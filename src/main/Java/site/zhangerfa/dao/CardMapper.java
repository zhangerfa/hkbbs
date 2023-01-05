package site.zhangerfa.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.zhangerfa.pojo.Card;

import java.util.List;

@Mapper
public interface CardMapper {

    /**
     * 查询一页卡片，当传入学号不为0时，学号为合法学号，查询该学号用户所发的一页卡片
     *            当传入学号为0时，查询一页卡片
     * 一页为10张卡片
     * @param stuId 0 或 合法学号
     * @param offset 要查询页的起始行
     * @param limit 要查询页的末位行
     * @return 一页卡片对象的 list
     */
    List<Card> selectOnePageCards(String stuId, int offset, int limit);

    int getNumOfCards();

    /*
    发帖
     */
    int addCard(Card card);

    Card selectCardById(int id);
}

