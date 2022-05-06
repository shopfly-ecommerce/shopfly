/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.order.service;

import cloud.shopfly.b2c.core.trade.order.model.vo.TradeVO;

/**
 * 交易管理
 * @author Snow create in 2018/4/9
 * @version v2.0
 * @since v7.0.0
 */
public interface TradeManager {

    /**
     * 交易创建
     * @param clientType  客户的类型
     * @return
     */
    TradeVO createTrade(String clientType);



}
