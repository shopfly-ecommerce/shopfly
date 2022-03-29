/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.client.trade;

import com.enation.app.javashop.core.aftersale.model.dos.RefundGoodsDO;

import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: 售后client
 * @date 2018/8/13 16:01
 * @since v7.0.0
 */
public interface AfterSaleClient {

    /**
     * 查询退款单状态
     */
    void queryRefundStatus();

    /**
     * 获取退货单的商品列表
     * @param sn 退款单号
     * @return 退货商品列表
     */
    List<RefundGoodsDO> getRefundGoods(String sn);

}
