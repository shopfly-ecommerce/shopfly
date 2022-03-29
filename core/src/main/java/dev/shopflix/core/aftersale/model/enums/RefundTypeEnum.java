/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.aftersale.model.enums;

/**
 * 申请售后类型枚举类
 *
 * @author zjp
 * @version v7.0
 * @since v7.0 上午9:38 2018/5/3
 */
public enum RefundTypeEnum {

    //取消订单
    CANCEL_ORDER("取消订单"),
    //申请售后
    AFTER_SALE("申请售后");

    private String description;

    RefundTypeEnum(String des) {
        this.description = des;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }
}
