package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.dao.CommentMapper;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;


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
}
