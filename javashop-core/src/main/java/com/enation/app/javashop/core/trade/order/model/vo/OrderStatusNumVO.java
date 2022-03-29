/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.order.model.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * 订单各个状态的订单数
 * <br/>所有状态查看 OrderTagEnum
 *
 * @author Snow create in 2018/6/14
 * @version v2.0
 * @since v7.0.0
 */
public class OrderStatusNumVO implements Serializable {


    @ApiModelProperty(value = "所有订单数")
    private Integer allNum;

    @ApiModelProperty(value = "待付款订单数")
    private Integer waitPayNum;

    @ApiModelProperty(value = "待发货订单数")
    private Integer waitShipNum;

    @ApiModelProperty(value = "待收货订单数")
    private Integer waitRogNum;

    @ApiModelProperty(value = "已取消订单数")
    private Integer cancelNum;

    @ApiModelProperty(value = "已完成订单数")
    private Integer completeNum;

    @ApiModelProperty(value = "待评论订单数")
    private Integer waitCommentNum;

    @ApiModelProperty(value = "售后中订单数")
    private Integer refundNum;

    public Integer getAllNum() {
        return allNum;
    }

    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
    }

    public Integer getWaitPayNum() {
        return waitPayNum;
    }

    public void setWaitPayNum(Integer waitPayNum) {
        this.waitPayNum = waitPayNum;
    }

    public Integer getWaitShipNum() {
        return waitShipNum;
    }

    public void setWaitShipNum(Integer waitShipNum) {
        this.waitShipNum = waitShipNum;
    }

    public Integer getWaitRogNum() {
        return waitRogNum;
    }

    public void setWaitRogNum(Integer waitRogNum) {
        this.waitRogNum = waitRogNum;
    }

    public Integer getCancelNum() {
        return cancelNum;
    }

    public void setCancelNum(Integer cancelNum) {
        this.cancelNum = cancelNum;
    }

    public Integer getCompleteNum() {
        return completeNum;
    }

    public void setCompleteNum(Integer completeNum) {
        this.completeNum = completeNum;
    }

    public Integer getWaitCommentNum() {
        return waitCommentNum;
    }

    public void setWaitCommentNum(Integer waitCommentNum) {
        this.waitCommentNum = waitCommentNum;
    }

    public Integer getRefundNum() {
        return refundNum;
    }

    public void setRefundNum(Integer refundNum) {
        this.refundNum = refundNum;
    }

    @Override
    public String toString() {
        return "OrderStatusNumVO{" +
                "allNum=" + allNum +
                ", waitPayNum=" + waitPayNum +
                ", waitShipNum=" + waitShipNum +
                ", waitRogNum=" + waitRogNum +
                ", cancelNum=" + cancelNum +
                ", completeNum=" + completeNum +
                ", waitCommentNum=" + waitCommentNum +
                ", refundNum=" + refundNum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderStatusNumVO that = (OrderStatusNumVO) o;
        return Objects.equals(allNum, that.allNum) &&
                Objects.equals(waitPayNum, that.waitPayNum) &&
                Objects.equals(waitShipNum, that.waitShipNum) &&
                Objects.equals(waitRogNum, that.waitRogNum) &&
                Objects.equals(cancelNum, that.cancelNum) &&
                Objects.equals(completeNum, that.completeNum) &&
                Objects.equals(waitCommentNum, that.waitCommentNum) &&
                Objects.equals(refundNum, that.refundNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(allNum,
                waitPayNum,
                waitShipNum,
                waitRogNum,
                cancelNum,
                completeNum,
                waitCommentNum,
                refundNum);
    }
}
