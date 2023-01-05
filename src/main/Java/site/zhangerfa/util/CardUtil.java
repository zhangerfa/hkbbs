package site.zhangerfa.util;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import site.zhangerfa.pojo.Card;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.CommentService;
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
     * @param comments
     * @return
     */
    public List<Map> completeComment(List<Comment> comments){
        if (comments == null) return null;
        List<Map> res = new ArrayList<>();
        for (Comment comment : comments) {
            HashMap<String, Object> map = new HashMap<>();
            res.add(map);
            map.put("content", comment.getContent());
            String username = userService.getUsernameByStuId(comment.getStuId());
            map.put("username", username);
            // 查询回复该评论的评论
            List<Comment> comments4Comment = commentService.getCommentsForEntity(Constant.ENTITY_TYPE_COMMENT,
                    comment.getEntityId(), 0, Integer.MAX_VALUE); // 查询所有回复该回复的回复
            List<Map> listOfcomment = new ArrayList<>();
            map.put("comment4comment", listOfcomment);
            if (comments4Comment != null){
                for (Comment comment4Comment : comments4Comment) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("content", comment4Comment.getContent());
                    hashMap.put("username",
                            userService.getUsernameByStuId(comment4Comment.getStuId()));
                    // 回帖的回帖中可能包含楼层之间的回复，不一定是回复楼主
                    // 获取发出被回复的用户的用户信息(target中存储了用户的学号)
                    User user;
                    if (comment4Comment.getTargetId() != "0"){
                        user = userService.getUserByStuId(comment4Comment.getTargetId());
                    }else {
                        user = null;
                    }
                    hashMap.put("target", user);
                    hashMap.put("numOfComment",
                            commentService.getNumOfCommentsForEntity(Constant.ENTITY_TYPE_COMMENT, comment4Comment.getId()));
                    listOfcomment.add(hashMap);
                }
            }
        }
        return res;
    }
}
