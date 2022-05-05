/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.service.impl;

import dev.shopflix.core.aftersale.service.AfterSaleManager;
import dev.shopflix.core.base.SettingGroup;
import dev.shopflix.core.client.system.SettingClient;
import dev.shopflix.core.payment.model.dos.PaymentMethodDO;
import dev.shopflix.core.payment.service.PaymentMethodManager;
import dev.shopflix.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import dev.shopflix.core.trade.TradeErrorCode;
import dev.shopflix.core.trade.cart.model.vo.CouponVO;
import dev.shopflix.core.trade.order.model.dos.OrderDO;
import dev.shopflix.core.trade.order.model.dos.OrderItemsDO;
import dev.shopflix.core.trade.order.model.dos.OrderMetaDO;
import dev.shopflix.core.trade.order.model.dto.OrderQueryParam;
import dev.shopflix.core.trade.order.model.enums.*;
import dev.shopflix.core.trade.order.model.vo.*;
import dev.shopflix.core.trade.order.service.OrderMetaManager;
import dev.shopflix.core.trade.order.service.OrderQueryManager;
import dev.shopflix.core.trade.sdk.model.OrderDetailDTO;
import dev.shopflix.core.trade.sdk.model.OrderSkuDTO;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.ServiceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.shopflix.framework.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单查询业务实现类
 *
 * @author Snow create in 2018/5/14
 * @version v2.0
 * @since v7.0.0
 */
@Service
public class OrderQueryManagerImpl implements OrderQueryManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private AfterSaleManager afterSaleManager;

    @Autowired
    private OrderMetaManager orderMetaManager;

    @Autowired
    private SettingClient settingClient;

    @Autowired
    private PaymentMethodManager paymentMethodManager;

    @Override
    public OrderStatusNumVO getOrderStatusNum(Integer memberId) {

        StringBuffer sql = new StringBuffer("select order_type,order_status,pay_status,ship_status,payment_type,comment_status,count(order_id) as count from es_order o ");

        List<Object> term = new ArrayList<>();

        List<String> sqlSplice = new ArrayList<>();
        // 按买家查询
        if (memberId != null) {
            sqlSplice.add("o.member_id = ? ");
            term.add(memberId);
        }
        String sqlSplicing = SqlSplicingUtil.sqlSplicing(sqlSplice);
        if (!StringUtil.isEmpty(sqlSplicing)) {
            sql.append(sqlSplicing);
        }
        sql.append(" GROUP BY order_status,pay_status,ship_status,comment_status,payment_type,order_type");

        List<Map<String, Object>> list = this.daoSupport.queryForList(sql.toString(), term.toArray());

        // 所有订单数
        StringBuilder allNumSql = new StringBuilder("select count(order_id) as count from es_order o ");
        if (!StringUtil.isEmpty(sqlSplicing)) {
            allNumSql.append(sqlSplicing);
        }

        OrderStatusNumVO numVO = new OrderStatusNumVO();
        numVO.setWaitShipNum(0);
        numVO.setWaitPayNum(0);
        numVO.setWaitRogNum(0);
        numVO.setCompleteNum(0);
        numVO.setCancelNum(0);
        numVO.setWaitCommentNum(0);
        numVO.setAllNum(this.daoSupport.queryForInt(allNumSql.toString(), term.toArray()));

        // 支付状态未支付，订单状态已确认，为待付款订单
        for (Map map : list) {
            boolean flag = (OrderStatusEnum.CONFIRM.value().equals(map.get("order_status").toString()) && !"COD".equals(map.get("payment_type").toString()))
                    || (OrderStatusEnum.ROG.value().equals(map.get("order_status").toString()) && "COD".equals(map.get("payment_type").toString()));
            if (flag) {
                numVO.setWaitPayNum(numVO.getWaitPayNum() + (null == map.get("count") ? 0 : Integer.parseInt(map.get("count").toString())));
            }

            // 物流状态为未发货，订单状态为已收款，为待发货订单
            flag = (OrderStatusEnum.CONFIRM.value().equals(map.get("order_status").toString()) && "COD".equals(map.get("payment_type").toString()) && OrderTypeEnum.normal.name().equals(map.get("order_type").toString()))
                    || (OrderStatusEnum.PAID_OFF.value().equals(map.get("order_status").toString()) && !"COD".equals(map.get("payment_type").toString()) && OrderTypeEnum.normal.name().equals(map.get("order_type").toString()))
                    || (OrderTypeEnum.pintuan.name().equals(map.get("order_type").toString()) && OrderStatusEnum.FORMED.value().equals(map.get("order_status").toString()));
            if (flag) {
                numVO.setWaitShipNum(numVO.getWaitShipNum() + (null == map.get("count") ? 0 : Integer.parseInt(map.get("count").toString())));
            }

            // 订单状态为已发货，为待收货订单
            if (OrderStatusEnum.SHIPPED.value().equals(map.get("order_status").toString())) {
                numVO.setWaitRogNum(numVO.getWaitRogNum() + (null == map.get("count") ? 0 : Integer.parseInt(map.get("count").toString())));
            }

            // 订单状态为已取消，为已取消订单
            if (OrderStatusEnum.CANCELLED.value().equals(map.get("order_status").toString())) {
                numVO.setCancelNum(numVO.getCancelNum() + (null == map.get("count") ? 0 : Integer.parseInt(map.get("count").toString())));
            }

            // 订单状态为已完成，为已完成订单
            if (OrderStatusEnum.COMPLETE.value().equals(map.get("order_status").toString())) {
                numVO.setCompleteNum(numVO.getCompleteNum() + (null == map.get("count") ? 0 : Integer.parseInt(map.get("count").toString())));
            }

            // 评论状态为未评论，订单状态为已收货，为待评论订单
            if (CommentStatusEnum.UNFINISHED.value().equals(map.get("comment_status").toString()) && OrderStatusEnum.ROG.value().equals(map.get("order_status").toString())) {
                numVO.setWaitCommentNum(numVO.getWaitCommentNum() + (null == map.get("count") ? 0 : Integer.parseInt(map.get("count").toString())));
            }
        }

        // 申请售后，但未完成售后的订单
        numVO.setRefundNum(this.afterSaleManager.getAfterSaleCount(memberId));

        return numVO;

    }

    @Override
    public Integer getOrderNumByMemberId(Integer memberId) {
        String sql = "select count(0) from es_order where member_id=?";
        Integer num = this.daoSupport.queryForInt(sql, memberId);
        return num;
    }

    @Override
    public Integer getOrderCommentNumByMemberId(Integer memberId, String commentStatus) {
        StringBuffer sql = new StringBuffer("select count(0) from es_order where member_id=? ");

        sql.append(" and ship_status='" + ShipStatusEnum.SHIP_ROG + "' and comment_status = ?  ");

        Integer num = this.daoSupport.queryForInt(sql.toString(), memberId, commentStatus);
        return num;
    }

    @Override
    public OrderDetailVO getModel(String orderSn, Integer buyerId) {

        List param = new ArrayList();

        StringBuffer sql = new StringBuffer();
        sql.append("select * from es_order where sn = ? ");
        param.add(orderSn);

        if (buyerId != null) {
            sql.append(" and member_id =?");
            param.add(buyerId);
        }

        OrderDO orderDO = this.daoSupport.queryForObject(sql.toString(), OrderDO.class, param.toArray());
        if (orderDO == null) {
            throw new ServiceException(TradeErrorCode.E453.code(), "订单不存在");
        }

        OrderDetailVO detailVO = new OrderDetailVO();
        BeanUtils.copyProperties(orderDO, detailVO);
        //初始化sku信息
        List<OrderSkuVO> skuList = JsonUtil.jsonToList(orderDO.getItemsJson(), OrderSkuVO.class);

        //订单商品原价
        double goodsOriginalPrice = 0.00;

        for (OrderSkuVO skuVO : skuList) {
            //设置商品的可操作状态
            skuVO.setGoodsOperateAllowableVO(new GoodsOperateAllowable(PaymentTypeEnum.valueOf(orderDO.getPaymentType()), OrderStatusEnum.valueOf(orderDO.getOrderStatus()),
                    ShipStatusEnum.valueOf(orderDO.getShipStatus()), ServiceStatusEnum.valueOf(skuVO.getServiceStatus()),
                    PayStatusEnum.valueOf(orderDO.getPayStatus())));

            //计算订单商品原价总和
            goodsOriginalPrice = CurrencyUtil.add(goodsOriginalPrice, CurrencyUtil.mul(skuVO.getOriginalPrice(), skuVO.getNum()));
        }

        detailVO.setOrderSkuList(skuList);

        // 初始化订单允许状态
        OrderOperateAllowable operateAllowableVO = new OrderOperateAllowable(detailVO);

        detailVO.setOrderOperateAllowableVO(operateAllowableVO);


        List<OrderMetaDO> metalList = this.orderMetaManager.list(orderSn);


        for (OrderMetaDO metaDO : metalList) {

            //订单的赠品信息
            if (OrderMetaKeyEnum.GIFT.name().equals(metaDO.getMetaKey())) {
                String giftJson = metaDO.getMetaValue();
                if (!StringUtil.isEmpty(giftJson)) {
                    List<FullDiscountGiftDO> giftList = JsonUtil.jsonToList(giftJson, FullDiscountGiftDO.class);
                    detailVO.setGiftList(giftList);
                }
            }

            //使用的积分
            if (OrderMetaKeyEnum.POINT.name().equals(metaDO.getMetaKey())) {
                String pointStr = metaDO.getMetaValue();
                int point = 0;
                if (!StringUtil.isEmpty(pointStr)) {
                    point = Integer.valueOf(pointStr);
                }

                detailVO.setUsePoint(point);

            }


            //赠送的积分
            if (OrderMetaKeyEnum.GIFT_POINT.name().equals(metaDO.getMetaKey())) {
                String giftPointStr = metaDO.getMetaValue();
                int giftPoint = 0;
                if (!StringUtil.isEmpty(giftPointStr)) {
                    giftPoint = Integer.valueOf(giftPoint);
                }

                detailVO.setGiftPoint(giftPoint);

            }


            //满减金额
            if (OrderMetaKeyEnum.FULL_MINUS.name().equals(metaDO.getMetaKey())) {
                Double fullMinus = 0D;
                if (!StringUtil.isEmpty(metaDO.getMetaValue())) {
                    fullMinus = Double.valueOf(metaDO.getMetaValue());
                }
                detailVO.setFullMinus(fullMinus);
            }


            if (OrderMetaKeyEnum.COUPON.name().equals(metaDO.getMetaKey())) {

                String couponJson = metaDO.getMetaValue();
                if (!StringUtil.isEmpty(couponJson)) {
                    List<CouponVO> couponList = JsonUtil.jsonToList(couponJson, CouponVO.class);
                    if (couponList != null && !couponList.isEmpty()) {
                        CouponVO couponVO = couponList.get(0);
                        detailVO.setGiftCoupon(couponVO);
                    }

                }

            }

            //优惠券抵扣金额
            if (OrderMetaKeyEnum.COUPON_PRICE.name().equals(metaDO.getMetaKey())) {
                String couponPriceStr = metaDO.getMetaValue();
                Double couponPrice = 0D;
                if (!StringUtil.isEmpty(couponPriceStr)) {
                    couponPrice = Double.valueOf(couponPriceStr);
                }
                //设置优惠券抵扣金额
                detailVO.setCouponPrice(couponPrice);

            }


        }


        //查询订单优惠券抵扣金额
        String couponPriceStr = this.orderMetaManager.getMetaValue(orderSn, OrderMetaKeyEnum.COUPON_PRICE);
        Double couponPrice = 0D;

        if (!StringUtil.isEmpty(couponPriceStr)) {
            couponPrice = Double.valueOf(couponPriceStr);
        }

        //设置优惠券抵扣金额
        detailVO.setCouponPrice(couponPrice);

        //设置订单的返现金额
        Double cashBack = CurrencyUtil.sub(detailVO.getDiscountPrice(), couponPrice);
        detailVO.setCashBack(cashBack < 0 ? 0 : cashBack);

        //当商品总价(优惠后的商品单价*数量+总优惠金额)超过商品原价总价
        if (detailVO.getGoodsPrice().doubleValue() > goodsOriginalPrice) {
            detailVO.setGoodsPrice(CurrencyUtil.sub(goodsOriginalPrice, cashBack));
        }
        if (OrderTypeEnum.pintuan.name().equals(detailVO.getOrderType())) {

            //如果订单
            int waitNums = convertOwesNums(orderDO);
            if (waitNums == 0 && PayStatusEnum.PAY_YES.value().equals(detailVO.getPayStatus())) {
                detailVO.setPingTuanStatus("已成团");
            } else if (OrderStatusEnum.CANCELLED.value().equals(detailVO.getOrderStatus())) {
                detailVO.setPingTuanStatus("未成团");
            } else {
                detailVO.setPingTuanStatus("待成团");
            }
        }

        return detailVO;
    }

    /**
     * 转换拼团的 待成团人数
     *
     * @param orderDO 订单do
     * @return 待成团人数
     */
    private int convertOwesNums(OrderDO orderDO) {
        //取出个性化数据
        String orderData = orderDO.getOrderData();
        Gson gson = new GsonBuilder().create();
        if (!StringUtil.isEmpty(orderData)) {

            //将个性化数据转为map
            Map map = gson.fromJson(orderData, HashMap.class);

            //转换拼团个性化数据
            String json = (String) map.get(OrderDataKey.pintuan.name());
            if (!StringUtil.isEmpty(json)) {
                Map pintuanMap = gson.fromJson(json, HashMap.class);
                Double nums = (Double) pintuanMap.get("owesPersonNums");
                return nums.intValue();
            }
        }
        return 0;
    }

    @Override
    public OrderDetailDTO getModel(String orderSn) {
        OrderDetailVO orderDetailVO = this.getModel(orderSn, null);
        OrderDetailDTO detailDTO = new OrderDetailDTO();
        BeanUtils.copyProperties(orderDetailVO, detailDTO);
        detailDTO.setOrderSkuList(new ArrayList<>());

        for (OrderSkuVO skuVO : orderDetailVO.getOrderSkuList()) {
            OrderSkuDTO skuDTO = new OrderSkuDTO();
            BeanUtil.copyProperties(skuVO, skuDTO);
            detailDTO.getOrderSkuList().add(skuDTO);
        }

        String json = this.orderMetaManager.getMetaValue(detailDTO.getSn(), OrderMetaKeyEnum.GIFT);
        List<FullDiscountGiftDO> giftList = JsonUtil.jsonToList(json, FullDiscountGiftDO.class);
        detailDTO.setGiftList(giftList);

        return detailDTO;
    }

    @Override
    public List<OrderItemsDO> orderItems(String orderSn) {
        return daoSupport.queryForList("select * from es_order_items where order_sn = ?", OrderItemsDO.class, orderSn);
    }

    @Override
    public double getOrderRefundPrice(String orderSn) {
        double refundPrice = 0.00;
/*        List<OrderItemsDO> itemsDOList = this.orderItems(orderSn);
        if (itemsDOList != null && itemsDOList.size() != 0) {
            for (OrderItemsDO itemsDO : itemsDOList) {
                refundPrice = CurrencyUtil.add(refundPrice, itemsDO.getRefundPrice());
            }
        }*/
        OrderDetailDTO detailDTO = getModel(orderSn);
        refundPrice= CurrencyUtil.sub( detailDTO.getPayMoney(),detailDTO.getShippingPrice());
        return refundPrice;
    }

    @Override
    public Page list(OrderQueryParam paramDTO) {

        StringBuffer sql = new StringBuffer("select * from es_order o where disabled=0 ");
        List<Object> term = new ArrayList<>();

        if (paramDTO.getKeywords() != null) {
            sql.append(" and (sn like ? or items_json like ? )");
            term.add("%" + paramDTO.getKeywords() + "%");
            term.add("%" + paramDTO.getKeywords() + "%");
        }

        // 按买家查询
        Integer memberId = paramDTO.getMemberId();
        if (memberId != null) {
            sql.append(" and o.member_id=?");
            term.add(memberId);
        }

        // 按订单编号查询
        if (StringUtil.notEmpty(paramDTO.getOrderSn())) {
            sql.append(" and o.sn like ?");
            term.add("%" + paramDTO.getOrderSn() + "%");
        }

        // 按交易编号查询
        if (StringUtil.notEmpty(paramDTO.getTradeSn())) {
            sql.append(" and o.trade_sn like ?");
            term.add("%" + paramDTO.getTradeSn() + "%");
        }

        // 按时间查询
        Long startTime = paramDTO.getStartTime();
        Long endTime = paramDTO.getEndTime();
        if (startTime != null) {

            String startDay = DateUtil.toString(startTime, "yyyy-MM-dd");
            sql.append(" and o.create_time >= ?");
            term.add(DateUtil.getDateline(startDay + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        }

        if (endTime != null) {
            String endDay = DateUtil.toString(endTime, "yyyy-MM-dd");
            sql.append(" and o.create_time <= ?");
            term.add(DateUtil.getDateline(endDay + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }

        // 按购买人用户名
        String memberName = paramDTO.getBuyerName();
        if (StringUtil.notEmpty(memberName)) {
            sql.append(" and o.member_name like ?");
            term.add("%" + memberName + "%");
        }

        // 按标签查询
        String tag = paramDTO.getTag();
        if (!StringUtil.isEmpty(tag)) {
            OrderTagEnum tagEnum = OrderTagEnum.valueOf(tag);
            switch (tagEnum) {
                case ALL:
                    break;
                //待付款
                case WAIT_PAY:
                    // 非货到付款的，未付款状态的可以结算 OR 货到付款的要发货或收货后才能结算
                    sql.append(" and ( ( ( payment_type!='cod' and  order_status='" + OrderStatusEnum.CONFIRM + "') ");
                    sql.append(" or ( payment_type='cod' and   order_status='" + OrderStatusEnum.ROG + "'  ) ) ");
                    sql.append(" or order_status = '" + OrderStatusEnum.NEW + "' )");
                    break;

                //待发货
                case WAIT_SHIP:
                    // 普通订单：
                    //      非货到付款的，要已结算才能发货 OR 货到付款的，已确认就可以发货
                    // 拼团订单：
                    //      已经成团的
                    sql.append(" and (");
                    sql.append(" ( payment_type!='cod' and order_type='" + OrderTypeEnum.normal + "'  and  order_status='" + OrderStatusEnum.PAID_OFF + "')  ");
                    sql.append(" or ( payment_type='cod' and order_type='" + OrderTypeEnum.normal + "'  and  order_status='" + OrderStatusEnum.CONFIRM + "') ");
                    sql.append(" or ( order_type='" + OrderTypeEnum.pintuan + "'  and  order_status='" + OrderStatusEnum.FORMED + "') ");
                    sql.append(")");
                    break;

                //待收货
                case WAIT_ROG:
                    sql.append(" and o.order_status='" + OrderStatusEnum.SHIPPED + "'");
                    break;

                //待评论
                case WAIT_COMMENT:
                    sql.append(" and o.ship_status='" + ShipStatusEnum.SHIP_ROG + "' and o.comment_status='" + CommentStatusEnum.UNFINISHED + "' ");
                    break;

                //已取消
                case CANCELLED:
                    sql.append(" and o.order_status='" + OrderStatusEnum.CANCELLED + "'");
                    break;

                case COMPLETE:
                    sql.append(" and o.order_status='" + OrderStatusEnum.COMPLETE + "'");
                    break;
                default:
                    break;
            }
        }
        //订单状态
        if (!StringUtil.isEmpty(paramDTO.getOrderStatus())) {
            sql.append(" and o.order_status = ?");
            term.add(paramDTO.getOrderStatus());
        }

        if (StringUtil.notEmpty(paramDTO.getBuyerName())) {
            sql.append(" and o.items_json like ?");
            term.add("%" + paramDTO.getGoodsName() + "%");
        }
        if (StringUtil.notEmpty(paramDTO.getShipName())) {
            sql.append(" and o.ship_name like ?");
            term.add("%" + paramDTO.getShipName() + "%");
        }
        // 按商品名称查询
        if (StringUtil.notEmpty(paramDTO.getGoodsName())) {
            sql.append(" and o.items_json like ?");
            term.add("%" + paramDTO.getGoodsName() + "%");
        }

        sql.append(" order by o.order_id desc");

        // 先按PO进行查询
        Page<OrderDO> page = daoSupport.queryForPage(sql.toString(), paramDTO.getPageNo(), paramDTO.getPageSize(),
                OrderDO.class, term.toArray());


        //订单自动取消天数
        int cancelLeftDay = getCancelLeftDay();

        // 转为VO
        List<OrderDO> orderList = page.getData();
        List<OrderLineVO> lineList = new ArrayList();
        for (OrderDO orderDO : orderList) {
            OrderLineVO line = new OrderLineVO(orderDO);

            //如果未付款并且是在线支付则显示取消时间
            if (!PayStatusEnum.PAY_YES.value().equals(orderDO.getPayStatus())
                    && PaymentTypeEnum.ONLINE.value().equals(orderDO.getPaymentType())
                    && !OrderStatusEnum.CANCELLED.value().equals(orderDO.getOrderStatus())
                    && !OrderStatusEnum.INTODB_ERROR.value().equals(orderDO.getOrderStatus())) {
                //计算自动取消剩余时间
                Long leftTime = getCancelLeftTime(line.getCreateTime(), cancelLeftDay);
                line.setCancelLeftTime(leftTime);
            } else {
                line.setCancelLeftTime(0L);
            }
            //默认订单是不支持原路退款操作的
            line.setIsRetrace(false);
            //如果订单已付款并且是在线支付的，那么需要获取订单的支付方式判断是否支持原路退款操作
            if (PayStatusEnum.PAY_YES.value().equals(orderDO.getPayStatus())
                    && PaymentTypeEnum.ONLINE.value().equals(orderDO.getPaymentType())
                    && orderDO.getPaymentPluginId() != null) {
                //获取订单的支付方式
                PaymentMethodDO paymentMethodDO = this.paymentMethodManager.getByPluginId(orderDO.getPaymentPluginId());
                paymentMethodDO.setIsRetrace(paymentMethodDO.getIsRetrace() == null ? 0 : paymentMethodDO.getIsRetrace());

                if (paymentMethodDO != null && paymentMethodDO.getIsRetrace() == 1) {
                    line.setIsRetrace(true);
                }
            }
            if (OrderTypeEnum.pintuan.name().equals(line.getOrderType())) {

                //如果订单
                int waitNums = convertOwesNums(orderDO);
                line.setWaitingGroupNums(waitNums);
                if (waitNums == 0 && PayStatusEnum.PAY_YES.value().equals(line.getPayStatus())) {
                    line.setPingTuanStatus("已成团");
                } else if (OrderStatusEnum.CANCELLED.value().equals(line.getOrderStatus())) {
                    line.setPingTuanStatus("未成团");
                } else {
                    line.setPingTuanStatus("待成团");
                }
            }

            lineList.add(line);
        }


        // 生成新的Page
        long totalCount = page.getDataTotal();
        Page<OrderLineVO> linePage = new Page(paramDTO.getPageNo(), totalCount, paramDTO.getPageSize(), lineList);

        return linePage;
    }

    private Long getCancelLeftTime(Long createTime, int cancelLeftDay) {

        Long cancelTime = createTime + cancelLeftDay * 24 * 60 * 60;
        Long now = DateUtil.getDateline();
        Long leftTime = cancelTime - now;
        if (leftTime < 0) {
            leftTime = 0L;
        }
        return leftTime;

    }

    /**
     * 读取订单自动取消天数
     *
     * @return
     */
    private int getCancelLeftDay() {
        String settingVOJson = this.settingClient.get(SettingGroup.TRADE);
        OrderSettingVO settingVO = JsonUtil.jsonToObject(settingVOJson, OrderSettingVO.class);
        int day = settingVO.getCancelOrderDay();
        return day;
    }

    @Override
    public List<OrderDetailVO> getOrderByTradeSn(String tradeSn, Integer memberId) {
        String sql = "select * from es_order where trade_sn = ?";
        List<OrderDetailVO> orderDetailVOList = this.daoSupport.queryForList(sql, OrderDetailVO.class, tradeSn);

        if (orderDetailVOList == null) {
            return new ArrayList<>();
        }
        return orderDetailVOList;
    }

    @Override
    public List<OrderDetailDTO> getOrderByTradeSn(String tradeSn) {
        List<OrderDetailVO> orderDetailVOList = this.getOrderByTradeSn(tradeSn, null);

        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
        for (OrderDetailVO orderDetailVO : orderDetailVOList) {
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            BeanUtils.copyProperties(orderDetailVO, orderDetailDTO);
            orderDetailDTOList.add(orderDetailDTO);
        }
        return orderDetailDTOList;
    }

    @Override
    public List<OrderFlowNode> getOrderFlow(String orderSn) {

        OrderDetailVO orderDetailVO = this.getModel(orderSn, null);
        String orderStatus = orderDetailVO.getOrderStatus();
        String paymentType = orderDetailVO.getPaymentType();

        //取消流程
        if (orderStatus.equals(OrderStatusEnum.CANCELLED.name())) {
            return OrderFlow.getCancelFlow();
        }
        //出库失败流程
        if (orderStatus.equals(OrderStatusEnum.INTODB_ERROR.name())) {
            return OrderFlow.getIntodbErrorFlow();
        }

        List<OrderFlowNode> resultFlow = OrderFlow.getFlow(orderDetailVO.getOrderType(), paymentType);

        boolean isEnd = false;
        for (OrderFlowNode flow : resultFlow) {

            flow.setShowStatus(1);
            if (isEnd) {
                flow.setShowStatus(0);
            }

            if (flow.getOrderStatus().equals(orderStatus)) {
                isEnd = true;
            }

        }

        return resultFlow;
    }

    @Override
    public OrderDO getDoByOrderSn(String orderSn) {

        String sql = "select * from es_order where sn = ?";

        return this.daoSupport.queryForObject(sql, OrderDO.class, orderSn);
    }

}