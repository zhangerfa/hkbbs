package site.zhangerfa.service;

import site.zhangerfa.controller.vo.UserVo;
import site.zhangerfa.entity.Entity;

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
    void like(Entity entity, String stuId);

    /**
     * 点赞，调用此接口后改变当前用户对实体的点赞状态
     *      将点赞状态设置为status
     * @param entity 被点赞实体
     * @param stuId
     * @param status
     */
    void likeWithStatus(Entity entity, String stuId, int status);

    /**
     * 获取指定实体的被赞数量
     * @return
     */
    int getLikeCount(Entity entity);

    /**
     * 获取用户对特定实体的点赞状态
     * @param entity 被点赞实体
     * @return 1 - 点赞/感兴趣，0 - 未点赞/不感兴趣
     */
    int getLikeStatus(String stuId, Entity entity);

    List<UserVo> getLikeUsers(Entity entity);
}
