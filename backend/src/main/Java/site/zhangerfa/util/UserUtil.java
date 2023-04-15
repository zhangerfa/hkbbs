package site.zhangerfa.util;

import java.util.regex.Pattern;

/**
 * 用户数据的处理工具类
 */
public class UserUtil {
    /**
     * 判断当前连接中是否已有用户登录
     * @param hostHolder
     * @return
     */
    public static boolean isLogin(HostHolder hostHolder){
        return hostHolder.getUser() != null;
    }

    /**
     * 检查学号是否合法
     * @param stuId
     * @return
     */
    public static boolean isStuIdValid(String stuId){
        String regex = "[UuMmDd][0-9]{9}";
        return isMatchRegex(regex, stuId);
    }

    /**
     * 判断传入的文本是否和传入的正则表达式 match
     * @param regex
     * @param text
     * @return
     */
    public static boolean isMatchRegex(String regex, String text){
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(text).matches();
    }
}
