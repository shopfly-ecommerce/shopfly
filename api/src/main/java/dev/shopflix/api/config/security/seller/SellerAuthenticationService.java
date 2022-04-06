/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.config.security.seller;


import dev.shopflix.framework.auth.AuthUser;
import dev.shopflix.framework.security.impl.AbstractAuthenticationService;
import dev.shopflix.framework.security.model.Admin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * jwt token 鉴权管理
 * <p>
 * Created by kingapex on 2018/3/12.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/12
 */
@SuppressWarnings("AlibabaUndefineMagicConstant")
@Component
public class SellerAuthenticationService  extends AbstractAuthenticationService {

    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 将token解析为Clerk
     *
     * @param token
     * @return
     */
    @Override
    protected AuthUser parseToken(String token) {
        AuthUser authUser=  tokenManager.parse(Admin.class, token);
//        User user = (User) authUser;
//        checkUserDisable(Role.CLERK, user.getUid());
        return authUser;
    }


}
