/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.aftersale.model.enums;

/**
 * 退款(货)操作枚举类
 *
 * @author zjp
 * @version v7.0
 * @since v7.0 下午4:54 2018/5/2
 */
public enum RefundOperateEnum {

    //申请退(款)货
    APPLY("申请退(款)货"),
    //管理员审核
    ADMIN_APPROVAL("管理员审核"),
    //退货入库
    STOCK_IN("退货入库"),
    //取消
    CANCEL("取消"),
    //管理员退款
    ADMIN_REFUND("管理员退款");

    private String description;

    RefundOperateEnum(String des) {
        this.description = des;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }
}
