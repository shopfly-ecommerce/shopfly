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
public class DistributionSellbackOrderVO {

    @ApiModelProperty(value = "订单编号", name = "sn")
    private String sn;

    @ApiModelProperty(value = "购买会员名称", name = "member_name")
    private String memberName;

    @ApiModelProperty("退还返利金额")
    private Double price;

    @ApiModelProperty(value = "退款金额", name = "orderPrice")
    private Double orderPrice;

    @ApiModelProperty("下单时间")
    private Long createTime;

    public DistributionSellbackOrderVO(DistributionOrderDO distributionOrderDO, Integer memberId) {
        this.sn = distributionOrderDO.getOrderSn();
        this.memberName = distributionOrderDO.getBuyerMemberName();
        if (distributionOrderDO.getMemberIdLv1() != null && memberId.equals(distributionOrderDO.getMemberIdLv1())) {
            this.price = distributionOrderDO.getGrade1SellbackPrice();
        } else {
            this.price = distributionOrderDO.getGrade2SellbackPrice();
        }
        this.orderPrice = distributionOrderDO.getOrderPrice();
        this.createTime = distributionOrderDO.getCreateTime();
    }


    public DistributionSellbackOrderVO() {

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
        return "DistributionSellbackOrderVO{" +
                "sn='" + sn + '\'' +
                ", memberName='" + memberName + '\'' +
                ", price=" + price +
                ", orderPrice=" + orderPrice +
                ", createTIme=" + createTime +
                '}';
    }
}
