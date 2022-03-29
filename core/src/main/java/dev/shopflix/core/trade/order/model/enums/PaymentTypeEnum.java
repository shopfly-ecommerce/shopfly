/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.model.enums;

/**
 * 支付方式
 *
 * @author Snow create in 2018/4/8
 * @version v2.0
 * @since v7.0.0
 */
public enum PaymentTypeEnum {

    /**
     * 在线支付
     */
    ONLINE("在线支付"),

    /**
     * 货到付款
     */
    COD("货到付款");

    private String description;


    PaymentTypeEnum(String description) {
        this.description = description;
    }

    public static PaymentTypeEnum defaultType() {

        return ONLINE;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }

}
