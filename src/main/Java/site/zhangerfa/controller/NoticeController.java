package site.zhangerfa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.pojo.Notice;
import site.zhangerfa.service.NoticeService;
import site.zhangerfa.util.HostHolder;

import java.util.List;

@RestController
@Tag(name = "通知")
@RequestMapping("/notices")
public class NoticeController {
    @Resource
    private HostHolder hostHolder;
    @Resource
    private NoticeService noticeService;

    @GetMapping("")
    @Operation(summary = "获取通知")
    public Result<String> getNotices(){
        String res = "";
        // 查询所有未读的通知
        List<Notice> notices = noticeService.getNoticesForUser(hostHolder.getUser().getStuId(), 0, 10);
        for (Notice notice : notices) {
            res += noticeService.getNotice(notice);
            res += "\n";
        }
        return new Result<>(Code.GET_OK, res, "查询成功");
    }
}
