/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.distribution.model.vo;

import cloud.shopfly.b2c.core.distribution.model.dos.DistributionOrderDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分销订单
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-05-23 下午2:33
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DistributionOrderVO {

    @ApiModelProperty("订单编号")
    private String sn;
    @ApiModelProperty(value = "购买会员名称", name = "member_name")
    private String memberName;
    private Double price;
    @ApiModelProperty(value = "订单金额", name = "order_price")
    private Double orderPrice;
    @ApiModelProperty(value = "下单时间", name = "create_time")
    private Long createTime;

    public DistributionOrderVO(DistributionOrderDO distributionOrderDO, Integer memberId) {
        this.sn = distributionOrderDO.getOrderSn();
        this.memberName = distributionOrderDO.getBuyerMemberName();
        if (memberId.equals(distributionOrderDO.getMemberIdLv1())) {
            this.price = distributionOrderDO.getGrade1Rebate();
        } else {
            this.price = distributionOrderDO.getGrade2Rebate();
        }
        this.orderPrice = distributionOrderDO.getOrderPrice();
        this.createTime = distributionOrderDO.getCreateTime();
    }


    public DistributionOrderVO() {

    }


    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }


    @Override
    public String toString() {
        return "DistributionOrderVO{" +
                "sn='" + sn + '\'' +
                ", memberName='" + memberName + '\'' +
                ", price=" + price +
                ", orderPrice=" + orderPrice +
                ", createTime=" + createTime +
                '}';
    }
}
