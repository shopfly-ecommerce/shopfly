/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.member.service.impl;

import com.enation.app.javashop.core.member.model.vo.SalesVO;
import com.enation.app.javashop.core.member.service.MemberSalesManager;
import com.enation.app.javashop.core.trade.order.model.enums.ShipStatusEnum;
import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.database.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * MemberSalesMangerImpl
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-06-29 上午9:41
 */
@Service
public class MemberSalesManagerImpl implements MemberSalesManager {

    @Autowired
    @Qualifier("tradeDaoSupport")
    private DaoSupport daoSupport;

    /**
     * 商品销售记录
     *
     * @param pageSize
     * @param pageNo
     * @param goodsId
     * @return
     */
    @Override
    public Page<SalesVO> list(Integer pageSize, Integer pageNo, Integer goodsId) {
        return this.daoSupport.queryForPage("select o.member_name as buyer_name,oi.price,oi.num,o.payment_time as pay_time from " +
                "es_order_items oi inner join es_order o on o.sn = oi.order_sn where o.ship_status = ? and goods_id = ? and num>0 order by create_time desc", pageNo, pageSize, SalesVO.class, ShipStatusEnum.SHIP_ROG.value(),goodsId);

    }
}
