/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.aftersale.model.vo;

import cloud.shopfly.b2c.core.aftersale.model.enums.RefundStatusEnum;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundWayEnum;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import io.swagger.annotations.ApiModelProperty;

/**
 * @version v7.0
 * @Description: 退款单导出excelVO
 * @author: zjp
 * @Date: 2018/7/23 14:14
 */
public class ExportRefundExcelVO {

    /**
     * 退(货)款单id
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * 退货(款)单编号
     */
    @Column(name = "sn")
    @ApiModelProperty(name = "sn", value = "退货(款)单编号", required = false)
    private String sn;
    /**
     * 会员名称
     */
    @Column(name = "member_name")
    @ApiModelProperty(value = "会员名称")
    private String memberName;

    /**
     * 订单编号
     */
    @Column(name = "order_sn")
    @ApiModelProperty(name = "order_sn", value = "订单编号", required = false)
    private String orderSn;
    /**
     * 退(货)款状态
     */
    @Column(name = "refund_status")
    @ApiModelProperty(name = "refund_status", value = "退(货)款状态", required = false)
    private String refundStatus;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "创建时间", required = false)
    private Long createTime;
    /**
     * 退款金额
     */
    @Column(name = "refund_price")
    @ApiModelProperty(name = "refund_price", value = "退款金额", required = false)
    private Double refundPrice;

    /**
     * 退款方式(原路退回，在线支付，线下支付)
     */
    @Column(name = "refund_way")
    @ApiModelProperty(name = "refund_way", value = "退款方式(原路退回，线下支付)", required = false)
    private String refundWay;
    @Column(name = "refund_time")
    @ApiModelProperty(name = "refund_time", value = "退款时间", hidden = true)
    private Long refundTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getRefundStatus() {
        return RefundStatusEnum.valueOf(refundStatus).description();
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Double getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(Double refundPrice) {
        this.refundPrice = refundPrice;
    }

    public String getRefundWay() {
        return RefundWayEnum.valueOf(refundWay).description();
    }

    public void setRefundWay(String refundWay) {
        this.refundWay = refundWay;
    }

    public Long getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Long refundTime) {
        this.refundTime = refundTime;
    }

    @Override
    public String toString() {
        return "ExportRefundExcelVO{" +
                "id=" + id +
                ", sn='" + sn + '\'' +
                ", memberName='" + memberName + '\'' +
                ", orderSn='" + orderSn + '\'' +
                ", refundStatus='" + refundStatus + '\'' +
                ", createTime=" + createTime +
                ", refundPrice=" + refundPrice +
                ", refundWay='" + refundWay + '\'' +
                ", refundTime=" + refundTime +
                '}';
    }
}
