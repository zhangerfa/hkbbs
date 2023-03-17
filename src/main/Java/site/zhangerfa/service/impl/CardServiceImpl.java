package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.dao.PostMapper;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.pojo.Post;
import site.zhangerfa.service.CardService;
import java.util.List;

@Service
public class CardServiceImpl extends PostServiceImpl implements CardService {
    @Resource
    private PostMapper postMapper;

    @Override
    public List<Card> getOnePageCards(String stuId, int offset, int limit) {
        if (stuId == null){
            throw new IllegalArgumentException("未输入学号");
        }
        offset = Math.max(offset, 1);
        limit = Math.min(limit, postMapper.getNumOfCards());
        return postMapper.selectOnePageCards(stuId, offset, limit);
    }

    @Override
    public int getTotalNums() {
        return postMapper.getNumOfCards();
    }

    @Override
    public boolean add(Post card) {
        super.add(card);
        int addNum = postMapper.addCard(card);
        return addNum != 0;
    }
}
