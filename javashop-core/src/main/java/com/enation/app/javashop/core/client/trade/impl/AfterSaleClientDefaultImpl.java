/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.client.trade.impl;

import com.enation.app.javashop.core.aftersale.model.dos.RefundGoodsDO;
import com.enation.app.javashop.core.aftersale.service.AfterSaleManager;
import com.enation.app.javashop.core.client.trade.AfterSaleClient;
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
@ConditionalOnProperty(value="javashop.product", havingValue="stand")
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
