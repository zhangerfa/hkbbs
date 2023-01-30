package site.zhangerfa.controller;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.zhangerfa.pojo.Notice;
import site.zhangerfa.service.NoticeService;
import site.zhangerfa.util.HostHolder;

import java.util.List;

@Controller
@RequestMapping("/notices")
public class NoticeController {
    @Resource
    private HostHolder hostHolder;
    @Resource
    private NoticeService noticeService;

    @RequestMapping("")
    @ResponseBody
    public String getNotices(){
        String res = "";
        // 查询所有未读的通知
        List<Notice> notices = noticeService.getNoticesForUser(hostHolder.getUser().getStuId(), 0, 10);
        for (Notice notice : notices) {
            res += noticeService.getNotice(notice);
            res += "\n";
        }
        return res;
    }
}
