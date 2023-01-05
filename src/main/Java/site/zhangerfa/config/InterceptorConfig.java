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
        // 设置放行路径
        String[] excludePath = {"/**/*.css", "/**/*.png", "/**/*.jpg",
                "/**/*.svg", "/**/*.js"};
        registry.addInterceptor(checkLoginInterceptor)
                // 不写拦截路径默认拦截所有请求
//                .addPathPatterns("/")
                .excludePathPatterns(excludePath);
    }
}
