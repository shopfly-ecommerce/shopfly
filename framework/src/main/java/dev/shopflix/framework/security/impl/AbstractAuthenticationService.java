/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.security.impl;

import dev.shopflix.framework.ShopflixConfig;
import dev.shopflix.framework.auth.AuthUser;

import dev.shopflix.framework.security.AuthenticationService;
import dev.shopflix.framework.security.TokenManager;
import dev.shopflix.framework.security.model.Role;
import dev.shopflix.framework.security.model.TokenConstant;
import dev.shopflix.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 公用的安全配置
 * 7.2.0起，废弃掉重放攻击的判断
 * v2.0: 优化token解析，使用统一的tokenParser
 *
 * @author zh
 * @version v2.0
 * @date 18/8/7 下午8:23
 * @since v7.0
 */
public abstract class AbstractAuthenticationService implements AuthenticationService {

    @Autowired
    protected TokenManager tokenManager;


    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    /**
     * 单例模式的cache
     */
    private static Cache<String, Integer> cache;


    @Autowired
    private ShopflixConfig shopflixConfig;


    /**
     * 鉴权，先获取token，再根据token来鉴权
     * 生产环境要由nonce和时间戳，签名来获取token
     * 开发环境可以直接传token
     *
     * @param req
     */
    @Override
    public void auth(HttpServletRequest req) {
        String token = this.getToken(req);
        //System.out.println("token======="+token);
        if (StringUtil.notEmpty(token)) {
            Authentication authentication = getAuthentication(token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
    }

    private String getKey(Role role, long uid) {

        return role.name() + "_" + uid;
    }

    /**
     * 解析一个token
     * 子类需要将token解析自己的子业务权限模型：Admin,seller buyer...
     *
     * @param token
     * @return
     */
    protected abstract AuthUser parseToken(String token);

    /**
     * 根据一个 token 生成授权
     *
     * @param token
     * @return 授权
     */
    protected Authentication getAuthentication(String token) {
        try {

            AuthUser user = parseToken(token);
            List<GrantedAuthority> auths = new ArrayList<>();

            List<String> roles = user.getRoles();

            for (String role : roles) {
                auths.add(new SimpleGrantedAuthority("ROLE_" + role));
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("user", null, auths);
            authentication.setDetails(user);

            return authentication;
        } catch (Exception e) {
            logger.error("认证异常", e);
            return new UsernamePasswordAuthenticationToken("anonymous", null);
        }
    }

    /**
     * 获取token
     * 7.2.0起，废弃掉重放攻击的判断
     *
     * @param req
     * @return
     */
    protected String getToken(HttpServletRequest req) {

        String token = req.getHeader(TokenConstant.HEADER_STRING);
        if (StringUtil.notEmpty(token)) {
            token = token.replaceAll(TokenConstant.TOKEN_PREFIX, "").trim();
        }

        return token;
    }

    private static final Object lock = new Object();

    /**
     * 获取本地缓存<br/>
     * 用于记录被禁用的用户<br/>
     * 此缓存的key为：角色+用户id，如： admin_1
     * value为：1则代表此用户被禁用
     *
     * @return
     */
    protected Cache<String, Integer> getCache() {

        if (cache != null) {
            return cache;
        }
        synchronized (lock) {
            if (cache != null) {
                return cache;
            }
            //缓存时间为session有效期+一分钟
            //也就表示，用户如果被禁用，session超时这个cache也就不需要了：
            //因为他需要重新登录就可以被检测出无效
            int sessionTimeout = shopflixConfig.getRefreshTokenTimeout() - shopflixConfig.getAccessTokenTimeout() + 60;

            //使用ehcache作为缓存
            CachingProvider provider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");
            CacheManager cacheManager = provider.getCacheManager();

            MutableConfiguration<String, Integer> configuration =
                    new MutableConfiguration<String, Integer>()
                            .setTypes(String.class, Integer.class)
                            .setStoreByValue(false)
                            .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, sessionTimeout)));

            cache = cacheManager.createCache("userDisable", configuration);

            return cache;
        }
    }


}
