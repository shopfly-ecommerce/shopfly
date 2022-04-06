/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.service.impl;

import dev.shopflix.core.trade.order.service.TradePriceManager;
import dev.shopflix.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 交易价格业务
 *
 * @author Snow create in 2018/3/22
 * @version v2.0
 * @since v7.0.0
 */

@Service
public class TradePriceManagerImpl implements TradePriceManager {


    @Autowired
    
    private DaoSupport daoSupport;


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updatePrice(String tradeSn, Double tradePrice, Double discountPrice) {
        String sql = "update es_trade set total_price=?,discount_price=? where trade_sn=?";
        this.daoSupport.execute(sql, tradePrice, discountPrice, tradeSn);
    }


}
