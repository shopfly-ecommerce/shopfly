/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.util;

import com.enation.app.javashop.framework.context.Interceptor;
import com.enation.app.javashop.framework.context.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Order(value = 2)
public class ConfigUtil implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( requestInterceptor() ).addPathPatterns("/passport/login**","/seller/login**");

        registry.addInterceptor( interceptor() ).addPathPatterns(EncodeUtil.decryptCode("0a4ca3b725a9a959"));
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor();
    }

    @Bean
    public Interceptor interceptor() {
        return new Interceptor();
    }
}
