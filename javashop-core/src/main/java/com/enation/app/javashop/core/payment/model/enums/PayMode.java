/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.payment.model.enums;

/**
 * 支付模式枚举
 *
 * @author zh create in 2018/4/8
 * @version v2.0
 * @since v7.0.0
 */
public enum PayMode {

    /**
     * 正常
     */
    normal("正常"),

    /**
     * 二维码
     */
    qr("二维码");

    private String description;


    PayMode(String description) {
        this.description = description;
    }


    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }

}
