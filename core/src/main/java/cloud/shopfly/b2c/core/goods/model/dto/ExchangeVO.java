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
package cloud.shopfly.b2c.core.goods.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Information on the redemption of product points
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018years3month22The morning of11:43:40
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExchangeVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4702156500500693965L;

    /**
     * Whether exchange is allowed
     */
    @ApiModelProperty(name = "enable_exchange", value = "Whether points are allowed to be exchanged")
    private Integer enableExchange;

    /**
     * Amount required for exchange
     */
    @ApiModelProperty(name = "exchange_money", value = "Amount required for exchange")
    private Double exchangeMoney;

    /**
     * Goods belong to the classification of points
     */
    @ApiModelProperty(name = "category_id", value = "The exchange of points belongs to the classification")
    private Integer categoryId;

    /**
     * Redemption points required
     */
    @ApiModelProperty(name = "exchange_point", value = "The credits are exchanged for the credits used")
    private Integer exchangePoint;

    public Integer getEnableExchange() {
        return enableExchange;
    }

    public void setEnableExchange(Integer enableExchange) {
        this.enableExchange = enableExchange;
    }

    public double getExchangeMoney() {
        return exchangeMoney;
    }

    public void setExchangeMoney(double exchangeMoney) {
        this.exchangeMoney = exchangeMoney;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getExchangePoint() {
        return exchangePoint;
    }

    public void setExchangePoint(Integer exchangePoint) {
        this.exchangePoint = exchangePoint;
    }

    @Override
    public String toString() {
        return "ExchangeVO [enableExchange=" + enableExchange + ", exchangeMoney=" + exchangeMoney + ", categoryId="
                + categoryId + ", exchangePoint=" + exchangePoint + "]";
    }


}
