package site.zhangerfa.service;

import site.zhangerfa.pojo.Notice;
import site.zhangerfa.pojo.Page;

import java.util.List;

public interface NoticeService {
    /**
     * 添加通知
     * @param notice
     * @return
     */
    boolean add(Notice notice);

    Notice getNoticeById(int id);

    /**
     * 获取用户的指定范围的已读通知
     * @param stuId
     * @return
     */
    List<Notice> getReadNoticesForUser(String stuId, Page page);

    /**
     * 获取用户指定类型的已读通知
     * @param stuId
     * @param actionType
     * @return
     */
    List<Notice> getReadNoticesForUser(String stuId, int actionType, Page page);

    /**
     * 获取该用户所有的已读通知数量
     * @param stuId
     * @return
     */
    int getNumOfReadNotice(String stuId);

    /**
     * 获取该用户特定类型的通知数量
     * @param stuId
     * @return
     */
    int getNumOfReadNotice(String stuId, int actionType);

    /**
     * 获取该用户所有未读通知的数量
     * @param stuId
     * @return
     */
    int getNumOfUnreadNotice(String stuId);

    /**
     * 获取该用户特定类型的未读通知数量
     * @param stuId
     * @param actionType 通知类型
     * @return
     */
    int getNumOfUnreadNotice(String stuId, int actionType);

    /**
     * 获取用户一页未读通知
     * @param stuId
     * @param page
     * @return
     */
    List<Notice> getUnreadNoticesForUser(String stuId, Page page);

    /**
     * 获取一页用户指定类型的未读通知
     * @param stuId
     * @param actionType
     * @param page
     * @return
     */
    List<Notice> getUnreadNoticesForUser(String stuId, int actionType, Page page);

    /**
     * 将传入id对应的通知标为已读
     * @param id
     * @return
     */
    boolean updateNoticeById(int id);
}
