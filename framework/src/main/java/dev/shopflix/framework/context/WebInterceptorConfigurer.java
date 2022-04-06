/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.context;

import dev.shopflix.framework.security.XssStringJsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.List;

/**
 * 
 * 加载自定义的 拦截器
 * @author Chopper
 * @version v1.0
 * @since v6.2 2017年3月7日 下午5:29:56
 *
 */
@Configuration
@Order(value = 1)
public class WebInterceptorConfigurer implements WebMvcConfigurer {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

		//参数蛇形转驼峰拦截
		argumentResolvers.add(new SnakeToCamelModelAttributeMethodProcessor(true));
		argumentResolvers.add(new SnakeToCamelArgumentResolver());

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ShopflixRequestInterceptor()).addPathPatterns("/**");
	}

	/**
	 * 自定义 ObjectMapper ，用于xss攻击过滤
 	 * @param builder
	 * @return
	 */
	@Bean
	@Primary
	public ObjectMapper xssObjectMapper(Jackson2ObjectMapperBuilder builder) {
		//解析器
		ObjectMapper objectMapper = builder.createXmlMapper(false).build();
		//注册xss解析器
		SimpleModule xssModule = new SimpleModule("XssStringJsonSerializer");
		xssModule.addSerializer(new XssStringJsonSerializer());
		objectMapper.registerModule(xssModule);
		//返回
		return objectMapper;
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		ApplicationHome home = new ApplicationHome(WebInterceptorConfigurer.class);
		File jarFile = home.getSource();
		String path = jarFile.getParentFile().toString();
		System.out.println(path);
		registry.addResourceHandler("/images/**")
				//用户文件的路径
				.addResourceLocations("/images/**","file:"+path+"/images/");
	}

}
