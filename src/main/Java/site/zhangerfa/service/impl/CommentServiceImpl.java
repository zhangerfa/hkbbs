package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.dao.CommentMapper;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.service.CardService;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.util.Constant;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    @Resource
    private CardService cardService;


    @Override
    public List<Comment> getCommentsForEntity(int entityType, int entityId, int offset, int limit) {
        return commentMapper.selectCommentsByEntity(entityType, entityId, offset, limit);
    }

    @Override
    public int getNumOfCommentsForEntity(int entityType, int entityId) {
        return commentMapper.getNumOfCommentsForEntity(entityType, entityId);
    }

    @Override
    public Comment getCommentById(int id) {
        return commentMapper.selectCommentById(id);
    }

    @Override
    public boolean addComment(Comment comment) {
        if (comment == null){
            throw new IllegalArgumentException("未传入评论信息");
        }
        // 增加评论
        int addNum = commentMapper.insertComment(comment);
        // 当评论卡片时，卡片表中卡片的评论数量+1
        if (comment.getEntityType() == Constant.ENTITY_TYPE_CARD){
            cardService.commentNumPlusOne(comment.getEntityId());
        }
        return addNum != 0;
    }
}
