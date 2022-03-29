/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.order.service.impl;

import com.enation.app.javashop.core.trade.order.model.dos.OrderLogDO;
import com.enation.app.javashop.core.trade.order.service.OrderLogManager;
import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.database.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单日志表业务类
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-16 12:01:34
 */
@Service
public class OrderLogManagerImpl implements OrderLogManager {

    @Autowired
    @Qualifier("tradeDaoSupport")
    private DaoSupport daoSupport;

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_order_log  ";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, OrderLogDO.class);

        return webPage;
    }

    @Override
    public List listAll(String orderSn) {

        String sql = "select * from es_order_log where order_sn = ?";
        List<OrderLogDO> list = this.daoSupport.queryForList(sql, OrderLogDO.class, orderSn);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderLogDO add(OrderLogDO orderLog) {
        this.daoSupport.insert(orderLog);
        return orderLog;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderLogDO edit(OrderLogDO orderLog, Integer id) {
        this.daoSupport.update(orderLog, id);
        return orderLog;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        this.daoSupport.delete(OrderLogDO.class, id);
    }

    @Override
    public OrderLogDO getModel(Integer id) {
        return this.daoSupport.queryForObject(OrderLogDO.class, id);
    }
}
