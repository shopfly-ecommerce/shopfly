/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.trade;

import dev.shopflix.core.base.SettingGroup;
import dev.shopflix.core.client.member.MemberHistoryReceiptClient;
import dev.shopflix.core.client.system.SettingClient;
import dev.shopflix.core.trade.TradeErrorCode;
import dev.shopflix.core.trade.cart.model.dos.OrderPermission;
import dev.shopflix.core.trade.order.model.dos.OrderLogDO;
import dev.shopflix.core.trade.order.model.dos.TradeDO;
import dev.shopflix.core.trade.order.model.dto.OrderQueryParam;
import dev.shopflix.core.trade.order.model.enums.OrderTagEnum;
import dev.shopflix.core.trade.order.model.enums.PaymentTypeEnum;
import dev.shopflix.core.trade.order.model.vo.*;
import dev.shopflix.core.trade.order.service.OrderLogManager;
import dev.shopflix.core.trade.order.service.OrderOperateManager;
import dev.shopflix.core.trade.order.service.OrderQueryManager;
import dev.shopflix.core.trade.order.service.TradeQueryManager;
import dev.shopflix.framework.context.UserContext;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.security.model.Buyer;
import dev.shopflix.framework.util.DateUtil;
import dev.shopflix.framework.util.JsonUtil;
import dev.shopflix.framework.util.StringUtil;
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
 * 会员订单相关控制器
 *
 * @author Snow create in 2018/5/14
 * @version v2.0
 * @since v7.0.0
 */

@Api(description = "会员订单API")
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


    @ApiOperation(value = "查询会员订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_name", value = "商品名称关键字", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "key_words", value = "关键字", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order_status", value = "订单状态", dataType = "String", paramType = "query",
                    allowableValues = "ALL,WAIT_PAY,WAIT_SHIP,WAIT_ROG,CANCELLED,COMPLETE,WAIT_COMMENT,REFUND",
                    example = "ALL:所有订单,WAIT_PAY:待付款,WAIT_SHIP:待发货,WAIT_ROG:待收货," +
                            "CANCELLED:已取消,COMPLETE:已完成,WAIT_COMMENT:待评论,REFUND:售后中"),
            @ApiImplicitParam(name = "page_no", value = "页数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "条数", dataType = "int", paramType = "query"),
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
            throw new ServiceException(TradeErrorCode.E455.code(), "订单状态参数错误");
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

        //货到付款的订单不允许线上支付
        List<OrderLineVO> list = page.getData();
        for (OrderLineVO order : list) {
            if (PaymentTypeEnum.COD.value().equals(order.getPaymentType())) {
                order.getOrderOperateAllowableVO().setAllowPay(false);
            }
        }
        page.setData(list);

        return page;
    }


    @ApiOperation(value = "查询单个订单明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "订单编号", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = "/{order_sn}")
    public OrderDetailVO get(@ApiIgnore @PathVariable("order_sn") String orderSn) {
        Buyer buyer = UserContext.getBuyer();
        OrderDetailVO detailVO = this.orderQueryManager.getModel(orderSn, buyer.getUid());

        if (detailVO.getNeedReceipt() == 1) {
            detailVO.setReceiptHistory(memberHistoryReceiptClient.getReceiptHistory(orderSn));
        }

        //货到付款的订单不允许线上支付
        if (PaymentTypeEnum.COD.value().equals(detailVO.getPaymentType())) {
            detailVO.getOrderOperateAllowableVO().setAllowPay(false);
        }

        return detailVO;
    }


    @ApiOperation(value = "确认收货")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "订单编号", required = true, dataType = "String", paramType = "path")
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


    @ApiOperation(value = "取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "订单编号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "reason", value = "取消原因", required = true, dataType = "String", paramType = "query"),
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


    @ApiOperation(value = "查询订单状态的数量")
    @GetMapping(value = "/status-num")
    public OrderStatusNumVO getStatusNum() {
        Buyer buyer = UserContext.getBuyer();
        return this.orderQueryManager.getOrderStatusNum(buyer.getUid());
    }


    @ApiOperation(value = "根据交易编号查询订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "trade_sn", value = "交易编号", required = true, dataType = "String", paramType = "path"),
    })
    @GetMapping(value = "/{trade_sn}/list")
    public List<OrderDetailVO> getOrderList(@ApiIgnore @PathVariable("trade_sn") String tradeSn) {
        Buyer buyer = UserContext.getBuyer();
        List<OrderDetailVO> orderDetailVOList = this.orderQueryManager.getOrderByTradeSn(tradeSn, buyer.getUid());
        return orderDetailVOList;
    }


    @ApiOperation(value = "根据交易编号或者订单编号查询收银台数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "trade_sn", value = "交易编号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "order_sn", value = "订单编号", dataType = "String", paramType = "query")
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
            throw new ServiceException(TradeErrorCode.E455.code(), "参数错误");
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


    @ApiOperation(value = "订单流程图数据", notes = "订单流程图数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "订单sn", required = true, dataType = "String", paramType = "path"),
    })
    @GetMapping(value = "/{order_sn}/flow")
    public List<OrderFlowNode> getOrderStatusFlow(@ApiIgnore @PathVariable(name = "order_sn") String orderSn) {
        return this.orderQueryManager.getOrderFlow(orderSn);
    }

    @ApiOperation(value = "查询订单日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "订单编号", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = "/{order_sn}/log")
    public List<OrderLogDO> getList(@ApiIgnore @PathVariable("order_sn") String orderSn) {
        List<OrderLogDO> logDOList = this.orderLogManager.listAll(orderSn);
        return logDOList;
    }

    /**
     * 计算距离订单自动失效时间
     * @param createTime  订单创建时间
     * @return   倒计时  单位:秒
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
