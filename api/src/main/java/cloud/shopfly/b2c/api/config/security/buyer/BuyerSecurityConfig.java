/*
 * Yi family of hui（Beijing）All Rights Reserved.
 * You may not use this file without permission.
 * The official address：www.javamall.com.cn
 */
package cloud.shopfly.b2c.api.config.security.buyer;

import cloud.shopfly.b2c.core.base.DomainHelper;
import cloud.shopfly.b2c.framework.security.TokenAuthenticationFilter;
import cloud.shopfly.b2c.framework.security.model.Role;
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
 * Buyer security configuration
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/12
 */
@Configuration
@EnableWebSecurity
public class BuyerSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DomainHelper domainHelper;

    @Autowired
    private AuthenticationService buyerAuthenticationService;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;


    /**
     * definesellerProject permissions
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                // Disable the session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // Define a validation failure return format
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint).and()
                .authorizeRequests()
                .and()
                .addFilterBefore(new TokenAuthenticationFilter(buyerAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class);
        // Filter the path of the Base API
        http.authorizeRequests().antMatchers("/pages/**"
                , "/captchas/**"
                , "/uploaders/**/**"
                , "/settings/**"
                , "/regions/**"
                , "/site-show/**"
                , "/countries"
                , "/countries/**"
                , "/ueditor/**").permitAll();

        // Filter out local images and media files
        //gif,jpg,png,jpeg,mp4,quicktime
        http.authorizeRequests().antMatchers("/images/**/*.jpeg","/images/**/*.jpg","/images/**/*.gif","/images/**/*.png","/images/**/*.mp4","/images/**/*.quicktime").anonymous();;

        // Filter out Swaggers path
        http.authorizeRequests().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**").anonymous();
        // Filter out apis that dont require buyer privileges
        http.authorizeRequests().antMatchers("/debugger/**", "/jquery.min.js", "/order/pay/weixin/**", "/order/pay/callback/**", "/order/pay/query/**", "/pintuan/orders/**", "/pintuan/goods", "/pintuan/goods/**", "/goods/**", "/pages/**", "/focus-pictures/**",
                "/shops/list", "/shops/{spring:[0-9]+}", "/shops/cats/{spring:[0-9]+}", "/shops/navigations/{spring:[0-9]+}", "/promotions/**", "/view",
                "/shops/sildes/{spring:[0-9]+}", "/members/logout*", "/passport/**", "/trade/goods/**", "/order/pay/return/**",
                "/isr/**",
                "/members/asks/goods/{spring:[0-9]+}", "/members/comments/goods/{spring:[0-9]+}", "/members/comments/goods/{spring:[0-9]+}/count", "/distribution/su/**", "/passport/connect/pc/WECHAT/**",
                "/passport/login-binder/pc/**", "/account-binder/**", "/wechat/**", "/qq/**", "/apple/**", "/alipay/**", "/order/pay/paypal/**").permitAll().and();
        // Define access only if you have buyer permission
        http.authorizeRequests().anyRequest().hasRole(Role.BUYER.name());
        http.headers().addHeaderWriter(xFrameOptionsHeaderWriter());
        // Disable caching
        http.headers().cacheControl().and().contentSecurityPolicy("script-src 'self'");

    }


    public XFrameOptionsHeaderWriter xFrameOptionsHeaderWriter() throws URISyntaxException {

        String buyerDomain = domainHelper.getBuyerDomain();

        URI uri = new URI(buyerDomain);

        AllowFromStrategy allowFromStrategy = new StaticAllowFromStrategy(uri);

        XFrameOptionsHeaderWriter xFrameOptionsHeaderWriter = new XFrameOptionsHeaderWriter(allowFromStrategy);

        return xFrameOptionsHeaderWriter;
    }

    /**
     * Define cross-domain configuration
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
