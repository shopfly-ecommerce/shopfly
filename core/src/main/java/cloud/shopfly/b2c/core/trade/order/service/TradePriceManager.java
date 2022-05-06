/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.order.service;

/**
 * 交易价格业务接口
 *
 * @author kingapex
 * @version v2.0
 * @since v7.0.0
 * 2017年3月23日上午10:01:30
 */
public interface TradePriceManager {


    /**
     * 未付款的订单，商家修改订单金额，同时修改交易价格
     *
     * @param tradeSn       交易编号
     * @param tradePrice
     * @param discountPrice
     */
    void updatePrice(String tradeSn, Double tradePrice, Double discountPrice);

}
