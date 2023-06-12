package site.zhangerfa.util;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import site.zhangerfa.Constant.Constant;
import site.zhangerfa.controller.vo.UserVo;
import site.zhangerfa.entity.User;
import site.zhangerfa.service.*;

@Component
public class EntityUtil {
    @Resource
    private PostService postService;
    @Resource
    private CommentService commentService;
    @Resource
    private CardService cardService;
    @Resource
    private HoleNicknameService holeNicknameService;
    @Resource
    private UserService userService;

    /**
     * 获取实体的所有者学号
     * @param entityType 实体类型
     * @param entityId 实体id
     * @return 所有者学号, 若实体不存在则返回null
     */
    public String getOwnerStuId(int entityType, int entityId) {
        if (entityType == Constant.ENTITY_TYPE_POST || entityType == Constant.ENTITY_TYPE_HOLE)
            return postService.getPostById(entityId).getPosterId();
        else if (entityType == Constant.ENTITY_TYPE_COMMENT)
            return commentService.getCommentById(entityId).getPosterId();
        else if (entityType == Constant.ENTITY_TYPE_CARD)
            return cardService.getById(entityId).getPosterId();
        return null;
    }

    /**
     * 获取实体的所有者信息，包括昵称、头像URL，如果实体为树洞或在树洞中的评论，则信息改为匿名信息
     * @param entityType 实体类型
     * @param entityId 实体id
     * @return
     */
    public UserVo getOwnerInfo(int entityType, int entityId) {
        // 获取实体所有者学号
        String ownerStuId = getOwnerStuId(entityType, entityId);
        // 如果实体为树洞，或如果实体为评论且评论属于树洞，评论者信息改为匿名
        String username;
        String headerUrl;
        if (entityType == Constant.ENTITY_TYPE_HOLE ||
                (entityType == Constant.ENTITY_TYPE_COMMENT
                        && commentService.getCommentById(entityId).getOwnerType()
                        == Constant.ENTITY_TYPE_HOLE)) {
            holeNicknameService.getHoleNickname(entityId, ownerStuId);
            username = holeNicknameService.getHoleNickname(entityId, ownerStuId);
            headerUrl = "https://zhangerfa-1316526930.cos.ap-guangzhou.myqcloud.com/hkbbs/default.jpg";
        } else {
            username = userService.getUserByStuId(ownerStuId).getUsername();
            headerUrl = userService.getUserByStuId(ownerStuId).getHeaderUrl();
        }
        return new UserVo(username, headerUrl);
    }

    /**
     * 获取用户在不同类型实体中的基本信息，包括昵称、头像URL
     * @param postType 帖子类型
     * @param stuId 用户学号
     * @return
     */
    public UserVo getUserVo(int postType, String stuId) {
        String username;
        String headerUrl;
        User user = userService.getUserByStuId(stuId);
        if (postType == Constant.ENTITY_TYPE_HOLE){
            username = holeNicknameService.getHoleNickname(0, stuId);
            headerUrl = "https://zhangerfa-1316526930.cos.ap-guangzhou.myqcloud.com/hkbbs/default.jpg";
        }else{
            username = user.getUsername();
            headerUrl = user.getHeaderUrl();
        }
        return new UserVo(username, headerUrl);
    }

    /**
     * 将user对象转换为UserVo对象
     * @param user
     * @return
     */
    public static UserVo getUserVo(User user){
        return new UserVo(user.getUsername(), user.getHeaderUrl());
    }
}
