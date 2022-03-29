/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.service.impl;

import dev.shopflix.core.trade.order.model.dos.OrderOutStatus;
import dev.shopflix.core.trade.order.model.enums.OrderOutStatusEnum;
import dev.shopflix.core.trade.order.model.enums.OrderOutTypeEnum;
import dev.shopflix.core.trade.order.service.OrderOutStatusManager;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单出库状态业务类
 *
 * @author xlp
 * @version v2.0
 * @since v7.0.0
 * 2018-07-10 14:06:38
 */
@Service
public class OrderOutStatusManagerImpl implements OrderOutStatusManager {

    @Autowired
    @Qualifier("tradeDaoSupport")
    private DaoSupport daoSupport;

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_order_out_status  ";

        return this.daoSupport.queryForPage(sql, page, pageSize, OrderOutStatus.class);
    }

    @Override
    @Transactional(value = "", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public OrderOutStatus add(OrderOutStatus orderOutStatus) {
        this.daoSupport.insert(orderOutStatus);
        return orderOutStatus;
    }

    @Override
    @Transactional(value = "", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void edit(String orderSn, OrderOutTypeEnum typeEnum, OrderOutStatusEnum statusEnum) {
        String sql = "update es_order_out_status set out_status =? where order_sn=? and out_type=?";
        this.daoSupport.execute(sql, statusEnum.name(), orderSn, typeEnum.name());
    }

    @Override
    @Transactional(value = "", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {

        this.daoSupport.delete(OrderOutStatus.class, id);
    }


    @Override
    public OrderOutStatus getModel(String orderSn, OrderOutTypeEnum typeEnum) {
        String sql = "select * from es_order_out_status where order_sn=? and out_type=?";
        return this.daoSupport.queryForObject(sql, OrderOutStatus.class, orderSn, typeEnum);
    }

}
