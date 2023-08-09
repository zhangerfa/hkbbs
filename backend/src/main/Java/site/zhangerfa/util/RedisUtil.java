package site.zhangerfa.util;

import site.zhangerfa.Constant.Constant;
import site.zhangerfa.entity.Entity;

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
    public static final String LIKE_USER_SET_PREFIX = "likeUserSet:entityType:";

    public static String getRegisterCodeKey(String stuId){
        return LOGIN_CODE_PREFIX + stuId;
    }

    /**
     * 获取点赞指定实体的其他用户学号集合的key
     * @return
     */
    public static String getLikeUserSetKey(Entity entity){
        int entityType = entity.getEntityType();
        int entityId = entity.getEntityId();
        // likeUserSet:entityType:entityId --> set(stuId)
        String entityTyeName;
        // 帖子和树洞统一为post
        if (entityType == Constant.ENTITY_TYPE_POST || entityType == Constant.ENTITY_TYPE_HOLE) {
            entityTyeName = Constant.getEntityTyeName(Constant.ENTITY_TYPE_POST);
        }else {
            entityTyeName = Constant.getEntityTyeName(entityType);
        }
        return LIKE_USER_SET_PREFIX + entityTyeName + ":" + entityId;
    }

    /**
     * 获取点踩指定实体的其他用户学号集合的key
     * @param entity
     * @return
     */
    public static String getUnLikeUserSetKey(Entity entity){
        // unLikeUserSet:entityType:entityId --> set(stuId)
        return "un" + getLikeUserSetKey(entity);
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
