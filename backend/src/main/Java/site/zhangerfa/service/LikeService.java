package site.zhangerfa.service;

/**
 * 点赞
 */
public interface LikeService {
    /**
     * 点赞，调用此接口后改变点赞状态
     * @param stuId      点赞用户学号
     * @param entityType 被点赞实体类型
     * @param entityId   被点赞实体id
     * @return
     */
    void like(String stuId, int entityType, int entityId);

    /**
     * 获取实体被赞数量
     * @param entityType
     * @param entityId
     * @return
     */
    int getLikedCount(int entityType, int entityId);

    /**
     * 获取用户对特定实体的点赞状态
     * @param stuId 点赞用户学号
     * @param entityType 被点赞实体类型
     * @param entityId 被点赞实体id
     * @return 1 - 点赞，0 - 未点赞
     */
    int getLikedStatus(String stuId, int entityType, int entityId);
}
