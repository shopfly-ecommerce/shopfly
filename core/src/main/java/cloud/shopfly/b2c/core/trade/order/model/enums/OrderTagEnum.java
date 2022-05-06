/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.order.model.enums;

/**
 * 前端订单页面TAB标签枚举
 *
 * @author Snow create in 2018/5/14
 * @version v2.0
 * @since v7.0.0
 */
public enum OrderTagEnum {


    /**
     * 所有订单
     */
    ALL("所有订单"),

    /**
     * 待付款
     */
    WAIT_PAY("待付款"),

    /**
     * 待发货
     */
    WAIT_SHIP("待发货"),

    /**
     * 待收货
     */
    WAIT_ROG("待收货"),

    /**
     * 已取消
     */
    CANCELLED("已取消"),

    /**
     * 已完成
     */
    COMPLETE("已完成"),

    /**
     * 待评论
     */
    WAIT_COMMENT("待评论"),

    /**
     * 售后中
     */
    REFUND("售后中");

    private String description;


    OrderTagEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static OrderTagEnum defaultType() {
        return ALL;
    }

    public String value() {
        return this.name();
    }


}
