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
package cloud.shopfly.b2c.core.aftersale.model.vo;

import cloud.shopfly.b2c.core.trade.order.model.vo.OrderDetailVO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author zjp
 * @version v7.0
 * @Description After-sale application parametersVO
 * @ClassName RefundApplyVO
 * @since v7.0 In the morning11:28 2018/7/4
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RefundApplyVO {

    @ApiModelProperty(name = "original_way", value = "Whether to support the original way back")
    private String originalWay;

    @ApiModelProperty(name = "return_money", value = "The refund amount")
    private Double returnMoney;

    @ApiModelProperty(name = "return_point", value = "Refund integral")
    private Integer returnPoint;

    @ApiModelProperty(name = "order", value = "The order details")
    private OrderDetailVO order;

    @ApiModelProperty(name = "sku_list", value = "The item list")
    private List<RefundSkuVO> skuList;

    public String getOriginalWay() {
        return originalWay;
    }

    public void setOriginalWay(String originalWay) {
        this.originalWay = originalWay;
    }

    public Double getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Double returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Integer getReturnPoint() {
        return returnPoint;
    }

    public void setReturnPoint(Integer returnPoint) {
        this.returnPoint = returnPoint;
    }

    public OrderDetailVO getOrder() {
        return order;
    }

    public void setOrder(OrderDetailVO order) {
        this.order = order;
    }

    public List<RefundSkuVO> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<RefundSkuVO> skuList) {
        this.skuList = skuList;
    }

    @Override
    public String toString() {
        return "RefundApplyVO{" +
                "originalWay='" + originalWay + '\'' +
                ", returnMoney=" + returnMoney +
                ", returnPoint=" + returnPoint +
                ", order=" + order +
                ", skuList=" + skuList +
                '}';
    }
}
