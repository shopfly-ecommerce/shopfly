/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 商家订单控制器
 *
 * @author Snow create in 2018/6/13
 * @version v2.0
 * @since v7.0.0
 */

@Api(description = "商家订单API")
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

    @ApiOperation(value = "查询会员订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keywords", value = "关键字", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order_sn", value = "订单编号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "buyer_name", value = "买家姓名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "goods_name", value = "商品名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "start_time", value = "开始时间", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "end_time", value = "结束时间", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "member_id", value = "会员ID", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "order_status", value = "订单状态", dataType = "String", paramType = "query",
                    allowableValues = "ALL,WAIT_PAY,WAIT_SHIP,WAIT_ROG,CANCELLED,COMPLETE,WAIT_COMMENT,REFUND",
                    example = "ALL:所有订单,WAIT_PAY:待付款,WAIT_SHIP:待发货,WAIT_ROG:待收货," +
                            "CANCELLED:已取消,COMPLETE:已完成,WAIT_COMMENT:待评论,REFUND:售后中"),
            @ApiImplicitParam(name = "page_no", value = "页数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "条数", dataType = "int", paramType = "query"),
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


    @ApiOperation(value = "查询单个订单明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "订单编号", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = "/{order_sn}")
    public OrderDetailVO get(@ApiIgnore @PathVariable("order_sn") String orderSn) {
        OrderDetailVO detailVO = this.orderQueryManager.getModel(orderSn, null);

        if (detailVO.getNeedReceipt() == 1) {
            detailVO.setReceiptHistory(memberHistoryReceiptClient.getReceiptHistory(orderSn));
        }

        return detailVO;
    }


    @ApiOperation(value = "查询订单状态的数量")
    @GetMapping(value = "/status-num")
    public OrderStatusNumVO getStatusNum() {
        return this.orderQueryManager.getOrderStatusNum(null);
    }


    @ApiOperation(value = "订单发货", notes = "商家对某订单执行发货操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "订单sn", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "ship_no", value = "发货单号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "logi_id", value = "物流公司id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "logi_name", value = "物流公司名称", required = true, dataType = "String", paramType = "query"),
    })
    @ResponseBody
    @PostMapping(value = "/{order_sn}/delivery")
    public String ship(@ApiIgnore @NotNull(message = "必须指定订单编号") @PathVariable(name = "order_sn") String orderSn,
                       @ApiIgnore @NotNull(message = "必须输入发货单号") @Length(max = 20, message = "物流单号不正确") String shipNo,
                       @ApiIgnore @NotNull(message = "必须选择物流公司") Integer logiId,
                       @ApiIgnore String logiName) {

        DeliveryVO delivery = new DeliveryVO();
        delivery.setDeliveryNo(shipNo);
        delivery.setOrderSn(orderSn);
        delivery.setLogiId(logiId);
        delivery.setLogiName(logiName);
        orderOperateManager.ship(delivery, OrderPermission.admin);

        return "";
    }


    @ApiOperation(value = "商家修改收货人地址", notes = "商家发货前，可以修改收货人地址信息")
    @PutMapping(value = "/{order_sn}/address")
    public OrderConsigneeVO updateOrderConsignee(@ApiIgnore @PathVariable(name = "order_sn") String orderSn, OrderConsigneeVO orderConsignee) {
        return this.orderOperateManager.updateOrderConsignee(orderConsignee);
    }


    @ApiOperation(value = "商家修改订单价格", notes = "买家付款前可以修改订单价格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "订单sn", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "order_price", value = "订单价格", required = true, dataType = "Double", paramType = "query"),
    })
    @PutMapping(value = "/{order_sn}/price")
    public String updateOrderPrice(@ApiIgnore @PathVariable(name = "order_sn") String orderSn,
                                   @ApiIgnore @NotNull(message = "修改后价格不能为空") Double orderPrice) {
        this.orderOperateManager.updateOrderPrice(orderSn, orderPrice);
        return "";
    }


    @ApiOperation(value = "确认收款")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "订单编号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "pay_price", value = "付款金额", dataType = "double", paramType = "query")
    })
    @PostMapping(value = "/{order_sn}/pay")
    public String payOrder(@ApiIgnore @PathVariable("order_sn") String orderSn, @ApiIgnore Double payPrice) {
        this.orderOperateManager.payOrder(orderSn, payPrice, "", OrderPermission.admin);
        return "";
    }


    @ApiOperation(value = "订单流程图数据", notes = "订单流程图数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "订单sn", required = true, dataType = "String", paramType = "path"),
    })
    @GetMapping(value = "/{order_sn}/flow")
    public List<OrderFlowNode> getOrderStatusFlow(@ApiIgnore @PathVariable(name = "order_sn") String orderSn) {
        List<OrderFlowNode> orderFlowList = this.orderQueryManager.getOrderFlow(orderSn);
        return orderFlowList;
    }

    @ApiOperation(value = "导出订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "订单编号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ship_name", value = "收货人", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "goods_name", value = "商品名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "buyer_name", value = "买家名字", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "start_time", value = "开始时间", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "end_time", value = "结束时间", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "order_status", value = "订单状态", dataType = "String", paramType = "query",
                    allowableValues = "ALL,WAIT_PAY,WAIT_SHIP,WAIT_ROG,CANCELLED,COMPLETE,WAIT_COMMENT,REFUND",
                    example = "ALL:所有订单,WAIT_PAY:待付款,WAIT_SHIP:待发货,WAIT_ROG:待收货," +
                            "CANCELLED:已取消,COMPLETE:已完成,WAIT_COMMENT:待评论,REFUND:售后中"),
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

    @ApiOperation(value = "取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "订单编号", required = true, dataType = "String", paramType = "path"),
    })
    @PostMapping(value = "/{order_sn}/canceled")
    public String cancelledOrder(@ApiIgnore @PathVariable("order_sn") String orderSn) {

        CancelVO cancelVO = new CancelVO();
        cancelVO.setReason("管理员取消");
        cancelVO.setOrderSn(orderSn);
        cancelVO.setOperator("平台管理员");

        this.orderOperateManager.cancel(cancelVO, OrderPermission.admin);
        return "";
    }

}
