/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.payment.model.enums;

/**
 * @author snow
 * @version v1.0
 * @Description: Paypal客户端使用配置参数
 * @date 2022年04月29日14:28:24
 * @since v7.0.0
 */
public enum PaypalConfigItem {

    /**
     * PAYPAL_CLIENT_ID
     */
    PAYPAL_CLIENT_ID("PAYPAL_CLIENT_ID"),
    /**
     * PAYPAL_CLIENT_SECRET
     */
    PAYPAL_CLIENT_SECRET("PAYPAL_CLIENT_SECRET");


    private String text;

    PaypalConfigItem(String text) {
        this.text = text;

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String value() {
        return this.name();
    }


}
