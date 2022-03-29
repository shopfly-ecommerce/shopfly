/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.model.enums;

/**
 * 交易状态
 *
 * @author Snow
 * @version v1.0
 * @date 2017年8月18日下午9:20:46
 * @since v6.4.0
 */
public enum TradeStatusEnum {

    /**
     * 新订单
     */
    NEW("新订单"),

    /**
     * 已付款
     */
    PAID_OFF("已付款");

    private String description;

    TradeStatusEnum(String description) {
        this.description = description;

    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }

}
