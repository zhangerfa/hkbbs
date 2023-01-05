package site.zhangerfa.controller.interceptor;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import site.zhangerfa.pojo.User;
import site.zhangerfa.service.UserService;
import site.zhangerfa.util.CookieUtil;
import site.zhangerfa.util.HostHolder;

// 该拦截器用于检测访问资源时用户是否登录
@Component
public class CheckLoginInterceptor implements HandlerInterceptor {
    @Resource
    private UserService userService;

    @Resource
    private HostHolder hostHolder;

    // 当用户访问登录、注册页面时如果没有携带登录凭证码放行，
    //              如果携带了则跳转到卡片墙
    // 其他页面通过检测登录凭证判断是否放行
    //      如果放行，携带用户学号信息
    @Override
    public boolean preHandle(jakarta.servlet.http.HttpServletRequest request,
                             jakarta.servlet.http.HttpServletResponse response,
                             Object handler) throws Exception {
        String ticket = CookieUtil.getValue(request, "ticket");
        if (userService.checkTicket(ticket)){
            // ticket有效，将用户信息存储到hostHolder中，供这次请求共享
            hostHolder.setUser(userService.getUserByTicket(ticket));
        }

        // 判断是否访问与注册、登录有关页面
        String url = request.getRequestURL().toString();
        String[] pass = {"users", "login", "register"};
        for (String s : pass) {
            if (url.contains(s)){
                // 访问与注册、登录有关请求，放行
                if (userService.checkTicket(ticket)){
                    // ticket有效，跳转到卡片墙
                    response.sendRedirect("/wall");
                    return false;
                }else {
                    // ticket无效，放行
                    return true;
                }
            }
        }

        // 访问 非注册、登录有关请求
        if (userService.checkTicket(ticket)){
            // ticket有效，放行
            return true;
        }else {
            // ticket无效，跳转到登录页面
            response.sendRedirect("/login");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 当hostHolder中有用户数据且后端要调用模板引擎时，将数据传入modelAndView供模板引擎调用
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null){
            // 后端调用模板引擎且登录凭证有效
            modelAndView.addObject("user", user);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 当次请求清空hostHolder中请求删除
        hostHolder.clear();
    }
}
