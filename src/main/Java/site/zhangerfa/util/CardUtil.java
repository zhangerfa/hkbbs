package site.zhangerfa.util;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.service.UserService;

import java.util.*;

@Component
public class CardUtil {
    @Resource
    private UserService userService;

    @Resource
    private CommentService commentService;

    // 补全卡片信息
    public List<Map> completeCard(List<Card> cards){
        List<Map> res = new ArrayList<>();
        for (Card card : cards) {
            String username = userService.getUsernameByStuId(card.getPosterId());
            Map<String, Object> map = new HashMap<>();
            map.put("card", card);
            map.put("username", username);
            res.add(map);
        }
        return res;
    }

    /**
     * 补全评论信息
     * @param comments 评论对象集合
     * @return 最终返回一个list，每个值是一个map，包含一个评论的信息
     *            每个map包含，username、content、createTime、comments、commentNum
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
            // 评论人的用户名
            String username = userService.getUsernameByStuId(comment.getStuId());
            map.put("username", username);
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
     *            每个map包含，username、content、createTime、commentNum
     *         节点顺序是树的深度优先遍历
     */

    public List<Map> serializeComments(List<Map> completedComments){
        if (completedComments == null) return null;
        List<Map> res = new ArrayList<>();
        for (Map comment : completedComments) {
            Deque<Map> deque = new ArrayDeque<>();
            // 第一层评论作为根节点入队
            deque.addLast(comment);
            int count = 1; // 记录当前层还有几条评论未遍历
            int deep = 0; // 记录当前节点位于第几层
            while (!deque.isEmpty()){
                Map<String, Object> curComment = deque.getFirst();
                // 子节点入队
                List<Map> subComments = (List<Map>) curComment.get("comments");
                if (subComments.size() > 0){
                    for (Map subComment : subComments) {
                        deque.addLast(subComment);
                    }
                }
                // 遍历当前节点
                curComment.remove("comments");
                curComment.put("deep", deep); // 该评论是第几层评论
                res.add(curComment);
                // 当前节点出队
                deque.removeFirst();
                if (--count == 0){
                    // 这层节点遍历完毕时，队列中全部为下层节点
                    deep++;
                    count = deque.size();
                }
            }
        }
        return res;
    }
}
