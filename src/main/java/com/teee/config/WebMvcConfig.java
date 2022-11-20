package com.teee.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Xu ZhengTao
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${path.picPath}")
    private String picPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/pic/**").addResourceLocations("file:" + picPath);
    }
}
