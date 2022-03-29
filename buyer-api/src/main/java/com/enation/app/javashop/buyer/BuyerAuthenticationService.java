/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.buyer;

import com.enation.app.javashop.framework.auth.AuthUser;
import com.enation.app.javashop.framework.security.impl.AbstractAuthenticationService;
import com.enation.app.javashop.framework.security.model.Buyer;
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
public class BuyerAuthenticationService extends AbstractAuthenticationService {

    @Override
    protected AuthUser parseToken(String token) {
        AuthUser authUser=  tokenManager.parse(Buyer.class, token);
//        User user = (User) authUser;
//        checkUserDisable(Role.BUYER, user.getUid());
        return authUser;
    }



}
