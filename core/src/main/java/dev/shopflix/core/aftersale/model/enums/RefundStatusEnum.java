/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.aftersale.model.enums;

/**
 * 退款(货)流程枚举类
 *
 * @author zjp
 * @version v7.0
 * @since v7.0 上午9:25 2018/5/3
 */
public enum RefundStatusEnum {

    //申请中
    APPLY("申请中"),
    //申请通过
    PASS("申请通过"),
    //审核拒绝
    REFUSE("审核拒绝"),
    //退货入库
    STOCK_IN("退货入库"),
    //待人工处理
    WAIT_FOR_MANUAL("待人工处理"),
    //申请取消
    CANCEL("申请取消"),
    //退款中
    REFUNDING("退款中"),
    //退款失败
    REFUNDFAIL("退款失败"),
    //完成
    COMPLETED("完成");

    private String description;

    RefundStatusEnum(String des) {
        this.description = des;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }
}
