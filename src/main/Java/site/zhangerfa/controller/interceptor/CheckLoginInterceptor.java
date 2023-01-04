package site.zhangerfa.controller.interceptor;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import site.zhangerfa.service.UserService;

// 该拦截器用于检测访问资源时用户是否登录
@Component
public class CheckLoginInterceptor implements HandlerInterceptor {
    @Resource
    private UserService userService;

    // 当用户访问登录、注册页面时如果没有携带登录凭证码放行，
    //              如果携带了则跳转到卡片墙
    // 其他页面通过检测登录凭证判断是否放行
    @Override
    public boolean preHandle(jakarta.servlet.http.HttpServletRequest request,
                             jakarta.servlet.http.HttpServletResponse response,
                             Object handler) throws Exception {

        String url = request.getRequestURL().toString();
        String[] pass = {"users", "login", "register"};

        for (String s : pass) {
            if (url.contains(s)){
                // 访问与注册、登录有关请求，放行
                Cookie[] cookies = request.getCookies();
                // 检查cookie中包含有效的登录验证码
                if (cookieContainTicket(cookies)) {
                    // 包含有效登录验证码 跳转到卡片墙
                    response.sendRedirect("/wall");
                    return false;
                }
                // 没有登录验证码或没有有效登录验证码
                // 当前访问与注册、登录有关请求，放行
                return true;
            }
        }

        // 访问 非注册、登录有关请求
        // 检查cookie中包含有效的登录验证码
        Cookie[] cookies = request.getCookies();
        // 检查cookie中包含有效的登录验证码
        if (cookieContainTicket(cookies)) {
            // 包含有效登录验证码，放行
            return true;
        }

        // 没有登陆凭证、有登录凭证已过期均跳转到登录页面
        response.sendRedirect("/login");
        return false;
    }

    /**
     * 判断cookie中是否包含有效的ticket
     *
     */
    private boolean cookieContainTicket(Cookie[] cookies){
        // 未携带任何cookie
        if (cookies == null) {
            return false;
        }
        // cookie中是否有登录验证码
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("ticket")){
                // 判断登录验证码是否有效
                if (userService.checkTicket(cookie.getValue())){
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
