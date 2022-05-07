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
package cloud.shopfly.b2c.api.seller.trade;

import cloud.shopfly.b2c.core.client.member.MemberHistoryReceiptClient;
import cloud.shopfly.b2c.core.trade.cart.model.dos.OrderPermission;
import cloud.shopfly.b2c.core.trade.order.model.dto.OrderQueryParam;
import cloud.shopfly.b2c.core.trade.order.model.vo.*;
import cloud.shopfly.b2c.core.trade.order.service.OrderOperateManager;
import cloud.shopfly.b2c.core.trade.order.service.OrderQueryManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Merchant order controller
 *
 * @author Snow create in 2018/6/13
 * @version v2.0
 * @since v7.0.0
 */

@Api(description = "Merchants ordersAPI")
@RestController
@RequestMapping("/seller/trade/orders")
@Validated
public class OrderSellerController {

    @Autowired
    private OrderQueryManager orderQueryManager;

    @Autowired
    private OrderOperateManager orderOperateManager;

    @Autowired
    private MemberHistoryReceiptClient memberHistoryReceiptClient;

    @ApiOperation(value = "Query membership order list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keywords", value = "keyword", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order_sn", value = "Order no.", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "buyer_name", value = "Buyers name", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "goods_name", value = "Name", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "start_time", value = "The start time", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "end_time", value = "The end of time", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "member_id", value = "membersID", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "order_status", value = "Status", dataType = "String", paramType = "query",
                    allowableValues = "ALL,WAIT_PAY,WAIT_SHIP,WAIT_ROG,CANCELLED,COMPLETE,WAIT_COMMENT,REFUND",
                    example = "ALL:All orders,WAIT_PAY:For the payment,WAIT_SHIP:To send the goods,WAIT_ROG:For the goods," +
                            "CANCELLED:Has been cancelled,COMPLETE:Has been completed,WAIT_COMMENT:To comment on,REFUND:In the after-sale"),
            @ApiImplicitParam(name = "page_no", value = "Number of pages", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "A number of", dataType = "int", paramType = "query"),
    })
    @GetMapping()
    public Page<OrderLineVO> list(@ApiIgnore String orderSn, @ApiIgnore String buyerName, @ApiIgnore String goodsName, @ApiIgnore Integer memberId,
                                  @ApiIgnore Long startTime, @ApiIgnore Long endTime, @ApiIgnore String orderStatus,
                                  @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @ApiIgnore String keywords) {

        OrderQueryParam param = new OrderQueryParam();
        param.setOrderSn(orderSn);
        param.setBuyerName(buyerName);
        param.setGoodsName(goodsName);
        param.setStartTime(startTime);
        param.setEndTime(endTime);
        param.setTag(orderStatus);
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);
        param.setKeywords(keywords);
        param.setMemberId(memberId);

        Page<OrderLineVO> page = this.orderQueryManager.list(param);
        return page;
    }


    @ApiOperation(value = "Example Query the details of a single order")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "Order no.", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = "/{order_sn}")
    public OrderDetailVO get(@ApiIgnore @PathVariable("order_sn") String orderSn) {
        OrderDetailVO detailVO = this.orderQueryManager.getModel(orderSn, null);

        if (detailVO.getNeedReceipt() == 1) {
            detailVO.setReceiptHistory(memberHistoryReceiptClient.getReceiptHistory(orderSn));
        }

        return detailVO;
    }


    @ApiOperation(value = "Query the quantity of order status")
    @GetMapping(value = "/status-num")
    public OrderStatusNumVO getStatusNum() {
        return this.orderQueryManager.getOrderStatusNum(null);
    }


    @ApiOperation(value = "Orders for shipment", notes = "A merchant delivers an order")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "The ordersn", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "ship_no", value = "Invoice no.", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "logi_id", value = "Logistics companyid", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "logi_name", value = "Name of logistics Company", required = true, dataType = "String", paramType = "query"),
    })
    @ResponseBody
    @PostMapping(value = "/{order_sn}/delivery")
    public String ship(@ApiIgnore @NotNull(message = "Order number must be specified") @PathVariable(name = "order_sn") String orderSn,
                       @ApiIgnore @NotNull(message = "The invoice number must be entered") @Length(max = 20, message = "The tracking number is incorrect") String shipNo,
                       @ApiIgnore @NotNull(message = "You must choose a logistics company") Integer logiId,
                       @ApiIgnore String logiName) {

        DeliveryVO delivery = new DeliveryVO();
        delivery.setDeliveryNo(shipNo);
        delivery.setOrderSn(orderSn);
        delivery.setLogiId(logiId);
        delivery.setLogiName(logiName);
        orderOperateManager.ship(delivery, OrderPermission.admin);

        return "";
    }


    @ApiOperation(value = "The merchant modifies the consignee address", notes = "Merchants can modify the consignee address information before delivering the goods")
    @PutMapping(value = "/{order_sn}/address")
    public OrderConsigneeVO updateOrderConsignee(@ApiIgnore @PathVariable(name = "order_sn") String orderSn, OrderConsigneeVO orderConsignee) {
        return this.orderOperateManager.updateOrderConsignee(orderConsignee);
    }


    @ApiOperation(value = "The merchant modifies the order price", notes = "Buyers can modify the order price before payment")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "The ordersn", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "order_price", value = "The order price", required = true, dataType = "Double", paramType = "query"),
    })
    @PutMapping(value = "/{order_sn}/price")
    public String updateOrderPrice(@ApiIgnore @PathVariable(name = "order_sn") String orderSn,
                                   @ApiIgnore @NotNull(message = "The price cannot be empty after modification") Double orderPrice) {
        this.orderOperateManager.updateOrderPrice(orderSn, orderPrice);
        return "";
    }


    @ApiOperation(value = "Confirm receipt")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "Order no.", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "pay_price", value = "The payment amount", dataType = "double", paramType = "query")
    })
    @PostMapping(value = "/{order_sn}/pay")
    public String payOrder(@ApiIgnore @PathVariable("order_sn") String orderSn, @ApiIgnore Double payPrice) {
        this.orderOperateManager.payOrder(orderSn, payPrice, "", OrderPermission.admin);
        return "";
    }


    @ApiOperation(value = "Order flow chart data", notes = "Order flow chart data")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "The ordersn", required = true, dataType = "String", paramType = "path"),
    })
    @GetMapping(value = "/{order_sn}/flow")
    public List<OrderFlowNode> getOrderStatusFlow(@ApiIgnore @PathVariable(name = "order_sn") String orderSn) {
        List<OrderFlowNode> orderFlowList = this.orderQueryManager.getOrderFlow(orderSn);
        return orderFlowList;
    }

    @ApiOperation(value = "Exporting order List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "Order no.", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ship_name", value = "The consignee", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "goods_name", value = "Name", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "buyer_name", value = "Buyer name", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "start_time", value = "The start time", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "end_time", value = "The end of time", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "order_status", value = "Status", dataType = "String", paramType = "query",
                    allowableValues = "ALL,WAIT_PAY,WAIT_SHIP,WAIT_ROG,CANCELLED,COMPLETE,WAIT_COMMENT,REFUND",
                    example = "ALL:All orders,WAIT_PAY:For the payment,WAIT_SHIP:To send the goods,WAIT_ROG:For the goods," +
                            "CANCELLED:Has been cancelled,COMPLETE:Has been completed,WAIT_COMMENT:To comment on,REFUND:In the after-sale"),
    })
    @GetMapping("/export")
    public List export(@ApiIgnore String orderSn, @ApiIgnore String shipName, @ApiIgnore String goodsName, @ApiIgnore String buyerName,
                       @ApiIgnore Long startTime, @ApiIgnore Long endTime, @ApiIgnore String orderStatus) {

        OrderQueryParam param = new OrderQueryParam();
        param.setOrderSn(orderSn);
        param.setShipName(shipName);
        param.setGoodsName(goodsName);
        param.setBuyerName(buyerName);
        param.setTag(orderStatus);
        param.setStartTime(startTime);
        param.setEndTime(endTime);
        param.setPageNo(1);
        param.setPageSize(10000);

        Page page = this.orderQueryManager.list(param);
        return page.getData();
    }

    @ApiOperation(value = "Cancel the order")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "Order no.", required = true, dataType = "String", paramType = "path"),
    })
    @PostMapping(value = "/{order_sn}/canceled")
    public String cancelledOrder(@ApiIgnore @PathVariable("order_sn") String orderSn) {

        CancelVO cancelVO = new CancelVO();
        cancelVO.setReason("Administrator Cancel");
        cancelVO.setOrderSn(orderSn);
        cancelVO.setOperator("Platform administrator");

        this.orderOperateManager.cancel(cancelVO, OrderPermission.admin);
        return "";
    }

}
