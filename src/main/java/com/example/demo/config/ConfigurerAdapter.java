package com.example.demo.config;

import com.example.demo.Interceptor.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2018/1/5 0005.
 */

@Configuration
public class ConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new AuthorityInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new Authority1Interceptor()).addPathPatterns("/**");
        registry.addInterceptor(new Authority2Interceptor()).addPathPatterns("/**");
        registry.addInterceptor(new Authority3Interceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
