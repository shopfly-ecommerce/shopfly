/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.event;

import dev.shopflix.core.trade.order.model.vo.TradeVO;

/**
 * 交易入库事件
 * @author Snow create in 2018/6/26
 * @version v2.0
 * @since v7.0.0
 */
public interface TradeIntoDbEvent {


    /**
     * 交易入库
     * @param tradeVO
     */
    void onTradeIntoDb(TradeVO tradeVO);
}
