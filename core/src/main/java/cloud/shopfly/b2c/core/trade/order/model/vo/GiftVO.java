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
package cloud.shopfly.b2c.core.trade.order.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * The giftsVO
 *
 * @author Snow create in 2018/4/9
 * @version v2.0
 * @since v7.0.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GiftVO implements Serializable {

    @ApiModelProperty(value = "The giftsID")
    private Integer giftId;

    @ApiModelProperty(value = "merchantsID")
    private Integer sellerId;

    @ApiModelProperty(value = "Name of gift")
    private String giftName;

    @ApiModelProperty(value = "Quantity")
    private Integer giftNum;


    public Integer getGiftId() {
        return giftId;
    }

    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public Integer getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(Integer giftNum) {
        this.giftNum = giftNum;
    }

    @Override
    public String toString() {
        return "GiftVO{" +
                "giftId=" + giftId +
                ", sellerId=" + sellerId +
                ", giftName='" + giftName + '\'' +
                ", giftNum=" + giftNum +
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

        GiftVO giftVO = (GiftVO) o;

        return new EqualsBuilder()
                .append(giftId, giftVO.giftId)
                .append(sellerId, giftVO.sellerId)
                .append(giftName, giftVO.giftName)
                .append(giftNum, giftVO.giftNum)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(giftId)
                .append(sellerId)
                .append(giftName)
                .append(giftNum)
                .toHashCode();
    }
}
