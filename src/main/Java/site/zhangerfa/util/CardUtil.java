package site.zhangerfa.util;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Hole;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.service.HoleNicknameService;
import site.zhangerfa.service.UserService;

import java.util.*;

@Component
public class CardUtil {
    @Resource
    private UserService userService;

    @Resource
    private CommentService commentService;

    @Resource
    private HoleNicknameService holeNicknameService;

    // 补全卡片信息
    public List<Map> completeCard(List<Card> cards){
        List<Map> res = new ArrayList<>();
        for (Card card : cards) {
            User user = userService.getUserByStuId(card.getPosterId());
            Map<String, Object> map = new HashMap<>();
            map.put("card", card);
            map.put("user", user);
            res.add(map);
        }
        return res;
    }

    /**
     * 补全评论信息
     * @param comments 评论对象集合
     * @return 最终返回一个list，每个值是一个map，包含一个评论的信息
     *            每个map包含，commentId、poster、content、createTime、comments、commentNum
     *               分别是谁评论的、评论了什么、什么时候评论的，评论的评论集合、评论数量
     *               comments是该评论的评论集合，是一个list，每个值为map
     */
    public List<Map> completeComments(List<Comment> comments){
        if (comments == null) return null;
        List<Map> res = new ArrayList<>();
        for (Comment comment : comments) {
            HashMap<String, Object> map = new HashMap<>();
            res.add(map);
            // 评论id
            map.put("commentId", comment.getId());
            // 评论人的信息
            User user = userService.getUserByStuId(comment.getStuId());
            map.put("poster", user);
            // 评论内容
            map.put("content", comment.getContent());
            // 评论时间
            map.put("createTime", comment.getCreateTime());
            // 评论的评论集合
            List<Comment> comments4Comment = commentService.getCommentsForEntity(Constant.ENTITY_TYPE_COMMENT,
                    comment.getId(), 0, Integer.MAX_VALUE); // 查询所有评论该评论的评论
            List<Map> completedComments = completeComments(comments4Comment); // 补全评论信息
            map.put("comments", completedComments);
            // 评论数量
            map.put("commentNum", completedComments.size());
            }
        return res;
    }

    /**
     * 序列化评论树（深度优先遍历）
     * @param completedComments
     * @return 最终返回一个list，每个值是一个map，包含一个评论的信息
     *            每个map包含，commentId、poster、content、createTime、commentNum、deep
     *            deep为节点所在的深度
     *         节点顺序是树的深度优先遍历
     */

    public List<Map> serializeComments(List<Map> completedComments){
        return serializeComments(completedComments, 0);
    }

    private List<Map> serializeComments(List<Map> completedComments, int deep){
        if (completedComments == null || completedComments.size() == 0){
            return new ArrayList<>();
        }
        List<Map> res = new ArrayList<>();
        for (Map comment : completedComments) {
            // 将当前节点添加到返回集合中
            comment.put("deep", deep);
            List<Map> subComments = (List<Map>) comment.remove("comments");
            res.add(comment);
            // 将当前节点的所有子节点以深度优先的顺序序列化添加到返回集合中
            List<Map> comments = serializeComments(subComments, deep + 1);
            for (Map map : comments) {
                res.add(map);
            }
        }
        return res;
    }

    /**
     * 补全树洞信息
     * @param holes
     * @return
     */
    public List<Hole> completeHoles(List<Hole> holes){
        for (Hole hole : holes) {
            String holeNickname = holeNicknameService.getHoleNickname(hole.getId(), hole.getPosterId());
            hole.setNickname(holeNickname);
        }
        return holes;
    }
}
