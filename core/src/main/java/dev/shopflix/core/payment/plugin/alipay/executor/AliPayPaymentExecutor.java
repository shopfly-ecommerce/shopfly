/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.payment.plugin.alipay.executor;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import dev.shopflix.core.payment.PaymentErrorCode;
import dev.shopflix.core.payment.model.enums.ClientType;
import dev.shopflix.core.payment.model.enums.PayMode;
import dev.shopflix.core.payment.model.enums.TradeType;
import dev.shopflix.core.payment.model.vo.PayBill;
import dev.shopflix.core.payment.plugin.alipay.AlipayPluginConfig;
import dev.shopflix.core.payment.plugin.alipay.ShopflixAlipayUtil;
import dev.shopflix.framework.context.ThreadContextHolder;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.logs.Debugger;
import dev.shopflix.framework.util.JsonUtil;
import dev.shopflix.framework.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: 支付宝pc端
 * @date 2018/4/1714:55
 * @since v7.0.0
 */
@Service
public class AliPayPaymentExecutor extends AlipayPluginConfig {

    @Autowired
    private Debugger debugger;


    /**
     * 支付
     *
     * @param bill
     * @return
     */
    public Map onPay(PayBill bill) {

        try {

            AlipayClient alipayClient =  super.buildClient(bill.getClientType());

            //设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(this.getReturnUrl(bill));
            alipayRequest.setNotifyUrl(this.getCallBackUrl(bill.getTradeType(), bill.getClientType()));

            debugger.log("callback url is ");
            debugger.log(alipayRequest.getNotifyUrl());

            Map<String, String> sParaTemp =  createParam(bill);

            // 扫描二维码模式
            if (PayMode.qr.name().equals(bill.getPayMode())) {
                sParaTemp.put("qr_pay_mode", "4");
                sParaTemp.put("qrcode_width", "200");
            }

            ObjectMapper json = new ObjectMapper();

            String bizContent =  json.writeValueAsString( sParaTemp);

            //填充业务参数
            alipayRequest.setBizContent(bizContent);
            AlipayResponse response = alipayClient.pageExecute(alipayRequest);
            return JsonUtil.toMap(response.getBody());

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    /**
     * 同步支付回调
     *
     * @param tradeType
     */
    public String onReturn(TradeType tradeType) {

        //支付账单编号
        String billSn = "";
        // 交易号
        try {


            HttpServletRequest request = ThreadContextHolder.getHttpRequest();
            billSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号WAIT_BUYER_PAY	交易创建，等待买家付款 TRADE_CLOSED	未付款交易超时关闭，或支付完成后全额退款 TRADE_SUCCESS	交易支付成功 TRADE_FINISHED	交易结束，不可退款
            String returnTradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String totalAmount = request.getParameter("total_amount");

            Map<String, String> cfgparams = this.getConfig(ClientType.PC);
            String alipayPublicKey = cfgparams.get("alipay_public_key");

            // 验证是否成功
            if (ShopflixAlipayUtil.verify(alipayPublicKey)) {
                //新版本同步没有交易状态
                double payPrice = StringUtil.toDouble(totalAmount, 0d);
                //this.paySuccess(billSn, returnTradeNo, tradeType, payPrice);
            } else {

                throw new ServiceException(PaymentErrorCode.E503.code(), "验证失败");
            }

        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("验证发生异常", e);
            }
        }
        return billSn;
    }

    /**
     * 异步支付回调
     *
     * @param tradeType
     */
    public String onCallback(TradeType tradeType, ClientType clientType) {
        debugger.log("进入支付宝回调");
        try {

            HttpServletRequest request = ThreadContextHolder.getHttpRequest();

            // 商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            // 支付宝交易号
            String returnTradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            // 交易状态
            String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            // 付款金额
            String totalAmount = request.getParameter("total_amount");

            Map<String, String> cfgparams = this.getConfig(clientType);
            String alipayPublicKey = cfgparams.get("alipay_public_key");

            debugger.log("outTradeNo:["+outTradeNo+"],returnTradeNo:["+returnTradeNo+"],tradeStatus:["+tradeStatus+"],totalAmount:["+totalAmount+"]");
            debugger.log("alipayPublicKey:");
            debugger.log(alipayPublicKey);

            // 验证成功
            if (ShopflixAlipayUtil.verify(alipayPublicKey)) {
                debugger.log("验证成功");

                if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
                    double payPrice = StringUtil.toDouble(totalAmount, 0d);
                    this.paySuccess(outTradeNo, returnTradeNo, tradeType, payPrice);
                }
                // 请不要修改或删除
                return "success";
            } else {// 验证失败
                debugger.log("验证失败");
                return "fail";
            }
        } catch (Exception e) {
            this.logger.error("验证发生异常", e);
            return "fail";
        }


    }

    /**
     * 查询支付结果
     *
     * @param bill
     * @return
     */
    public String onQuery(PayBill bill) {

        AlipayClient alipayClient =  super.buildClient(bill.getClientType());

        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();

        String outTradeNo = bill.getBillSn();

        Map<String, String> sParaTemp = new HashMap<String, String>(16);
        sParaTemp.put("out_trade_no", bill.getBillSn());

        ObjectMapper json = new ObjectMapper();

        try {
            alipayRequest.setBizContent(json.writeValueAsString(sParaTemp));

            AlipayTradeQueryResponse response = alipayClient.execute(alipayRequest);
            if (response.isSuccess()) {

                String tradeStatus = response.getTradeStatus();
                // 交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）
                if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {

                    String totalAmount = response.getTotalAmount();
                    double payPrice = StringUtil.toDouble(totalAmount, 0d);

                    String returnTradeNo = response.getTradeNo();

                    this.paySuccess(outTradeNo, returnTradeNo, bill.getTradeType(), payPrice);

                }
            } else {

                logger.error("支付查询失败");
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return "";
    }


}
