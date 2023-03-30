package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.dao.NoticeMapper;
import site.zhangerfa.pojo.Notice;
import site.zhangerfa.pojo.Page;
import site.zhangerfa.service.*;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Resource
    private NoticeMapper noticeMapper;

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
    public List<Notice> getReadNoticesForUser(String stuId, Page page) {
        page.completePage(getNumOfReadNotice(stuId));
        return noticeMapper.selectReadNoticesForUser(stuId, 0, page.getOffset(), page.getLimit());
    }

    @Override
    public List<Notice> getReadNoticesForUser(String stuId, int actionType, Page page) {
        page.completePage(getNumOfReadNotice(stuId, actionType));
        return noticeMapper.selectReadNoticesForUser(stuId, actionType, page.getOffset(), page.getLimit());
    }

    @Override
    public List<Notice> getUnreadNoticesForUser(String stuId, Page page) {
        page.completePage(getNumOfReadNotice(stuId));
        return noticeMapper.selectUnreadNoticesForUser(stuId, 0, page.getOffset(), page.getLimit());
    }

    @Override
    public List<Notice> getUnreadNoticesForUser(String stuId, int actionType, Page page) {
        page.completePage(getNumOfReadNotice(stuId, actionType));
        return noticeMapper.selectUnreadNoticesForUser(stuId, actionType, page.getOffset(), page.getLimit());
    }

    @Override
    public boolean updateNoticeById(int id) {
        return noticeMapper.updateStatusById(id) > 0;
    }

    @Override
    public int getNumOfReadNotice(String stuId) {
        return noticeMapper.getNumOfReadNotice(stuId, -1);
    }

    @Override
    public int getNumOfReadNotice(String stuId, int actionType) {
        return noticeMapper.getNumOfReadNotice(stuId, actionType);
    }

    @Override
    public int getNumOfUnreadNotice(String stuId) {
        return noticeMapper.getNumOfUnreadNotice(stuId, -1);
    }

    @Override
    public int getNumOfUnreadNotice(String stuId, int actionType) {
        return noticeMapper.getNumOfUnreadNotice(stuId, actionType);
    }
}
