/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.order.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 订单查询参数DTO
 *
 * @author Snow create in 2018/5/14
 * @version v2.0
 * @since v7.0.0
 */
@ApiIgnore
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderQueryParam {

    @ApiModelProperty(value = "第几页")
    private Integer pageNo;

    @ApiModelProperty(value = "每页条数")
    private Integer pageSize;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(value = "交易编号")
    private String tradeSn;

    /**
     * 例如：所有订单，待付款，待发货 等等
     *
     * @link OrderTagEnum
     */
    @ApiModelProperty(value = "页面标签",
            example = "ALL:所有订单,WAIT_PAY:待付款,WAIT_SHIP:待发货,WAIT_ROG:待收货," +
                    "CANCELLED:已取消,COMPLETE:已完成,WAIT_COMMENT:待评论,REFUND:售后中")
    private String tag;

    @ApiModelProperty(value = "商家ID")
    private Integer sellerId;

    @ApiModelProperty(value = "会员ID")
    private Integer memberId;

    @ApiModelProperty(value = "收货人")
    private String shipName;

    @ApiModelProperty(value = "开始时间")
    private Long startTime;

    @ApiModelProperty(value = "起止时间")
    private Long endTime;

    @ApiModelProperty(value = "买家昵称")
    private String buyerName;

    @ApiModelProperty(value = "订单状态")
    private String orderStatus;

    @ApiModelProperty(value = "付款状态")
    private String payStatus;

    /**
     * 可以查询 订单编号 买家 商品名称
     */
    @ApiModelProperty(value = "关键字")
    private String keywords;


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getTradeSn() {
        return tradeSn;
    }

    public void setTradeSn(String tradeSn) {
        this.tradeSn = tradeSn;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderQueryParam that = (OrderQueryParam) o;

        return new EqualsBuilder()
                .append(pageNo, that.pageNo)
                .append(pageSize, that.pageSize)
                .append(goodsName, that.goodsName)
                .append(orderSn, that.orderSn)
                .append(tradeSn, that.tradeSn)
                .append(tag, that.tag)
                .append(sellerId, that.sellerId)
                .append(memberId, that.memberId)
                .append(shipName, that.shipName)
                .append(startTime, that.startTime)
                .append(endTime, that.endTime)
                .append(buyerName, that.buyerName)
                .append(orderStatus, that.orderStatus)
                .append(payStatus, that.payStatus)
                .append(keywords, that.keywords)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(pageNo)
                .append(pageSize)
                .append(goodsName)
                .append(orderSn)
                .append(tradeSn)
                .append(tag)
                .append(sellerId)
                .append(memberId)
                .append(shipName)
                .append(startTime)
                .append(endTime)
                .append(buyerName)
                .append(orderStatus)
                .append(payStatus)
                .append(keywords)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "OrderQueryParam{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", goodsName='" + goodsName + '\'' +
                ", orderSn='" + orderSn + '\'' +
                ", tradeSn='" + tradeSn + '\'' +
                ", tag='" + tag + '\'' +
                ", sellerId=" + sellerId +
                ", memberId=" + memberId +
                ", shipName='" + shipName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", buyerName='" + buyerName + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", keywords='" + keywords + '\'' +
                '}';
    }
}
