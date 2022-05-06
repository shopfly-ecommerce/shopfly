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
 * 仪表盘业务实现类
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
     * 获取仪表盘数据
     *
     * @return 商家中心数据
     */
    @Override
    public ShopDashboardVO getData() {
        // 返回值
        ShopDashboardVO shopDashboardVO = new ShopDashboardVO();

        Integer num = goodsClient.queryGoodsCountByParam(1);

        // 出售中的商品数量
        String marketGoods = null == num ? "0" : num.toString();
        shopDashboardVO.setMarketGoods(null == marketGoods ? "0" : marketGoods);

        // 仓库待上架的商品数量
        num = goodsClient.queryGoodsCountByParam(0);


        String pendingGoods = null == num ? "0" : num.toString();
        shopDashboardVO.setPendingGoods(null == pendingGoods ? "0" : pendingGoods);

        // 待处理买家咨询数量
        String pendingMemberAsk = this.memberAskClient.getNoReplyCount().toString();
        shopDashboardVO.setPendingMemberAsk(null == pendingMemberAsk ? "0" : pendingMemberAsk);

        OrderStatusNumVO orderStatusNumVO = this.orderClient.getOrderStatusNum(null);
        // 所有订单数量
        shopDashboardVO.setAllOrdersNum(null == orderStatusNumVO.getAllNum() ? "0" : orderStatusNumVO.getAllNum().toString());
        // 待付款订单数量
        shopDashboardVO.setWaitPayOrderNum(null == orderStatusNumVO.getWaitPayNum() ? "0" : orderStatusNumVO.getWaitPayNum().toString());
        // 待发货订单数量
        shopDashboardVO.setWaitShipOrderNum(null == orderStatusNumVO.getWaitShipNum() ? "0" : orderStatusNumVO.getWaitShipNum().toString());
        // 待收货订单数量
        shopDashboardVO.setWaitDeliveryOrderNum(null == orderStatusNumVO.getWaitRogNum() ? "0" : orderStatusNumVO.getWaitRogNum().toString());
        // 待处理申请售后订单数量
        shopDashboardVO.setAftersaleOrderNum(null == orderStatusNumVO.getRefundNum() ? "0" : orderStatusNumVO.getRefundNum().toString());

        return shopDashboardVO;
    }

}
