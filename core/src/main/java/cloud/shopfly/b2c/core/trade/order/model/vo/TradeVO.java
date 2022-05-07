/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.trade.order.model.vo;

import cloud.shopfly.b2c.core.trade.cart.model.vo.CouponVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PriceDetailVO;
import cloud.shopfly.b2c.core.trade.order.model.dto.OrderDTO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * tradingVO
 *
 * @author Snow create in 2018/4/9
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TradeVO implements Serializable {

    private static final long serialVersionUID = -8580648928412433120L;

    @ApiModelProperty(name = "trade_sn", value = "Transaction number")
    private String tradeSn;

    @ApiModelProperty(value = "membersid")
    private Integer memberId;

    @ApiModelProperty(value = "Members nickname")
    private String memberName;

    @ApiModelProperty(value = "Method of payment")
    private String paymentType;

    @ApiModelProperty(value = "Price information")
    private PriceDetailVO priceDetail;

    @ApiModelProperty(value = "Consignee information")
    private ConsigneeVO consignee;

    @ApiModelProperty(value = "Coupon collection")
    private List<CouponVO> couponList;

    @ApiModelProperty(value = "Orders for collection")
    private List<OrderDTO> orderList;

    @ApiModelProperty(value = "Gift set")
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
