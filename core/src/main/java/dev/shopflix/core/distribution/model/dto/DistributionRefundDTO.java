/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.distribution.model.dto;

/**
 * DistributionRefundDTO
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-06-04 下午7:03
 */
public class DistributionRefundDTO {

    /**
     * lv1 会员id
     */
    private Integer memberIdLv1;
    /**
     * lv2 会员id
     */
    private Integer memberIdLv2;
    /**
     * lv1 会员返利
     */
    private Double refundLv1;
    /**
     * lv2 会员返利
     */
    private Double RefundLv2;

    /**
     * 退款金额
     */
    private Double refundMoney;


    public Double getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Double refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Integer getMemberIdLv1() {
        return memberIdLv1;
    }

    public void setMemberIdLv1(Integer memberIdLv1) {
        this.memberIdLv1 = memberIdLv1;
    }

    public Integer getMemberIdLv2() {
        return memberIdLv2;
    }

    public void setMemberIdLv2(Integer memberIdLv2) {
        this.memberIdLv2 = memberIdLv2;
    }

    public Double getRefundLv1() {
        return refundLv1;
    }

    public void setRefundLv1(Double refundLv1) {
        this.refundLv1 = refundLv1;
    }

    public Double getRefundLv2() {
        return RefundLv2;
    }

    public void setRefundLv2(Double refundLv2) {
        RefundLv2 = refundLv2;
    }

    @Override
    public String toString() {
        return "DistributionRefundDTO{" +
                "memberIdLv1=" + memberIdLv1 +
                ", memberIdLv2=" + memberIdLv2 +
                ", refundLv1=" + refundLv1 +
                ", RefundLv2=" + RefundLv2 +
                ", refundMoney=" + refundMoney +
                '}';
    }
}
