/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.base.message;

import cloud.shopfly.b2c.core.aftersale.model.dos.RefundDO;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundStatusEnum;

import java.io.Serializable;

/**
 * 退货退款消息
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/19 上午10:36
 */
public class RefundChangeMsg implements Serializable {

    private static final long serialVersionUID = -5608209655474949712L;

    private RefundDO refund;

    /**
     * 售后状态
     */
    private RefundStatusEnum refundStatusEnum;

    public RefundChangeMsg(RefundDO refundDO, RefundStatusEnum refundStatusEnum) {
        this.refund = refundDO;
        this.refundStatusEnum = refundStatusEnum;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public RefundDO getRefund() {
        return refund;
    }

    public void setRefund(RefundDO refund) {
        this.refund = refund;
    }

    public RefundStatusEnum getRefundStatusEnum() {
        return refundStatusEnum;
    }

    public void setRefundStatusEnum(RefundStatusEnum refundStatusEnum) {
        this.refundStatusEnum = refundStatusEnum;
    }
}
