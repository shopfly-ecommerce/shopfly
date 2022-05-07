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
package cloud.shopfly.b2c.core.trade.cart.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Portfolio goods activity model
 *
 * @author Snow
 * @version v1.0
 * 2017years08month23day16:06:48
 * @since v6.4
 */
@SuppressWarnings("AlibabaPojoMustOverrideToString")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GroupPromotionVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3188594102818543304L;

    /**
     * Promotional tool type storePromotionTypeEnum.XXX.getType();
     */
    @ApiModelProperty(value = "Type of promotional tools")
    private String promotionType;

    /**
     * Store corresponding tools according to the above active tool typeVo<br>
     * For example,：The type above isgroupbuy, then the value here isGroupbuyVo
     */
    @ApiModelProperty(value = "Event details")
    private Object activityDetail;

    @ApiModelProperty(value = "Goods collection")
    private List<CartSkuVO> skuList;

    /**
     * Merchant price subtotal= The sum of the small sums in a collection of goods.
     */
    @ApiModelProperty(value = "Commodity prices subtotal")
    private Double subtotal;


    @ApiModelProperty(value = "Is it a combined activity,1In order to combine activities,2For single product activities")
    private Integer isGroup;

    @ApiModelProperty(value = "The difference")
    private Double spreadPrice;

    @ApiModelProperty(value = "Discount amount")
    private Double discountPrice;

    /**
     * The shopping cart page-Whether full discount is selected
     * 1For the selected
     */
    @ApiModelProperty(value = "Activity selected.1For the selected,0uncheck")
    private Integer checked;


    @Override
    public String toString() {
        return "GroupPromotionVO{" +
                "promotionType='" + promotionType + '\'' +
                ", activityDetail=" + activityDetail +
                ", skuList=" + skuList +
                ", subtotal=" + subtotal +
                ", isGroup=" + isGroup +
                ", spreadPrice=" + spreadPrice +
                ", discountPrice=" + discountPrice +
                ", checked=" + checked +
                '}';
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public Object getActivityDetail() {
        return activityDetail;
    }

    public void setActivityDetail(Object activityDetail) {
        this.activityDetail = activityDetail;
    }

    public List<CartSkuVO> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<CartSkuVO> skuList) {
        this.skuList = skuList;
    }

    public Double getSubtotal() {
        if (subtotal == null) {
            subtotal = 0.0;
        }
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Integer isGroup) {
        this.isGroup = isGroup;
    }

    public Double getSpreadPrice() {
        return spreadPrice;
    }

    public void setSpreadPrice(Double spreadPrice) {
        this.spreadPrice = spreadPrice;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GroupPromotionVO that = (GroupPromotionVO) o;

        return new EqualsBuilder()
                .append(promotionType, that.promotionType)
                .append(activityDetail, that.activityDetail)
                .append(skuList, that.skuList)
                .append(subtotal, that.subtotal)
                .append(isGroup, that.isGroup)
                .append(spreadPrice, that.spreadPrice)
                .append(discountPrice, that.discountPrice)
                .append(checked, that.checked)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(promotionType)
                .append(activityDetail)
                .append(skuList)
                .append(subtotal)
                .append(isGroup)
                .append(spreadPrice)
                .append(discountPrice)
                .append(checked)
                .toHashCode();
    }
}
