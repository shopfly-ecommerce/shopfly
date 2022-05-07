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
package cloud.shopfly.b2c.core.promotion.pintuan.service.impl;

import cloud.shopfly.b2c.core.aftersale.model.vo.BuyerCancelOrderVO;
import cloud.shopfly.b2c.core.aftersale.service.AfterSaleManager;
import cloud.shopfly.b2c.core.client.member.MemberClient;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.promotion.pintuan.model.*;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PinTuanSearchManager;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PintuanGoodsManager;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PintuanManager;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PintuanOrderManager;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.dto.OrderDTO;
import cloud.shopfly.b2c.core.trade.order.model.dto.PersonalizedData;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderDataKey;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderSkuVO;
import cloud.shopfly.b2c.core.promotion.pintuan.model.*;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.trigger.Interface.TimeTrigger;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingapex on 2019-01-24.
 * Group order business implementation class<br/>
 * The realization of a group of orders to open and participate in the group*
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-24
 */

@Service
public class PintuanOrderManagerImpl implements PintuanOrderManager {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired

    private DaoSupport tradeDaoSupport;


    @Autowired
    private PintuanGoodsManager pintuanGoodsManager;

    @Autowired
    private MemberClient memberClient;

    @Autowired
    private PintuanManager pintuanManager;

    @Autowired
    private TimeTrigger timeTrigger;

    @Autowired
    private PinTuanSearchManager pinTuanSearchManager;

    @Autowired
    private AfterSaleManager afterSaleManager;



    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PintuanOrder createOrder(OrderDTO order, Integer skuId, Integer pinTuanOrderId) {

        PintuanOrder pintuanOrder;
        PinTuanGoodsVO pinTuanGoodsVO = pintuanGoodsManager.getDetail(skuId);

        // The group order is not empty, which means to participate in the group
        if (pinTuanOrderId != null) {
            pintuanOrder = getMainOrderById(pinTuanOrderId);
            if (pintuanOrder == null) {
                if (logger.isErrorEnabled()) {
                    logger.error("Try to join the group, but the group order[" + pinTuanOrderId + "]There is no");
                }
                throw new ResourceNotFoundException("Spell group order[" + pinTuanOrderId + "]There is no");
            }


            if (logger.isDebugEnabled()) {
                logger.debug("Join a group order：");
                logger.debug(pintuanOrder);
            }
        } else {

            // Create a spell group
            pintuanOrder = new PintuanOrder();
            pintuanOrder.setEndTime(pinTuanGoodsVO.getEndTime());
            pintuanOrder.setOfferedNum(0);
            pintuanOrder.setPintuanId(pinTuanGoodsVO.getPintuanId());
            pintuanOrder.setRequiredNum(pinTuanGoodsVO.getRequiredNum());
            pintuanOrder.setSkuId(skuId);
            pintuanOrder.setGoodsId(pinTuanGoodsVO.getGoodsId());
            pintuanOrder.setThumbnail(pinTuanGoodsVO.getThumbnail());
            pintuanOrder.setOrderStatus(PintuanOrderStatus.new_order.name());
            pintuanOrder.setGoodsName(pinTuanGoodsVO.getGoodsName());

            // Add a group order
            this.tradeDaoSupport.insert(pintuanOrder);
            pinTuanOrderId = this.tradeDaoSupport.getLastId("es_pintuan_order");
            pintuanOrder.setOrderId(pinTuanOrderId);

            if (logger.isDebugEnabled()) {
                logger.debug("Create a new group order：");
                logger.debug(pintuanOrder);
            }
        }

        // Create a suborder
        PintuanChildOrder childOrder = new PintuanChildOrder();
        childOrder.setSkuId(skuId);
        childOrder.setOrderStatus(PintuanOrderStatus.wait.name());

        // Group activity ID
        childOrder.setPintuanId(pinTuanGoodsVO.getPintuanId());
        childOrder.setOrderSn(order.getSn());

        // Group order ID
        childOrder.setOrderId(pintuanOrder.getOrderId());
        childOrder.setMemberId(order.getMemberId());
        childOrder.setMemberName(order.getMemberName());
        childOrder.setOriginPrice(pinTuanGoodsVO.getOriginPrice());
        childOrder.setSalesPrice(pinTuanGoodsVO.getSalesPrice());

        tradeDaoSupport.insert(childOrder);

        if (logger.isDebugEnabled()) {
            logger.debug("Create a new suborder：");
            logger.debug(childOrder);
        }
        return pintuanOrder;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void payOrder(OrderDO order) {

        String orderSn = order.getSn();
        // Find suborders
        PintuanChildOrder childOrder = this.getChildByOrderSn(orderSn);

        if (logger.isDebugEnabled()) {
            logger.debug("The order【" + order.getSn() + "】支付成功，获得其对应拼团子The order为：");
            logger.debug(childOrder);
        }

        // Find the master order
        PintuanOrder pintuanOrder = this.getMainOrderById(childOrder.getOrderId());

        // Join a participant
        Member member = memberClient.getModel(order.getMemberId());
        Participant participant = new Participant();
        participant.setId(member.getMemberId());
        participant.setName(member.getUname());
        participant.setFace(member.getFace());
        pintuanOrder.appendParticipant(participant);


        // The number of clusters
        Integer requiredNum = pintuanOrder.getRequiredNum();

        // Number of participants
        Integer offeredNum = pintuanOrder.getOfferedNum();

        // The new one
        offeredNum++;
        pintuanOrder.setOfferedNum(offeredNum);

        // If the group is already formed if the system sets the group to two and the actual payment is made by three, then the group is formed
        if (offeredNum >= requiredNum) {

            if (logger.isDebugEnabled()) {
                logger.debug("Spell group order：" + pintuanOrder + "Have clouds");
            }

            pintuanOrder.setOrderStatus(PintuanOrderStatus.formed.name());

            // Update group orders
            tradeDaoSupport.update(pintuanOrder, pintuanOrder.getOrderId());

            formed(pintuanOrder.getOrderId());

            if (logger.isDebugEnabled()) {
                logger.debug("Update all sub-orders and regular orders as formed");
            }
        } else {

            if (logger.isDebugEnabled()) {
                logger.debug("offeredNum[" + offeredNum + "],requiredNum[" + requiredNum + "]");
                logger.debug("Spell group order：" + pintuanOrder + "Has yet to form a smooth dough");
            }

            // Update group order to be group
            pintuanOrder.setOrderStatus(PintuanOrderStatus.wait.name());

            // Update suborder to paid status
            tradeDaoSupport.execute("update es_pintuan_child_order set order_status=? where child_order_id=?", PintuanOrderStatus.pay_off.name(), childOrder.getChildOrderId());

            // Update group orders
            tradeDaoSupport.update(pintuanOrder, pintuanOrder.getOrderId());
        }

        // The first person, the one who created the group, configure the scheduled task for processing
        if (pintuanOrder.getOfferedNum() == 1) {
            // Mark delayed tasks to process this order, delay cancellation/auto-group at the end of the activity
            timeTrigger.add("pintuanOrderHandlerTriggerExecuter", pintuanOrder.getOrderId(), pintuanOrder.getEndTime(), "pintuan_order_handler_" + pintuanOrder.getOrderId());
        }


        // Update the number of group members, update this order, but also to update the entire group of orders
        updatePintuanPerson(pintuanOrder, requiredNum - offeredNum);

    }

    /**
     * According to theidAccess model
     *
     * @param id
     * @return
     */
    @Override
    public PintuanOrder getModel(Integer id) {
        return this.tradeDaoSupport.queryForObject(PintuanOrder.class, id);
    }

    /**
     * Update the number of players in a group
     *
     * @param pintuanOrder Group master order
     * @param num          Quantity
     */
    private void updatePintuanPerson(PintuanOrder pintuanOrder, Integer num) {

        // Query all suborders based on the master order
        String sql = "select o.order_data,o.sn from  es_pintuan_child_order pc inner join es_order o on pc.order_sn = o.sn  and pc.order_id = ?";
        List<Map> list = this.tradeDaoSupport.queryForList(sql, pintuanOrder.getOrderId());

        for (Map orderMap : list) {

            String orderData = orderMap.get("order_data") == null ? "" : orderMap.get("order_data").toString();
            String sn = orderMap.get("sn").toString();

            PersonalizedData personalizedData = new PersonalizedData(orderData);
            Map map = new HashMap<>();
            // Were a few people short of a group
            map.put("owesPersonNums", num);
            personalizedData.setPersonalizedData(OrderDataKey.pintuan, map);

            if (logger.isDebugEnabled()) {
                logger.debug("Spell group order" + sn + "We still need the size of the group:" + num);
            }

            // Update personalization data for orders
            tradeDaoSupport.execute("update es_order set order_data=? where sn=?", personalizedData.getData(), sn);


        }


    }

    /**
     * A group order group operation
     *
     * @param pinTuanOrderId
     */
    private void formed(Integer pinTuanOrderId) {

        // The order
        PintuanOrder pintuanOrder = this.getMainOrderById(pinTuanOrderId);

        String sql = "select order_sn from es_pintuan_child_order where order_id =?";

        List<Map> list = tradeDaoSupport.queryForList(sql, pinTuanOrderId);
        list.forEach(map -> {
            String orderSn = map.get("order_sn").toString();
            // Update order status to Formed
            tradeDaoSupport.execute("update es_order set order_status=? where sn=?", OrderStatusEnum.FORMED.name(), orderSn);

        });

        // Update all suborders as already formed
        tradeDaoSupport.execute("update es_pintuan_child_order set order_status=? where order_id=?", PintuanOrderStatus.formed.name(), pinTuanOrderId);

        PintuanGoodsDO goodsDO = pintuanGoodsManager.getModel(pintuanOrder.getPintuanId(), pintuanOrder.getSkuId());
        pintuanGoodsManager.addQuantity(goodsDO.getId(), pintuanOrder.getOfferedNum());
        pinTuanSearchManager.addIndex(pintuanGoodsManager.getDetail(goodsDO.getSkuId()));

    }


    @Override
    public PintuanOrderDetailVo getMainOrderBySn(String orderSn) {

        String sql = "select o.*,co.origin_price,co.sales_price from es_pintuan_order o ,es_pintuan_child_order co where o.order_id =co.order_id  and co.order_sn=?";

        return tradeDaoSupport.queryForObject(sql, PintuanOrderDetailVo.class, orderSn);

    }

    @Override
    public List<PintuanOrder> getWaitOrder(Integer goodsId, Integer skuId) {

        String sql = "select * from es_pintuan_order where sku_id=? and order_status=? " +
                " and pintuan_id = (select pintuan_id from es_pintuan p inner join es_pintuan_goods pg on p.promotion_id = pg.pintuan_id " +
                "  where start_time < ? and end_time >? and pg.sku_id = ? GROUP BY pintuan_id) ";

        return tradeDaoSupport.queryForList(sql, PintuanOrder.class, skuId, PintuanOrderStatus.wait.name(), DateUtil.getDateline(), DateUtil.getDateline(), skuId);
    }

    /**
     * Reads all suborders of an order
     *
     * @param orderId
     * @return
     */
    @Override
    public List<PintuanChildOrder> getPintuanChild(Integer orderId) {
        String sql = "select * from es_pintuan_child_order where order_id=?";
        return tradeDaoSupport.queryForList(sql, PintuanChildOrder.class, orderId);
    }

    /**
     * Deal with group orders
     *
     * @param orderId The orderid
     */
    @Override
    public void handle(Integer orderId) {

        // Process orders
        PintuanOrder pintuanOrder = this.getMainOrderById(orderId);

        Pintuan pintuan = pintuanManager.getModel(pintuanOrder.getPintuanId());

        List<PintuanChildOrder> pintuanChildOrders = this.getPintuanChild(pintuanOrder.getOrderId());

        // If virtual group is not enabled
        if (pintuan.getEnableMocker().equals(0)) {
            // Number of members < number of participants
            if (pintuanOrder.getOfferedNum() < pintuanOrder.getRequiredNum()) {
                pintuanChildOrders.forEach(child -> {
                    BuyerCancelOrderVO buyerCancelOrderVO = new BuyerCancelOrderVO();
                    buyerCancelOrderVO.setRefundReason("System cancel order");
                    buyerCancelOrderVO.setOrderSn(child.getOrderSn());
                    afterSaleManager.sysCancelOrder(buyerCancelOrderVO);
                });
            }
        }//Virtual clusters&&No clouds
        else if (pintuanOrder.getRequiredNum() - pintuanOrder.getOfferedNum() > 0) {

            // Modify order information
            int num = pintuanOrder.getRequiredNum() - pintuanOrder.getOfferedNum();
            pintuanOrder.setOfferedNum(pintuanOrder.getRequiredNum());
            // Anonymous participants
            for (int i = 0; i < num; i++) {
                Participant participant = new Participant();
                participant.setId(-1);
                participant.setName("Jack Bauer");
                participant.setFace("http://image.com/normal/912BDD3146AE4BE19831DB9F357A34D8.jpeg");
                pintuanOrder.appendParticipant(participant);
            }
            // Update personalization data for orders
            Map map = new HashMap(1);
            map.put("order_id", pintuanOrder.getOrderId());
            // The goods are already packaged
            pintuanOrder.setOrderStatus(PintuanOrderStatus.formed.name());
            this.tradeDaoSupport.update("es_pintuan_order", pintuanOrder, map);
            // Update this group order group
            this.formed(orderId);
        }

    }


    /**
     * Find the group order according to the order number
     *
     * @param orderSn
     * @return
     */
    private PintuanChildOrder getChildByOrderSn(String orderSn) {
        String sql = "select * from es_pintuan_child_order where order_sn=?";
        return tradeDaoSupport.queryForObject(sql, PintuanChildOrder.class, orderSn);
    }

    /**
     * Obtain by orderskuid
     *
     * @param order Regular ordersdo
     * @return This order corresponds toskuid
     */
    private Integer getSkuId(OrderDO order) {
        String itemsJson = order.getItemsJson();
        List<OrderSkuVO> list = JsonUtil.jsonToList(itemsJson, OrderSkuVO.class);
        OrderSkuVO skuVO = list.get(0);
        return skuVO.getSkuId();

    }

    private PintuanOrder getMainOrderById(Integer orderId) {
        String sql = "select * from es_pintuan_order where order_id=?";
        return tradeDaoSupport.queryForObject(sql, PintuanOrder.class, orderId);
    }

    /**
     * To find theskuOrders to group<br/>
     *
     * @param skuId
     * @return If this existsskuIs returned, otherwise returnednull
     */
    private PintuanOrder getBySkuId(Integer skuId) {
        String sql = "select * from es_pintuan_order where sku_id=?";
        return tradeDaoSupport.queryForObject(sql, PintuanOrder.class, skuId);
    }

}
