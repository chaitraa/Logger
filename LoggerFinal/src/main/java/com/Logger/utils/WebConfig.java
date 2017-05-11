package com.Logger.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by technology on 10/5/17.
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET")
                .allowedHeaders("*")
                .exposedHeaders("Allow", "Connection","Content-Encoding","Content-Length",
                        "Location","Refresh","Retry-After","Retry-After","WWW-Authenticate")
                .allowCredentials(true).maxAge(86400);
    }
}