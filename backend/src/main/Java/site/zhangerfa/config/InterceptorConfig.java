package site.zhangerfa.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.zhangerfa.controller.interceptor.CheckLoginInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Resource
    private CheckLoginInterceptor checkLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加登录检测拦截器
        registry.addInterceptor(checkLoginInterceptor);
    }
}
