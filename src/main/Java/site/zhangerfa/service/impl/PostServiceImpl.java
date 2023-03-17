package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;
import site.zhangerfa.dao.PostMapper;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Post;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.service.PostService;
import site.zhangerfa.util.Constant;
import site.zhangerfa.util.HostHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PostServiceImpl implements PostService {
    @Resource
    private HostHolder hostHolder;
    @Resource
    private CommentService commentService;
    @Resource
    private PostMapper postMapper;
    /**
     * 判空及HTML转义处理
     * @param post
     * @return
     */
    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.NESTED)
    public boolean add(Post post) {
        if (post == null){
            throw new IllegalArgumentException("发帖内容为空");
        }
        // 转义HTML标记，防止HTML注入
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
        return true;
    }

    @Override
    public Post getPostById(int id) {
        return postMapper.selectPostById(id);
    }

    /**
     * 删除帖子，包括删除帖子的所有评论
     * @param id
     * @return
     */
    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.NESTED)
    public Map<String, Object> deleteById(int id) {
        // 权限验证 只有发帖者可以删除自己发的帖子
        Post post = postMapper.selectPostById(id);
        String stuId = hostHolder.getUser().getStuId();
        if (!stuId.equals(post.getPosterId())){
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
        postMapper.deletePostById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        map.put("msg", "删除成功");
        return map;
    }

    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.NESTED)
    public Map<String, Object> deleteComment(int commentId) {
        // 帖子表评论数减一
        Comment comment = commentService.getCommentById(commentId);
        postMapper.commentNumMinusOne(comment.getEntityId());
        // 删除评论
        return commentService.deleteComment(commentId);
    }

    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.NESTED)
    public boolean addComment(Comment comment, int postId) {
        // 发布评论
        boolean flag = commentService.addComment(comment);
        // 卡片表中评论数量加一
        return flag & (postMapper.commentNumPlusOne(comment.getEntityId()) > 0);
    }

    @Override
    public List<Comment> getComments(int id) {
        return getComments(id, 0, Integer.MAX_VALUE);
    }

    @Override
    public List<Comment> getComments(int id, int offset, int limit) {
        return commentService.getCommentsForEntity(Constant.ENTITY_TYPE_HOLE, id, offset, limit);
    }
}
