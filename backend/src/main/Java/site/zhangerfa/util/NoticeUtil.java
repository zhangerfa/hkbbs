package site.zhangerfa.util;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import site.zhangerfa.controller.tool.NoticeInfo;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Notice;
import site.zhangerfa.pojo.Post;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.service.HoleNicknameService;
import site.zhangerfa.service.PostService;
import site.zhangerfa.Constant.Constant;
import site.zhangerfa.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class NoticeUtil {
    @Resource
    private PostService postService;
    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;
    @Resource
    private HoleNicknameService holeNicknameService;

    /**
     * 将评论数据包装为notice对象
     * @param comment
     * @param postId
     * @return
     */
    public Notice getNotice(Comment comment, int postId) {
        Notice notice = new Notice();
        // 被评论人的学号
        String receivingUserId;
        if (comment.getEntityType() == Constant.ENTITY_TYPE_COMMENT){
            receivingUserId = commentService.getCommentById(comment.getEntityId()).getPosterId();
        } else {
            Post post = postService.getPostById(comment.getEntityId());
            if (post == null)
                return null;
            receivingUserId = post.getPosterId();
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
            noticeInfos.add(noticeInfo);
            // 动作发出者信息
            // 如果动作发生实体为树洞，动作发出者信息改为匿名
            if (notice.getEntityType() == Constant.ENTITY_TYPE_HOLE){
                noticeInfo.setActionUsername(holeNicknameService.getHoleNickname(
                        notice.getEntityOwnerId(), notice.getActionUserId()));
                noticeInfo.setActionUserHeadUrl("https://zhangerfa-1316526930.cos.ap-guangzhou.myqcloud.com/hkbbs/default.jpg");
            }else{
                User user = userService.getUserByStuId(notice.getActionUserId());
                noticeInfo.setActionUsername(user.getUsername());
                noticeInfo.setActionUserHeadUrl(user.getHeaderUrl());
            }
            // 被动作指向实体的类型
            noticeInfo.setEntityType(Constant.getEntityTyeName(notice.getEntityType()));
            // 被动作指向实体的内容，如果是帖子为标题，评论则为内容
            if (notice.getEntityType() == Constant.ENTITY_TYPE_COMMENT){
                noticeInfo.setEntityContent(commentService.getCommentById(notice.getEntityId()).getContent());
            }else {
                noticeInfo.setEntityContent(postService.getPostById(notice.getEntityId()).getTitle());
            }
            // 动作内容，如果为评论则为评论内容，动作无具体内容则为空字符串
            if (notice.getActionType() == Constant.ACTION_COMMENT){
                noticeInfo.setActionContent(commentService.getCommentById(notice.getCommentId()).getContent());
            }else {
                noticeInfo.setActionContent("");
            }
            noticeInfo.setPostId(notice.getEntityOwnerId());
        }
        return noticeInfos;
    }
}
