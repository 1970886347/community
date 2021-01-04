package com.hzc.community.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.FileNotFoundException;

@Configuration
public class LocalSourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path=null;
        try {
            path = ResourceUtils.getURL("classpath:").getFile()+"static/img/";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        registry.addResourceHandler("/img/**").addResourceLocations("file:"+path.substring(1));
    }
}
