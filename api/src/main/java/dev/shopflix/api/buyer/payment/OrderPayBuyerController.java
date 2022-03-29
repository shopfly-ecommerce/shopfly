/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.payment;

import dev.shopflix.core.base.DomainHelper;
import dev.shopflix.core.payment.model.dto.PayParam;
import dev.shopflix.core.payment.model.enums.ClientType;
import dev.shopflix.core.payment.model.enums.PayMode;
import dev.shopflix.core.payment.model.enums.TradeType;
import dev.shopflix.core.payment.model.vo.PaymentMethodVO;
import dev.shopflix.core.payment.service.OrderPayManager;
import dev.shopflix.core.payment.service.PaymentManager;
import dev.shopflix.core.payment.service.PaymentMethodManager;
import dev.shopflix.core.trade.order.model.enums.PayStatusEnum;
import dev.shopflix.core.trade.order.model.vo.OrderDetailVO;
import dev.shopflix.core.trade.order.service.OrderQueryManager;
import dev.shopflix.framework.logs.Debugger;
import dev.shopflix.framework.util.AbstractRequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: 订单支付
 * @date 2018/4/1616:44
 * @since v7.0.0
 */
@Api(description = "订单支付API")
@RestController
@RequestMapping("/order/pay")
@Validated
public class OrderPayBuyerController {

    @Autowired
    private PaymentManager paymentManager;

    @Autowired
    private OrderPayManager orderPayManager;

    @Autowired
    private OrderQueryManager orderQueryManager;

    @Autowired
    private PaymentMethodManager paymentMethodManager;

    @Autowired
    private DomainHelper domainHelper;

    @Autowired
    private Debugger debugger;


    @ApiOperation(value = "订单检查 是否需要支付 为false代表不需要支付，出现支付金额为0，或者已经支付，为true代表需要支付")
    @GetMapping(value = "/needpay/{sn}")
    public boolean check(@PathVariable(name = "sn") String sn) {
        OrderDetailVO order = this.orderQueryManager.getModel(sn, null);
        return order.getNeedPayMoney() != 0 && !order.getPayStatus().equals(PayStatusEnum.PAY_YES.value());
    }


    @ApiOperation(value = "查询支持的支付方式")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "client_type", value = "调用客户端PC,WAP,NATIVE,REACT", required = true, dataType = "String", paramType = "path", allowableValues = "PC,WAP,NATIVE,REACT")
    })
    @GetMapping(value = "/{client_type}")
    public List<PaymentMethodVO> queryPayments(@PathVariable(name = "client_type") String clientType) {

        List<PaymentMethodVO> list = paymentMethodManager.queryMethodByClient(clientType);

        return list;
    }


    @ApiOperation(value = "对一个交易发起支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sn", value = "要支付的交易sn", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "trade_type", value = "交易类型", required = true, dataType = "String", paramType = "path", allowableValues = "trade,order")
    })
    @GetMapping(value = "/{trade_type}/{sn}")
    public Map payTrade(@PathVariable(name = "sn") String sn, @PathVariable(name = "trade_type") String tradeType, @Valid PayParam param) {

        param.setSn(sn);
        param.setTradeType(tradeType);
        return orderPayManager.pay(param);
    }

    @ApiIgnore
    @ApiOperation(value = "接收支付同步回调")
    @GetMapping(value = "/return/{trade_type}/{pay_mode}/{client}/{sub_sn}/{plugin_id}", produces = MediaType.TEXT_HTML_VALUE)
    public String payReturn(@PathVariable(name = "trade_type") String tradeType, @PathVariable(name = "plugin_id") String paymentPluginId,
                            @PathVariable(name = "pay_mode") String payMode,@PathVariable(name = "client") String client,
                            @PathVariable(name = "sub_sn") String subSn, HttpServletResponse response) {

        this.paymentManager.payReturn(TradeType.valueOf(tradeType), paymentPluginId);

        String serverName = domainHelper.getBuyerDomain();
        if (AbstractRequestUtil.isMobile()) {
            serverName = domainHelper.getMobileDomain();
        }

        String url = serverName + "/payment-complete";
        //uniapp  h5
        if (ClientType.WAP.name().equals(client)) {

            url = serverName+"/order-module/cashier/cashier?is_callback=yes";

            if (TradeType.trade.name().equals(tradeType)) {
                url += "&trade_sn=" + subSn;
            } else {
                url += "&order_sn=" + subSn;
            }
        }

        String jumpHtml = "<script>";
        //扫码支付
        if (PayMode.qr.name().equals(payMode)) {
            //二维码模式嵌在的iframe中的，要设置此相应允许被buyer域名的frame嵌套
            jumpHtml += "window.parent.location.href='" + url + "'";
        } else {
            jumpHtml += "location.href='" + url + "'";
        }

        jumpHtml += "</script>";

        return jumpHtml;
    }

    @ApiIgnore
    @ApiOperation(value = "接收支付异步回调")
    @RequestMapping(value = "/callback/{trade_type}/{plugin_id}/{client_type}")
    public String payCallback(@PathVariable(name = "trade_type") String tradeType, @PathVariable(name = "plugin_id") String paymentPluginId,
                              @PathVariable(name = "client_type") String clientType) {

        debugger.log("接收到回调消息");
        debugger.log("tradeType:[" + tradeType + "],paymentPluginId:[" + paymentPluginId + "],clientType:[" + clientType + "]");


        String result = this.paymentManager.payCallback(TradeType.valueOf(tradeType), paymentPluginId, ClientType.valueOf(clientType));

        return result;
    }


    @ApiOperation(value = "主动查询支付结果")
    @GetMapping(value = "/order/pay/query/{trade_type}/{sn}")
    public String query(@PathVariable(name = "trade_type") String tradeType, @Valid PayParam param,
                        @PathVariable(name = "sn") String sn) {

        param.setSn(sn);
        param.setTradeType(tradeType);

        String result = this.paymentManager.queryResult(param);

        return result;
    }

    @ApiOperation(value = "APP对一个交易发起支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sn", value = "要支付的交易sn", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "trade_type", value = "交易类型", required = true, dataType = "String", paramType = "path", allowableValues = "trade,order")
    })
    @GetMapping(value = "/app/{trade_type}/{sn}")
    public String appPayTrade(@PathVariable(name = "sn") String sn, @PathVariable(name = "trade_type") String tradeType, @Valid PayParam param) {

        param.setSn(sn);
        param.setTradeType(tradeType);

        Map map = orderPayManager.pay(param);

        return map.get("gateway_url").toString();
    }

}
