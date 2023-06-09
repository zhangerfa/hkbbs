package site.zhangerfa.util;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import site.zhangerfa.Constant.Constant;
import site.zhangerfa.controller.tool.*;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.pojo.Post;
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
    public List<PostInfo> completePostInfo(Result<List<Post>> result){
        List<PostInfo> postInfos = new ArrayList<>();
        for (Post post : result.getData()) {
            PostInfo postInfo = new PostInfo(post);
            // 补充发帖人信息
            UserDTO userDTO = entityUtil.getUserDTO(post.getPostType(), post.getPosterId());
            postInfo.setPosterName(userDTO.getUsername());
            postInfo.setPosterHeaderUrl(userDTO.getHeaderUrl());
            // 补充点赞数量
            postInfo.setLikeNum(likeService.getLikeCount(post.getPostType(), post.getId()));

            postInfos.add(postInfo);
        }
        return postInfos;
    }

    /**
     * 获取帖子和发帖人详细信息
     * @param postId
     * @param currentPage 当前评论页码
     * @param pageSize 当前页展示评论数量
     * @return
     */
    public Result<PostDetails> getPostAndPosterDetails(int postId, int currentPage, int pageSize){
        // 帖子信息
        Post post = postService.getPostById(postId);
        if (post == null) return new Result<>(Code.GET_ERR, null, "您访问的帖子已被删除");
        PostDetails postDetails = new PostDetails(post);
        // 帖子点赞数量
        postDetails.setLikeNum(likeService.getLikeCount(post.getPostType(), postId));
        // 作者信息
        UserDTO userDTO = entityUtil.getUserDTO(post.getPostType(), post.getPosterId());
        postDetails.setPosterName(userDTO.getUsername());
        postDetails.setPosterHeaderUrl(userDTO.getHeaderUrl());
        // 分页信息
        PageUtil pageUtil = new PageUtil(currentPage, pageSize,
                commentService.getNumOfCommentsForEntity(Constant.ENTITY_TYPE_POST, postId));
        Page page = pageUtil.generatePage();
        postDetails.setPage(page);
        // 获取评论集合
        int[] fromTo = pageUtil.getFromTo();
        List<Comment> comments = postService.getComments(Constant.ENTITY_TYPE_POST, postId, fromTo[0], fromTo[1]);
        // 获取每个评论的详细信息
        List<CommentDetails> commentsDetails;
        commentsDetails = getCommentsDetails(comments, page.getPageSize(), postId);
        postDetails.setCommentDetails(commentsDetails);
        return new Result<>(Code.GET_OK, postDetails, "查询成功");
    }

    /**
     * 获取传入所有评论的详细信息
     *
     * @param comments
     * @param pageSize
     * @return
     */
    private List<CommentDetails> getCommentsDetails(List<Comment> comments, int pageSize, int postId){
        return getCommentsDetails(comments, 0, pageSize, postId);
    }

    private List<CommentDetails> getCommentsDetails(List<Comment> comments, int deep, int pageSize, int postId){
        if (comments == null || comments.size() == 0){
            return new ArrayList<>();
        }
        List<CommentDetails> res = new ArrayList<>();
        for (Comment comment : comments) {
            // 评论信息
            CommentDetails commentDetails = new CommentDetails(comment);
            // 记录当前评论深度
            commentDetails.setDeep(deep);
            // 评论发布者信息
            UserDTO userDTO = entityUtil.getUserDTO(comment.getOwnerType(), comment.getPosterId());
            commentDetails.setPosterName(userDTO.getUsername());
            commentDetails.setPosterHeaderUrl(userDTO.getHeaderUrl());
            // 分页信息
            PageUtil pageUtil = new PageUtil(1, pageSize,
                    commentService.getNumOfCommentsForEntity(
                            Constant.ENTITY_TYPE_COMMENT, comment.getId()));
            Page page = pageUtil.generatePage();
            commentDetails.setPage(page);
            // 评论点赞数量
            commentDetails.setLikeNum(likeService.getLikeCount(Constant.ENTITY_TYPE_COMMENT, comment.getId()));
            // 子评论详细信息
            // 子评论集合
            int[] fromTo = pageUtil.getFromTo();
            List<Comment> subComments = commentService.getCommentsForEntity(Constant.ENTITY_TYPE_COMMENT,
                    comment.getId(), fromTo[0], fromTo[1]);
            // 获取子评论的详细信息
            List<CommentDetails> subCommentsDetails = getCommentsDetails(subComments, deep + 1, pageSize);
            commentDetails.setCommentDetails(subCommentsDetails);

            res.add(commentDetails);
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
