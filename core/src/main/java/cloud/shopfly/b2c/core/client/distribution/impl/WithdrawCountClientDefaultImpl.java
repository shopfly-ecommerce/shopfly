/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.distribution.impl;

import cloud.shopfly.b2c.core.client.distribution.WithdrawCountClient;
import cloud.shopfly.b2c.core.distribution.service.WithdrawCountManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 可提现金额计算
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/23 上午7:46
 */
@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class WithdrawCountClientDefaultImpl implements WithdrawCountClient {


    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private WithdrawCountManager withdrawCountManager;

    /**
     * 每天执行结算
     */
    @Override
    public void everyDay() {
        withdrawCountManager.withdrawCount();
    }
}
