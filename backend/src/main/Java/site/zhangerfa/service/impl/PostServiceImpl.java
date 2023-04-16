package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.dao.PostMapper;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Image;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.pojo.Post;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.service.ImageService;
import site.zhangerfa.service.PostService;
import site.zhangerfa.Constant.Constant;
import site.zhangerfa.util.HostHolder;
import site.zhangerfa.util.PageUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 帖子对卡片接口的实现类
 */
@Service
public class PostServiceImpl implements PostService {
    @Resource
    private HostHolder hostHolder;
    @Resource
    private CommentService commentService;
    @Resource
    private PostMapper postMapper;
    @Resource
    private ImageService imageService;
    /**
     * 判空及HTML转义处理
     * @param post
     * @return
     */
    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.NESTED)
    public boolean add(Post post, int postType) {
        if (post == null){
            throw new IllegalArgumentException("发帖内容为空");
        }
        // 转义HTML标记，防止HTML注入
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
        // post表中添加post
        int addNum = postMapper.add(post, postType);
        // 将帖子中的图片url插入image表
        for (String image : post.getImages()) {
            imageService.add(new Image(Constant.ENTITY_TYPE_POST, post.getId(), image));
        }
        return addNum != 0;
    }

    @Override
    public Post getPostById(int id) {
        Post post = postMapper.selectPostById(id);
        // 获取帖子中图片
        post.setImages(imageService.getImagesForEntity(Constant.ENTITY_TYPE_POST, id));
        return post;
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
        // 删除帖子的评论
        List<Comment> comments = commentService.getCommentsForEntity(Constant.ENTITY_TYPE_POST, id, 0, Integer.MAX_VALUE);
        for (Comment comment : comments) {
            deleteComment(comment.getId());
        }
        // 删除帖子中的所有图片
        imageService.deleteImageById(id);
        // 删除帖子
        postMapper.deletePostById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        map.put("msg", "删除成功");
        return map;
    }

    @Override
    public int getTotalNums(int postType, String posterId) {
        return postMapper.getNumOfPosts(postType, posterId);
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
    public List<Comment> getComments(int entityType, int entityId, int from, int to) {
        return commentService.getCommentsForEntity(entityType, entityId, from, to);
    }

    @Override
    public int getPostType(int id) {
        Integer postType = postMapper.getPostType(id);
        if (postType == null) return -1;
        return postType;
    }

    @Override
    public Result<List<Post>> getOnePagePosts(String stuId, int postType,
                                              int currentPage, int pageSize) {
        if (stuId == null)
            return new Result<>(Code.GET_ERR, null, "未输入学号");
        // 分页信息
        PageUtil pageUtil = new PageUtil(currentPage, pageSize, getTotalNums(postType, stuId));
        Page page = pageUtil.generatePage();
        int[] fromTo = pageUtil.getFromTo();
        List<Post> posts = postMapper.selectOnePagePosts(postType, stuId, fromTo[0], fromTo[1]);
        // 补充帖子中的图片
        for (Post post : posts) {
            post.setImages(imageService.getImagesForEntity(Constant.ENTITY_TYPE_POST, post.getId()));
        }
        return new Result<>(Code.GET_OK, posts, "获取成功");
    }
}
