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
package cloud.shopfly.b2c.core.statistics.model.vo;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * Store overview, all non-chart data
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018/5/11 20:17
 */
@ApiModel(value = "Store profileVO")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ShopProfileVO {

    @ApiModelProperty(value = "Place the order amount")
    @Column(name = "order_money")
    private String orderMoney;

    @ApiModelProperty(value = "Number of single members")
    @Column(name = "order_member")
    private String orderMember;

    @ApiModelProperty(value = "Order quantity")
    @Column(name = "order_num")
    private String orderNum;

    @ApiModelProperty(value = "Number of items ordered")
    @Column(name = "order_goods")
    private String orderGoods;

    @ApiModelProperty(value = "Average customer unit price")
    @Column(name = "average_member_money")
    private String averageMemberMoney;

    @ApiModelProperty(value = "Commodity price")
    @Column(name = "average_goods_money")
    private String averageGoodsMoney;

    @ApiModelProperty(value = "Merchandise collection")
    @Column(name = "goods_collect")
    private String goodsCollect;

    @ApiModelProperty(value = "Total store merchandise")
    @Column(name = "total_goods")
    private String totalGoods;

    @ApiModelProperty(value = "Rush hour")
    @Column(name = "order_fastigium")
    private String orderFastigium;

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getOrderMember() {
        return orderMember;
    }

    public void setOrderMember(String orderMember) {
        this.orderMember = orderMember;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderGoods() {
        return orderGoods;
    }

    public void setOrderGoods(String orderGoods) {
        this.orderGoods = orderGoods;
    }

    public String getAverageMemberMoney() {
        return averageMemberMoney;
    }

    public void setAverageMemberMoney(String averageMemberMoney) {
        this.averageMemberMoney = averageMemberMoney;
    }

    public String getAverageGoodsMoney() {
        return averageGoodsMoney;
    }

    public void setAverageGoodsMoney(String averageGoodsMoney) {
        this.averageGoodsMoney = averageGoodsMoney;
    }

    public String getGoodsCollect() {
        return goodsCollect;
    }

    public void setGoodsCollect(String goodsCollect) {
        this.goodsCollect = goodsCollect;
    }

    public String getTotalGoods() {
        return totalGoods;
    }

    public void setTotalGoods(String totalGoods) {
        this.totalGoods = totalGoods;
    }

    public String getOrderFastigium() {
        return orderFastigium;
    }

    public void setOrderFastigium(String orderFastigium) {
        this.orderFastigium = orderFastigium;
    }

    @Override
    public String toString() {
        return "ShopProfileVO{" +
                "orderMoney=" + orderMoney +
                ", orderMember=" + orderMember +
                ", orderNum=" + orderNum +
                ", orderGoods=" + orderGoods +
                ", averageMemberMoney=" + averageMemberMoney +
                ", averageGoodsMoney=" + averageGoodsMoney +
                ", goodsCollect=" + goodsCollect +
                ", totalGoods=" + totalGoods +
                ", orderFastigium=" + orderFastigium +
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
        ShopProfileVO that = (ShopProfileVO) o;
        return Objects.equals(orderMoney, that.orderMoney) &&
                Objects.equals(orderMember, that.orderMember) &&
                Objects.equals(orderNum, that.orderNum) &&
                Objects.equals(orderGoods, that.orderGoods) &&
                Objects.equals(averageMemberMoney, that.averageMemberMoney) &&
                Objects.equals(averageGoodsMoney, that.averageGoodsMoney) &&
                Objects.equals(goodsCollect, that.goodsCollect) &&
                Objects.equals(totalGoods, that.totalGoods) &&
                Objects.equals(orderFastigium, that.orderFastigium);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderMoney, orderMember, orderNum, orderGoods,
                averageMemberMoney, averageGoodsMoney, goodsCollect,
                totalGoods, orderFastigium);
    }
}
