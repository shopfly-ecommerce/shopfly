package dev.shopflix.core.payment.plugin.paypal.executor;

import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import dev.shopflix.core.base.DomainSettings;
import dev.shopflix.core.client.trade.OrderClient;
import dev.shopflix.core.payment.model.dos.PaymentBillDO;
import dev.shopflix.core.payment.model.vo.PayBill;
import dev.shopflix.core.payment.plugin.paypal.CaptureOrder;
import dev.shopflix.core.payment.plugin.paypal.PayPalClient;
import dev.shopflix.core.payment.plugin.paypal.PaypalPluginConfig;
import dev.shopflix.core.payment.service.PaymentBillManager;
import dev.shopflix.core.trade.order.model.vo.OrderDetailVO;
import dev.shopflix.framework.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 支付
     * @param bill
     * @return
     */
    public Map onPay(PayBill bill) {

        PayPalClient payPalClient = new PayPalClient();

        OrdersCreateRequest request = new OrdersCreateRequest();
        request.header("prefer","return=representation");
        request.requestBody(buildRequestBody(bill));

        Map result = new HashMap();

        try {
            HttpResponse<Order> response = payPalClient.client().execute(request);

            if (response.statusCode() == 201) {
                // System.out.println("Status Code: " + response.statusCode());
                // System.out.println("Status: " + response.result().status());
                // System.out.println("Order ID: " + response.result().id());
                // paypal 流水号

                //this.orderClient.updateOrderPayOrderNo(response.result().id(), bill.getSn());

                this.paymentBillManager.updateTradeNoByBillSn(bill.getSn(), response.result().id());

                // System.out.println("Links: ");
                for (LinkDescription link : response.result().links()) {
                    // System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
                    if ("approve".equals(link.rel())) {
                        result.put("approve", link.href());
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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
    private OrderRequest buildRequestBody(PayBill bill) {

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
                .description("Sporting Goods")
                // API 调用者提供的外部 ID。用于协调客户交易与 PayPal 交易。出现在交易和结算报告中，但对付款人不可见。
                .customId(bill.getBillSn())
                // 软描述符是用于构造出现在付款人卡对帐单上的对帐单描述符的动态文本。
                .softDescriptor("HighFashions")

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
                        new Name().fullName("John Doe"))
                        //地址
                        .addressPortable(new AddressPortable().addressLine1("123 Townsend St").addressLine2("Floor 6")
                                .adminArea2("San Francisco").adminArea1("CA").postalCode("94107").countryCode("US")));
        purchaseUnitRequests.add(purchaseUnitRequest);
        //订单明细
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }


}
