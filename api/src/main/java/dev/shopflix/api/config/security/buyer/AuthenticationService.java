/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.config.security.buyer;

import dev.shopflix.framework.auth.AuthUser;
import dev.shopflix.framework.security.impl.AbstractAuthenticationService;
import dev.shopflix.framework.security.model.Buyer;
import org.springframework.stereotype.Component;

/**
 * buyer 鉴权管理
 * <p>
 * Created by kingapex on 2018/3/12.
 * v2.0: 重构鉴权机制
 *
 * @author kingapex
 * @version 2.0
 * @since 7.0.0
 * 2018/3/12
 */
@Component
public class AuthenticationService extends AbstractAuthenticationService {

    @Override
    protected AuthUser parseToken(String token) {
        AuthUser authUser=  tokenManager.parse(Buyer.class, token);
        return authUser;
    }



}
