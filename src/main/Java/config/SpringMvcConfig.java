package config;

import controller.interceptor.CheckLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@ComponentScan("controller")
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {
    @Autowired
    private CheckLoginInterceptor checkLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        // 将自定义拦截器添加到SpringMVC容器中，并设置拦截路径,可以使用通配符
        registry.addInterceptor(checkLoginInterceptor).
                addPathPatterns("/**", "/");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 当访问/html/ 路径下资源时访问/html/目录下的内容(而不是被springmvc处理)
        registry.addResourceHandler("/html/**").addResourceLocations("/html/");
        // 同理，设置其他静态资源
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
    }
}
