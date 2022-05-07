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
     * pay
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
     * An asynchronous callback
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

            // Third party serial number
            String returnOrderNo = obj.get("id")+"";

            // Approve a deal
            if("CHECKOUT.ORDER.APPROVED".equals(obj.get("event_type")+"")){
                JSONObject resource = obj.get("resource", JSONObject.class);
                List<JSONObject> list = resource.get("purchase_units", ArrayList.class);
                JSONObject purchase = list.get(0);
                JSONObject amount = purchase.get("amount", JSONObject.class);

                // Pay the amount
                String value = amount.get("value")+"";
                // Order/transaction SN
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
     * Query payment result
     *
     * @param bill
     * @return
     */
    public String onQuery(PayBill bill) {
        System.out.println("Active query interface");
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
                throw new ServiceException("500", "Outstanding payment");
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
        // Deduct the money immediately after the customer pays
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext()
                // The label that covers the company name in the PayPal account on the PayPal website.
                .brandName(domainSettings.getName())

                // The type of login page to display on the PayPal site for the customer to check out.
                .landingPage("BILLING")

                // Redirect the customers URL after the customer cancels the payment.
                .cancelUrl("http://buyer-api-paypal.vaiwan.com/order/pay/callback/trade/paypalPlugin/PC")

                // Redirects the customers URL after the customer approves the payment.
                .returnUrl("http://buyer-api-paypal.vaiwan.com/order/pay/callback/trade/paypalPlugin/PC")

                // Configure the continue or pay now checkout process.
                // After redirecting the customer to the PayPal payment page, a CONTINUE button appears. Use this option if you do not know the final amount when you start the checkout process and you want to redirect the customer to the merchant page without processing the payment.
                // PAY_NOW. After redirecting the customer to the PayPal payment page, the "Pay Now" button appears. Use this option if you know the final amount when you initiate checkout and you want the payment to be processed immediately when the customer clicks Pay Now.
                .userAction("CONTINUE")

                // GET_FROM_FILE. Use the shipping address provided by the customer on the PayPal website.
                // NO_SHIPPING. Edit the shipping address from PayPal. Recommended for digital goods.
                // SET_PROVIDED_ADDRESS. Use the address provided by the merchant. Customers cannot change this address on the PayPal website.
                .shippingPreference("SET_PROVIDED_ADDRESS");
        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<PurchaseUnitRequest>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                // The external ID provided by the API caller to the purchasing unit. Multiple purchasing units require patches when you have to update orders. If you ignore this value and the order contains only one purchase unit, PayPal sets this value to default.
                .referenceId("PUHF")
                // Purchase specification
                //.description("Sporting Goods")
                // The external ID provided by the API caller. Used to coordinate customer transactions with PayPal transactions. Appears in transaction and settlement reports but is not visible to the payer.
                .customId(bill.getTradeType().name() + "-" + bill.getBillSn())
                // The soft descriptor is the dynamic text used to construct the statement descriptor that appears on the payer card statement.
                //.softDescriptor("HighFashions")

                // Details of order amount. necessary
                .amountWithBreakdown(
                        new AmountWithBreakdown()
                                // Currency code required
                                .currencyCode("USD")
                                // Total amount required
                                .value(bill.getOrderPrice()+""))

                // Logistics details required
                .shippingDetail(new ShippingDetail().name(
                        // Name of consignee
                        new Name().fullName(orderDetailVO.getShipName()))
                        // address
                        .addressPortable(new AddressPortable()
                                .addressLine1(orderDetailVO.getShipAddr())
                                .adminArea2(orderDetailVO.getShipCity())
                                .adminArea1(orderDetailVO.getShipState())
                                .postalCode(orderDetailVO.getShipZip())
                                .countryCode(orderDetailVO.getShipCountryCode())));

        purchaseUnitRequests.add(purchaseUnitRequest);
        // The order details
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }


    /**
     * readbodyparameter
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
