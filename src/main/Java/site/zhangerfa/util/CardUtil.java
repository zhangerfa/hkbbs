package site.zhangerfa.util;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import site.zhangerfa.pojo.*;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.service.HoleNicknameService;
import site.zhangerfa.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CardUtil {
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

    /**
     * 补全树洞信息
     * @param holes
     * @return
     */
    public List<Hole> completeHoles(List<Hole> holes){
        if (holes == null){
            return null;
        }
        for (Hole hole : holes) {
            String holeNickname = holeNicknameService.getHoleNickname(hole.getId(), hole.getPosterId());
            hole.setNickname(holeNickname);
        }
        return holes;
    }


    /**
     * 获取传入树洞的完整评论信息
     * @param comments
     * @return 返回一个集合，集合中一个元素是一个map，存储一个评论的信息
     *              包含 nickname,content,createTime,posterId,commentId
     */
    public List<Map> serializeComments(int holeId, List<Comment> comments){
        return serializeComments(holeId, comments, 0);
    }

    public List<Map> serializeComments(int holeId, List<Comment> comments, int deep){
        if (comments == null || comments.size() ==0){
            return new ArrayList<>();
        }
        List<Map> res = new ArrayList<>();
        for (Comment comment : comments) {
            // 将当前节点加入集合
            Map<String, Object> map = new HashMap<>();
            res.add(map);
            String holeNickname = holeNicknameService.getHoleNickname(holeId, comment.getPosterId());
            map.put("nickname", holeNickname);
            map.put("content", comment.getContent());
            map.put("createTime", comment.getCreateTime());
            map.put("posterId", comment.getPosterId());
            map.put("commentId", comment.getId());
            map.put("deep", deep);
            List<Comment> comments4Comment = commentService.getCommentsForEntity(Constant.ENTITY_TYPE_HOLE_COMMENT,
                    comment.getId(), 0, Integer.MAX_VALUE); // 查询所有评论该评论的评论
            // 评论数量
            map.put("commentNum", comments4Comment.size());
            // 将当前节点的所有子节点序列化后节点集合加入集合
            List<Map> subComments = serializeComments(holeId, comments4Comment, deep + 1);
            for (Map subComment : subComments) {
                res.add(subComment);
            }
        }
        return res;
    }
}
