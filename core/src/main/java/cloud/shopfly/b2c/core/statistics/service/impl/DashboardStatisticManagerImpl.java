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
package cloud.shopfly.b2c.core.statistics.service.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.client.member.MemberAskClient;
import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.statistics.model.vo.ShopDashboardVO;
import cloud.shopfly.b2c.core.statistics.service.DashboardStatisticManager;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderStatusNumVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Dashboard business implementation class
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018/6/25 10:41
 */
@Service
public class DashboardStatisticManagerImpl implements DashboardStatisticManager {

    @Autowired
    private MemberAskClient memberAskClient;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private GoodsClient goodsClient;

    /**
     * Get dashboard data
     *
     * @return Merchant center data
     */
    @Override
    public ShopDashboardVO getData() {
        // The return value
        ShopDashboardVO shopDashboardVO = new ShopDashboardVO();

        Integer num = goodsClient.queryGoodsCountByParam(1);

        // Quantity of goods on sale
        String marketGoods = null == num ? "0" : num.toString();
        shopDashboardVO.setMarketGoods(null == marketGoods ? "0" : marketGoods);

        // Quantity of goods to be put on shelves in warehouse
        num = goodsClient.queryGoodsCountByParam(0);


        String pendingGoods = null == num ? "0" : num.toString();
        shopDashboardVO.setPendingGoods(null == pendingGoods ? "0" : pendingGoods);

        // Number of pending buyer inquiries
        String pendingMemberAsk = this.memberAskClient.getNoReplyCount().toString();
        shopDashboardVO.setPendingMemberAsk(null == pendingMemberAsk ? "0" : pendingMemberAsk);

        OrderStatusNumVO orderStatusNumVO = this.orderClient.getOrderStatusNum(null);
        // All order quantity
        shopDashboardVO.setAllOrdersNum(null == orderStatusNumVO.getAllNum() ? "0" : orderStatusNumVO.getAllNum().toString());
        // Number of orders pending payment
        shopDashboardVO.setWaitPayOrderNum(null == orderStatusNumVO.getWaitPayNum() ? "0" : orderStatusNumVO.getWaitPayNum().toString());
        // Backlog order quantity
        shopDashboardVO.setWaitShipOrderNum(null == orderStatusNumVO.getWaitShipNum() ? "0" : orderStatusNumVO.getWaitShipNum().toString());
        // Backlog order quantity
        shopDashboardVO.setWaitDeliveryOrderNum(null == orderStatusNumVO.getWaitRogNum() ? "0" : orderStatusNumVO.getWaitRogNum().toString());
        // Number of after-sale orders to be processed
        shopDashboardVO.setAftersaleOrderNum(null == orderStatusNumVO.getRefundNum() ? "0" : orderStatusNumVO.getRefundNum().toString());

        return shopDashboardVO;
    }

}
