/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.aftersale;

/**
 * 售后异常码
 * Created by kingapex on 2018/3/13.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/13
 */
public enum AftersaleErrorCode {

    /**
     * 某个异常
     */
    E600("退款金额不能大于支付金额"),
    E601("操作不允许"),
    E602("商品不存在"),
    E603("退款单不存在"),
    E604("订单不存在"),
    E605("退款方式必填"),
    E606("入库失败"),
    E607("申请售后货品数量不能大于购买数量"),
    E608("导出数据失败"),
    E609("可退款金额为0，无需申请退款/退货，请与平台联系解决");

    private String describe;

    AftersaleErrorCode(String des) {
        this.describe = des;
    }

    /**
     * 获取异常码
     *
     * @return
     */
    public String code() {
        return this.name().replaceAll("E", "");
    }

    /**
     * 获取异常描述
     *
     * @return
     */
    public String describe() {
        return this.describe;
    }


}
