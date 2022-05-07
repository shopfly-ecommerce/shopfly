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
package cloud.shopfly.b2c.core.system.model.dos;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;


/**
 * Area table
 *
 * @author cs
 * @version v1.0
 * @since v7.0.0
 * 2018-08-22 15:10:51
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@Table(name = "es_rate_area")
public class RateAreaDO implements Serializable {

    private static final long serialVersionUID = 8051779001011336L;

    @Id
    @ApiModelProperty(hidden = true)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ApiParam(value = "Regional parent-child data")
    @Column(name = "area_json")
    private String areaJson;

    @ApiParam(value = "region‘，‘Separate example parameters：Beijing, Shanxi, Tianjin, Shanghai")
    @Column(name = "area")
    private String area;

    @ApiModelProperty(value = "regionid‘，‘Separate example parameters：1，2，3，4 ",hidden = true)
    @Column(name = "area_id")
    private String areaId;

    @Column(name = "create_time")
    private Long createTime;


    public String getAreaJson() {
        return areaJson;
    }

    public void setAreaJson(String areaJson) {
        this.areaJson = areaJson;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }


    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
