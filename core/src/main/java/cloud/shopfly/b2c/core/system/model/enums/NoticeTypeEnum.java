/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.system.model.enums;

/**
 * @author zjp
 * @version v7.0
 * @Description 店铺站内消息枚举类
 * @ClassName ShopNoticeTypeEnum
 * @since v7.0 下午2:21 2018/7/10
 */
public enum NoticeTypeEnum {
    /**
     * 订单
     */
    ORDER("订单"),
    /**
     * 商品
     */
    GOODS("商品"),
    /**
     * 售后
     */
    AFTERSALE("售后"),
    /**
     * 其他
     */
    OTHER("其他");

    private String description;

    NoticeTypeEnum(String des) {
        this.description = des;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }
}
