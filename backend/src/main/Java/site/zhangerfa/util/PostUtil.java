package site.zhangerfa.util;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import site.zhangerfa.Constant.Constant;
import site.zhangerfa.controller.tool.*;
import site.zhangerfa.controller.vo.CommentDetailsVo;
import site.zhangerfa.controller.vo.PostDetailsVo;
import site.zhangerfa.controller.vo.PostVo;
import site.zhangerfa.controller.vo.UserVo;
import site.zhangerfa.entity.Comment;
import site.zhangerfa.entity.Entity;
import site.zhangerfa.entity.Page;
import site.zhangerfa.entity.Post;
import site.zhangerfa.service.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostUtil {
    @Resource
    private CommentService commentService;
    @Resource
    private PostService postService;
    @Resource
    private EntityUtil entityUtil;
    @Resource
    private LikeService likeService;

    /**
     * 补充帖子信息中发帖人的信息(用于帖子列表，而非详情页面)
     * @param result
     * @return
     */
    public List<PostVo> completePostInfo(Result<List<Post>> result){
        List<PostVo> postVos = new ArrayList<>();
        for (Post post : result.getData()) {
            PostVo postVo = new PostVo(post);
            // 补充发帖人信息
            UserVo userVo = entityUtil.getUserVo(post.getPostType(), post.getPosterId());
            postVo.setPoster(userVo);
            // 补充点赞数量
            Entity entity = new Entity(post.getPostType(), post.getId());
            postVo.setLikeNum(likeService.getLikeCount(entity));

            postVos.add(postVo);
        }
        return postVos;
    }

    /**
     * 获取帖子和发帖人详细信息
     * @param postId
     * @param currentPage 当前评论页码
     * @param pageSize 当前页展示评论数量
     * @return
     */
    public Result<PostDetailsVo> getPostAndPosterDetails(int postId, int currentPage, int pageSize){
        // 帖子信息
        Post post = postService.getPostById(postId);
        if (post == null) return new Result<>(Code.GET_ERR, null, "您访问的帖子已被删除");
        PostDetailsVo postDetailsVo = new PostDetailsVo(post);
        // 帖子点赞数量
        Entity entity = new Entity(post.getPostType(), postId);
        postDetailsVo.setLikeNum(likeService.getLikeCount(entity));
        // 作者信息
        UserVo userVo = entityUtil.getUserVo(post.getPostType(), post.getPosterId());
        postDetailsVo.setPoster(userVo);
        // 分页信息
        PageUtil pageUtil = new PageUtil(currentPage, pageSize,
                commentService.getNumOfCommentsForEntity(entity));
        Page page = pageUtil.generatePage();
        postDetailsVo.setPage(page);
        // 获取评论集合
        int[] fromTo = pageUtil.getFromTo();
        List<Comment> comments = postService.getComments(entity, fromTo[0], fromTo[1]);
        // 获取每个评论的详细信息
        List<CommentDetailsVo> commentsDetails;
        commentsDetails = getCommentsDetails(comments, page.getPageSize(), postId);
        postDetailsVo.setCommentDetailVos(commentsDetails);
        return new Result<>(Code.GET_OK, postDetailsVo, "查询成功");
    }

    /**
     * 获取传入所有评论的详细信息
     *
     * @param comments
     * @param pageSize
     * @return
     */
    private List<CommentDetailsVo> getCommentsDetails(List<Comment> comments, int pageSize, int postId){
        return getCommentsDetails(comments, 0, pageSize, postId);
    }

    private List<CommentDetailsVo> getCommentsDetails(List<Comment> comments, int deep, int pageSize, int postId){
        if (comments == null || comments.size() == 0){
            return new ArrayList<>();
        }
        List<CommentDetailsVo> res = new ArrayList<>();
        for (Comment comment : comments) {
            // 评论信息
            CommentDetailsVo commentDetailsVo = new CommentDetailsVo(comment);
            // 记录当前评论深度
            commentDetailsVo.setDeep(deep);
            // 评论发布者信息
            UserVo userVo = entityUtil.getUserVo(comment.getOwnerType(), comment.getPosterId());
            commentDetailsVo.setPosterName(userVo.getUsername());
            commentDetailsVo.setPosterHeaderUrl(userVo.getHeaderUrl());
            // 分页信息
            Entity entity = new Entity(Constant.ENTITY_TYPE_COMMENT, comment.getId());
            PageUtil pageUtil = new PageUtil(1, pageSize,
                    commentService.getNumOfCommentsForEntity(entity));
            Page page = pageUtil.generatePage();
            commentDetailsVo.setPage(page);
            // 评论点赞数量
            Entity ownEntity = new Entity(comment.getOwnerType(), comment.getId());
            commentDetailsVo.setLikeNum(likeService.getLikeCount(ownEntity));
            // 子评论详细信息
            // 子评论集合
            int[] fromTo = pageUtil.getFromTo();
            List<Comment> subComments = commentService.getCommentsForEntity(entity,
                    fromTo[0], fromTo[1]);
            // 获取子评论的详细信息
            List<CommentDetailsVo> subCommentsDetails = getCommentsDetails(subComments, deep + 1, pageSize);
            commentDetailsVo.setCommentDetailVos(subCommentsDetails);

            res.add(commentDetailsVo);
        }
        return res;
    }

    /**
     * 判断帖子类型是否合法
     * @param type
     * @return
     */
    public boolean isPostTypeValid(int type){
        if (type == Constant.ENTITY_TYPE_POST || type == Constant.ENTITY_TYPE_HOLE)
            return true;
        return false;
    }
}
