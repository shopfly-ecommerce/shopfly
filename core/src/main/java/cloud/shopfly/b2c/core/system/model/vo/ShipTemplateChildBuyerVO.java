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

import cloud.shopfly.b2c.framework.database.annotation.Column;

import java.io.Serializable;

/**
 * @author fk
 * @version v2.0
 * @Description:
 * @date 2018/10/26 16:32
 * @since v7.0.0
 */
public class ShipTemplateChildBuyerVO extends ShipTemplateChildBaseVO implements Serializable {

    @Column(name = "area_id")
    private String areaId;


    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "ShipTemplateChildBuyerVO{" +
                "areaId='" + areaId + '\'' +
                '}';
    }
}
