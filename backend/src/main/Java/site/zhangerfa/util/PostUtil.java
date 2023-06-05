package site.zhangerfa.util;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import site.zhangerfa.Constant.Constant;
import site.zhangerfa.controller.tool.*;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.service.HoleNicknameService;
import site.zhangerfa.service.PostService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.pojo.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostUtil {
    @Resource
    private UserService userService;
    @Resource
    private CommentService commentService;
    @Resource
    private HoleNicknameService holeNicknameService;
    @Resource(name = "postServiceImpl")
    private PostService postService;

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
            User user = userService.getUserByStuId(post.getPosterId());
            postInfo.setPosterName(user.getUsername());
            postInfo.setPosterHeaderUrl(user.getHeaderUrl());

            postInfos.add(postInfo);
        }
        return postInfos;
    }

    /**
     * 将树洞贴补充发布者信息并匿名化-昵称和头像换为匿名
     * @param result
     * @return
     */
    public List<PostInfo> completeHoleInfo(Result<List<Post>> result){
        ArrayList<PostInfo> holeInfos = new ArrayList<>();
        for (Post post : result.getData()) {
            PostInfo holeInfo = new PostInfo(post);
            // 匿名化
            holeInfo.setPosterName(holeNicknameService.getHoleNickname(
                    post.getId(), post.getPosterId()));
            holeInfo.setPosterHeaderUrl("https://zhangerfa-1316526930.cos.ap-guangzhou.myqcloud.com/hkbbs/default.jpg");

            holeInfos.add(holeInfo);
        }
        return holeInfos;
    }

    /**
     * 获取帖子和发帖人详细信息
     * @param postId
     * @param currentPage 当前评论页码
     * @param pageSize 当前页展示评论数量
     * @return
     */
    public Result<PostDetails> getPostAndPosterDetails(int postId, int currentPage, int pageSize){
        // 获取帖子类型
        int postType = postService.getPostType(postId);
        // 帖子信息
        Post card = postService.getPostById(postId);
        if (card == null) return new Result<>(Code.GET_ERR, null, "您访问的帖子已被删除");
        PostDetails postDetails = new PostDetails(card);
        // 作者信息: 如果为树洞则替换为匿名信息
        User poster = userService.getUserByStuId(card.getPosterId());
        String posterName = postType == Constant.ENTITY_TYPE_POST? poster.getUsername():
                holeNicknameService.getHoleNickname(postId, poster.getStuId());
        postDetails.setPosterName(posterName);
        String posterHeaderUrl = postType == Constant.ENTITY_TYPE_POST? poster.getHeaderUrl():
                "https://zhangerfa-1316526930.cos.ap-guangzhou.myqcloud.com/hkbbs/default.jpg";
        postDetails.setPosterHeaderUrl(posterHeaderUrl);
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
            User poster = userService.getUserByStuId(comment.getPosterId());
            String[] usernameAndHeaderUrl = getUsernameAndHeaderUrl(poster, postId);
            commentDetails.setPosterName(usernameAndHeaderUrl[0]);
            commentDetails.setPosterHeaderUrl(usernameAndHeaderUrl[1]);
            // 分页信息
            PageUtil pageUtil = new PageUtil(1, pageSize,
                    commentService.getNumOfCommentsForEntity(
                            Constant.ENTITY_TYPE_COMMENT, comment.getId()));
            Page page = pageUtil.generatePage();
            commentDetails.setPage(page);
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

    /**
     * 获取发布者的用户名和头像信息，如果为匿名则返回匿名信息
     * @param poster
     * @param postId 发布实体所在的帖子id
     * @return
     */
    public String[] getUsernameAndHeaderUrl(User poster, int postId){
        int postType = postService.getPostType(postId);
        String[] res = new String[2];
        res[0] = postType == Constant.ENTITY_TYPE_POST? poster.getUsername():
                holeNicknameService.getHoleNickname(postId, poster.getStuId());
        res[1] = postType == Constant.ENTITY_TYPE_POST? poster.getHeaderUrl():
                "https://zhangerfa-1316526930.cos.ap-guangzhou.myqcloud.com/hkbbs/default.jpg";
        return res;
    }
}
