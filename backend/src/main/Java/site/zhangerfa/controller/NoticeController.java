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
import site.zhangerfa.pojo.Page;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.service.NoticeService;
import site.zhangerfa.service.PostService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.util.Constant;
import site.zhangerfa.util.HostHolder;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "通知")
@RequestMapping("/notices")
public class NoticeController {
    @Resource
    private HostHolder hostHolder;
    @Resource
    private NoticeService noticeService;
    @Resource
    private UserService userService;
    @Resource(name = "postServiceImpl")
    private PostService postService;
    @Resource
    private CommentService commentService;

    @GetMapping("/")
    @Operation(summary = "获取一页通知", description = "获取当前会话用户的一页通知，当未读通知数量≥一页通知的个数时返回最新的一页未读通知，" +
            "当未读通知不满一页时使用最新的已读通知填充")
    @Parameters({
            @Parameter(name = "currentPage", description = "当前页码", required = true),
            @Parameter(name = "pageSize", description = "当前页要展示的评论数量", required = true),
    })
    public Result<List<NoticeInfo>> getNotices(@Parameter(hidden = true) Page page){
        // 查询所有未读的通知
        if (hostHolder.getUser() == null) return new Result<>(Code.GET_ERR, null, "用户未登录");
        String stuId = hostHolder.getUser().getStuId();
        List<Notice> notices = noticeService.getUnreadNoticesForUser(stuId, page);
        if (notices.size() < page.getPageSize()){
            // 当未读通知不满一页时使用最新的已读通知填充
            List<Notice> readNotices = noticeService.getReadNoticesForUser(stuId,
                    new Page(1, page.getPageSize() - notices.size()));
            notices.addAll(readNotices);
        }
        List<NoticeInfo> noticeInfos = new ArrayList<>();
        for (Notice notice : notices) {
            NoticeInfo noticeInfo = new NoticeInfo(userService.getUserByStuId(notice.getActionUserId()));
            noticeInfo.setEntityType(Constant.getEntityTye(notice.getEntityType()));
            // 被动作指向实体的内容，如果是帖子为标题，评论则为内容
            if (notice.getEntityType() == Constant.ENTITY_TYPE_COMMENT){
                noticeInfo.setEntityContent(commentService.getCommentById(notice.getEntityId()).getContent());
            }else {
                noticeInfo.setEntityContent(postService.getPostById(notice.getEntityId()).getTitle());
            }
            // 动作内容，如果为评论则为评论内容，动作无具体内容则为空字符串
            if (notice.getActionType() == Constant.ACTION_COMMENT){
                noticeInfo.setActionContent(commentService.getCommentById(notice.getCommentId()).getContent());
            }else {
                noticeInfo.setActionContent("");
            }
            noticeInfo.setPostId(notice.getEntityOwnerId());

            noticeInfos.add(noticeInfo);
        }
        return new Result<>(Code.GET_OK, noticeInfos, "查询成功");
    }

    @Operation(summary = "通知已读", description = "将用户已读的通知的id以数组形式返回")
    @PutMapping("/read")
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
        if (actionType == null){
             numOfUnreadNotice = noticeService.getNumOfUnreadNotice(stuId);
        }else {
            numOfUnreadNotice = noticeService.getNumOfUnreadNotice(stuId, actionType);
        }
        return new Result<>(Code.GET_OK, numOfUnreadNotice);
    }
}
