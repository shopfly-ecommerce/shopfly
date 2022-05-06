/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.payment.model.enums;

/**
 * @author fk
 * @version v2.0
 * @Description: 微信客户端使用配置参数
 * @date 2018/4/11 17:05
 * @since v7.0.0
 */
public enum WeixinConfigItem {

    /**
     * 商户号MCHID
     */
    mchid("商户号MCHID"),
    /**
     * APPID
     */
    appid("APPID"),
    /**
     * API密钥(key)
     */
    key("API密钥(key)"),
    /**
     * 应用密钥(AppScret)
     */
    app_secret("应用密钥(AppScret)"),
    /**
     * 证书路径
     */
    p12_path("证书路径");

    private String text;

    WeixinConfigItem(String text) {
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
