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
        registry.addInterceptor(new TeacherInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new CourseReviewInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new SendNoticeInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new ClassroomInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
