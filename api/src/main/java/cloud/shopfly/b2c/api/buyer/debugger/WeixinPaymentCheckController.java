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
package cloud.shopfly.b2c.api.buyer.debugger;

import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.enums.PayMode;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cloud.shopfly.b2c.core.payment.service.PaymentManager;
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
@ConditionalOnProperty(value = "shopfly.debugger", havingValue = "true")
public class WeixinPaymentCheckController {

    @Autowired
    private PaymentManager paymentManager;


    @GetMapping(value = "/weixin/pc/qr/iframe")
    public String qrPayFrame(  ) {
        StringBuffer html = new StringBuffer();

        html.append("<iframe id=\"iframe-qrcode\" width=\"200px\" height=\"200px\" scrolling=\"no\" src=\"payment/weixin/pc/qr/image\"></iframe>");

        return html.toString();
    }

    @GetMapping(value = "/weixin/pc/qr/image")
    public String qrPay(  ) {

        PayBill payBill = createBill();
        payBill.setClientType(ClientType.PC);
        payBill.setPayMode(PayMode.qr.name());

        StringBuffer html = new StringBuffer();
        Map<String,Object> map = paymentManager.pay(payBill);
        html.append("<form action='"+ map.get("gateway_url") +"' method='POST' >");

//        if (form.getFormItems() != null) {
//            form.getFormItems().forEach(formItem -> {
//                html.append("<input type='hidden' style='width:1000px' name='" + formItem.getItemName() + "' value='" + formItem.getItemValue() + "' />");
//            });
//        }

        html.append("<input type='submit' value='pay'/>");

        html.append("</form>");
        return html.toString();
    }




    private PayBill createBill() {

        PayBill payBill = new PayBill();
        payBill.setTradeType(TradeType.debugger);
        payBill.setOrderPrice(0.01);
        payBill.setPluginId("weixinPayPlugin");
        payBill.setBillSn("123456");

        return payBill;
    }
}
