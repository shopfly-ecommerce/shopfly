/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.statistics.model.vo;

import dev.shopflix.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * 店铺概况，各项非图表数据
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018/5/11 20:17
 */
@ApiModel(value = "店铺概况VO")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ShopProfileVO {

    @ApiModelProperty(value = "下单金额")
    @Column(name = "order_money")
    private String orderMoney;

    @ApiModelProperty(value = "下单会员数")
    @Column(name = "order_member")
    private String orderMember;

    @ApiModelProperty(value = "下单量")
    @Column(name = "order_num")
    private String orderNum;

    @ApiModelProperty(value = "下单商品数")
    @Column(name = "order_goods")
    private String orderGoods;

    @ApiModelProperty(value = "平均客单价")
    @Column(name = "average_member_money")
    private String averageMemberMoney;

    @ApiModelProperty(value = "商品均价")
    @Column(name = "average_goods_money")
    private String averageGoodsMoney;

    @ApiModelProperty(value = "商品收藏量")
    @Column(name = "goods_collect")
    private String goodsCollect;

    @ApiModelProperty(value = "店铺商品总数")
    @Column(name = "total_goods")
    private String totalGoods;

    @ApiModelProperty(value = "下单高峰期")
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
