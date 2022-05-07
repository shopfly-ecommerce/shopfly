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
package cloud.shopfly.b2c.api.buyer.trade;

import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.client.member.MemberHistoryReceiptClient;
import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.trade.TradeErrorCode;
import cloud.shopfly.b2c.core.trade.cart.model.dos.OrderPermission;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderLogDO;
import cloud.shopfly.b2c.core.trade.order.model.dos.TradeDO;
import cloud.shopfly.b2c.core.trade.order.model.dto.OrderQueryParam;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderTagEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PaymentTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.*;
import cloud.shopfly.b2c.core.trade.order.service.OrderLogManager;
import cloud.shopfly.b2c.core.trade.order.service.OrderOperateManager;
import cloud.shopfly.b2c.core.trade.order.service.OrderQueryManager;
import cloud.shopfly.b2c.core.trade.order.service.TradeQueryManager;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Member order related controller
 *
 * @author Snow create in 2018/5/14
 * @version v2.0
 * @since v7.0.0
 */

@Api(description = "Member of the orderAPI")
@RestController
@RequestMapping("/trade/orders")
@Validated
public class OrderBuyerController {

    @Autowired
    private OrderQueryManager orderQueryManager;

    @Autowired
    private OrderOperateManager orderOperateManager;

    @Autowired
    private TradeQueryManager tradeQueryManager;

    @Autowired
    private MemberHistoryReceiptClient memberHistoryReceiptClient;

    @Autowired
    private OrderLogManager orderLogManager;

    @Autowired
    private SettingClient settingClient;


    @ApiOperation(value = "Query membership order list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_name", value = "Product name keyword", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "key_words", value = "keyword", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order_status", value = "Status", dataType = "String", paramType = "query",
                    allowableValues = "ALL,WAIT_PAY,WAIT_SHIP,WAIT_ROG,CANCELLED,COMPLETE,WAIT_COMMENT,REFUND",
                    example = "ALL:All orders,WAIT_PAY:For the payment,WAIT_SHIP:To send the goods,WAIT_ROG:For the goods," +
                            "CANCELLED:Has been cancelled,COMPLETE:Has been completed,WAIT_COMMENT:To comment on,REFUND:In the after-sale"),
            @ApiImplicitParam(name = "page_no", value = "Number of pages", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "A number of", dataType = "int", paramType = "query"),
    })
    @GetMapping()
    public Page<OrderLineVO> list(@ApiIgnore String keyWords, @ApiIgnore String goodsName, @ApiIgnore String orderStatus,
                                  @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {

        try {
            if (StringUtil.isEmpty(orderStatus)) {
                orderStatus = "ALL";
            }
            OrderTagEnum.valueOf(orderStatus);
        } catch (Exception e) {
            throw new ServiceException(TradeErrorCode.E455.code(), "The order status parameter is incorrect");
        }

        Buyer buyer = UserContext.getBuyer();
        OrderQueryParam param = new OrderQueryParam();
        param.setGoodsName(goodsName);
        param.setTag(orderStatus);
        param.setMemberId(buyer.getUid());
        param.setKeywords(keyWords);
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);

        Page page = this.orderQueryManager.list(param);

        // Online payment is not allowed for cash on delivery orders
        List<OrderLineVO> list = page.getData();
        for (OrderLineVO order : list) {
            if (PaymentTypeEnum.COD.value().equals(order.getPaymentType())) {
                order.getOrderOperateAllowableVO().setAllowPay(false);
            }
        }
        page.setData(list);

        return page;
    }


    @ApiOperation(value = "Example Query the details of a single order")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "Order no.", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = "/{order_sn}")
    public OrderDetailVO get(@ApiIgnore @PathVariable("order_sn") String orderSn) {
        Buyer buyer = UserContext.getBuyer();
        OrderDetailVO detailVO = this.orderQueryManager.getModel(orderSn, buyer.getUid());

        if (detailVO.getNeedReceipt() == 1) {
            detailVO.setReceiptHistory(memberHistoryReceiptClient.getReceiptHistory(orderSn));
        }

        // Online payment is not allowed for cash on delivery orders
        if (PaymentTypeEnum.COD.value().equals(detailVO.getPaymentType())) {
            detailVO.getOrderOperateAllowableVO().setAllowPay(false);
        }

        return detailVO;
    }


    @ApiOperation(value = "Confirm the goods")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "Order no.", required = true, dataType = "String", paramType = "path")
    })
    @PostMapping(value = "/{order_sn}/rog")
    public String rog(@ApiIgnore @PathVariable("order_sn") String orderSn) {

        Buyer buyer = UserContext.getBuyer();
        RogVO rogVO = new RogVO();
        rogVO.setOrderSn(orderSn);
        rogVO.setOperator(buyer.getUsername());

        orderOperateManager.rog(rogVO, OrderPermission.buyer);
        return "";
    }


    @ApiOperation(value = "Cancel the order")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "Order no.", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "reason", value = "Cancel the reason", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping(value = "/{order_sn}/cancel")
    public String cancel(@ApiIgnore @PathVariable("order_sn") String orderSn, String reason) {

        Buyer buyer = UserContext.getBuyer();
        CancelVO cancelVO = new CancelVO();
        cancelVO.setOperator(buyer.getUsername());
        cancelVO.setOrderSn(orderSn);
        cancelVO.setReason(reason);
        orderOperateManager.cancel(cancelVO, OrderPermission.buyer);


        return "";
    }


    @ApiOperation(value = "Query the quantity of order status")
    @GetMapping(value = "/status-num")
    public OrderStatusNumVO getStatusNum() {
        Buyer buyer = UserContext.getBuyer();
        return this.orderQueryManager.getOrderStatusNum(buyer.getUid());
    }


    @ApiOperation(value = "Query the order list by transaction number")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "trade_sn", value = "Transaction number", required = true, dataType = "String", paramType = "path"),
    })
    @GetMapping(value = "/{trade_sn}/list")
    public List<OrderDetailVO> getOrderList(@ApiIgnore @PathVariable("trade_sn") String tradeSn) {
        Buyer buyer = UserContext.getBuyer();
        List<OrderDetailVO> orderDetailVOList = this.orderQueryManager.getOrderByTradeSn(tradeSn, buyer.getUid());
        return orderDetailVOList;
    }


    @ApiOperation(value = "Query cash register data according to transaction number or order number")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "trade_sn", value = "Transaction number", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order_sn", value = "Order no.", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "/cashier")
    public CashierVO getCashier(@ApiIgnore String tradeSn, @ApiIgnore String orderSn) {

        String shipName, shipAddr, shipMobile, shipCountry, shipState, shipCity, payTypeText;
        Double needPayPrice;
        Long createTime;

        if (tradeSn != null) {

            TradeDO tradeDO = this.tradeQueryManager.getModel(tradeSn);
            shipName = tradeDO.getConsigneeName();
            shipAddr = tradeDO.getConsigneeAddress();
            shipMobile = tradeDO.getConsigneeMobile();
            shipCountry = tradeDO.getConsigneeCountry();
            shipState = tradeDO.getConsigneeState();
            shipCity = tradeDO.getConsigneeCity();
            needPayPrice = tradeDO.getTotalPrice();
            payTypeText = tradeDO.getPaymentType();
            createTime = tradeDO.getCreateTime();

        } else if (orderSn != null) {

            OrderDetailVO detailVO = this.orderQueryManager.getModel(orderSn, UserContext.getBuyer().getUid());
            shipName = detailVO.getShipName();
            shipAddr = detailVO.getShipAddr();
            shipMobile = detailVO.getShipMobile();
            shipCountry = detailVO.getShipCountry();
            shipState = detailVO.getShipState();
            shipCity = detailVO.getShipCity();
            needPayPrice = detailVO.getNeedPayMoney();
            payTypeText = detailVO.getPaymentType();
            createTime = detailVO.getCreateTime();

        } else {
            throw new ServiceException(TradeErrorCode.E455.code(), "Parameter error");
        }

        CashierVO cashierVO = new CashierVO();
        cashierVO.setShipCountry(shipCountry);
        cashierVO.setShipState(shipState);
        cashierVO.setShipCity(shipCity);
        cashierVO.setShipAddr(shipAddr);
        cashierVO.setShipMobile(shipMobile);
        cashierVO.setShipName(shipName);
        cashierVO.setNeedPayPrice(needPayPrice);
        cashierVO.setPayTypeText(payTypeText);
        cashierVO.setCountDown(handlecountDown(createTime));
        return cashierVO;
    }


    @ApiOperation(value = "Order flow chart data", notes = "Order flow chart data")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "The ordersn", required = true, dataType = "String", paramType = "path"),
    })
    @GetMapping(value = "/{order_sn}/flow")
    public List<OrderFlowNode> getOrderStatusFlow(@ApiIgnore @PathVariable(name = "order_sn") String orderSn) {
        return this.orderQueryManager.getOrderFlow(orderSn);
    }

    @ApiOperation(value = "Querying order Logs")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "Order no.", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = "/{order_sn}/log")
    public List<OrderLogDO> getList(@ApiIgnore @PathVariable("order_sn") String orderSn) {
        List<OrderLogDO> logDOList = this.orderLogManager.listAll(orderSn);
        return logDOList;
    }

    /**
     * Calculate the automatic invalidation time from the order
     * @param createTime  Order Creation time
     * @return   Countdown unit:seconds
     */
    private Long handlecountDown(Long createTime){

        String json = this.settingClient.get(SettingGroup.TRADE);
        OrderSettingVO orderSettingVO = JsonUtil.jsonToObject(json,OrderSettingVO.class);
        Integer cancelOrderDay = orderSettingVO.getCancelOrderDay();

        Long cancelTime = DateUtil.startOfTodDay() + (cancelOrderDay+1L) * 24 * 60 * 60;
        Long now = DateUtil.getDateline();
        Long leftTime = cancelTime - now;
        if (leftTime < 0) {
            leftTime = 0L;
        }
        return leftTime;
    }


}
