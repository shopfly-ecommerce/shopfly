/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.system.impl;

import cloud.shopfly.b2c.core.client.system.RoleClient;
import cloud.shopfly.b2c.core.system.service.RoleSeller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description:
 * @date 2018/9/26 14:12
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value="shopflix.product", havingValue="stand")
public class RoleClientDefaultImpl implements RoleClient {

    @Autowired
    private RoleSeller roleManager;

    @Override
    public Map<String, List<String>> getRoleMap() {

        return roleManager.getRoleMap();
    }
}
