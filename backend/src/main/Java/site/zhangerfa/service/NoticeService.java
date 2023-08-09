package site.zhangerfa.service;

import site.zhangerfa.entity.Comment;
import site.zhangerfa.entity.Entity;
import site.zhangerfa.entity.Notice;

import java.util.List;

public interface NoticeService {
    /**
     * 添加评论通知
     * @param comment 评论
     */
    void addCommentNotice(Comment comment);

    /**
     * 添加点赞通知
     * @param entityType 被点赞实体的类型
     * @param entityId 被点赞实体的id
     * @param stuId 点赞者的学号
     */
    void addLikeNotice(Entity entity, String stuId);

    Notice getNoticeById(int id);

    /**
     * 获取用户指定类型的已读通知
     * @param stuId
     * @param actionType 通知类型，-1表示所有类型
     * @return
     */
    List<Notice> getReadNoticesForUser(String stuId, int actionType, int currentPage, int pageSize);

    /**
     * 获取该用户特定类型的通知数量
     * @param stuId
     * @param actionType 通知类型, -1表示所有类型
     * @return
     */
    int getNumOfReadNotice(String stuId, int actionType);

    /**
     * 获取该用户特定类型的未读通知数量
     * @param stuId
     * @param actionType 通知类型
     * @return
     */
    int getNumOfUnreadNotice(String stuId, int actionType);

    /**
     * 获取一页用户指定类型的未读通知
     * @param stuId
     * @param actionType
     * @return
     */
    List<Notice> getUnreadNoticesForUser(String stuId, int actionType, int currentPage, int pageSize);

    /**
     * 将传入id对应的通知标为已读
     * @param id
     * @return
     */
    boolean updateNoticeById(int id);
}
