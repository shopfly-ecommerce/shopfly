package dev.shopflix.core.payment.plugin.paypal;

import cn.hutool.json.JSONUtil;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.*;
import dev.shopflix.core.client.trade.OrderClient;
import dev.shopflix.core.payment.model.enums.ClientType;
import dev.shopflix.core.payment.model.enums.TradeType;
import dev.shopflix.core.payment.model.vo.ClientConfig;
import dev.shopflix.core.payment.model.vo.PayBill;
import dev.shopflix.core.payment.model.vo.RefundBill;
import dev.shopflix.core.payment.service.PaymentPluginManager;
import dev.shopflix.core.trade.order.model.vo.OrderDetailVO;
import dev.shopflix.core.trade.sdk.model.OrderDetailDTO;
import dev.shopflix.framework.context.ThreadContextHolder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PaypalPlugin
 *
 * @author snow
 * @version 1.0.0
 * 2022-04-22 18:51:00
 */
@Service
public class PaypalPlugin extends PayPalClient implements PaymentPluginManager  {

    private static 	Map<String, String> map = new HashMap<String, String>();

    private static final String clientId = "Aejo36jCAH2pqkN3rbG0e3upgklt_0uZNaQ7WXAvPdvQZkShK7vsf4xdE0GU8340tbDS_qP3gkkFdnOh";
    private static final String clientSecret = "EBbNOsKPsr6eNnnC1x4dFNkcgXy_yMhHxhdKIpf9Xhhj5RvyFRgaxKjwmKlwaQt1zEhmh4tgLDEZWxIv";

    // sandbox 沙箱/ live 正式环境
    private static final String mode = "sandbox";

    private static final String PP_SUCCESS = "success";
    /**
     * approved 获准
     */
    public static final String APPROVED = "approved";

    /**
     * sandbox 沙箱or live生产
     */
    public static final String PAYPAY_MODE = "live";

    /**
     * PayPal 取消支付回调地址
     */
    public static final String PAYPAL_CANCEL_URL = "/result/pay/cancel";

    /**
     * PayPal 取消支付回调地址
     */
    public static final String PAYPAL_SUCCESS_URL = "/result/pay/success";

    /**
     * 当前货币币种简称, 默认为人名币的币种 EUR CNY USD
     */
    public static final String CURRENTCY = "USD";

    /**
     * approval_url 验证url
     */
    public static final String APPROVAL_URL = "approval_url";

    public static final String CAPTURE = "CAPTURE";
    public static final String BRANDNAME = "Supernote";
    public static final String LANDINGPAGE = "NO_PREFERENCE";
    public static final String USERACTION = "PAY_NOW";
    public static final String SHIPPINGPREFERENCE = "SET_PROVIDED_ADDRESS";
    public static final String COMPLETED = "COMPLETED";

    public static String paypalOrderId = null;


    @Autowired
    private OrderClient orderClient;


    @Override
    public String getPluginId() {
        return "paypalPlugin";
    }

    @Override
    public String getPluginName() {
        return "Paypal";
    }

    @Override
    public List<ClientConfig> definitionClientConfig() {
        return null;
    }

    @Override
    public Map pay(PayBill bill) {

        //读取收货地址
        OrderDetailDTO orderDetailDTO = orderClient.getModel(bill.getSn());


        OrdersCreateRequest request = new OrdersCreateRequest();
        request.header("prefer","return=representation");
        request.requestBody(buildRequestBody(bill));

        try {
            HttpResponse<Order> response = client().execute(request);

            if (response.statusCode() == 201) {
                System.out.println("Status Code: " + response.statusCode());
                System.out.println("Status: " + response.result().status());
                System.out.println("Order ID: " + response.result().id());
                paypalOrderId = response.result().id();

                System.out.println("Intent: " + response.result().checkoutPaymentIntent());
                System.out.println("Links: ");
                for (LinkDescription link : response.result().links()) {
                    System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
                }
                System.out.println("Total Amount: " + response.result().purchaseUnits().get(0).amountWithBreakdown().currencyCode()
                        + " " + response.result().purchaseUnits().get(0).amountWithBreakdown().value());
                System.out.println("Full response body:");
                System.out.println(JSONUtil.toJsonStr(response.result()));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public void onReturn(TradeType tradeType) {
        System.out.println("同步支付回调");
    }

    @Override
    public String onCallback(TradeType tradeType, ClientType clientType) {
        System.out.println("异步支付回调");

        HttpServletRequest request = ThreadContextHolder.getHttpRequest();
        System.out.println(JSONUtil.toJsonStr(request.getParameterMap()));



        return null;
    }

    @Override
    public String onQuery(PayBill bill) {
        System.out.println("主动查询接口");
        try {
            HttpResponse<Order> orderResponse = new CaptureOrder().captureOrder(paypalOrderId, false);
            String captureId = "";
            if (orderResponse.statusCode() == 201){
                System.out.println("Captured Successfully");
                System.out.println("Status Code: " + orderResponse.statusCode());
                System.out.println("Status: " + orderResponse.result().status());
                System.out.println("Order ID: " + orderResponse.result().id());
                System.out.println("Links:");
                for (LinkDescription link : orderResponse.result().links()) {
                    System.out.println("\t" + link.rel() + ": " + link.href());
                }
                System.out.println("Capture ids:");
                for (PurchaseUnit purchaseUnit : orderResponse.result().purchaseUnits()) {
                    for (Capture capture : purchaseUnit.payments().captures()) {
                        System.out.println("\t" + capture.id());
                        captureId = capture.id();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean onTradeRefund(RefundBill bill) {
        return false;
    }

    @Override
    public String queryRefundStatus(RefundBill bill) {
        return null;
    }

    @Override
    public Integer getIsRetrace() {
        return null;
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
                .brandName("EXAMPLE INC")

                //要在 PayPal 网站上显示以供客户结帐的登录页面类型。
                .landingPage("BILLING")

                //客户取消付款后重定向客户的 URL。
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
                //API 调用方为采购单位提供的外部 ID。当您必须通过 更新订单时，多个采购单位需要PATCH。如果您忽略此值且订单仅包含一个购买单位，PayPal 会将此值设置为default。
                .referenceId("PUHF")
                //购买说明
                .description("Sporting Goods")
                //API 调用者提供的外部 ID。用于协调客户交易与 PayPal 交易。出现在交易和结算报告中，但对付款人不可见。
                .customId("CUST-HighFashions")
                //软描述符是用于构造出现在付款人卡对帐单上的对帐单描述符的动态文本。
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
