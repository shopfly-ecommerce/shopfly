/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.aftersale.model.dto;

import com.enation.app.javashop.core.aftersale.model.dos.RefundGoodsDO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author zjp
 * @version v7.0
 * @Description 退货(款)单详细DTO
 * @ClassName RefundDetailDTO
 * @since v7.0 上午11:32 2018/5/8
 */
public class RefundDetailDTO implements Serializable {

    @ApiModelProperty(value = "退货（款）单")
    private RefundDTO refund;

    @ApiModelProperty(value = "退货商品")
    private List<RefundGoodsDO> refundGoods;

    public RefundDTO getRefund() {
        return refund;
    }

    public void setRefund(RefundDTO refund) {
        this.refund = refund;
    }

    public List<RefundGoodsDO> getRefundGoods() {
        return refundGoods;
    }

    public void setRefundGoods(List<RefundGoodsDO> refundGoods) {
        this.refundGoods = refundGoods;
    }

    @Override
    public String toString() {
        return "RefundDetailDTO{" +
                "refund=" + refund +
                ", refundGoods=" + refundGoods +
                '}';
    }
}
