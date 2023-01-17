package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;
import site.zhangerfa.dao.CardMapper;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.service.CardService;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.util.Constant;
import site.zhangerfa.util.HostHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CardServiceImpl implements CardService {
    @Resource
    private CardMapper cardMapper;
    @Resource
    private CommentService commentService;
    @Resource
    private HostHolder hostHolder;

    @Override
    public List<Card> getOnePageCards(String stuId, int offset, int limit) {
        if (stuId == null){
            throw new IllegalArgumentException("未输入学号");
        }
        offset = Math.max(offset, 1);
        limit = Math.min(limit, cardMapper.getNumOfCards());
        return cardMapper.selectOnePageCards(stuId, offset, limit);
    }

    @Override
    public boolean deleteById(int id) {
        int deleteNum = cardMapper.deleteCardById(id);
        return deleteNum > 0;
    }

    @Override
    public int getTotalNums() {
        return cardMapper.getNumOfCards();
    }

    @Override
    public boolean add(Card card) {
        if (card == null){
            throw new IllegalArgumentException("输入为空");
        }
        // 转义HTML标记，防止HTML注入
        card.setTitle(HtmlUtils.htmlEscape(card.getTitle()));
        card.setContent(HtmlUtils.htmlEscape(card.getContent()));
        int addNum = cardMapper.addCard(card);
        return addNum != 0;
    }

    @Override
    public Card getCardById(int id) {
        return cardMapper.selectCardById(id);
    }

    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.NESTED)
    public boolean addComment(Comment comment) {
        // 发布评论
        boolean flag = commentService.addComment(comment);
        // 卡片表中评论数量加一
        flag &= commentNumPlusOne(comment.getEntityId());
        return flag;
    }

    @Override
    public Map<String, Object> deleteComment(int commentId) {
        // 卡片表评论数减一
        Comment comment = commentService.getCommentById(commentId);
        commentNumMinusOne(comment.getEntityId());
        // 删除评论
        return commentService.deleteComment(commentId);
    }

    @Override
    public List<Comment> getComments(int id) {
        return getComments(id, 0, Integer.MAX_VALUE);
    }

    @Override
    public List<Comment> getComments(int id, int offset, int limit) {
        return commentService.getCommentsForEntity(Constant.ENTITY_TYPE_CARD, id, offset, limit);
    }

    @Override
    public Map<String, Object> deleteCard(int id) {
        // 权限验证 只有发帖者可以删除自己发的帖子
        Card card = cardMapper.selectCardById(id);
        String stuId = hostHolder.getUser().getStuId();
        if (!stuId.equals(card.getPosterId())){
            Map<String, Object> map = new HashMap<>();
            map.put("result", false);
            map.put("msg", "您没有权限删除");
            return map;
        }
        // 删除卡片的评论
        List<Comment> comments = commentService.getCommentsForEntity(Constant.ENTITY_TYPE_CARD, id, 0, Integer.MAX_VALUE);
        for (Comment comment : comments) {
            deleteComment(comment.getId());
        }
        // 删除卡片
        cardMapper.deleteCardById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        map.put("msg", "删除成功");
        return map;
    }

    public boolean commentNumPlusOne(int cardId) {
        int flag = cardMapper.commentNumPlusOne(cardId);
        return flag > 0;
    }

    public boolean commentNumMinusOne(int cardId) {
        int flag = cardMapper.commentNumMinusOne(cardId);
        return flag > 0;
    }
}
