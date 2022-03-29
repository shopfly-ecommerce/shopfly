/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.order.model.vo;

import com.enation.app.javashop.core.trade.order.model.enums.OrderStatusEnum;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 订单流程图对象
 *
 * @author Snow create in 2018/6/25
 * @version v2.0
 * @since v7.0.0
 */
public class OrderFlowNode implements Serializable {

    @ApiModelProperty(value = "文字")
    private String text;

    @ApiModelProperty(value = "订单状态")
    private String orderStatus;

    @ApiModelProperty(value = "展示效果", allowableValues = "0,1,2,3", example = "0:灰色,1:普通显示,2:结束显示,3:取消显示")
    private Integer showStatus;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }

    @Override
    public String toString() {
        return "OrderOperateFlow{" +
                "text='" + text + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", showStatus=" + showStatus +
                '}';
    }


    public OrderFlowNode() {

    }

    public OrderFlowNode(OrderStatusEnum orderStatus) {
        this.text = orderStatus.description();
        this.orderStatus = orderStatus.value();
        this.showStatus = 1;
    }

}
