package com.huang.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置CORS跨域支持、拦截器
 * @author Ikaros
 * @since 2025/8/21 16:02 星期四
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")              // 拦截全部路径
                .allowedOrigins("http://localhost:8079")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)        // 允许前端带 Cookie
                .maxAge(3600);                 // 预检缓存 1 小时
    }
}
