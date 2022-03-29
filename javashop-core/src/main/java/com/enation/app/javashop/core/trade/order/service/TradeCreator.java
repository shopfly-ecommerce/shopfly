/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.order.service;

import com.enation.app.javashop.core.trade.order.model.vo.TradeVO;

/**
 * Created by kingapex on 2019-01-23.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-23
 */
public interface TradeCreator {

    /**
     * 检测配送范围
     *
     * @return
     */
    TradeCreator checkShipRange();

    /**
     * 检测商品合法性
     *
     * @return
     */
    TradeCreator checkGoods();

    /**
     * 检测促销活动合法性
     *
     * @return
     */
    TradeCreator checkPromotion();

    /**
     * 创建交易
     *
     * @return
     */
    TradeVO createTrade();

}
