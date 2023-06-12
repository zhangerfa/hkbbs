package site.zhangerfa.util;

import site.zhangerfa.entity.User;

import java.util.Calendar;
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

    /**
     * 由学号推出年级（如果本科毕业两年称为大六）
     */
    public static String getAge(User user){
        StringBuilder age = new StringBuilder();
        String stuId = user.getStuId();
        switch (stuId.charAt(0)) {
            case 'U' -> age.append("大");
            case 'M' -> age.append("研");
            case 'D' -> age.append("博");
        }
        // 目前是上学的第几年
        int years = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(stuId.substring(1, 5));
        age.append(years);
        return age.toString();
    }
}
