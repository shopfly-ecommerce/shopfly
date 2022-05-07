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
 * <<<<<<< HEAD
 * Management end home page statistics
 * =======
 * Business center, home page statistics
 * >>>>>>> master
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018/6/25 10:19
 */
@ApiModel("Home page statistics")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ShopDashboardVO {

    @ApiModelProperty(value = "Goods on sale")
    @Column(name = "market_goods")
    private String marketGoods;

    @ApiModelProperty(value = "Merchandise waiting to hit the shelves")
    @Column(name = "pending_goods")
    private String pendingGoods;

    @ApiModelProperty(value = "Buyer inquiries to be processed")
    @Column(name = "pending_member_ask")
    private String pendingMemberAsk;

    @ApiModelProperty(value = "All orders")
    @Column(name = "all_order_num")
    private String allOrdersNum;

    @ApiModelProperty(value = "Order number to be paid")
    @Column(name = "wait_pay_order_num")
    private String waitPayOrderNum;

    @ApiModelProperty(value = "Order number of goods to be delivered")
    @Column(name = "wait_ship_order_num")
    private String waitShipOrderNum;

    @ApiModelProperty(value = "Backlog order number")
    @Column(name = "wait_delivery_order_num")
    private String waitDeliveryOrderNum;

    @ApiModelProperty(value = "Number of after-sale orders to be processed")
    @Column(name = "after_sale_order_num")
    private String afterSaleOrderNum;

    public String getMarketGoods() {
        return marketGoods;
    }

    public void setMarketGoods(String marketGoods) {
        this.marketGoods = marketGoods;
    }

    public String getPendingGoods() {
        return pendingGoods;
    }

    public void setPendingGoods(String pendingGoods) {
        this.pendingGoods = pendingGoods;
    }

    public String getPendingMemberAsk() {
        return pendingMemberAsk;
    }

    public void setPendingMemberAsk(String buyerMessage) {
        this.pendingMemberAsk = buyerMessage;
    }

    public String getAllOrdersNum() {
        return allOrdersNum;
    }

    public void setAllOrdersNum(String allOrdersNum) {
        this.allOrdersNum = allOrdersNum;
    }

    public String getWaitPayOrderNum() {
        return waitPayOrderNum;
    }

    public void setWaitPayOrderNum(String waitPayOrderNum) {
        this.waitPayOrderNum = waitPayOrderNum;
    }

    public String getWaitShipOrderNum() {
        return waitShipOrderNum;
    }

    public void setWaitShipOrderNum(String waitShipOrderNum) {
        this.waitShipOrderNum = waitShipOrderNum;
    }

    public String getWaitDeliveryOrderNum() {
        return waitDeliveryOrderNum;
    }

    public void setWaitDeliveryOrderNum(String waitDeliveryOrderNum) {
        this.waitDeliveryOrderNum = waitDeliveryOrderNum;
    }

    public String getAfterSaleOrderNum() {
        return afterSaleOrderNum;
    }

    public void setAftersaleOrderNum(String afterSaleOrderNum) {
        this.afterSaleOrderNum = afterSaleOrderNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShopDashboardVO that = (ShopDashboardVO) o;
        return Objects.equals(marketGoods, that.marketGoods) &&
                Objects.equals(pendingGoods, that.pendingGoods) &&
                Objects.equals(pendingMemberAsk, that.pendingMemberAsk) &&
                Objects.equals(allOrdersNum, that.allOrdersNum) &&
                Objects.equals(waitPayOrderNum, that.waitPayOrderNum) &&
                Objects.equals(waitShipOrderNum, that.waitShipOrderNum) &&
                Objects.equals(waitDeliveryOrderNum, that.waitDeliveryOrderNum) &&
                Objects.equals(afterSaleOrderNum, that.afterSaleOrderNum);
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                marketGoods,
                pendingGoods,
                pendingMemberAsk,
                allOrdersNum,
                waitPayOrderNum,
                waitShipOrderNum,
                waitDeliveryOrderNum,
                afterSaleOrderNum);

    }

    @Override
    public String toString() {
        return "ShopDashboardVO{" +
                "marketGoods='" + marketGoods + '\'' +
                ", pendingGoods='" + pendingGoods + '\'' +
                ", buyerMessage='" + pendingMemberAsk + '\'' +
                ", allOrdersNum='" + allOrdersNum + '\'' +
                ", waitPayOrderNum='" + waitPayOrderNum + '\'' +
                ", waitShipOrderNum='" + waitShipOrderNum + '\'' +
                ", waitDeliveryOrderNum='" + waitDeliveryOrderNum + '\'' +
                ", afterSaleOrderNum='" + afterSaleOrderNum + '\'' +
                '}';
    }
}
