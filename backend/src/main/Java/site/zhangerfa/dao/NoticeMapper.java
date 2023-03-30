package site.zhangerfa.dao;

import org.apache.ibatis.annotations.Mapper;
import site.zhangerfa.pojo.Notice;

import java.util.List;

@Mapper
public interface NoticeMapper {
    int insertNotice(Notice notice);

    Notice selectNoticeById(int id);

    /**
     * 获取用户的某类型的所有通知
     *    当 actionType为 0 时查询该用户指定数量的通知
     * @param stuId 学号
     * @param actionType 动作类型
     * @return
     */
    List<Notice> selectReadNoticesForUser(String stuId, int actionType, int offset, int limit);

    /**
     * 获取用户的某类型的所有通知
     *    当 actionType为 0 时查询该用户指定数量的通知
     * @param stuId 学号
     * @param actionType 动作类型
     * @return
     */
    List<Notice> selectUnreadNoticesForUser(String stuId, int actionType, int offset, int limit);

    /**
     * 查询某类主题的通知数量
     * @param actionType 传入-1则获取所有类型通知
     * @return
     */
    int getNumOfReadNotice(String stuId, Integer actionType);

    /**
     * 查询未读的通知数量
     * @param stuId
     * @param actionType 当传入为 null时，查询所有未读通知数量
     *                   不为 null时查询对应主题的未读通知数量
     * @return
     */
    int getNumOfUnreadNotice(String stuId, Integer actionType);

    /**
     * 将传入 id 对应的通知标记为已读
     * @param id
     * @return
     */
    int updateStatusById(int id);
}
