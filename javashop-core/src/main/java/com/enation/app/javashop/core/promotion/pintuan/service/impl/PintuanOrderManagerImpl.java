/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.pintuan.service.impl;

import com.enation.app.javashop.core.aftersale.model.vo.BuyerCancelOrderVO;
import com.enation.app.javashop.core.aftersale.service.AfterSaleManager;
import com.enation.app.javashop.core.client.member.MemberClient;
import com.enation.app.javashop.core.member.model.dos.Member;
import com.enation.app.javashop.core.promotion.pintuan.model.*;
import com.enation.app.javashop.core.promotion.pintuan.service.PinTuanSearchManager;
import com.enation.app.javashop.core.promotion.pintuan.service.PintuanGoodsManager;
import com.enation.app.javashop.core.promotion.pintuan.service.PintuanManager;
import com.enation.app.javashop.core.promotion.pintuan.service.PintuanOrderManager;
import com.enation.app.javashop.core.trade.order.model.dos.OrderDO;
import com.enation.app.javashop.core.trade.order.model.dto.OrderDTO;
import com.enation.app.javashop.core.trade.order.model.dto.PersonalizedData;
import com.enation.app.javashop.core.trade.order.model.enums.OrderDataKey;
import com.enation.app.javashop.core.trade.order.model.enums.OrderStatusEnum;
import com.enation.app.javashop.core.trade.order.model.vo.OrderSkuVO;
import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.exception.ResourceNotFoundException;
import com.enation.app.javashop.framework.trigger.Interface.TimeTrigger;
import com.enation.app.javashop.framework.util.DateUtil;
import com.enation.app.javashop.framework.util.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingapex on 2019-01-24.
 * 拼团订单业务实现类<br/>
 * 实现了拼团订单开团和参团 *
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
    @Qualifier("tradeDaoSupport")
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
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PintuanOrder createOrder(OrderDTO order, Integer skuId, Integer pinTuanOrderId) {

        PintuanOrder pintuanOrder;
        PinTuanGoodsVO pinTuanGoodsVO = pintuanGoodsManager.getDetail(skuId);

        //拼团订单不为空，表示要参团
        if (pinTuanOrderId != null) {
            pintuanOrder = getMainOrderById(pinTuanOrderId);
            if (pintuanOrder == null) {
                if (logger.isErrorEnabled()) {
                    logger.error("试图参加拼团，但拼团订单[" + pinTuanOrderId + "]不存在");
                }
                throw new ResourceNotFoundException("拼团订单[" + pinTuanOrderId + "]不存在");
            }


            if (logger.isDebugEnabled()) {
                logger.debug("参加拼团订单：");
                logger.debug(pintuanOrder);
            }
        } else {

            //创建拼团
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

            //新增一个拼团订单
            this.tradeDaoSupport.insert(pintuanOrder);
            pinTuanOrderId = this.tradeDaoSupport.getLastId("es_pintuan_order");
            pintuanOrder.setOrderId(pinTuanOrderId);

            if (logger.isDebugEnabled()) {
                logger.debug("创建一个新的拼团订单：");
                logger.debug(pintuanOrder);
            }
        }

        //创建子订单
        PintuanChildOrder childOrder = new PintuanChildOrder();
        childOrder.setSkuId(skuId);
        childOrder.setOrderStatus(PintuanOrderStatus.wait.name());

        //拼团活动id
        childOrder.setPintuanId(pinTuanGoodsVO.getPintuanId());
        childOrder.setOrderSn(order.getSn());

        //拼团订单id
        childOrder.setOrderId(pintuanOrder.getOrderId());
        childOrder.setMemberId(order.getMemberId());
        childOrder.setMemberName(order.getMemberName());
        childOrder.setOriginPrice(pinTuanGoodsVO.getOriginPrice());
        childOrder.setSalesPrice(pinTuanGoodsVO.getSalesPrice());

        tradeDaoSupport.insert(childOrder);

        if (logger.isDebugEnabled()) {
            logger.debug("创建一个新的子订单：");
            logger.debug(childOrder);
        }
        return pintuanOrder;
    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void payOrder(OrderDO order) {

        String orderSn = order.getSn();
        //查找子订单
        PintuanChildOrder childOrder = this.getChildByOrderSn(orderSn);

        if (logger.isDebugEnabled()) {
            logger.debug("订单【" + order.getSn() + "】支付成功，获得其对应拼团子订单为：");
            logger.debug(childOrder);
        }

        //查找主订单
        PintuanOrder pintuanOrder = this.getMainOrderById(childOrder.getOrderId());

        //加入一个参团者
        Member member = memberClient.getModel(order.getMemberId());
        Participant participant = new Participant();
        participant.setId(member.getMemberId());
        participant.setName(member.getUname());
        participant.setFace(member.getFace());
        pintuanOrder.appendParticipant(participant);


        //成团人数
        Integer requiredNum = pintuanOrder.getRequiredNum();

        //已参团人数
        Integer offeredNum = pintuanOrder.getOfferedNum();

        //新增一人
        offeredNum++;
        pintuanOrder.setOfferedNum(offeredNum);

        //如果已经成团 如果系统设置两人成团，实际三人付款，那么也成团
        if (offeredNum >= requiredNum) {

            if (logger.isDebugEnabled()) {
                logger.debug("拼团订单：" + pintuanOrder + "已经成团");
            }

            pintuanOrder.setOrderStatus(PintuanOrderStatus.formed.name());

            //更新拼团订单
            tradeDaoSupport.update(pintuanOrder, pintuanOrder.getOrderId());

            formed(pintuanOrder.getOrderId());

            if (logger.isDebugEnabled()) {
                logger.debug("更新所有子订单及普通订单为已成团");
            }
        } else {

            if (logger.isDebugEnabled()) {
                logger.debug("offeredNum[" + offeredNum + "],requiredNum[" + requiredNum + "]");
                logger.debug("拼团订单：" + pintuanOrder + "尚未成团");
            }

            //更新拼团订单为待成团
            pintuanOrder.setOrderStatus(PintuanOrderStatus.wait.name());

            //更新子订单为已支付状态
            tradeDaoSupport.execute("update es_pintuan_child_order set order_status=? where child_order_id=?", PintuanOrderStatus.pay_off.name(), childOrder.getChildOrderId());

            //更新拼团订单
            tradeDaoSupport.update(pintuanOrder, pintuanOrder.getOrderId());
        }

        //第一个人，即为创建拼团的人，那么配置定时任务进行处理
        if (pintuanOrder.getOfferedNum() == 1) {
            //标记延时任务处理这个订单，活动结束时   延时取消/自动成团
            timeTrigger.add("pintuanOrderHandlerTriggerExecuter", pintuanOrder.getOrderId(), pintuanOrder.getEndTime(), "pintuan_order_handler_" + pintuanOrder.getOrderId());
        }


        //更新拼团成团人数，更新本订单，也要更新整个团的订单
        updatePintuanPerson(pintuanOrder, requiredNum - offeredNum);

    }

    /**
     * 根据id获取模型
     *
     * @param id
     * @return
     */
    @Override
    public PintuanOrder getModel(Integer id) {
        return this.tradeDaoSupport.queryForObject(PintuanOrder.class, id);
    }

    /**
     * 更新拼团成团人数
     *
     * @param pintuanOrder 拼团主订单
     * @param num          数量
     */
    private void updatePintuanPerson(PintuanOrder pintuanOrder, Integer num) {

        //根据主订单查询所有的子订单
        String sql = "select o.order_data,o.sn from  es_pintuan_child_order pc inner join es_order o on pc.order_sn = o.sn  and pc.order_id = ?";
        List<Map> list = this.tradeDaoSupport.queryForList(sql, pintuanOrder.getOrderId());

        for (Map orderMap : list) {

            String orderData = orderMap.get("order_data") == null ? "" : orderMap.get("order_data").toString();
            String sn = orderMap.get("sn").toString();

            PersonalizedData personalizedData = new PersonalizedData(orderData);
            Map map = new HashMap<>();
            //还差几人成团
            map.put("owesPersonNums", num);
            personalizedData.setPersonalizedData(OrderDataKey.pintuan, map);

            if (logger.isDebugEnabled()) {
                logger.debug("拼团订单" + sn + "还差成团人数:" + num);
            }

            //更新订单的个性化数据
            tradeDaoSupport.execute("update es_order set order_data=? where sn=?", personalizedData.getData(), sn);


        }


    }

    /**
     * 某个拼团订单成团操作
     *
     * @param pinTuanOrderId
     */
    private void formed(Integer pinTuanOrderId) {

        //订单
        PintuanOrder pintuanOrder = this.getMainOrderById(pinTuanOrderId);

        String sql = "select order_sn from es_pintuan_child_order where order_id =?";

        List<Map> list = tradeDaoSupport.queryForList(sql, pinTuanOrderId);
        list.forEach(map -> {
            String orderSn = map.get("order_sn").toString();
            //更新订单状态为已成团
            tradeDaoSupport.execute("update es_order set order_status=? where sn=?", OrderStatusEnum.FORMED.name(), orderSn);

        });

        //更新所有子订单为已经成团
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
     * 读取某订单的所有子订单
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
     * 处理拼团订单
     *
     * @param orderId 订单id
     */
    @Override
    public void handle(Integer orderId) {

        //处理订单
        PintuanOrder pintuanOrder = this.getMainOrderById(orderId);

        Pintuan pintuan = pintuanManager.getModel(pintuanOrder.getPintuanId());

        List<PintuanChildOrder> pintuanChildOrders = this.getPintuanChild(pintuanOrder.getOrderId());

        //如果未开启虚拟成团
        if (pintuan.getEnableMocker().equals(0)) {
            //成团人数<参团人数
            if (pintuanOrder.getOfferedNum() < pintuanOrder.getRequiredNum()) {
                pintuanChildOrders.forEach(child -> {
                    BuyerCancelOrderVO buyerCancelOrderVO = new BuyerCancelOrderVO();
                    buyerCancelOrderVO.setRefundReason("系统取消订单");
                    buyerCancelOrderVO.setOrderSn(child.getOrderSn());
                    afterSaleManager.sysCancelOrder(buyerCancelOrderVO);
                });
            }
        }//虚拟成团&&未成团
        else if (pintuanOrder.getRequiredNum() - pintuanOrder.getOfferedNum() > 0) {

            //修改订单信息
            int num = pintuanOrder.getRequiredNum() - pintuanOrder.getOfferedNum();
            pintuanOrder.setOfferedNum(pintuanOrder.getRequiredNum());
            //匿名参团人
            for (int i = 0; i < num; i++) {
                Participant participant = new Participant();
                participant.setId(-1);
                participant.setName("小强");
                participant.setFace("http://javashop-statics.oss-cn-beijing.aliyuncs.com/v70/normal/912BDD3146AE4BE19831DB9F357A34D8.jpeg");
                pintuanOrder.appendParticipant(participant);
            }
            //更新订单的个性化数据
            Map map = new HashMap(1);
            map.put("order_id", pintuanOrder.getOrderId());
            //该商品已经成团
            pintuanOrder.setOrderStatus(PintuanOrderStatus.formed.name());
            this.tradeDaoSupport.update("es_pintuan_order", pintuanOrder, map);
            //更新这个拼团订单成团了
            this.formed(orderId);
        }

    }


    /**
     * 根据订单编号找到拼团子订单
     *
     * @param orderSn
     * @return
     */
    private PintuanChildOrder getChildByOrderSn(String orderSn) {
        String sql = "select * from es_pintuan_child_order where order_sn=?";
        return tradeDaoSupport.queryForObject(sql, PintuanChildOrder.class, orderSn);
    }

    /**
     * 通过订单获取skuid
     *
     * @param order 普通订单do
     * @return 这个订单对应的skuid
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
     * 查找此sku待成团的订单<br/>
     *
     * @param skuId
     * @return 如果存在这个sku的拼团订单则返回，否则返回null
     */
    private PintuanOrder getBySkuId(Integer skuId) {
        String sql = "select * from es_pintuan_order where sku_id=?";
        return tradeDaoSupport.queryForObject(sql, PintuanOrder.class, skuId);
    }

}
