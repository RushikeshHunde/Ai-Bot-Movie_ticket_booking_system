package com.TicketBookingSystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    	String projectPath = System.getProperty("user.dir");


        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + projectPath + "/src/main/resources/static/images/");
    }
}