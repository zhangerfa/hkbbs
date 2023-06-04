package site.zhangerfa.Constant;

/**
 * Redis常量
 */
public class RedisConstant {
    /**
     * 验证码前缀
     */
    public static final String LOGIN_CODE_PREFIX = "login:code:";

    /**
     * 用户前缀
     */
    public static final String USER_PREFIX = "user:";

    /**
     * 验证码过期时间
     */
    public static final int LOGIN_CODE_EXPIRE_MINUTE = 10;

    /**
     * 用户过期时间
     */
    public static final int USER_EXPIRE_MINUTE = 60;

    /**
     * 被点赞指定用户的其他用户学号集合
     */
    public static final String USER_LIKED_SET = "user:liked:set:";

    /**
     * 被点赞指定用户的其他用户学号集合长度
     */
    public static final String USER_LIKED_COUNT = "user:liked:count:";
}
