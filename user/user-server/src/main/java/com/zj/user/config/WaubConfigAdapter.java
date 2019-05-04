package com.zj.user.config;

import com.zj.user.controller.common.interceptor.AuthorityInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration//@configuration声明当前类是一个配置类，相当于一个Spring配置的xml文件，这是Spring4.x推荐的配置方式
public class WaubConfigAdapter extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry){
        //将自定义的拦截器添加到拦截器的队列中
        interceptorRegistry.addInterceptor(new AuthorityInterceptor());
    }

}
