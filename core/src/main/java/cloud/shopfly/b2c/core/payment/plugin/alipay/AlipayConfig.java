/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.payment.plugin.alipay;

/**
 * @author fk
 * @version v2.0
 * @Description: 支付宝配置相关
 * @date 2018/4/12 10:25
 * @since v7.0.0
 */
public class AlipayConfig {


    /**
     * 签名方式
     */
    public static String signType = "RSA2";

    /**
     * 字符编码格式
     */
    public static String charset = "utf-8";


    /**
     * 支付宝网关
     */
    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";


}

