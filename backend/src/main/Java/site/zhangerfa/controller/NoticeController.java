package site.zhangerfa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.NoticeInfo;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.pojo.Notice;
import site.zhangerfa.service.NoticeService;
import site.zhangerfa.util.HostHolder;
import site.zhangerfa.util.NoticeUtil;

import java.util.List;
import java.util.Objects;

@RestController
@Tag(name = "通知")
@RequestMapping("/notices")
public class NoticeController {
    @Resource
    private HostHolder hostHolder;
    @Resource
    private NoticeService noticeService;
    @Resource
    private NoticeUtil noticeUtil;

    @GetMapping("/")
    @Operation(summary = "获取一页通知", description = "获取当前登录用户的一页通知，当未读通知数量≥一页通知的个数时返回最新的一页未读通知，" +
            "当未读通知不满一页时使用最新的已读通知填充。通知内容包括：做出动作用户对一个实体做了特定动作：要将信息：A对实体B做了动作C通知给用户D；" +
            "actionUsername + actionType + \"了您的\" + entityType + \"(\" +\n" +
            "        entityContent + \"):\" + actionContent" +
            "举例：东九小韭菜评论了您的帖子（今晚三国杀）:约呀")
    @Parameters({@Parameter(name = "currentPage", description = "当前页码"),
            @Parameter(name = "pageSize", description = "每页大小")})
    public Result<List<NoticeInfo>> getNotices(int currentPage, int pageSize){
        // 判断是否登录
        if (hostHolder.getUser() == null) return new Result<>(Code.GET_ERR, null, "用户未登录");
        String stuId = hostHolder.getUser().getStuId();
        // 获取未读通知数量
        List<Notice> notices = noticeService.getUnreadNoticesForUser(stuId, -1, currentPage, pageSize);
        // 补充通知信息
        List<NoticeInfo> noticeInfos = noticeUtil.getNoticeInfos(notices);
        return new Result<>(Code.GET_OK, noticeInfos, "查询成功");
    }

    @Operation(summary = "通知已读", description = "传入的通知都将被标记为已读")
    @PutMapping("/read")
    @Parameter(name = "ids", description = "已读通知的id集合")
    public Result<Boolean> readNotices(int[] ids){
        for (int id : ids) {
            noticeService.updateNoticeById(id);
        }
        return new Result<>(Code.UPDATE_OK, true, "全部已读");
    }

    @Operation(summary = "未读通知数", description = "获取当前会话用户的未读通知数量，" +
            "当传入actionType来指定通知类型时获取指定类型的未读通知数量")
    @GetMapping("/unread")
    public Result<Integer> getNumOfUnreadNotices(@RequestParam(required = false) Integer actionType){
        if (hostHolder.getUser() == null) return new Result<>(Code.GET_ERR, null, "用户未登录");
        int numOfUnreadNotice;
        String stuId = hostHolder.getUser().getStuId();
        numOfUnreadNotice = noticeService.getNumOfUnreadNotice(stuId, Objects.requireNonNullElse(actionType, -1));
        return new Result<>(Code.GET_OK, numOfUnreadNotice);
    }
}
