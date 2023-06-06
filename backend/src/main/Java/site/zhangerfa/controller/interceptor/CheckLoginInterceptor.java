package site.zhangerfa.controller.interceptor;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.pojo.LoginTicket;
import site.zhangerfa.service.LoginTicketService;
import site.zhangerfa.service.UserService;
import site.zhangerfa.util.CookieUtil;
import site.zhangerfa.util.HostHolder;

/**
 * 该拦截器用于检测访问资源时用户是否登录
 */
@Component
public class CheckLoginInterceptor implements HandlerInterceptor {
    @Resource
    private UserService userService;
    @Resource
    private LoginTicketService loginTicketService;

    @Resource
    private HostHolder hostHolder;

    /**
     * 当用户访问登录、注册页面时如果没有携带登录凭证码放行，
     *               如果携带了则跳转到卡片墙
     * 其他页面通过检测登录凭证判断是否放行
     *       如果放行，携带用户学号信息
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(jakarta.servlet.http.HttpServletRequest request,
                             jakarta.servlet.http.HttpServletResponse response,
                             Object handler) throws Exception {
        // 注册、登录接口、API文档页面放行
        String url = request.getRequestURL().toString();
        String[] pass = {"Code", "isExist", "login", "register", "doc", "swagger"};
        for (String s : pass)
            if (url.contains(s)) return true;
        // 获取cookie中携带的登录凭证
        String ticket = CookieUtil.getValue(request, "ticket");
        // 判断是否有登录凭证
        if (ticket != null){
            // 有登录凭证，判断是否有效
            LoginTicket loginTicketByTicket = loginTicketService.getLoginTicketByTicket(ticket);
            if (loginTicketByTicket == null || loginTicketByTicket.getStatus() == 0) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":" + Code.GET_ERR + ", \"data\":null" +
                        ",\"msg\":\"请先登录！\"}");
                return false;
            }
            String stuId = loginTicketByTicket.getStuId();
            hostHolder.setUser(userService.getUserByStuId(stuId));
            return true;
        }
        // 没有登录凭证，其他页面不放行
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 当次请求清空hostHolder中请求删除
        hostHolder.clear();
    }
}
