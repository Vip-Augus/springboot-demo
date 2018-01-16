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
        /*registry.addInterceptor(new TeacherInterceptor()).addPathPatterns("/web/score/list").addPathPatterns("/web/score/mark").addPathPatterns("/course*//*");
        registry.addInterceptor(new CourseReviewInterceptor()).addPathPatterns("/web/course/review*//*");
        registry.addInterceptor(new SendNoticeInterceptor()).addPathPatterns("/web/notice/send");
        registry.addInterceptor(new ClassroomInterceptor()).addPathPatterns("/web/classroom/add").addPathPatterns("/web/ep/add").addPathPatterns("/web/ep/update").addPathPatterns("/web/ep/delete")
                                                            .addPathPatterns("/web/epRecord/add").addPathPatterns("/web/epRecord/update").addPathPatterns("/web/epRecord/delete");
        registry.addInterceptor(new AuthorityInterceptor()).addPathPatterns("/web/user/teacher").addPathPatterns("/web/user/delete").addPathPatterns("/web/user/auth");*/
        super.addInterceptors(registry);
    }
}
