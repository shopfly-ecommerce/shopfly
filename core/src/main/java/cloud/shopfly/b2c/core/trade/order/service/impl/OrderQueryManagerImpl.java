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

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderItemsDO;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderMetaDO;
import cloud.shopfly.b2c.core.trade.order.model.dto.OrderQueryParam;
import cloud.shopfly.b2c.core.trade.order.model.enums.*;
import cloud.shopfly.b2c.core.trade.order.model.vo.*;
import cloud.shopfly.b2c.core.trade.order.service.OrderMetaManager;
import cloud.shopfly.b2c.core.trade.order.service.OrderQueryManager;
import cloud.shopfly.b2c.core.aftersale.service.AfterSaleManager;
import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.payment.model.dos.PaymentMethodDO;
import cloud.shopfly.b2c.core.payment.service.PaymentMethodManager;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import cloud.shopfly.b2c.core.trade.TradeErrorCode;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CouponVO;
import cloud.shopfly.b2c.framework.util.*;
import cloud.shopfly.b2c.core.trade.order.model.enums.*;
import cloud.shopfly.b2c.core.trade.order.model.vo.*;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderSkuDTO;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Order query business implementation class
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
        // Enquiry by buyer
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

        // All orders
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

        // The payment status is not paid, the order status has been confirmed, it is the order to be paid
        for (Map map : list) {
            boolean flag = (OrderStatusEnum.CONFIRM.value().equals(map.get("order_status").toString()) && !"COD".equals(map.get("payment_type").toString()))
                    || (OrderStatusEnum.ROG.value().equals(map.get("order_status").toString()) && "COD".equals(map.get("payment_type").toString()));
            if (flag) {
                numVO.setWaitPayNum(numVO.getWaitPayNum() + (null == map.get("count") ? 0 : Integer.parseInt(map.get("count").toString())));
            }

            // The logistics status is unshipped, and the order status is received, which is the order to be shipped
            flag = (OrderStatusEnum.CONFIRM.value().equals(map.get("order_status").toString()) && "COD".equals(map.get("payment_type").toString()) && OrderTypeEnum.normal.name().equals(map.get("order_type").toString()))
                    || (OrderStatusEnum.PAID_OFF.value().equals(map.get("order_status").toString()) && !"COD".equals(map.get("payment_type").toString()) && OrderTypeEnum.normal.name().equals(map.get("order_type").toString()))
                    || (OrderTypeEnum.pintuan.name().equals(map.get("order_type").toString()) && OrderStatusEnum.FORMED.value().equals(map.get("order_status").toString()));
            if (flag) {
                numVO.setWaitShipNum(numVO.getWaitShipNum() + (null == map.get("count") ? 0 : Integer.parseInt(map.get("count").toString())));
            }

            // The order status is shipped and it is an order to be received
            if (OrderStatusEnum.SHIPPED.value().equals(map.get("order_status").toString())) {
                numVO.setWaitRogNum(numVO.getWaitRogNum() + (null == map.get("count") ? 0 : Integer.parseInt(map.get("count").toString())));
            }

            // The order status is cancelled, the order is cancelled
            if (OrderStatusEnum.CANCELLED.value().equals(map.get("order_status").toString())) {
                numVO.setCancelNum(numVO.getCancelNum() + (null == map.get("count") ? 0 : Integer.parseInt(map.get("count").toString())));
            }

            // Order status is completed, is completed order
            if (OrderStatusEnum.COMPLETE.value().equals(map.get("order_status").toString())) {
                numVO.setCompleteNum(numVO.getCompleteNum() + (null == map.get("count") ? 0 : Integer.parseInt(map.get("count").toString())));
            }

            // The comment status is uncommented, the order status is received, and the order is to be commented
            if (CommentStatusEnum.UNFINISHED.value().equals(map.get("comment_status").toString()) && OrderStatusEnum.ROG.value().equals(map.get("order_status").toString())) {
                numVO.setWaitCommentNum(numVO.getWaitCommentNum() + (null == map.get("count") ? 0 : Integer.parseInt(map.get("count").toString())));
            }
        }

        // Application for after-sales service, but not completed the after-sales order
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
            throw new ServiceException(TradeErrorCode.E453.code(), "Order does not exist");
        }

        OrderDetailVO detailVO = new OrderDetailVO();
        BeanUtils.copyProperties(orderDO, detailVO);
        // Example Initialize skU information
        List<OrderSkuVO> skuList = JsonUtil.jsonToList(orderDO.getItemsJson(), OrderSkuVO.class);

        // Original price of order goods
        double goodsOriginalPrice = 0.00;

        for (OrderSkuVO skuVO : skuList) {
            // Sets the operational state of the item
            skuVO.setGoodsOperateAllowableVO(new GoodsOperateAllowable(PaymentTypeEnum.valueOf(orderDO.getPaymentType()), OrderStatusEnum.valueOf(orderDO.getOrderStatus()),
                    ShipStatusEnum.valueOf(orderDO.getShipStatus()), ServiceStatusEnum.valueOf(skuVO.getServiceStatus()),
                    PayStatusEnum.valueOf(orderDO.getPayStatus())));

            // Calculate the sum of the original price of the goods ordered
            goodsOriginalPrice = CurrencyUtil.add(goodsOriginalPrice, CurrencyUtil.mul(skuVO.getOriginalPrice(), skuVO.getNum()));
        }

        detailVO.setOrderSkuList(skuList);

        // Initialize the order permit state
        OrderOperateAllowable operateAllowableVO = new OrderOperateAllowable(detailVO);

        detailVO.setOrderOperateAllowableVO(operateAllowableVO);


        List<OrderMetaDO> metalList = this.orderMetaManager.list(orderSn);


        for (OrderMetaDO metaDO : metalList) {

            // Gift information for the order
            if (OrderMetaKeyEnum.GIFT.name().equals(metaDO.getMetaKey())) {
                String giftJson = metaDO.getMetaValue();
                if (!StringUtil.isEmpty(giftJson)) {
                    List<FullDiscountGiftDO> giftList = JsonUtil.jsonToList(giftJson, FullDiscountGiftDO.class);
                    detailVO.setGiftList(giftList);
                }
            }

            // Integral used
            if (OrderMetaKeyEnum.POINT.name().equals(metaDO.getMetaKey())) {
                String pointStr = metaDO.getMetaValue();
                int point = 0;
                if (!StringUtil.isEmpty(pointStr)) {
                    point = Integer.valueOf(pointStr);
                }

                detailVO.setUsePoint(point);

            }


            // Bonus points
            if (OrderMetaKeyEnum.GIFT_POINT.name().equals(metaDO.getMetaKey())) {
                String giftPointStr = metaDO.getMetaValue();
                int giftPoint = 0;
                if (!StringUtil.isEmpty(giftPointStr)) {
                    giftPoint = Integer.valueOf(giftPoint);
                }

                detailVO.setGiftPoint(giftPoint);

            }


            // Full amount reduction
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

            // Coupon deduction amount
            if (OrderMetaKeyEnum.COUPON_PRICE.name().equals(metaDO.getMetaKey())) {
                String couponPriceStr = metaDO.getMetaValue();
                Double couponPrice = 0D;
                if (!StringUtil.isEmpty(couponPriceStr)) {
                    couponPrice = Double.valueOf(couponPriceStr);
                }
                // Set the amount of coupon deduction
                detailVO.setCouponPrice(couponPrice);

            }


        }


        // Query the discount amount of order coupons
        String couponPriceStr = this.orderMetaManager.getMetaValue(orderSn, OrderMetaKeyEnum.COUPON_PRICE);
        Double couponPrice = 0D;

        if (!StringUtil.isEmpty(couponPriceStr)) {
            couponPrice = Double.valueOf(couponPriceStr);
        }

        // Set the amount of coupon deduction
        detailVO.setCouponPrice(couponPrice);

        // Set the amount of cash back for the order
        Double cashBack = CurrencyUtil.sub(detailVO.getDiscountPrice(), couponPrice);
        detailVO.setCashBack(cashBack < 0 ? 0 : cashBack);

        // When the total price of the product (the unit price of the product after the discount * quantity + the total discount amount) exceeds the total price of the original price of the product
        if (detailVO.getGoodsPrice().doubleValue() > goodsOriginalPrice) {
            detailVO.setGoodsPrice(CurrencyUtil.sub(goodsOriginalPrice, cashBack));
        }
        if (OrderTypeEnum.pintuan.name().equals(detailVO.getOrderType())) {

            // If the order
            int waitNums = convertOwesNums(orderDO);
            if (waitNums == 0 && PayStatusEnum.PAY_YES.value().equals(detailVO.getPayStatus())) {
                detailVO.setPingTuanStatus("Have to make");
            } else if (OrderStatusEnum.CANCELLED.value().equals(detailVO.getOrderStatus())) {
                detailVO.setPingTuanStatus("No clouds");
            } else {
                detailVO.setPingTuanStatus("To stay together");
            }
        }

        return detailVO;
    }

    /**
     * Convert the number of players waiting to join a group
     *
     * @param orderDO The orderdo
     * @return Number of people waiting to form a group
     */
    private int convertOwesNums(OrderDO orderDO) {
        // Fetching personalized data
        String orderData = orderDO.getOrderData();
        Gson gson = new GsonBuilder().create();
        if (!StringUtil.isEmpty(orderData)) {

            // Convert personalized data to a map
            Map map = gson.fromJson(orderData, HashMap.class);

            // Convert group personalization data
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

        // Enquiry by buyer
        Integer memberId = paramDTO.getMemberId();
        if (memberId != null) {
            sql.append(" and o.member_id=?");
            term.add(memberId);
        }

        // Query by order number
        if (StringUtil.notEmpty(paramDTO.getOrderSn())) {
            sql.append(" and o.sn like ?");
            term.add("%" + paramDTO.getOrderSn() + "%");
        }

        // Search by transaction number
        if (StringUtil.notEmpty(paramDTO.getTradeSn())) {
            sql.append(" and o.trade_sn like ?");
            term.add("%" + paramDTO.getTradeSn() + "%");
        }

        // Query by time
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

        // By buyer username
        String memberName = paramDTO.getBuyerName();
        if (StringUtil.notEmpty(memberName)) {
            sql.append(" and o.member_name like ?");
            term.add("%" + memberName + "%");
        }

        // Query by label
        String tag = paramDTO.getTag();
        if (!StringUtil.isEmpty(tag)) {
            OrderTagEnum tagEnum = OrderTagEnum.valueOf(tag);
            switch (tagEnum) {
                case ALL:
                    break;
                // For the payment
                case WAIT_PAY:
                    // Blame of goods arrive pay, what did not pay state can settle accounts OR goods arrive pay should deliver goods OR ability after receiving goods settle accounts
                    sql.append(" and ( ( ( payment_type!='cod' and  order_status='" + OrderStatusEnum.CONFIRM + "') ");
                    sql.append(" or ( payment_type='cod' and   order_status='" + OrderStatusEnum.ROG + "'  ) ) ");
                    sql.append(" or order_status = '" + OrderStatusEnum.NEW + "' )");
                    break;

                // To send the goods
                case WAIT_SHIP:
                    // General order:
                    // Non cash on delivery, to have settled before delivery OR cash on delivery, confirmed can be shipped
                    // Group order:
                    // Its already in a group
                    sql.append(" and (");
                    sql.append(" ( payment_type!='cod' and order_type='" + OrderTypeEnum.normal + "'  and  order_status='" + OrderStatusEnum.PAID_OFF + "')  ");
                    sql.append(" or ( payment_type='cod' and order_type='" + OrderTypeEnum.normal + "'  and  order_status='" + OrderStatusEnum.CONFIRM + "') ");
                    sql.append(" or ( order_type='" + OrderTypeEnum.pintuan + "'  and  order_status='" + OrderStatusEnum.FORMED + "') ");
                    sql.append(")");
                    break;

                // For the goods
                case WAIT_ROG:
                    sql.append(" and o.order_status='" + OrderStatusEnum.SHIPPED + "'");
                    break;

                // To comment on
                case WAIT_COMMENT:
                    sql.append(" and o.ship_status='" + ShipStatusEnum.SHIP_ROG + "' and o.comment_status='" + CommentStatusEnum.UNFINISHED + "' ");
                    break;

                // Has been cancelled
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
        // Status
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
        // Search by commodity name
        if (StringUtil.notEmpty(paramDTO.getGoodsName())) {
            sql.append(" and o.items_json like ?");
            term.add("%" + paramDTO.getGoodsName() + "%");
        }

        sql.append(" order by o.order_id desc");

        // Query by PO first
        Page<OrderDO> page = daoSupport.queryForPage(sql.toString(), paramDTO.getPageNo(), paramDTO.getPageSize(),
                OrderDO.class, term.toArray());


        // Number of days for automatic order cancellation
        int cancelLeftDay = getCancelLeftDay();

        // To VO
        List<OrderDO> orderList = page.getData();
        List<OrderLineVO> lineList = new ArrayList();
        for (OrderDO orderDO : orderList) {
            OrderLineVO line = new OrderLineVO(orderDO);

            // Cancellation time is displayed if payment is not made and online
            if (!PayStatusEnum.PAY_YES.value().equals(orderDO.getPayStatus())
                    && PaymentTypeEnum.ONLINE.value().equals(orderDO.getPaymentType())
                    && !OrderStatusEnum.CANCELLED.value().equals(orderDO.getOrderStatus())
                    && !OrderStatusEnum.INTODB_ERROR.value().equals(orderDO.getOrderStatus())) {
                // Calculate the remaining time of automatic cancellation
                Long leftTime = getCancelLeftTime(line.getCreateTime(), cancelLeftDay);
                line.setCancelLeftTime(leftTime);
            } else {
                line.setCancelLeftTime(0L);
            }
            // The default order does not support the original route refund operation
            line.setIsRetrace(false);
            // If the order has been paid and paid online, you need to obtain the payment method of the order to determine whether the original refund operation is supported
            if (PayStatusEnum.PAY_YES.value().equals(orderDO.getPayStatus())
                    && PaymentTypeEnum.ONLINE.value().equals(orderDO.getPaymentType())
                    && orderDO.getPaymentPluginId() != null) {
                // Get the payment method for the order
                PaymentMethodDO paymentMethodDO = this.paymentMethodManager.getByPluginId(orderDO.getPaymentPluginId());
                paymentMethodDO.setIsRetrace(paymentMethodDO.getIsRetrace() == null ? 0 : paymentMethodDO.getIsRetrace());

                if (paymentMethodDO != null && paymentMethodDO.getIsRetrace() == 1) {
                    line.setIsRetrace(true);
                }
            }
            if (OrderTypeEnum.pintuan.name().equals(line.getOrderType())) {

                // If the order
                int waitNums = convertOwesNums(orderDO);
                line.setWaitingGroupNums(waitNums);
                if (waitNums == 0 && PayStatusEnum.PAY_YES.value().equals(line.getPayStatus())) {
                    line.setPingTuanStatus("Have to make");
                } else if (OrderStatusEnum.CANCELLED.value().equals(line.getOrderStatus())) {
                    line.setPingTuanStatus("No clouds");
                } else {
                    line.setPingTuanStatus("To stay together");
                }
            }

            lineList.add(line);
        }


        // Generate a new Page
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
     * Number of days to automatically cancel reading orders
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

        // Cancel the process
        if (orderStatus.equals(OrderStatusEnum.CANCELLED.name())) {
            return OrderFlow.getCancelFlow();
        }
        // Outbound failure process
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
