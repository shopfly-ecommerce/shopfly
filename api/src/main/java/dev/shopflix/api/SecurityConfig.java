/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
 */
package dev.shopflix.api;

import dev.shopflix.core.base.DomainHelper;
import dev.shopflix.framework.security.TokenAuthenticationFilter;
import dev.shopflix.framework.security.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.AllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.StaticAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by kingapex on 2018/3/12.
 * 买家安全配置
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/12
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DomainHelper domainHelper;

    @Autowired
    private AuthenticationService buyerAuthenticationService;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;


    /**
     * 定义seller工程的权限
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                //禁用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                //定义验权失败返回格式
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint).and()
                .authorizeRequests()
                .and()
                .addFilterBefore(new TokenAuthenticationFilter(buyerAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class);
        //过滤掉base api 的路径

        http.authorizeRequests().antMatchers("/pages/**"
                , "/captchas/**"
                , "/uploaders/**/**"
                , "/settings/**"
                , "/regions/**"
                , "/site-show/**"
                , "/ueditor/**").anonymous();

        //过滤掉swagger的路径
        http.authorizeRequests().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**").anonymous();
        //过滤掉不需要买家权限的api
        http.authorizeRequests().antMatchers("/debugger/**", "/jquery.min.js", "/order/pay/weixin/**", "/order/pay/callback/**", "/order/pay/query/**", "/pintuan/orders/**", "/pintuan/goods", "/pintuan/goods/**", "/goods/**", "/pages/**", "/focus-pictures/**",
                "/shops/list", "/shops/{spring:[0-9]+}", "/shops/cats/{spring:[0-9]+}", "/shops/navigations/{spring:[0-9]+}", "/promotions/**", "/view",
                "/shops/sildes/{spring:[0-9]+}", "/members/logout*", "/passport/**", "/trade/goods/**", "/order/pay/return/**",
                "/members/asks/goods/{spring:[0-9]+}", "/members/comments/goods/{spring:[0-9]+}", "/members/comments/goods/{spring:[0-9]+}/count", "/distribution/su/**", "/passport/connect/pc/WECHAT/**", "/passport/login-binder/pc/**", "/account-binder/**", "/wechat/**", "/qq/**", "/apple/**", "/alipay/**").permitAll().and();
        //定义有买家权限才可以访问
        http.authorizeRequests().anyRequest().hasRole(Role.BUYER.name());
        http.headers().addHeaderWriter(xFrameOptionsHeaderWriter());
        //禁用缓存
        http.headers().cacheControl();

    }


    public XFrameOptionsHeaderWriter xFrameOptionsHeaderWriter() throws URISyntaxException {

        String buyerDomain = domainHelper.getBuyerDomain();

        URI uri = new URI(buyerDomain);

        AllowFromStrategy allowFromStrategy = new StaticAllowFromStrategy(uri);

        XFrameOptionsHeaderWriter xFrameOptionsHeaderWriter = new XFrameOptionsHeaderWriter(allowFromStrategy);

        return xFrameOptionsHeaderWriter;
    }

    /**
     * 定义跨域配置
     *
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }


}
