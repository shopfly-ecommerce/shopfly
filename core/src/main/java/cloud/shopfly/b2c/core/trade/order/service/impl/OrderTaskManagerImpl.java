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
package cloud.shopfly.b2c.core.trade.order.service.impl;

import cloud.shopfly.b2c.core.trade.order.model.enums.*;
import cloud.shopfly.b2c.core.trade.order.model.vo.*;
import cloud.shopfly.b2c.core.trade.order.service.OrderOperateManager;
import cloud.shopfly.b2c.core.trade.order.service.OrderTaskManager;
import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.client.member.MemberCommentClient;
import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.system.model.vo.SiteSetting;
import cloud.shopfly.b2c.core.trade.cart.model.dos.OrderPermission;
import cloud.shopfly.b2c.core.trade.order.model.enums.*;
import cloud.shopfly.b2c.core.trade.order.model.vo.*;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Order task
 *
 * @author Snow create in 2018/7/13
 * @version v2.0
 * @since v7.0.0
 */
@Service
public class OrderTaskManagerImpl implements OrderTaskManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private SettingClient settingClient;

    @Autowired
    private OrderOperateManager orderOperateManager;

    @Autowired
    private MemberCommentClient memberCommentClient;


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void cancelTask() {
        OrderSettingVO settingVO = this.getOrderSetting();
        int time = this.dayConversionSecond(settingVO.getCancelOrderDay());

        String sql = "select sn from es_order  where payment_type!=? and create_time+?<? and (order_status=? or order_status=? )";
        List<Map> list = daoSupport.queryForList(sql, PaymentTypeEnum.COD.value(), time,
                DateUtil.getDateline(), OrderStatusEnum.NEW.value(), OrderStatusEnum.CONFIRM.value());

        for (Map map : list) {
            CancelVO cancel = new CancelVO();
            cancel.setOrderSn(map.get("sn").toString());
            cancel.setReason("Overdue payment");
            cancel.setOperator("System testing");
            this.orderOperateManager.cancel(cancel, OrderPermission.client);
        }

    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void rogTask() {
        OrderSettingVO settingVO = this.getOrderSetting();
        // The system time
        long unixTime = DateUtil.getDateline();
        int time = this.dayConversionSecond(settingVO.getRogOrderDay());

        String sql = "select sn from es_order where order_status = ? and ship_time+?<? ";
        List<Map> list = this.daoSupport.queryForList(sql, OrderStatusEnum.SHIPPED.value(), time, unixTime);
        for (Map map : list) {
            RogVO rog = new RogVO();
            rog.setOrderSn(map.get("sn").toString());
            rog.setOperator("System testing");
            this.orderOperateManager.rog(rog, OrderPermission.client);
        }

    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void completeTask() {
        OrderSettingVO settingVO = this.getOrderSetting();
        // The system time
        long unixTime = DateUtil.getDateline();
        int time = this.dayConversionSecond(settingVO.getCompleteOrderDay());

        String sql = "select sn from es_order where signing_time+?<? and  payment_type!=? and ((ship_status=?  and order_status!=?) or order_status= ?)";
        // Query all non-cod orders with received order status
        List<Map> list = this.daoSupport.queryForList(sql, time, unixTime,
                PaymentTypeEnum.COD.value(), ShipStatusEnum.SHIP_ROG.value(), OrderStatusEnum.COMPLETE.value(), OrderStatusEnum.CANCELLED.value());

        // Payment on delivery shall be completed n days after confirmation of payment
        sql = "select sn from es_order where signing_time+?<? and payment_type=? and ((pay_status=?  and order_status!=?) or order_status= ?);";
        // Query all cash on delivery orders with received order status
        List<Map> list2 = this.daoSupport.queryForList(sql, time, unixTime,
                PaymentTypeEnum.COD.value(), PayStatusEnum.PAY_YES.value(), OrderStatusEnum.COMPLETE.value(), OrderStatusEnum.CANCELLED.value());
        list.addAll(list2);
        for (Map map : list) {
            CompleteVO complete = new CompleteVO();
            complete.setOrderSn(map.get("sn").toString());
            complete.setOperator("System testing");
            this.orderOperateManager.complete(complete, OrderPermission.client);
        }
    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void payTask() {
        OrderSettingVO settingVO = this.getOrderSetting();
        int time = this.dayConversionSecond(settingVO.getCompleteOrderPay());

        String sql = "select sn,order_price from es_order where signing_time+?<? and payment_type=? and  order_status=?";
        List<Map> list = daoSupport.queryForList(sql, time, DateUtil.getDateline(), PaymentTypeEnum.COD.value(), OrderStatusEnum.ROG.value());
        for (Map map : list) {
            this.orderOperateManager.payOrder(map.get("sn").toString(), StringUtil.toDouble(map.get("order_price"), false), "", OrderPermission.client);
        }

    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void serviceTask() {
        OrderSettingVO settingVO = this.getOrderSetting();
        // The system time
        long unixTime = DateUtil.getDateline();
        int time = this.dayConversionSecond(settingVO.getServiceExpiredDay());

        String sql = "select sn from es_order where complete_time+?<? and order_status=? and items_json like ?";
        // Query all orders whose status is completed and has not applied for after-sale orders
        List<Map> list = this.daoSupport.queryForList(sql, time, unixTime, OrderStatusEnum.COMPLETE.value(),"%"+ ServiceStatusEnum.NOT_APPLY+"%");
        for (Map map : list) {
            this.orderOperateManager.updateOrderServiceStatus(map.get("sn").toString(), ServiceStatusEnum.EXPIRED.name());
        }

    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void commentTask() {
        OrderSettingVO settingVO = this.getOrderSetting();
        int time = this.dayConversionSecond(settingVO.getCommentOrderDay());

        String sql = "select * from es_order o where o.ship_status = ? and o.comment_status =?  and ship_time +?<?";
        List<OrderDetailVO> detailList = this.daoSupport.queryForList(sql, OrderDetailVO.class,
                ShipStatusEnum.SHIP_ROG.value(), CommentStatusEnum.UNFINISHED.value(), time, DateUtil.getDateline());
        List<OrderDetailDTO> detailDTOList = new ArrayList<>();
        for (OrderDetailVO orderDetail : detailList) {
            this.orderOperateManager.updateCommentStatus(orderDetail.getSn(), CommentStatusEnum.FINISHED);

            OrderDetailDTO detailDTO = new OrderDetailDTO();
            BeanUtils.copyProperties(orderDetail, detailDTO);
            detailDTOList.add(detailDTO);
        }
        this.memberCommentClient.autoGoodComments(detailDTOList);

    }


    /**
     * Read order Settings
     *
     * @return
     */
    private OrderSettingVO getOrderSetting() {
        String settingVOJson = this.settingClient.get(SettingGroup.TRADE);

        OrderSettingVO settingVO = JsonUtil.jsonToObject(settingVOJson, OrderSettingVO.class);
        return settingVO;
    }

    /**
     * Convert days to the corresponding number of seconds
     * In test mode, the default value is1seconds
     *
     * @param day
     * @return
     */
    private Integer dayConversionSecond(int day) {
        Integer time = day * 24 * 60 * 60;
        String siteSettingJson = settingClient.get(SettingGroup.SITE);

        SiteSetting siteSetting = JsonUtil.jsonToObject(siteSettingJson, SiteSetting.class);
        if (siteSetting.getTestMode().intValue() == 1) {
            time = 1;
        }

        return time;
    }


}
