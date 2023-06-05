package site.zhangerfa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.dao.NoticeMapper;
import site.zhangerfa.event.NoticeProducer;
import site.zhangerfa.service.NoticeService;
import site.zhangerfa.pojo.Notice;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private NoticeProducer noticeProducer;

    @Override
    public void add(String noticeType, Notice notice) {
        noticeProducer.addNotice(noticeType, notice);
    }

    @Override
    public Notice getNoticeById(int id) {
        return noticeMapper.selectById(id);
    }

    @Override
    public List<Notice> getReadNoticesForUser(String stuId, int actionType, int currentPage, int pageSize) {
        return getNoticesForUser(stuId, actionType, currentPage, pageSize, 1);
    }

    @Override
    public List<Notice> getUnreadNoticesForUser(String stuId, int actionType, int currentPage, int pageSize) {
        return getNoticesForUser(stuId, actionType, currentPage, pageSize, 0);
    }

    @Override
    public boolean updateNoticeById(int id) {
        return noticeMapper.updateStatusById(id) > 0;
    }

    @Override
    public int getNumOfReadNotice(String stuId, int actionType) {
        return getNumOfNotices(stuId, actionType, 1);
    }

    @Override
    public int getNumOfUnreadNotice(String stuId, int actionType) {
        return getNumOfNotices(stuId, actionType, 0);
    }

    /**
     * 获取该用户特定类型的通知数量
     * @param stuId
     * @param actionType 通知类型, -1表示所有类型
     * @param status 通知状态，0表示未读，1表示已读，-1表示所有状态
     * @return
     */
    private int getNumOfNotices(String stuId, int actionType, int status) {
        return noticeMapper.selectCount(new LambdaQueryWrapper<Notice>()
                .eq(Notice::getReceivingUserId, stuId)
                .eq(actionType != -1, Notice::getActionType, actionType)
                .eq(status != -1, Notice::getStatus, status)).intValue();
    }

    /**
     * 获取一页用户指定类型的已读或未读通知
     * @param stuId
     * @param actionType 通知类型，-1表示所有类型
     * @param currentPage
     * @param pageSize
     * @param status 通知状态，0表示未读，1表示已读，-1表示所有状态
     * @return
     */
    private List<Notice> getNoticesForUser(String stuId, int actionType, int currentPage, int pageSize, int status) {
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notice::getReceivingUserId, stuId)
                .eq(actionType != -1, Notice::getActionType, actionType)
                .eq(status != -1, Notice::getStatus, status);
        System.out.println(wrapper.getSqlSegment());
        return noticeMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(currentPage, pageSize), wrapper).getRecords();
    }
}
