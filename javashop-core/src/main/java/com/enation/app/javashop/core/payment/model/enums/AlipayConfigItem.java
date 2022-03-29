/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.payment.model.enums;

/**
 * @author fk
 * @version v2.0
 * @Description: 支付宝客户端使用配置参数
 * @date 2018/4/11 17:05
 * @since v7.0.0
 */
public enum AlipayConfigItem {

    /**
     * 支付宝公钥
     */
    alipay_public_key("支付宝公钥"),
    /**
     * 应用ID
     */
    app_id("应用ID"),
    /**
     * 商户私钥
     */
    merchant_private_key("商户私钥");

    private String text;

    AlipayConfigItem(String text) {
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
