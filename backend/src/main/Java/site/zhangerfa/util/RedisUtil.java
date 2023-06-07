package site.zhangerfa.util;

import site.zhangerfa.Constant.Constant;

/**
 * Redis常量及RedisKey生成工具
 */
public class RedisUtil {
    /**
     * 验证码前缀
     */
    public static final String LOGIN_CODE_PREFIX = "login:code:";

    /**
     * 登录凭证前缀
     */
    public static final String LOGIN_TICKET_PREFIX = "login:ticket:";

    /**
     * 验证码过期时间
     */
    public static final int LOGIN_CODE_EXPIRE_MINUTE = 10;

    /**
     * 登录凭证过期时间
     */
    public static final int LOGIN_TICKET_EXPIRE_DAY = 7;

    /**
     * 被点赞指定用户的其他用户学号集合
     */
    public static final String LIKE_USER_SET = "like:userSet:";

    /**
     * 被点赞指定实体的点赞数
     */
    public static final String USER_LIKED_COUNT = "like:count:";

    public static String getRegisterCodeKey(String stuId){
        return LOGIN_CODE_PREFIX + stuId;
    }

    /**
     * 获取被点赞指定用户的其他用户学号集合的key
     * @param entityType 实体类型
     * @param entityId 实体id
     * @return
     */
    public static String getUserLikedSetKey(int entityType, int entityId){
        String entityTyeName = Constant.getEntityTyeName(entityType);
        // like:userSet:entityType:entityId --> set(stuId)
        return LIKE_USER_SET + entityTyeName + ":" + entityId;
    }

    /**
     * 获取被点赞指定实体的点赞数的key
     * @param entityType 实体类型
     * @param entityId 实体id
     * @return
     */
    public static String getUserLikedCountKey(int entityType, int entityId) {
        String entityTyeName = Constant.getEntityTyeName(entityType);
        // like:count:entityType:entityId --> int
        return USER_LIKED_COUNT + entityTyeName + ":" + entityId;
    }

    /**
     * 获取登录凭证的key
     * @param ticket
     * @return
     */
    public static String getLoginTicketKey(String ticket){
        // login:ticket:ticket --> stuId
        return LOGIN_TICKET_PREFIX + ticket;
    }

}
