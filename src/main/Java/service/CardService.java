package service;

import dao.CardMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import pojo.Card;

import java.io.IOException;
import java.io.InputStream;

public class CardService implements CardMapper {
    private static CardMapper cardMapper;

    static {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        cardMapper = sqlSession.getMapper(CardMapper.class);
    }

    @Override
    public Card[] selectCardByStuId(String stuId) {
        return cardMapper.selectCardByStuId(stuId);
    }

    @Override
    public void addCard(Card card) {
        cardMapper.addCard(card);
    }

    @Override
    public Card[] selectOnePageCards() {
        return cardMapper.selectOnePageCards();
    }
}
