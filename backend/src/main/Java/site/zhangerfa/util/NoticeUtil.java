package site.zhangerfa.util;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import site.zhangerfa.Constant.Constant;
import site.zhangerfa.controller.tool.NoticeInfo;
import site.zhangerfa.controller.tool.UserDTO;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Notice;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.service.PostService;

import java.util.ArrayList;
import java.util.List;

@Component
public class NoticeUtil {
    @Resource
    private PostService postService;
    @Resource
    private CommentService commentService;
    @Resource
    private EntityUtil entityUtil;

    /**
     * 将评论数据包装为notice对象
     * @param comment
     * @return
     */
    public Notice getNotice(Comment comment) {
        Notice notice = new Notice(comment.getPosterId(),
                comment.getEntityType(), comment.getEntityId(),
                Constant.ACTION_COMMENT, comment.getOwnerType());
        // 被评论人的学号
        String receivingUserId = entityUtil.getOwnerStuId(comment.getEntityType(), comment.getEntityId());
        notice.setReceivingUserId(receivingUserId);
        // 评论的id
        notice.setActionId(comment.getId());
        return notice;
    }

    /**
     * 获取点赞notice
     * @param entityType 被点赞实体类型
     * @param entityId 被点赞实体id
     * @param posterId 点赞者学号
     * @return
     */
    public Notice getLikeNotice(int entityType, int entityId, String posterId){
        // 点赞通知
        Notice notice = new Notice(posterId, entityType, entityId, Constant.ACTION_LIKE);
        // 被点赞实体的所有者即为接收通知的人
        notice.setReceivingUserId(entityUtil.getOwnerStuId(entityType, entityId));
        return notice;
    }

    /**
     * 获取notice详细信息
     * @param notices
     * @return
     */
    public List<NoticeInfo> getNoticeInfos(List<Notice> notices){
        List<NoticeInfo> noticeInfos = new ArrayList<>();
        for (Notice notice : notices) {
            NoticeInfo noticeInfo = new NoticeInfo();
            noticeInfo.setId(notice.getId());
            noticeInfo.setCreateTime(notice.getCreateTime());
            noticeInfos.add(noticeInfo);
            // 动作发出者信息
            UserDTO userDTO = entityUtil.getUserDTO(notice.getOwnerType(), notice.getActionUserId());
            noticeInfo.setActionUser(userDTO);
            // 被动作指向实体的类型
            noticeInfo.setEntityType(Constant.getEntityTyeName(notice.getEntityType()));
            // 被动作指向实体的内容，如果是帖子为标题，评论则为内容
            if (notice.getEntityType() == Constant.ENTITY_TYPE_COMMENT){
                noticeInfo.setEntityContent(commentService.getCommentById(notice.getEntityId()).getContent());
            }else {
                noticeInfo.setEntityContent(postService.getPostById(notice.getEntityId()).getTitle());
            }
            // 动作内容
            if (notice.getActionType() == Constant.ACTION_COMMENT){
                Comment comment = commentService.getCommentById(notice.getActionId());
                noticeInfo.setActionContent(comment.getContent());
            }
            }
        return noticeInfos;
    }
}
