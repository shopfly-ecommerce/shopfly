/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.distribution.impl;

import dev.shopflix.core.client.distribution.CommissionTplClient;
import dev.shopflix.core.distribution.model.dos.CommissionTpl;
import dev.shopflix.core.distribution.service.CommissionTplManager;
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
@ConditionalOnProperty(value = "shopflix.product", havingValue = "stand")
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
