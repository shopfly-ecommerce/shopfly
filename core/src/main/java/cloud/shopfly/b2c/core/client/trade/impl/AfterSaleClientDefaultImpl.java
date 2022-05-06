/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.trade.impl;

import cloud.shopfly.b2c.core.client.trade.AfterSaleClient;
import cloud.shopfly.b2c.core.aftersale.model.dos.RefundGoodsDO;
import cloud.shopfly.b2c.core.aftersale.service.AfterSaleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description:
 * @date 2018/8/13 16:02
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value="shopfly.product", havingValue="stand")
public class AfterSaleClientDefaultImpl implements AfterSaleClient {

    @Autowired
    private AfterSaleManager afterSaleManager;

    @Override
    public void queryRefundStatus() {
        afterSaleManager.queryRefundStatus();
    }

    @Override
    public List<RefundGoodsDO> getRefundGoods(String sn) {

        return afterSaleManager.getRefundGoods(sn);
    }
}
