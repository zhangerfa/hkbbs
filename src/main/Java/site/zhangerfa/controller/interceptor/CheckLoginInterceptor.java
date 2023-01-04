package site.zhangerfa.controller.interceptor;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import site.zhangerfa.pojo.LoginTicket;
import site.zhangerfa.service.UserService;

import java.util.Date;

// 该拦截器用于检测访问资源时用户是否登录
@Component
public class CheckLoginInterceptor implements HandlerInterceptor {
    @Resource
    private UserService userService;
    @Override
    public boolean preHandle(jakarta.servlet.http.HttpServletRequest request,
                             jakarta.servlet.http.HttpServletResponse response,
                             Object handler) throws Exception {
        // 当用户访问登录、注册页面时如果没有携带登录凭证码放行，
        //              如果携带了则跳转到卡片墙
        // 其他页面通过检测登录凭证判断是否放行
        String url = request.getRequestURL().toString();
        String[] pass = {"users", "login", "register"};
        for (String s : pass) {
            if (url.contains(s)){
                // 判断是否携带登录验证码
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("ticket")){
                        // 有登录凭证码则跳转到首页
                        response.sendRedirect("/wall");
                        return false;
                    }
                }
                // 没有登录凭证码放行
                return true;
            }
        }

        // 验证用户是否有登录凭证
        String ticket = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("ticket")){
                ticket = cookie.getValue();
                break;
            }
        }
        // 如果有登录凭证，未过期则放行，过期则更改有效状态
        if (!ticket.equals("")){
            // 有登录凭证，检查是否过期
            LoginTicket loginTicket = userService.getTicket(ticket);
            if (loginTicket.getStatus() == 1){
                // 有效，判断是否过期
                if (loginTicket.getExpired().compareTo(new Date(System.currentTimeMillis())) == 1){
                    // 未过期，放行
                    return true;
                }else {
                    // 过期，更改登录凭证状态，重定向到登录页面
                    userService.updateTicket(ticket, 0);
                }
            }
        }

        // 没有登陆凭证、有登录凭证已过期均跳转到登录页面
        response.sendRedirect("/login");
        return false;
    }
}
