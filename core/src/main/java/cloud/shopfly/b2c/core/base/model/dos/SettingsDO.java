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
package cloud.shopfly.b2c.core.base.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * System setup entity
<<<<<<< HEAD
=======
 *
>>>>>>> master
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-27 18:47:17
 */

@Table(name = "es_settings")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SettingsDO implements Serializable {

    private static final long serialVersionUID = 3372606354638487L;

    /**
     * System Settingsid
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * System Configuration information
     */
    @Column(name = "cfg_value")
    @NotEmpty(message = "The system configuration information cannot be empty")
    @ApiModelProperty(name = "cfg_value", value = "System Configuration information", required = true)
    private String cfgValue;
    /**
     * Service setting identifier
     */
    @Column(name = "cfg_group")
    @NotEmpty(message = "The service setting identifier cannot be empty")
    @ApiModelProperty(name = "cfg_group", value = "Service setting identifier", required = true)
    private String cfgGroup;


    @Override
    public String toString() {
        return "SettingsDO [id=" + id + ", cfgValue=" + cfgValue + ", cfgGroup=" + cfgGroup + "]";
    }

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCfgValue() {
        return cfgValue;
    }

    public void setCfgValue(String cfgValue) {
        this.cfgValue = cfgValue;
    }

    public String getCfgGroup() {
        return cfgGroup;
    }

    public void setCfgGroup(String cfgGroup) {
        this.cfgGroup = cfgGroup;
    }


}
