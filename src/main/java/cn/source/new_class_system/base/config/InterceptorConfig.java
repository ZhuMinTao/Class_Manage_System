package cn.source.new_class_system.base.config;

import cn.source.new_class_system.user.web.interceptor.LoginHandlerInterceptor;
import cn.source.new_class_system.user.web.interceptor.PermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

     /** @PropertyDescription 先把redis加载到容器中 **/
    @Bean
    public LoginHandlerInterceptor getLoginHandlerInterceptor(){
        return new LoginHandlerInterceptor();
    }

    @Bean
    public PermissionInterceptor getPermissionInterceptor(){
        return new PermissionInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录认证
        registry.addInterceptor(getLoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/verification/**");

        //权限认证
        registry.addInterceptor(getPermissionInterceptor())
                .addPathPatterns("/**");


    }
}
