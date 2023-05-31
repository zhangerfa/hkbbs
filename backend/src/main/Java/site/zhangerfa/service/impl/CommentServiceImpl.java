package site.zhangerfa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.dao.CommentMapper;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.Constant.Constant;
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
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getEntityType, entityType)
                .eq(Comment::getEntityId, entityId);
        Page<Comment> commentPage = commentMapper.selectPage(new Page<>(offset, limit), wrapper);
        return commentPage.getRecords();
    }

    @Override
    public int getNumOfCommentsForEntity(int entityType, int entityId) {
        return commentMapper.selectCount(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getEntityType, entityType)
                .eq(Comment::getEntityId, entityId)).intValue();
    }

    @Override
    public Comment getCommentById(int id) {
        return commentMapper.selectById(id);
    }

    @Override
    public boolean addComment(Comment comment) {
        if (comment == null){
            throw new IllegalArgumentException("未传入评论信息");
        }
        // 增加评论
        comment.setPosterId(hostHolder.getUser().getStuId());
        int addNum = commentMapper.insert(comment);
        return addNum != 0;
    }

    @Override
    public Map<String, Object> deleteComment(int commentId) {
        // 权限验证 只有发帖者可以删除自己发的帖子
        Comment comment = commentMapper.selectById(commentId);
        Map<String, Object> map1 = checkLogin(hostHolder, comment.getPosterId());
        if (map1 != null) return map1;
        // 删除所有评论的评论

        List<Comment> comments = getCommentsForEntity(Constant.ENTITY_TYPE_COMMENT, commentId);
        for (Comment cur : comments) {
            deleteComment(cur.getId());
        }
        // 删除评论
        commentMapper.deleteById(commentId);

        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        map.put("msg", "删除成功");
        return map;
    }

    /**
     * 获取所有指向传入实体的评论
     * @param entityType
     * @param entityId
     * @return
     */
    List<Comment> getCommentsForEntity(int entityType, int entityId){
        return commentMapper.selectList(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getEntityType, entityType)
                .eq(Comment::getEntityId, entityId));
    }

    // 权限验证 只有发帖者可以删除自己发的帖子
    static Map<String, Object> checkLogin(HostHolder hostHolder, String posterId) {
        String stuId = hostHolder.getUser().getStuId();
        if (!stuId.equals(posterId)){
            Map<String, Object> map = new HashMap<>();
            map.put("result", false);
            map.put("msg", "您没有权限删除");
            return map;
        }
        return null;
    }
}
