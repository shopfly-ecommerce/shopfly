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
package cloud.shopfly.b2c.core.promotion.fulldiscount.model.vo;

import cloud.shopfly.b2c.core.promotion.coupon.model.dos.CouponDO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountDO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionGoodsDTO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Full discountVO
 *
 * @author Snow
 * @version v1.0
 * @since v7.0.0
 * 2018years4month17On the afternoon2:46:27
 */
@SuppressWarnings("AlibabaPojoMustOverrideToString")
@ApiModel(description = "Full discountVO")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FullDiscountVO extends FullDiscountDO implements Serializable {

    public FullDiscountVO() {
        this.goodsIdList = new ArrayList<>();
    }

    private static final long serialVersionUID = -5509785605653051033L;

    @ApiModelProperty(name = "goods_list", value = "List of promotional items")
    private List<PromotionGoodsDTO> goodsList;

    /**
     * productidThe list of
     */
    private List<Integer> goodsIdList;

    @ApiModelProperty(name = "status_text", value = "Active state")
    private String statusText;

    @ApiModelProperty(value = "The gifts")
    private FullDiscountGiftDO fullDiscountGiftDO;

    @ApiModelProperty(value = "A gift coupon")
    private CouponDO couponDO;


    @ApiModelProperty(value = "Give optimal integral")
    private Integer point;

    @ApiModelProperty(name = "status", value = "Activity status identification,expiredIndicates invalid")
    private String status;


    public List<PromotionGoodsDTO> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<PromotionGoodsDTO> goodsList) {
        this.goodsList = goodsList;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public FullDiscountGiftDO getFullDiscountGiftDO() {
        return fullDiscountGiftDO;
    }

    public void setFullDiscountGiftDO(FullDiscountGiftDO fullDiscountGiftDO) {
        this.fullDiscountGiftDO = fullDiscountGiftDO;
    }

    public CouponDO getCouponDO() {
        return couponDO;
    }

    public void setCouponDO(CouponDO couponDO) {
        this.couponDO = couponDO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public List<Integer> getGoodsIdList() {
        return goodsIdList;
    }

    public void setGoodsIdList(List<Integer> goodsIdList) {
        this.goodsIdList = goodsIdList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FullDiscountVO that = (FullDiscountVO) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(goodsList, that.goodsList)
                .append(goodsIdList, that.goodsIdList)
                .append(statusText, that.statusText)
                .append(fullDiscountGiftDO, that.fullDiscountGiftDO)
                .append(couponDO, that.couponDO)
                .append(point, that.point)
                .append(status, that.status)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(goodsList)
                .append(goodsIdList)
                .append(statusText)
                .append(fullDiscountGiftDO)
                .append(couponDO)
                .append(point)
                .append(status)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "FullDiscountVO{" +
                "goodsList=" + goodsList +
                ", goodsIdList=" + goodsIdList +
                ", statusText='" + statusText + '\'' +
                ", fullDiscountGiftDO=" + fullDiscountGiftDO +
                ", couponDO=" + couponDO +
                ", point=" + point +
                ", status='" + status + '\'' +
                "} " + super.toString();
    }
}
