/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.member.service.impl;

import cloud.shopfly.b2c.core.member.model.vo.SalesVO;
import cloud.shopfly.b2c.core.member.service.MemberSalesManager;
import cloud.shopfly.b2c.core.trade.order.model.enums.ShipStatusEnum;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import org.springframework.beans.factory.annotation.Autowired;
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
