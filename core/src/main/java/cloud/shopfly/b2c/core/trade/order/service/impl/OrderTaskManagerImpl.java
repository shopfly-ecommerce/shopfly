/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
import dev.shopflix.core.trade.order.model.enums.*;
import dev.shopflix.core.trade.order.model.vo.*;
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
 * 订单任务
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
            cancel.setReason("超时未付款");
            cancel.setOperator("系统检测");
            this.orderOperateManager.cancel(cancel, OrderPermission.client);
        }

    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void rogTask() {
        OrderSettingVO settingVO = this.getOrderSetting();
        //系统时间
        long unixTime = DateUtil.getDateline();
        int time = this.dayConversionSecond(settingVO.getRogOrderDay());

        String sql = "select sn from es_order where order_status = ? and ship_time+?<? ";
        List<Map> list = this.daoSupport.queryForList(sql, OrderStatusEnum.SHIPPED.value(), time, unixTime);
        for (Map map : list) {
            RogVO rog = new RogVO();
            rog.setOrderSn(map.get("sn").toString());
            rog.setOperator("系统检测");
            this.orderOperateManager.rog(rog, OrderPermission.client);
        }

    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void completeTask() {
        OrderSettingVO settingVO = this.getOrderSetting();
        //系统时间
        long unixTime = DateUtil.getDateline();
        int time = this.dayConversionSecond(settingVO.getCompleteOrderDay());

        String sql = "select sn from es_order where signing_time+?<? and  payment_type!=? and ((ship_status=?  and order_status!=?) or order_status= ?)";
        // 查询所有非货到付款并且订单状态为已收货的订单
        List<Map> list = this.daoSupport.queryForList(sql, time, unixTime,
                PaymentTypeEnum.COD.value(), ShipStatusEnum.SHIP_ROG.value(), OrderStatusEnum.COMPLETE.value(), OrderStatusEnum.CANCELLED.value());

        // 货到付款的，确认收款之后n天为完成
        sql = "select sn from es_order where signing_time+?<? and payment_type=? and ((pay_status=?  and order_status!=?) or order_status= ?);";
        // 查询所有货到付款并且订单状态为已收货的订单
        List<Map> list2 = this.daoSupport.queryForList(sql, time, unixTime,
                PaymentTypeEnum.COD.value(), PayStatusEnum.PAY_YES.value(), OrderStatusEnum.COMPLETE.value(), OrderStatusEnum.CANCELLED.value());
        list.addAll(list2);
        for (Map map : list) {
            CompleteVO complete = new CompleteVO();
            complete.setOrderSn(map.get("sn").toString());
            complete.setOperator("系统检测");
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
        //系统时间
        long unixTime = DateUtil.getDateline();
        int time = this.dayConversionSecond(settingVO.getServiceExpiredDay());

        String sql = "select sn from es_order where complete_time+?<? and order_status=? and items_json like ?";
        // 查询所有订单状态为已完成的订单并且是未申请售后的订单
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
     * 读取订单设置
     *
     * @return
     */
    private OrderSettingVO getOrderSetting() {
        String settingVOJson = this.settingClient.get(SettingGroup.TRADE);

        OrderSettingVO settingVO = JsonUtil.jsonToObject(settingVOJson, OrderSettingVO.class);
        return settingVO;
    }

    /**
     * 将天数转换为相应的秒数
     * 如果是测试模式，默认为1秒
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
