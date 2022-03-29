/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.order.model.enums;

/**
 * 申请售后的状态
 *
 * @author Snow
 * @version v2.0
 * @since v6.4
 * 2017年9月6日 下午4:16:53
 */
public enum ServiceStatusEnum {

    /**
     * 未申请
     */
    NOT_APPLY("未申请"),

    /**
     * 已申请
     */
    APPLY("已申请"),

    /**
     * 审核通过
     */
    PASS("审核通过"),

    /**
     * 审核未通过
     */
    REFUSE("审核未通过"),

    /**
     * 已失效或不允许申请售后
     */
    EXPIRED("已失效不允许申请售后");


    private String description;

    ServiceStatusEnum(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }
}
