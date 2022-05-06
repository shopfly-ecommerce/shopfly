package cloud.shopfly.b2c.core.payment.plugin.paypal.executor;

import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.payment.model.dos.PaymentBillDO;
import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import cloud.shopfly.b2c.core.base.DomainSettings;
import cloud.shopfly.b2c.core.payment.plugin.paypal.CaptureOrder;
import cloud.shopfly.b2c.core.payment.plugin.paypal.PayPalClient;
import cloud.shopfly.b2c.core.payment.plugin.paypal.PaypalPluginConfig;
import cloud.shopfly.b2c.core.payment.service.PaymentBillManager;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderDetailVO;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AlipayPaymentExecutor
 *
 * @author snow
 * @version 1.0.0
 * 2022-04-27 21:33:00
 */

@Service
public class PaypalPaymentExecutor extends PaypalPluginConfig {

    @Autowired
    private PaymentBillManager paymentBillManager;

    @Autowired
    private DomainSettings domainSettings;

    @Autowired
    private OrderClient orderClient;


    /**
     * 支付
     * @param bill
     * @return
     */
    public Map onPay(PayBill bill) {

        OrderDetailVO orderDetailVO = this.orderClient.getOrderVO(bill.getSn());
        PayPalClient payPalClient = super.buildClient(bill.getClientType());

        OrdersCreateRequest request = new OrdersCreateRequest();
        request.header("prefer","return=representation");
        request.requestBody(buildRequestBody(bill, orderDetailVO));

        Map result = new HashMap();

        try {
            HttpResponse<Order> response = payPalClient.client().execute(request);
            if (response.statusCode() == 201) {
                // System.out.println("Links: ");
                for (LinkDescription link : response.result().links()) {
                    // System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
                    if ("approve".equals(link.rel())) {
                        result.put("gateway_url", link.href());
                        result.put("form_items", new ArrayList<>());
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 异步回调
     * @param tradeType
     * @param clientType
     * @return
     */
    public String onCallback(TradeType tradeType, ClientType clientType) {
        try {
            HttpServletRequest request = ThreadContextHolder.getHttpRequest();
            String resultStr = this.getBodyData(request);
            System.out.println(resultStr);
            JSONObject obj = JSONUtil.toBean(resultStr, JSONObject.class);

            //第三方流水号
            String returnOrderNo = obj.get("id")+"";

            //批准交易
            if("CHECKOUT.ORDER.APPROVED".equals(obj.get("event_type")+"")){
                JSONObject resource = obj.get("resource", JSONObject.class);
                List<JSONObject> list = resource.get("purchase_units", ArrayList.class);
                JSONObject purchase = list.get(0);
                JSONObject amount = purchase.get("amount", JSONObject.class);

                //支付金额
                String value = amount.get("value")+"";
                //订单/交易 SN
                String customId = purchase.get("custom_id")+"";

                String type = customId.split("-")[0];
                String billSN = customId.split("-")[1];

                this.paySuccess(billSN, returnOrderNo, TradeType.valueOf(type), Double.parseDouble(value));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 查询支付结果
     *
     * @param bill
     * @return
     */
    public String onQuery(PayBill bill) {
        System.out.println("主动查询接口");
        PaymentBillDO billDO = this.paymentBillManager.getBillByBillSn(bill.getSn());

        try {
            HttpResponse<Order> orderResponse = new CaptureOrder().captureOrder(billDO.getReturnTradeNo(), false);

            if (orderResponse.statusCode() == 201){
                System.out.println("Captured Successfully");
                System.out.println("Status Code: " + orderResponse.statusCode());
                System.out.println("Status: " + orderResponse.result().status());
                System.out.println("Order ID: " + orderResponse.result().id());

                this.paySuccess(bill.getBillSn(), orderResponse.result().id(), bill.getTradeType(), bill.getOrderPrice());

            } else {
                throw new ServiceException("500", "未完成支付");
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return "";
    }


    /**
     * Method to generate sample create order body with <b>CAPTURE</b> intent
     *
     * @return OrderRequest with created order request
     */
    private OrderRequest buildRequestBody(PayBill bill, OrderDetailVO orderDetailVO) {

        OrderRequest orderRequest = new OrderRequest();
        // 在客户付款后立即扣款
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext()
                //覆盖 PayPal 网站上 PayPal 帐户中公司名称的标签。
                .brandName(domainSettings.getName())

                //要在 PayPal 网站上显示以供客户结帐的登录页面类型。
                .landingPage("BILLING")

                //客户取消付款后重定向客户的URL。
                .cancelUrl("http://buyer-api-paypal.vaiwan.com/order/pay/callback/trade/paypalPlugin/PC")

                //客户批准付款后重定向客户的 URL。
                .returnUrl("http://buyer-api-paypal.vaiwan.com/order/pay/callback/trade/paypalPlugin/PC")

                //配置继续或立即付款结帐流程。
                //CONTINUE. 将客户重定向到 PayPal 付款页面后，会出现一个继续按钮。如果在启动结账流程时不知道最终金额并且您希望将客户重定向到商家页面而不处理付款，请使用此选项。
                //PAY_NOW. 将客户重定向到 PayPal 付款页面后，会出现“立即付款”按钮。如果在启动结账时知道最终金额并且您希望在客户单击立即付款时立即处理付款，请使用此选项。
                .userAction("CONTINUE")

                //GET_FROM_FILE. 使用 PayPal 网站上客户提供的送货地址。
                //NO_SHIPPING. 从 PayPal 网站编辑收货地址。推荐用于数字商品。
                //SET_PROVIDED_ADDRESS. 使用商家提供的地址。客户无法在 PayPal 网站上更改此地址。
                .shippingPreference("SET_PROVIDED_ADDRESS");
        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<PurchaseUnitRequest>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                // API 调用方为采购单位提供的外部 ID。当您必须通过 更新订单时，多个采购单位需要PATCH。如果您忽略此值且订单仅包含一个购买单位，PayPal 会将此值设置为default。
                .referenceId("PUHF")
                // 购买说明
                //.description("Sporting Goods")
                // API 调用者提供的外部 ID。用于协调客户交易与 PayPal 交易。出现在交易和结算报告中，但对付款人不可见。
                .customId(bill.getTradeType().name() + "-" + bill.getBillSn())
                // 软描述符是用于构造出现在付款人卡对帐单上的对帐单描述符的动态文本。
                //.softDescriptor("HighFashions")

                //订单金额明细。必需
                .amountWithBreakdown(
                        new AmountWithBreakdown()
                                //货币代码  必需
                                .currencyCode("USD")
                                //总金额   必需
                                .value(bill.getOrderPrice()+""))

                //物流明细 必需
                .shippingDetail(new ShippingDetail().name(
                        //收货人姓名
                        new Name().fullName(orderDetailVO.getShipName()))
                        //地址
                        .addressPortable(new AddressPortable()
                                .addressLine1(orderDetailVO.getShipAddr())
                                .adminArea2(orderDetailVO.getShipCity())
                                .adminArea1(orderDetailVO.getShipState())
                                .postalCode(orderDetailVO.getShipZip())
                                .countryCode(orderDetailVO.getShipCountryCode())));

        purchaseUnitRequests.add(purchaseUnitRequest);
        //订单明细
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }


    /**
     * 读取body参数
     * @param request
     * @return
     */
    private String getBodyData(HttpServletRequest request) {
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine())) {
                data.append(line);
            }
        } catch (IOException e) {

        } finally {

        }
        return data.toString();
    }


}
