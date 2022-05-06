package cloud.shopfly.b2c.api.buyer.payment;

import cn.hutool.core.date.DateUtil;
import cloud.shopfly.b2c.core.base.DomainHelper;
import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.service.OrderPayManager;
import cloud.shopfly.b2c.core.payment.service.PaymentManager;
import cloud.shopfly.b2c.core.payment.service.PaymentMethodManager;
import cloud.shopfly.b2c.core.trade.order.service.OrderQueryManager;
import cloud.shopfly.b2c.framework.logs.Debugger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

/**
 * PaypalBuyerController
 *
 * @author snow
 * @version 1.0.0
 * 2022-04-29 16:58:00
 */
@Api(description = "Paypal支付API")
@RestController
@RequestMapping("/order/pay/paypal")
@Validated
public class PaypalBuyerController {

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

    @ApiIgnore
    @ApiOperation(value = "接收支付异步回调")
    @PostMapping(value = "/return/paypalPlugin", produces = MediaType.TEXT_HTML_VALUE)
    public String payReturnPost( HttpServletResponse response) {
        System.out.println("post 调用成功"+ DateUtil.date());
        String paymentPluginId = "paypalPlugin";

        String result = this.paymentManager.payCallback(TradeType.trade, paymentPluginId, ClientType.PC);
        return result;
    }
}
