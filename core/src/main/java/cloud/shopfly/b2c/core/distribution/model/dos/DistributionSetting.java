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
package cloud.shopfly.b2c.core.distribution.model.dos;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Distribution cashback setup
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-06-12 In the morning4:06
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DistributionSetting implements Serializable {

    /**
     * Withdrawal amount freezing period
     */
    @Min(message = "The freezing period cannot be negative", value = 0)
    @NotNull(message = "The freezing period cannot be empty")
    @ApiModelProperty(name = "cycle", value = "Freeze cycle")
    private Integer cycle = 0;
    /**
     * Whether to open goods cash back
     */
    @ApiModelProperty(name = "goods_model", value = "Whether to open goods cash back,1open,0不open")
    @NotNull(message = "Commodity cash back mode switch：1open/0close")
    private Integer goodsModel = 0;

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public Integer getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(Integer goodsModel) {
        this.goodsModel = goodsModel;
    }

    @Override
    public String toString() {
        return "DistributionSetting{" +
                "cycle=" + cycle +
                ", goodsModel=" + goodsModel +
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

        DistributionSetting that = (DistributionSetting) o;

        if (cycle != null ? !cycle.equals(that.cycle) : that.cycle != null) {
            return false;
        }
        return goodsModel != null ? goodsModel.equals(that.goodsModel) : that.goodsModel == null;
    }

    @Override
    public int hashCode() {
        int result = cycle != null ? cycle.hashCode() : 0;
        result = 31 * result + (goodsModel != null ? goodsModel.hashCode() : 0);
        return result;
    }
}
