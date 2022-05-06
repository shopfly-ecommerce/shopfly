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
package cloud.shopfly.b2c.core.system.model.dto;

import cloud.shopfly.b2c.core.system.model.dos.ShipTemplateSettingDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 扩展用于与商品相关的属性
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018年8月23日 下午4:02:52
 *
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ShipTemplateSettingDTO  implements Serializable {


    private static final long serialVersionUID = 1548871339469347053L;



    @ApiModelProperty(name = "items", value = "价格条件设置", required = false)
    private List<ShipTemplateSettingDO> priceSettings;


    @ApiModelProperty(name = "items", value = "重量条件设置", required = false)
    private List<ShipTemplateSettingDO> weightSettings;

    @ApiModelProperty(name = "items", value = "数量条件设置", required = false)
    private List<ShipTemplateSettingDO> itemsSettings;


    public List<ShipTemplateSettingDO> getPriceSettings() {
        return priceSettings;
    }

    public void setPriceSettings(List<ShipTemplateSettingDO> priceSettings) {
        this.priceSettings = priceSettings;
    }

    public List<ShipTemplateSettingDO> getWeightSettings() {
        return weightSettings;
    }

    public void setWeightSettings(List<ShipTemplateSettingDO> weightSettings) {
        this.weightSettings = weightSettings;
    }

    public List<ShipTemplateSettingDO> getItemsSettings() {
        return itemsSettings;
    }

    public void setItemsSettings(List<ShipTemplateSettingDO> itemsSettings) {
        this.itemsSettings = itemsSettings;
    }
}
