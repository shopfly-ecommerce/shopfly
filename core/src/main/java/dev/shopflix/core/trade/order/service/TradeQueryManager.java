/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.service;

import dev.shopflix.core.trade.order.model.dos.TradeDO;

/**
 * 交易查询接口
 *
 * @author Snow create in 2018/5/21
 * @version v2.0
 * @since v7.0.0
 */
public interface TradeQueryManager {

    /**
     * 根据交易单号查询交易对象
     *
     * @param tradeSn
     * @return
     */
    TradeDO getModel(String tradeSn);

}
