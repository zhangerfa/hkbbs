package site.zhangerfa.controller.interceptor;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

// 该拦截器用于检测访问资源时用户是否登录
@Component
public class CheckLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(jakarta.servlet.http.HttpServletRequest request,
                             jakarta.servlet.http.HttpServletResponse response,
                             Object handler) throws Exception {
        // 当用户访问登录、注册页面时放行，其他页面通过session进行是否登录验证
        String requestURI = request.getRequestURI();
        String[] pass = {"users", "login", "register"};
        for (String s : pass) {
            if (requestURI.contains(s)) return true;
        }

        // 验证用户是否登录
        HttpSession session = request.getSession();
        String stuId = (String) session.getAttribute("stuId");
        if (stuId != null) return true;

        // 没有登录的用户重定向到登录页面
        response.sendRedirect(request.getContextPath()+"/html/login.html");
        System.out.println(request.getContextPath());
        return false;
    }
}
