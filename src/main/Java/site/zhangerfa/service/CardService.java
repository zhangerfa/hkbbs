package site.zhangerfa.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.zhangerfa.pojo.Card;

import java.util.List;

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

    // isolation 隔离级别
    // propagation 事务传播机制
    @Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.NESTED)
    Card getCardById(int id);
}
