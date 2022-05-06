/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.order.service.impl;

import cloud.shopfly.b2c.core.trade.order.model.dos.TradeDO;
import cloud.shopfly.b2c.core.trade.order.service.TradeQueryManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 交易查询
 * @author Snow create in 2018/5/21
 * @version v2.0
 * @since v7.0.0
 */
@Service
public class TradeQueryManagerImpl implements TradeQueryManager {

    @Autowired

    private DaoSupport daoSupport;

    @Override
    public TradeDO getModel(String tradeSn) {
        String sql = "select * from es_trade where trade_sn = ?";
        return this.daoSupport.queryForObject(sql, TradeDO.class,tradeSn);
    }
}
