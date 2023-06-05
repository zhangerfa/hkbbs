package site.zhangerfa.util;

import site.zhangerfa.Constant.Constant;
import site.zhangerfa.Constant.RedisConstant;

/**
 * RedisKey生成工具
 */
public class RedisKeyUtil {
    public static String getRegisterCodeKey(String stuId){
        return RedisConstant.LOGIN_CODE_PREFIX + stuId;
    }

    public static String getUserKey(String stuId){
        return RedisConstant.USER_PREFIX + stuId;
    }

    /**
     * 获取被点赞指定用户的其他用户学号集合的key
     * @param entityType 实体类型
     * @param entityId 实体id
     * @return
     */
    public static String getUserLikedSetKey(int entityType, int entityId){
        String entityTyeName = Constant.getEntityTyeName(entityType);
        // user:liked:set:entityType:entityId --> set(stuId)
        return RedisConstant.USER_LIKED_SET + entityTyeName + ":" + entityId;
    }

    /**
     * 获取被点赞指定用户的其他用户学号集合长度的key
     * @param entityType 实体类型
     * @param entityId 实体id
     * @return
     */
    public static String getUserLikedCountKey(int entityType, int entityId) {
        String entityTyeName = Constant.getEntityTyeName(entityType);
        // user:liked:count:entityType:entityId
        return RedisConstant.USER_LIKED_COUNT + entityTyeName + ":" + entityId;
    }

}
