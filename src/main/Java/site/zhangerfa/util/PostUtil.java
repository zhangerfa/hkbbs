package site.zhangerfa.util;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.CommentDetails;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.service.HoleNicknameService;
import site.zhangerfa.service.UserService;

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

    /**
     * 获取传入所有评论的详细信息
     *
     * @param comments
     * @param pageSize
     * @return
     */
    public List<CommentDetails> getCommentsDetails(List<Comment> comments, int pageSize){
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
    public List<CommentDetails> getCommentDetailsForHole(int holeId, List<Comment> comments, int pageSize){
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
            Page page = new Page(1, pageSize);
            page.completePage(commentService.getNumOfCommentsForEntity(Constant.ENTITY_TYPE_COMMENT, comment.getId()));
            commentDetails.setPage(page);
            // 子评论详细信息
            // 子评论集合
            List<Comment> subComments = commentService.getCommentsForEntity(Constant.ENTITY_TYPE_COMMENT,
                    comment.getId(), page.getOffset(), page.getLimit());
            // 获取子评论的详细信息
            List<CommentDetails> subCommentsDetails = getCommentsDetails(subComments, deep + 1, pageSize);
            commentDetails.setCommentDetails(subCommentsDetails);

            res.add(commentDetails);
        }
        return res;
    }
}
