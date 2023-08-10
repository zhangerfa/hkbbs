package site.zhangerfa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.dao.PostMapper;
import site.zhangerfa.entity.Entity;
import site.zhangerfa.service.HoleNicknameService;
import site.zhangerfa.service.ImageService;
import site.zhangerfa.service.PostService;
import site.zhangerfa.entity.Comment;
import site.zhangerfa.entity.Image;
import site.zhangerfa.entity.Post;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.Constant.Constant;
import site.zhangerfa.util.HostHolder;

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
    @Resource
    private HoleNicknameService holeNicknameService;
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
        // post表中添加post
        int addNum = postMapper.insert(post);
        // 将帖子中的图片url插入image表
        Entity entity = new Entity(Constant.ENTITY_TYPE_POST, post.getId());
        for (String image : post.getImages()) {
            imageService.add(new Image(entity, image));
        }
        return addNum != 0;
    }

    @Override
    public Post getPostById(int id) {
        Post post = postMapper.selectById(id);
        if (post == null) return null;
        // 获取帖子中图片
        Entity entity = new Entity(Constant.ENTITY_TYPE_POST, id);
        post.setImages(imageService.getImagesForEntity(entity));
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
        Post post = postMapper.selectById(id);
        Map<String, Object> checkLoginMap = CommentServiceImpl.checkLogin(hostHolder, post.getPosterId());
        if (checkLoginMap != null) return checkLoginMap;
        // 删除帖子的评论
        Entity entity = new Entity(Constant.ENTITY_TYPE_POST, id);
        List<Comment> comments = commentService.getCommentsForEntity(entity,
                0, Integer.MAX_VALUE);
        for (Comment comment : comments) {
            deleteComment(comment.getId());
        }
        // 删除帖子中的所有图片
        imageService.deleteImagesForEntity(entity);
        // 如果删除的帖子为树洞，删除该树洞中所有随机昵称
        if (postMapper.getPostType(id) == Constant.ENTITY_TYPE_HOLE)
            holeNicknameService.deleteNicknamesForHole(id);
        // 删除帖子
        postMapper.deleteById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        map.put("msg", "删除成功");
        return map;
    }

    @Override
    public int getTotalNums(int postType, String posterId) {
        return postMapper.selectCount(new LambdaQueryWrapper<Post>()
                .eq(postType != -1, Post::getPostType, postType)
                .eq(!posterId.equals("0"), Post::getPosterId, posterId)).intValue();
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
        // 补充评论所属实体的类型
        comment.setOwnerType(getPostType(postId));
        // 发布评论
        boolean flag = commentService.addComment(comment);
        // 卡片表中评论数量加一
        return flag & (postMapper.commentNumPlusOne(comment.getEntityId()) > 0);
    }

    @Override
    public List<Comment> getComments(Entity entity, int from, int to) {
        return commentService.getCommentsForEntity(entity, from, to);
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
        // 获取帖子
        Page<Post> postPage = postMapper.selectPage(new Page<>(currentPage, pageSize),
                new LambdaQueryWrapper<Post>().
                        eq(Post::getPostType, postType)
                        .eq(!stuId.equals("0"), Post::getPosterId, stuId)
                        .orderByDesc(Post::getCreateTime));
        // 补充帖子中的图片
        for (Post post : postPage.getRecords()) {
            Entity entity = new Entity(Constant.ENTITY_TYPE_POST, post.getId());
            post.setImages(imageService.getImagesForEntity(entity));
        }
        return new Result<>(Code.GET_OK, postPage.getRecords(), "获取成功");
    }
}
