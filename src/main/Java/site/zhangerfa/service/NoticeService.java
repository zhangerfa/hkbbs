package site.zhangerfa.service;

import site.zhangerfa.pojo.Notice;

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
     * 获取用户的指定范围的通知
     * @param stuId
     * @return
     */
    List<Notice> getNoticesForUser(String stuId, int offset, int limit);

    /**
     * 获取用户该类型的所有通知
     * @param stuId
     * @param actionType
     * @return
     */
    List<Notice> getNoticesForUser(String stuId, int actionType, int offset, int limit);

    /**
     * 获取通知字符串
     * @param notice
     * @return
     */
    String getNotice(Notice notice);

    /**
     * 获取该用户所有的通知数量
     * @param stuId
     * @return
     */
    int getNumOfNotice(String stuId);

    /**
     * 获取该用户所有的通知数量
     * @param stuId
     * @return
     */
    int getNumOfNotice(String stuId, int actionType);

    /**
     * 获取该用户所有未读通知的数量
     * @param stuId
     * @return
     */
    int getNumOfUnreadNotice(String stuId);

    /**
     * 获取该用户所有传入类型的通知数量
     * @param stuId
     * @param actionType 通知类型
     * @return
     */
    int getNumOfUnreadNotice(String stuId, int actionType);
}
