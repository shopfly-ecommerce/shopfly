/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.config.security.buyer;

import cloud.shopfly.b2c.framework.auth.AuthUser;
import cloud.shopfly.b2c.framework.security.impl.AbstractAuthenticationService;
import cloud.shopfly.b2c.framework.security.model.Buyer;
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
