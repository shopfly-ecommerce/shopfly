/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.framework.context;

import cloud.shopfly.b2c.framework.security.XssStringJsonSerializer;
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
 * Load a custom interceptor
 * @author Chopper
 * @version v1.0
 * @since v6.2 2017years3month7On the afternoon5:29:56
 *
 */
@Configuration
@Order(value = 1)
public class WebInterceptorConfigurer implements WebMvcConfigurer {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

		// Parameter serpentine turn hump intercept
		argumentResolvers.add(new SnakeToCamelModelAttributeMethodProcessor(true));
		argumentResolvers.add(new SnakeToCamelArgumentResolver());

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ShopflyRequestInterceptor()).addPathPatterns("/**");
	}

	/**
	 * The customObjectMapper forxssAttack filter
 	 * @param builder
	 * @return
	 */
	@Bean
	@Primary
	public ObjectMapper xssObjectMapper(Jackson2ObjectMapperBuilder builder) {
		// The parser
		ObjectMapper objectMapper = builder.createXmlMapper(false).build();
		// Register the XSS parser
		SimpleModule xssModule = new SimpleModule("XssStringJsonSerializer");
		xssModule.addSerializer(new XssStringJsonSerializer());
		objectMapper.registerModule(xssModule);
		// return
		return objectMapper;
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		ApplicationHome home = new ApplicationHome(WebInterceptorConfigurer.class);

		File jarFile = home.getSource();
		String aPath = jarFile.getAbsolutePath();
		String path = jarFile.getParentFile().toString();
		// Run on jar, locate the sibling directory /images
		if (aPath.endsWith(".jar")){
			path+="/images/";
		}else {
			// If running on a class, it is positioned as a project directory
			path = path.replaceAll("/framework/target", "/images/");
		}
//		System.out.println(path);
		registry.addResourceHandler("/images/**")
				// Path to the user file
				.addResourceLocations("/images/**","file:"+path);
	}

}
