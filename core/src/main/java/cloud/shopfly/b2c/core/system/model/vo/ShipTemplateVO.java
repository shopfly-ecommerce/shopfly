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
package cloud.shopfly.b2c.core.system.model.vo;

import cloud.shopfly.b2c.core.system.model.dos.ShipTemplateDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: 运费模板VO
 * @date 2018/8/22 15:16
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ShipTemplateVO extends ShipTemplateDO implements Serializable {

    @ApiModelProperty(name = "items", value = "指定配送区域", required = true)
    private List<ShipTemplateSettingVO> items;

    public List<ShipTemplateSettingVO> getItems() {
        return items;
    }

    public void setItems(List<ShipTemplateSettingVO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ShipTemplateSettingDO{" +
                "items=" + items +
                '}';
    }
}
