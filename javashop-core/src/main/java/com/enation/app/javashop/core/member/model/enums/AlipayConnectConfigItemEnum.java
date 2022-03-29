/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.member.model.enums;

/**
 * @author zjp
 * @version v7.0
 * @Description 支付宝信任登录参数项枚举类
 * @ClassName AlipayConnectConfigItemEnum
 * @since v7.0 下午6:13 2018/6/28
 */
public enum AlipayConnectConfigItemEnum {
    /**
     * appId
     */
    app_id("app_id"),
    /**
     * 支付宝私钥
     */
    private_key("支付宝私钥"),
    /**
     * 支付宝公钥
     */
    public_key("支付宝公钥"),
    /**
     * 支付宝公钥
     */
    pid("app的pid");

    private String text;

    AlipayConnectConfigItemEnum(String text) {
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
