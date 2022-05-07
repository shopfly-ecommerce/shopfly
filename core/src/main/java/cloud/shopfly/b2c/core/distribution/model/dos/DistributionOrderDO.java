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
package cloud.shopfly.b2c.core.distribution.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Distribution of orders
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/21 In the morning11:13
 */
@Table(name = "es_distribution_order")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DistributionOrderDO {

    /**
     * A primary keyid
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;

    /**
     * Associated with ordinary ordersid
     */
    @Column(name = "order_id")
    @ApiModelProperty(value = "Associated with ordinary ordersid", required = true)
    private Integer orderId;

    /**
     * Order no.
     */
    @Column(name = "order_sn")
    @ApiModelProperty(value = "Order no.", required = true)
    private String orderSn;

    /**
     * Purchase membershipid
     */
    @Column(name = "buyer_member_id")
    @ApiModelProperty(value = "Purchase membershipid", required = true)
    private Integer buyerMemberId;

    /**
     * Purchase the name of the member
     */
    @Column(name = "buyer_member_name")
    @ApiModelProperty(value = "Purchase the name of the member", required = true)
    private String buyerMemberName;

    /**
     * 1Level distributorsid
     */
    @Column(name = "member_id_lv1")
    @ApiModelProperty(value = "1Level distributorsid", required = true)
    private Integer memberIdLv1;

    /**
     * 2Level distributorsid
     */
    @Column(name = "member_id_lv2")
    @ApiModelProperty(value = "2Level distributorsid", required = true)
    private Integer memberIdLv2;


    @Column(name = "lv1_point")
    @ApiModelProperty(value = "Cashback ratio of first-class template", required = true)
    private Double lv1Point;


    @Column(name = "lv2_point")
    @ApiModelProperty(value = "Cash back ratio of secondary templates", required = true)
    private Double lv2Point;

    @Column(name = "goods_rebate")
    @ApiModelProperty(value = "Cash back details", required = true)
    /**
     * see DistributionGoods.java
     * detail as list<DistributionGoods>
     */
    private String goodsRebate;


    /**
     * statementsid
     */
    @Column(name = "bill_id")
    @ApiModelProperty(value = "statementsid", required = true)
    private Integer billId;

    /**
     * Release date
     */
    @Column(name = "settle_cycle")
    @ApiModelProperty(value = "Release date", required = true)
    private Integer settleCycle;

    /**
     * Total
     */
    @Column(name = "order_price")
    @ApiModelProperty(value = "Total", required = true)
    private Double orderPrice;

    /**
     * Order Creation time
     */
    @Column(name = "create_time")
    @ApiModelProperty(value = "Order Creation time", required = true)
    private Long createTime;

    /**
     * 1Grade commission amount
     */
    @Column(name = "grade1_rebate")
    @ApiModelProperty(value = "1Grade commission amount", required = true)
    private Double grade1Rebate;

    /**
     * 2Grade commission amount
     */
    @Column(name = "grade2_rebate")
    @ApiModelProperty(value = "2Grade commission amount", required = true)
    private Double grade2Rebate;


    /**
     * 1Grade refund amount
     */
    @Column(name = "grade1_sellback_price")
    @ApiModelProperty(value = "1Grade refund amount", required = true)
    private Double grade1SellbackPrice;

    /**
     * 2Grade refund amount
     */
    @Column(name = "grade2_sellback_price")
    @ApiModelProperty(value = "2Grade refund amount", required = true)
    private Double grade2SellbackPrice;


    /**
     * Whether to return0=Did not return1=Have to return
     */
    @Column(name = "is_return")
    @ApiModelProperty(value = "Whether to return0=Did not return1=Have to return", required = true)
    private Integer isReturn;

    /**
     * The refund amount
     */
    @Column(name = "return_money")
    @ApiModelProperty(value = "The refund amount", required = true)
    private Double returnMoney;

    @Column(name = "is_withdraw")
    @ApiModelProperty(value = "Whether settlement0=No settlement1=Has been settled", required = true)
    private Integer isWithdraw;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getBuyerMemberId() {
        return buyerMemberId;
    }

    public void setBuyerMemberId(Integer buyerMemberId) {
        this.buyerMemberId = buyerMemberId;
    }

    public String getBuyerMemberName() {
        return buyerMemberName;
    }

    public void setBuyerMemberName(String buyerMemberName) {
        this.buyerMemberName = buyerMemberName;
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

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Integer getSettleCycle() {
        return settleCycle;
    }

    public void setSettleCycle(Integer settleCycle) {
        this.settleCycle = settleCycle;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Double getGrade1Rebate() {
        return grade1Rebate;
    }

    public void setGrade1Rebate(Double grade1Rebate) {
        this.grade1Rebate = grade1Rebate;
    }

    public Double getGrade2Rebate() {
        return grade2Rebate;
    }

    public void setGrade2Rebate(Double grade2Rebate) {
        this.grade2Rebate = grade2Rebate;
    }

    public Double getGrade1SellbackPrice() {
        return grade1SellbackPrice;
    }

    public void setGrade1SellbackPrice(Double grade1SellbackPrice) {
        this.grade1SellbackPrice = grade1SellbackPrice;
    }

    public Double getGrade2SellbackPrice() {
        return grade2SellbackPrice;
    }

    public void setGrade2SellbackPrice(Double grade2SellbackPrice) {
        this.grade2SellbackPrice = grade2SellbackPrice;
    }

    public Integer getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Integer isReturn) {
        this.isReturn = isReturn;
    }

    public Double getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Double returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Integer getIsWithdraw() {
        return isWithdraw;
    }

    public void setIsWithdraw(Integer isWithdraw) {
        this.isWithdraw = isWithdraw;
    }

    public Double getLv1Point() {
        return lv1Point;
    }

    public void setLv1Point(Double lv1Point) {
        this.lv1Point = lv1Point;
    }

    public Double getLv2Point() {
        return lv2Point;
    }

    public void setLv2Point(Double lv2Point) {
        this.lv2Point = lv2Point;
    }

    public String getGoodsRebate() {
        return goodsRebate;
    }

    public void setGoodsRebate(String goodsRebate) {
        this.goodsRebate = goodsRebate;
    }

    @Override
    public String toString() {
        return "DistributionOrderDO{" +
                "orderId=" + orderId +
                ", orderSn='" + orderSn + '\'' +
                ", buyerMemberId=" + buyerMemberId +
                ", buyerMemberName='" + buyerMemberName + '\'' +
                ", memberIdLv1=" + memberIdLv1 +
                ", memberIdLv2=" + memberIdLv2 +
                ", lv1Point=" + lv1Point +
                ", lv2Point=" + lv2Point +
                ", goodsRebate='" + goodsRebate + '\'' +
                ", billId=" + billId +
                ", orderPrice=" + orderPrice +
                ", grade1Rebate=" + grade1Rebate +
                ", grade2Rebate=" + grade2Rebate +
                ", grade1SellbackPrice=" + grade1SellbackPrice +
                ", grade2SellbackPrice=" + grade2SellbackPrice +
                ", isReturn=" + isReturn +
                ", returnMoney=" + returnMoney +
                ", isWithdraw=" + isWithdraw +
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

        DistributionOrderDO that = (DistributionOrderDO) o;

        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) {
            return false;
        }
        if (orderSn != null ? !orderSn.equals(that.orderSn) : that.orderSn != null) {
            return false;
        }
        if (buyerMemberId != null ? !buyerMemberId.equals(that.buyerMemberId) : that.buyerMemberId != null) {
            return false;
        }
        if (buyerMemberName != null ? !buyerMemberName.equals(that.buyerMemberName) : that.buyerMemberName != null) {
            return false;
        }
        if (memberIdLv1 != null ? !memberIdLv1.equals(that.memberIdLv1) : that.memberIdLv1 != null) {
            return false;
        }
        if (memberIdLv2 != null ? !memberIdLv2.equals(that.memberIdLv2) : that.memberIdLv2 != null) {
            return false;
        }
        if (billId != null ? !billId.equals(that.billId) : that.billId != null) {
            return false;
        }
        if (settleCycle != null ? !settleCycle.equals(that.settleCycle) : that.settleCycle != null) {
            return false;
        }
        if (orderPrice != null ? !orderPrice.equals(that.orderPrice) : that.orderPrice != null) {
            return false;
        }
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) {
            return false;
        }
        if (grade1Rebate != null ? !grade1Rebate.equals(that.grade1Rebate) : that.grade1Rebate != null) {
            return false;
        }
        if (grade2Rebate != null ? !grade2Rebate.equals(that.grade2Rebate) : that.grade2Rebate != null) {
            return false;
        }
        if (grade1SellbackPrice != null ? !grade1SellbackPrice.equals(that.grade1SellbackPrice) : that.grade1SellbackPrice != null) {
            return false;
        }
        if (grade2SellbackPrice != null ? !grade2SellbackPrice.equals(that.grade2SellbackPrice) : that.grade2SellbackPrice != null) {
            return false;
        }
        if (isReturn != null ? !isReturn.equals(that.isReturn) : that.isReturn != null) {
            return false;
        }
        if (returnMoney != null ? !returnMoney.equals(that.returnMoney) : that.returnMoney != null) {
            return false;
        }
        return isWithdraw != null ? isWithdraw.equals(that.isWithdraw) : that.isWithdraw == null;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (orderSn != null ? orderSn.hashCode() : 0);
        result = 31 * result + (buyerMemberId != null ? buyerMemberId.hashCode() : 0);
        result = 31 * result + (buyerMemberName != null ? buyerMemberName.hashCode() : 0);
        result = 31 * result + (memberIdLv1 != null ? memberIdLv1.hashCode() : 0);
        result = 31 * result + (memberIdLv2 != null ? memberIdLv2.hashCode() : 0);
        result = 31 * result + (billId != null ? billId.hashCode() : 0);
        result = 31 * result + (settleCycle != null ? settleCycle.hashCode() : 0);
        result = 31 * result + (orderPrice != null ? orderPrice.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (grade1Rebate != null ? grade1Rebate.hashCode() : 0);
        result = 31 * result + (grade2Rebate != null ? grade2Rebate.hashCode() : 0);
        result = 31 * result + (grade1SellbackPrice != null ? grade1SellbackPrice.hashCode() : 0);
        result = 31 * result + (grade2SellbackPrice != null ? grade2SellbackPrice.hashCode() : 0);
        result = 31 * result + (isReturn != null ? isReturn.hashCode() : 0);
        result = 31 * result + (returnMoney != null ? returnMoney.hashCode() : 0);
        result = 31 * result + (isWithdraw != null ? isWithdraw.hashCode() : 0);
        return result;
    }
}
