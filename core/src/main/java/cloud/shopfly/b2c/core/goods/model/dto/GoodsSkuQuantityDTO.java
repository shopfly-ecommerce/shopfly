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

import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * productskuInventory
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018years3month23The morning of11:26:49
 */
@ApiIgnore
public class GoodsSkuQuantityDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2050291085109943997L;

    /**
     * Inventory quantity
     */
    @ApiModelProperty(name = "quantity_count", value = "Inventory quantity")
    @Max(value = 99999999, message = "The inventory quantity is incorrect")
    @Min(value = 0, message = "The inventory quantity is incorrect")
    private Integer quantityCount;

    /**
     * skuid
     */
    @ApiModelProperty(name = "sku_id", value = "skuvalue")
    private Integer skuId;

    public Integer getQuantityCount() {
        return quantityCount;
    }

    public void setQuantityCount(Integer quantityCount) {
        this.quantityCount = quantityCount;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    @Override
    public String toString() {
        return "GoodsSkuQuantityVO [quantityCount=" + quantityCount + ", skuId=" + skuId + "]";
    }

}
