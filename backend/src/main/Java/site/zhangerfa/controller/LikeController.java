package site.zhangerfa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import site.zhangerfa.Constant.Constant;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.vo.LikeVo;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.controller.vo.UserVo;
import site.zhangerfa.service.LikeService;
import site.zhangerfa.service.NoticeService;
import site.zhangerfa.util.HostHolder;

import java.util.List;

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

    @Operation(summary = "点赞帖子", description = "调用此接口后改变当前登录用户对实体的点赞状态")
    @PutMapping("post/{postId}")
    public Result<LikeVo> likePost(int postType, @PathVariable int postId) {
        String stuId = hostHolder.getUser().getStuId();
        // 点赞
        likeService.like(postType, postId, stuId);
        // 通知被点赞实体的作者
        noticeService.addLikeNotice(postType, postId, stuId);
        // 查询点赞数量
        int likeCount = likeService.getLikeCount(postType, postId);
        // 查询当前用户对此实体的点赞状态
        int likeStatus = likeService.getLikeStatus(stuId, postType, postId);
        // 封装返回
        return new Result<>(Code.UPDATE_OK, new LikeVo(likeCount, likeStatus));
    }

    @Operation(summary = "点赞卡片", description = "更改当前用户对卡片的感兴趣或不感兴趣的状态")
    @PutMapping("/card/{cardId}")
    public Result<Boolean> likeCard(@PathVariable int cardId,
                                    @Parameter(description = "点赞状态：1-感兴趣，0-不感兴趣") int likeStatus) {
        String stuId = hostHolder.getUser().getStuId();
        // 点赞
        likeService.likeWithStatus(Constant.ENTITY_TYPE_CARD, cardId, stuId, likeStatus);
        return new Result<>(Code.UPDATE_OK, true);
    }

    @Operation(summary = "查询点赞用户列表", description = "查询指定实体的点赞用户列表")
    @GetMapping("/userList")
    public Result<List<UserVo>> getLikeUserListForCard(int entityType, int entityI) {
        List<UserVo> likeUsers = likeService.getLikeUsers(entityType, entityI);
        return new Result<>(Code.GET_OK, likeUsers);
    }

    @Operation(summary = "查询实体的点赞数量", description = "查询指定实体的被点赞数量")
    @GetMapping("/count")
    public Result<Integer> getLikeCountForPost(int entityType, int entityId) {
        return new Result<>(Code.GET_OK, likeService.getLikeCount(entityType, entityId));
    }

    @Operation(summary = "查询用户对实体的点赞状态", description = "查询用户对特定实体的点赞状态")
    @GetMapping("/status")
    public Result<Integer> getLikeStatusForPost(int entityType, int entityId) {
        String stuId = hostHolder.getUser().getStuId();
        return new Result<>(Code.GET_OK, likeService.getLikeStatus(stuId, entityType, entityId));
    }
}
