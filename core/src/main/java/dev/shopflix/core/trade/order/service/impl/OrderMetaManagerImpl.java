/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.service.impl;

import dev.shopflix.core.trade.order.model.dos.OrderMetaDO;
import dev.shopflix.core.trade.order.model.enums.OrderMetaKeyEnum;
import dev.shopflix.core.trade.order.service.OrderMetaManager;
import dev.shopflix.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单元信息
 *
 * @author Snow create in 2018/6/27
 * @version v2.0
 * @since v7.0.0
 */
@Service
public class OrderMetaManagerImpl implements OrderMetaManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Override
    public void add(OrderMetaDO orderMetaDO) {

        this.daoSupport.insert(orderMetaDO);
    }


    @Override
    public String getMetaValue(String orderSn, OrderMetaKeyEnum metaKey) {
        String sql = "select meta_value from es_order_meta where order_sn=? and meta_key=?";
        String metaJson = daoSupport.queryForString(sql, orderSn, metaKey.name());
        return metaJson;
    }

    @Override
    public List<OrderMetaDO> list(String orderSn) {

        String sql = "select * from es_order_meta where order_sn=?";

        return daoSupport.queryForList(sql, OrderMetaDO.class, orderSn);
    }

    @Override
    public void updateMetaValue(String orderSn, OrderMetaKeyEnum metaKey, String metaValue) {

        String sql = "update es_order_meta set meta_value = ? where order_sn=? and meta_key=?";
        this.daoSupport.execute(sql, metaValue, orderSn, metaKey.name());

    }


}
