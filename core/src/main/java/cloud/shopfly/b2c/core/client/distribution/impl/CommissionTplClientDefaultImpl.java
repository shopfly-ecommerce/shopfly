/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.distribution.impl;

import cloud.shopfly.b2c.core.client.distribution.CommissionTplClient;
import cloud.shopfly.b2c.core.distribution.model.dos.CommissionTpl;
import cloud.shopfly.b2c.core.distribution.service.CommissionTplManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * CommissionTplClientDefaultImpl
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-14 下午2:19
 */

@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class CommissionTplClientDefaultImpl implements CommissionTplClient {

    @Autowired
    private CommissionTplManager commissionTplManager;

    /**
     * 获取默认模版
     *
     * @return
     */
    @Override
    public CommissionTpl getDefaultCommission() {
        return commissionTplManager.getDefaultCommission();
    }
}
