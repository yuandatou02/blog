package com.huang.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 本地调试图片显示配置 (进行本地调试时，需要配置此类，上线则不需要)
 *
 * @author Ikaros
 * @since 2025/8/25 18:49 星期一
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 把磁盘绝对路径映射到 URL 路径 /upload/**
        registry.addResourceHandler("/config/**")
                .addResourceLocations("file:/usr/local/upload/config/");
    }
}
