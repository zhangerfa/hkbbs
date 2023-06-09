package site.zhangerfa.service;

import site.zhangerfa.controller.tool.UserDTO;

import java.util.List;

/**
 * 点赞
 */
public interface LikeService {
    /**
     * 点赞，调用此接口后改变当前用户对实体的点赞状态
     *     如果已经点赞，则取消点赞；如果没有点赞，则点赞
     * @return
     */
    void like(int entityType, int entityId, String stuId);

    /**
     * 点赞，调用此接口后改变当前用户对实体的点赞状态
     *      将点赞状态设置为status
     * @param entityType
     * @param entityId
     * @param stuId
     * @param status
     */
    void likeWithStatus(int entityType, int entityId, String stuId, int status);

    /**
     * 获取指定实体的被赞数量
     * @return
     */
    int getLikeCount(int entityType, int entityId);

    /**
     * 获取用户对特定实体的点赞状态
     * @param stuId 点赞用户学号
     * @param entityType 被点赞实体类型
     * @param entityId 被点赞实体id
     * @return 1 - 点赞/感兴趣，0 - 未点赞/不感兴趣
     */
    int getLikeStatus(String stuId, int entityType, int entityId);

    List<UserDTO> getLikeUsers(int entityType, int entityId);
}
