/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.debugger;

import dev.shopflix.core.payment.model.enums.ClientType;
import dev.shopflix.core.payment.model.enums.PayMode;
import dev.shopflix.core.payment.model.enums.TradeType;
import dev.shopflix.core.payment.model.vo.PayBill;
import dev.shopflix.core.payment.service.PaymentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-04-15
 */
@RestController
@RequestMapping("/debugger/payment")
@ConditionalOnProperty(value = "javashop.debugger", havingValue = "true")
public class AlipayCheckController {
    @Autowired
    private PaymentManager paymentManager;

    @GetMapping(value = "/alipay/pc/normal")
    public String payNomal(  ) {

        PayBill payBill = createBill();
        payBill.setClientType(ClientType.PC);
        payBill.setPayMode(PayMode.normal.name());

        StringBuffer html = new StringBuffer();
        Map<String,Object> map = paymentManager.pay(payBill);
        html.append("<form action='"+  map.get("gateway_url") +"' method='POST' target='_blank'>");

//        form.getFormItems().forEach(formItem -> {
//            html.append("<input type='hidden' style='width:1000px' name='" + formItem.getItemName() + "' value='" + formItem.getItemValue() + "' />");
//        });
//        html.append("<input type='submit' value='pay'/>");

        html.append("</form>");
        return html.toString();
    }

    @GetMapping(value = "/alipay/pc/qr/iframe")
    public String qrPayFrame(  ) {
        StringBuffer html = new StringBuffer();

        html.append("<iframe id=\"iframe-qrcode\" width=\"200px\" height=\"200px\" scrolling=\"no\" src=\"payment/alipay/pc/qr/image\"></iframe>");

        return html.toString();
    }

    @GetMapping(value = "/alipay/pc/qr/image")
    public String qrPay(  ) {

        PayBill payBill = createBill();
        payBill.setClientType(ClientType.PC);
        payBill.setPayMode(PayMode.qr.name());

        StringBuffer html = new StringBuffer();
        Map<String,Object> map = paymentManager.pay(payBill);
        html.append("<form action='"+  map.get("gateway_url") +"' method='POST' target='_blank'>");

//        form.getFormItems().forEach(formItem -> {
//
//            html.append("<input type='hidden' style='width:1000px' name='" + formItem.getItemName() + "' value='" + formItem.getItemValue() + "' />");
//
//        });
        html.append("<input type='submit' value='pay'/>");

        html.append("</form>");
        return html.toString();
    }



    @GetMapping(value = "/alipay/wap")
    public String wap(  ) {

        PayBill payBill = createBill();
        payBill.setClientType(ClientType.WAP);

        StringBuffer html = new StringBuffer();
        Map<String,Object> map = paymentManager.pay(payBill);
        html.append("<form action='"+  map.get("gateway_url") +"' method='POST' target='_blank'>");

//        form.getFormItems().forEach(formItem -> {
//
//            html.append("<input type='hidden' style='width:1000px' name='" + formItem.getItemName() + "' value='" + formItem.getItemValue() + "' />");
//
//        });
        html.append("<input type='submit' value='pay'/>");

        html.append("</form>");
        return html.toString();
    }

    private PayBill createBill() {

        PayBill payBill = new PayBill();
        payBill.setTradeType(TradeType.debugger);
        payBill.setOrderPrice(0.01);
        payBill.setPluginId("alipayDirectPlugin");
        payBill.setBillSn("123456");

        return payBill;
    }
}
