package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.dao.NoticeMapper;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Notice;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.*;
import site.zhangerfa.util.Constant;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private UserService userService;
    @Resource
    private HoleService holeService;
    @Resource
    private HoleNicknameService holeNicknameService;
    @Resource
    private CardService cardService;
    @Resource
    private CommentService commentService;

    @Override
    public boolean add(Notice notice) {
        int insertNotice = noticeMapper.insertNotice(notice);
        return insertNotice > 0;
    }

    @Override
    public Notice getNoticeById(int id) {
        return noticeMapper.selectNoticeById(id);
    }

    @Override
    public List<Notice> getNoticesForUser(String stuId, int offset, int limit) {
        return noticeMapper.selectNoticesForUser(stuId, 0, offset, limit);
    }

    @Override
    public List<Notice> getNoticesForUser(String stuId, int actionType, int offset, int limit) {
        return noticeMapper.selectNoticesForUser(stuId, actionType, offset, limit);
    }

    @Override
    public String getNotice(Notice notice) {
        int actionType = notice.getActionType();
        int entityType = notice.getEntityType(); // 动作指向实体的类型
        int entityId = notice.getEntityId(); // 动作指向实体的id
        // 发出动作的用户
        User actionUser = userService.getUserByStuId(notice.getActionUserId());

        // 如何称呼动作发出者
        String username = "";
        if (entityType == Constant.ENTITY_TYPE_CARD || entityType == Constant.ENTITY_TYPE_COMMENT){
            // 评论人的用户名
            username = actionUser.getUsername();
        } else if (entityType == Constant.ENTITY_TYPE_HOLE || entityType == Constant.ENTITY_TYPE_HOLE_COMMENT) {
            // 评论人的树洞昵称
            username = holeNicknameService.getHoleNickname(entityId, actionUser.getStuId());
        }

        // 动作指向实体的内容
        String content = "";
        if (entityType == Constant.ENTITY_TYPE_CARD){
            // 被评论卡片的标题
            content = cardService.getCardById(entityId).getTitle();
        } else if (entityType == Constant.ENTITY_TYPE_HOLE) {
            // 被评论树洞的标题
            content = holeService.getHoleById(entityId).getTitle();
        } else if (entityType == Constant.ENTITY_TYPE_COMMENT || entityType == Constant.ENTITY_TYPE_HOLE_COMMENT) {
            // 被评论的内容
            content = commentService.getCommentById(entityId).getContent();
            // 被评论内容最长展示十五字
            if (content.length() > 15){
                content = content.substring(0, 15) + "...";
            }
        }

        // 评论内容
        Comment comment = commentService.getCommentById(notice.getCommentId());
        String commentStr = comment == null? "该评论已删除": comment.getContent();

        return username + "评论了\"" + content + "\"\n" + commentStr;
    }

    @Override
    public int getNumOfNotice(String stuId) {
        return noticeMapper.getNumOfNotice(stuId, null);
    }

    @Override
    public int getNumOfNotice(String stuId, int actionType) {
        return noticeMapper.getNumOfNotice(stuId, actionType);
    }

    @Override
    public int getNumOfUnreadNotice(String stuId) {
        return noticeMapper.getNumOfUnreadNotice(stuId, null);
    }

    @Override
    public int getNumOfUnreadNotice(String stuId, int actionType) {
        return noticeMapper.getNumOfUnreadNotice(stuId, actionType);
    }
}
