package site.zhangerfa.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 * cookie工具类
 */
public class CookieUtil {
    /**
     * 获取的请求中指定name的cookie的值，如果没有该cookie则返回null
     * @param request
     * @param name
     * @return
     */
    public static String getValue(HttpServletRequest request, String name){
        if (request == null || name == null) {
            throw new  IllegalArgumentException("参数为空");
        }
        // 请求中未携带cookie，返回null
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        // 寻找指定name的cookie
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)){
                return cookie.getValue();
            }
        }
        return null;
    }
}
