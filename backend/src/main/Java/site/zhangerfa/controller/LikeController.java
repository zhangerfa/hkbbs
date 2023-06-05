package site.zhangerfa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.zhangerfa.Constant.Constant;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.LikeDTO;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.pojo.Notice;
import site.zhangerfa.service.LikeService;
import site.zhangerfa.service.NoticeService;
import site.zhangerfa.util.HostHolder;
import site.zhangerfa.util.NoticeUtil;

@RestController
@Tag(name = "点赞", description = "帖子、树洞、评论点赞，卡片点感兴趣、不感兴趣")
@RequestMapping("/like")
public class LikeController {
    @Resource
    private LikeService likeService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private NoticeService noticeService;
    @Resource
    private NoticeUtil noticeUtil;

    @Operation(summary = "点赞", description = "调用此接口后改变当前登录用户对实体的点赞状态")
    @PutMapping("")
    public Result<LikeDTO> like(@Parameter(description = "实体类型") int entityType,
                                @Parameter(description = "实体Id") int entityId) {
        String stuId = hostHolder.getUser().getStuId();
        // 点赞
        likeService.like(stuId, entityType, entityId);
        // 通知被点赞实体的作者
        // TODO 取消点赞不通知——————点赞后先查询是否已有点赞通知，有则不通知
        Notice likeNotice = noticeUtil.getLikeNotice(entityType, entityId, stuId);
        noticeService.add(Constant.NOTICE_TYPE_LIKE, likeNotice);
        // 查询点赞数量
        int likeCount = likeService.getLikedCount(entityType, entityId);
        // 查询当前用户对此实体的点赞状态
        int likeStatus = likeService.getLikedStatus(stuId, entityType, entityId);
        // 封装返回
        return new Result<>(Code.UPDATE_OK, new LikeDTO(likeCount, likeStatus));
    }
}
