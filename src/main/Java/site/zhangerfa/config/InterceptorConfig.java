package site.zhangerfa.config;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import site.zhangerfa.controller.interceptor.CheckLoginInterceptor;

@Component
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Resource
    private CheckLoginInterceptor checkLoginInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkLoginInterceptor)
                .addPathPatterns("/")
                .excludePathPatterns("/static/**");
    }

    // 自定义配置类覆盖了SpringBoot默认配置类
    // 必须重新将静态资源请求拦截到static路径下
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);

    }
}
