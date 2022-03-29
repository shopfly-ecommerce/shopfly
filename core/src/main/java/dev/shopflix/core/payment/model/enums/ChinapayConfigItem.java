/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.payment.model.enums;

/**
 * @author fk
 * @version v2.0
 * @Description: 银联在线客户端使用配置参数
 * @date 2018/4/11 17:05
 * @since v7.0.0
 */
public enum ChinapayConfigItem {

    /**
     * 商户代码
     */
    mer_id("商户代码"),
    /**
     * 配置文件security.properties存放位置
     */
    merchant_private_key("配置文件security.properties存放位置");

    private String text;

    ChinapayConfigItem(String text) {
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
