package site.zhangerfa.util;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import site.zhangerfa.Constant.Constant;
import site.zhangerfa.controller.tool.*;
import site.zhangerfa.pojo.*;
import site.zhangerfa.service.*;

import java.util.ArrayList;
import java.util.LinkedList;
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
     * @param postType 帖子类型
     * @return
     */
    public Result<PostDetails<Post>> getPostAndPosterDetails(int postId, int currentPage, int pageSize, int postType){
        PostDetails<Post> postDetails = new PostDetails<>();
        // 帖子信息
        Post card = postService.getPostById(postId);
        if (card == null) return new Result<>(Code.GET_ERR, null, "您访问的帖子已被删除");
        postDetails.setPost(card);
        // 作者信息
        User poster = userService.getUserByStuId(card.getPosterId());
        postDetails.setPoster(poster);
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
        if (postType == Constant.ENTITY_TYPE_POST){
             commentsDetails = getCommentsDetails(comments, page.getPageSize());
        }else {
            commentsDetails = getCommentDetailsForHole(postId, comments, page.getPageSize());
        }
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
    private List<CommentDetails> getCommentsDetails(List<Comment> comments, int pageSize){
        return getCommentsDetails(comments, 0, pageSize);
    }

    /**
     * 获取传入所有树洞评论的详细信息
     *
     * @param holeId
     * @param comments
     * @param pageSize
     * @return
     */
    private List<CommentDetails> getCommentDetailsForHole(int holeId, List<Comment> comments, int pageSize){
        // 获取评论详情
        List<CommentDetails> commentsDetails = getCommentsDetails(comments, 0, pageSize);
        // 将评论详情中 发帖人的 username 用随机昵称覆盖
        LinkedList<CommentDetails> stack = new LinkedList<>();
        for (int i = 0; i < commentsDetails.size(); i++){
            stack.add(commentsDetails.get(i));
            while (stack.size() != 0){
                CommentDetails details = stack.pop();
                // 将当前节点所有子节点入栈
                List<CommentDetails> subCommentDetails = details.getCommentDetails();
                if (subCommentDetails.size() > 0) stack.addAll(subCommentDetails);
                // 当前节点修改 username 为 随即昵称
                User poster = details.getPoster();
                poster.setUsername(holeNicknameService.getHoleNickname(holeId, poster.getStuId()));
            }
        }
        return commentsDetails;
    }

    private List<CommentDetails> getCommentsDetails(List<Comment> comments, int deep, int pageSize){
        if (comments == null || comments.size() == 0){
            return new ArrayList<>();
        }
        List<CommentDetails> res = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDetails commentDetails = new CommentDetails();
            // 记录当前评论深度
            commentDetails.setDeep(deep);
            // 评论信息
            commentDetails.setPost(comment);
            // 评论发布者信息
            commentDetails.setPoster(userService.getUserByStuId(comment.getPosterId()));
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
}
