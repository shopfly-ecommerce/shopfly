package dev.shopflix.core.payment.plugin.paypal;

/**
 * PaypalConfig
 *
 * @author snow
 * @version 1.0.0
 * 2022-04-22 20:22:00
 */
public class PaypalConfig {

    private static final String clientId = "xxxx";
    private static final String clientSecret = "xxxx";
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

}
