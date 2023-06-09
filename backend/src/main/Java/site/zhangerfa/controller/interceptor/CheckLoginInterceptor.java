package site.zhangerfa.controller.interceptor;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.service.UserService;
import site.zhangerfa.service.WebDataService;
import site.zhangerfa.util.HostHolder;
import site.zhangerfa.util.RedisUtil;

import java.util.concurrent.TimeUnit;

/**
 * 该拦截器用于检测访问资源时用户是否登录
 */
@Component
public class CheckLoginInterceptor implements HandlerInterceptor {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private UserService userService;
    @Resource
    private WebDataService webDataService;

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
        // 访问量加一
        webDataService.addPvForToday();
        // 注册、登录接口、API文档页面放行
        String url = request.getRequestURL().toString();
        String[] pass = {"Code", "isExist", "login", "register", "doc", "swagger"};
        for (String s : pass)
            if (url.contains(s)) return true;
        // 获取cookie中携带的登录凭证
        String ticket = site.zhangerfa.util.CookieUtil.getValue(request, "ticket");
        // 判断是否有登录凭证
        if (ticket != null){
            // 更新登录凭证过期时间
            String loginTicketKey = RedisUtil.getLoginTicketKey(ticket);
            stringRedisTemplate.expire(loginTicketKey,
                    RedisUtil.LOGIN_TICKET_EXPIRE_DAY, TimeUnit.MINUTES);
            // 将用户信息放入hostHolder中
            String stuId = stringRedisTemplate.opsForValue().get(loginTicketKey);
            // 如果没有学号信息，返回false
            if (stuId != null){
                hostHolder.setUser(userService.getUserByStuId(stuId));
                return true;
            }
        }
        // 没有登录凭证，不放行，提示用户登录
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":" + Code.GET_ERR + ", \"data\":null" +
                ",\"msg\":\"请先登录！\"}");
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 当次请求清空hostHolder中请求删除
        hostHolder.clear();
    }
}
