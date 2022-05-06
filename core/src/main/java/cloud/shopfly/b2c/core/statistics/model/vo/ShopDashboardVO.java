/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 管理端首页统计数据
 * =======
 * 商家中心，首页统计数据
 * >>>>>>> master
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018/6/25 10:19
 */
@ApiModel("首页统计数据")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ShopDashboardVO {

    @ApiModelProperty(value = "出售中的商品")
    @Column(name = "market_goods")
    private String marketGoods;

    @ApiModelProperty(value = "待上架的商品")
    @Column(name = "pending_goods")
    private String pendingGoods;

    @ApiModelProperty(value = "待处理买家咨询")
    @Column(name = "pending_member_ask")
    private String pendingMemberAsk;

    @ApiModelProperty(value = "所有订单数")
    @Column(name = "all_order_num")
    private String allOrdersNum;

    @ApiModelProperty(value = "待付款订单数")
    @Column(name = "wait_pay_order_num")
    private String waitPayOrderNum;

    @ApiModelProperty(value = "待发货订单数")
    @Column(name = "wait_ship_order_num")
    private String waitShipOrderNum;

    @ApiModelProperty(value = "待收货订单数")
    @Column(name = "wait_delivery_order_num")
    private String waitDeliveryOrderNum;

    @ApiModelProperty(value = "待处理售后订单数")
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
