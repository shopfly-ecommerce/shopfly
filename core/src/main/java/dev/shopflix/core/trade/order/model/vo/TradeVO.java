/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.model.vo;

import dev.shopflix.core.trade.cart.model.vo.CouponVO;
import dev.shopflix.core.trade.cart.model.vo.PriceDetailVO;
import dev.shopflix.core.trade.order.model.dto.OrderDTO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 交易VO
 *
 * @author Snow create in 2018/4/9
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TradeVO implements Serializable {

    private static final long serialVersionUID = -8580648928412433120L;

    @ApiModelProperty(name = "trade_sn", value = "交易编号")
    private String tradeSn;

    @ApiModelProperty(value = "会员id")
    private Integer memberId;

    @ApiModelProperty(value = "会员昵称")
    private String memberName;

    @ApiModelProperty(value = "支付方式")
    private String paymentType;

    @ApiModelProperty(value = "价格信息")
    private PriceDetailVO priceDetail;

    @ApiModelProperty(value = "收货人信息")
    private ConsigneeVO consignee;

    @ApiModelProperty(value = "优惠券集合")
    private List<CouponVO> couponList;

    @ApiModelProperty(value = "订单集合")
    private List<OrderDTO> orderList;

    @ApiModelProperty(value = "赠品集合")
    private List<GiftVO> giftList;


    public String getTradeSn() {
        return tradeSn;
    }

    public void setTradeSn(String tradeSn) {
        this.tradeSn = tradeSn;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public PriceDetailVO getPriceDetail() {
        return priceDetail;
    }

    public void setPriceDetail(PriceDetailVO priceDetail) {
        this.priceDetail = priceDetail;
    }

    public ConsigneeVO getConsignee() {
        return consignee;
    }

    public void setConsignee(ConsigneeVO consignee) {
        this.consignee = consignee;
    }

    public List<CouponVO> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponVO> couponList) {
        this.couponList = couponList;
    }

    public List<OrderDTO> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderDTO> orderList) {
        this.orderList = orderList;
    }

    public List<GiftVO> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<GiftVO> giftList) {
        this.giftList = giftList;
    }

    @Override
    public String toString() {
        return "TradeVO{" +
                "tradeSn='" + tradeSn + '\'' +
                ", memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", priceDetail=" + priceDetail +
                ", consignee=" + consignee +
                ", couponList=" + couponList +
                ", orderList=" + orderList +
                ", giftList=" + giftList +
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

        TradeVO tradeVO = (TradeVO) o;

        return new EqualsBuilder()
                .append(tradeSn, tradeVO.tradeSn)
                .append(memberId, tradeVO.memberId)
                .append(memberName, tradeVO.memberName)
                .append(paymentType, tradeVO.paymentType)
                .append(priceDetail, tradeVO.priceDetail)
                .append(consignee, tradeVO.consignee)
                .append(couponList, tradeVO.couponList)
                .append(orderList, tradeVO.orderList)
                .append(giftList, tradeVO.giftList)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(tradeSn)
                .append(memberId)
                .append(memberName)
                .append(paymentType)
                .append(priceDetail)
                .append(consignee)
                .append(couponList)
                .append(orderList)
                .append(giftList)
                .toHashCode();
    }
}
