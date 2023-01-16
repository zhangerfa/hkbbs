package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.dao.CommentMapper;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.service.CardService;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.service.HoleService;
import site.zhangerfa.util.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private CardService cardService;
    @Resource
    private HoleService holeService;


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
        // 当评论卡片或树洞时，卡片表中卡片的评论数量+1
        if (comment.getEntityType() == Constant.ENTITY_TYPE_CARD){
            cardService.commentNumPlusOne(comment.getEntityId());
        }else if (comment.getEntityType() == Constant.ENTITY_TYPE_HOLE){
            holeService.addComment(comment.getEntityId(), comment.getStuId());
        }
        return addNum != 0;
    }

    @Override
    public Map<String, Object> deleteComment(int id, String stuId) {
        // 权限验证 只有发帖者可以删除自己发的帖子
        Comment comment = commentMapper.selectCommentById(id);
        if (!stuId.equals(comment.getStuId())){
            Map<String, Object> map = new HashMap<>();
            map.put("result", false);
            map.put("msg", "您没有权限删除");
            return map;
        }
        // 删除所有评论的评论
        List<Comment> comments = commentMapper.getCommentsForEntity(Constant.ENTITY_TYPE_COMMENT, id);
        for (Comment cur : comments) {
            deleteComment(cur.getId(), stuId);
        }
        // 删除评论
        commentMapper.deleteCommentById(id);
        // 卡片的评论数量减一
        if (comment.getEntityType() == Constant.ENTITY_TYPE_CARD){
            cardService.commentNumMinusOne(comment.getEntityId());
        } else if (comment.getEntityType() == Constant.ENTITY_TYPE_HOLE){
            holeService.deleteComment(comment.getEntityId());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        map.put("msg", "删除成功");
        return map;
    }
}
