package com.service.todoit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("https://todoit-two.vercel.app","http://localhost:3000","http://localhost:5500","http://127.0.0.1:5500","http://127.0.0.1:3000")
                .allowedMethods("*")
                .allowedHeaders("Authorization", "Content-Type")
                .maxAge(3000)
                .allowCredentials(true);
    }
}
