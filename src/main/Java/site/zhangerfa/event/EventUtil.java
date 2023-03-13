package site.zhangerfa.event;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Notice;
import site.zhangerfa.service.CardService;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.service.HoleService;
import site.zhangerfa.util.Constant;

@Component
public class EventUtil {
    @Resource
    private CardService cardService;
    @Resource
    private HoleService holeService;
    @Resource
    private CommentService commentService;

    public Notice getNotice(Comment comment, int postId) {
        Notice notice = new Notice();
        // 被评论人的学号
        String receivingUserId = null;
        if (comment.getEntityType() == Constant.ENTITY_TYPE_CARD){
            receivingUserId = cardService.getCardById(comment.getEntityId()).getPosterId();
        } else if (comment.getEntityType() == Constant.ENTITY_TYPE_HOLE) {
            receivingUserId = holeService.getHoleById(comment.getEntityId()).getPosterId();
        } else if (comment.getEntityType() == Constant.ENTITY_TYPE_COMMENT) {
            receivingUserId = commentService.getCommentById(comment.getEntityId()).getPosterId();
        }
        notice.setReceivingUserId(receivingUserId);
        notice.setActionType(Constant.ACTION_COMMENT);
        notice.setActionUserId(comment.getPosterId()); // 评论人学号
        notice.setCommentId(comment.getId());
        notice.setEntityType(comment.getEntityType());
        notice.setEntityId(comment.getEntityId());
        // 实体所在帖子的id
        notice.setEntityOwnerId(postId);

        return notice;
    }
}
