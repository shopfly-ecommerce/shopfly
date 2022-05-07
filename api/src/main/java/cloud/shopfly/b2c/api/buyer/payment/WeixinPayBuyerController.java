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
package cloud.shopfly.b2c.api.buyer.payment;

import cloud.shopfly.b2c.core.passport.service.LoginWeChatManager;
import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.enums.PayMode;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cloud.shopfly.b2c.core.payment.plugin.weixin.WeixinPayPlugin;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.io.ByteArrayOutputStream;

/**
 * @author fk
 * @version v2.0
 * @Description: Order payment
 * @date 2018/4/1616:44
 * @since v7.0.0
 */
@Api(description = "Order paymentAPI")
@RestController
@RequestMapping("/order/pay")
@Validated
public class WeixinPayBuyerController {

    @Autowired
    private WeixinPayPlugin weixinPayPlugin;



    @Autowired
    private LoginWeChatManager loginWeChatManager;

    @ApiIgnore
    @ApiOperation(value = "Display a wechat QR code")
    @GetMapping(value = "/weixin/qr/{pr}")
    public byte[] qr(@PathVariable(name = "pr") String pr) throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Width of picture
        int width = 200;
        // highly
        int height = 200;
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix m = writer.encode("weixin://wxpay/bizpayurl?pr=" + pr, BarcodeFormat.QR_CODE, height, width);
        MatrixToImageWriter.writeToStream(m, "png", stream);

        return stream.toByteArray();
    }


    @ApiOperation(value = "Get the status of wechat scanning payment")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "weixin_trade_sn", value = "Wechat prepaid order number", required = true, dataType = "String", paramType = "path"),
    })
    @GetMapping("/weixin/status/{weixin_trade_sn}")
    public String payStatus(@PathVariable(name = "weixin_trade_sn") String weixinTradeSn) {


        PayBill bill = new PayBill();
        bill.setBillSn(weixinTradeSn);
        bill.setClientType(ClientType.PC);
        bill.setTradeType(TradeType.trade);
        bill.setPayMode(PayMode.qr.name());
        String result = weixinPayPlugin.onQuery(bill);
        return result;

    }

    @GetMapping("/weixin/h5/openid")
    public String getH5Openid(String code) {
        JSONObject accessTokenJson =  loginWeChatManager.getAccessToken(code);
        String openid = accessTokenJson.getString("openid");
        return openid;
    }

    @GetMapping("/weixin/mini/openid")
    public String getMiniOpenid(String code) {
        String openid = loginWeChatManager.getMiniOpenid(code);
        return openid;
    }

}
