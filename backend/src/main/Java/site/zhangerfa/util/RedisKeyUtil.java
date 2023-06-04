package site.zhangerfa.util;

import site.zhangerfa.Constant.RedisConstant;

/**
 * RedisKey生成工具
 */
public class RedisKeyUtil {
    public static String getRegisterCodeKey(String stuId){
        return RedisConstant.LOGIN_CODE_PREFIX + stuId;
    }
}
