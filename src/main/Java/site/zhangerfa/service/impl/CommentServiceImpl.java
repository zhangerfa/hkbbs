package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.dao.CommentMapper;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.util.Constant;
import site.zhangerfa.util.HostHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private HostHolder hostHolder;

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
        comment.setPosterId(hostHolder.getUser().getStuId());
        int addNum = commentMapper.insertComment(comment);
        return addNum != 0;
    }

    @Override
    public Map<String, Object> deleteComment(int commentId) {
        // 权限验证 只有发帖者可以删除自己发的帖子
        Comment comment = commentMapper.selectCommentById(commentId);
        String stuId = hostHolder.getUser().getStuId();
        if (!stuId.equals(comment.getId())){
            Map<String, Object> map = new HashMap<>();
            map.put("result", false);
            map.put("msg", "您没有权限删除");
            return map;
        }
        // 删除所有评论的评论
        List<Comment> comments = commentMapper.getCommentsForEntity(Constant.ENTITY_TYPE_COMMENT, commentId);
        for (Comment cur : comments) {
            deleteComment(cur.getId());
        }
        // 删除评论
        commentMapper.deleteCommentById(commentId);

        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        map.put("msg", "删除成功");
        return map;
    }
}
