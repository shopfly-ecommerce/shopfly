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

import cloud.shopfly.b2c.core.base.model.vo.ConfigItem;
import cloud.shopfly.b2c.core.base.plugin.upload.Uploader;
import cloud.shopfly.b2c.core.system.model.dos.UploaderDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * Storage scheme entity
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-22 09:31:56
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UploaderVO implements Serializable {

    private static final long serialVersionUID = -5402918566675345349L;
    /**
     * storageid
     */
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * Store name
     */
    @NotEmpty(message = "The storage name cannot be empty")
    @ApiModelProperty(name = "name", value = "Store name", required = true)
    private String name;
    /**
     * Whether open
     */
    @ApiModelProperty(name = "open", value = "Whether open", required = true, hidden = true)
    private Integer open;
    /**
     * Storage configuration
     */
    @ApiModelProperty(name = "config", value = "Storage configuration", required = true)
    private String config;
    /**
     * Memory cardid
     */
    @ApiModelProperty(name = "bean", value = "Memory cardid", required = true)
    @NotEmpty(message = "Memory cardidCant be empty")
    private String bean;
    @ApiModelProperty(name = "configItems", value = "Storage solution configuration item", required = true)
    private List<ConfigItem> configItems;


    public UploaderVO(UploaderDO upload) {
        this.id = upload.getId();
        this.name = upload.getName();
        this.open = upload.getOpen();
        this.bean = upload.getBean();
        Gson gson = new Gson();
        this.configItems = gson.fromJson(upload.getConfig(), new TypeToken<List<ConfigItem>>() {
        }.getType());
    }

    @Override
    public String toString() {
        return "UploaderVO [id=" + id + ", name=" + name + ", open=" + open + ", config=" + config + ", bean=" + bean
                + ", configItems=" + configItems + "]";
    }

    public UploaderVO(Uploader upload) {
        this.id = 0;
        this.name = upload.getPluginName();
        this.open = upload.getIsOpen();
        this.bean = upload.getPluginId();
        this.configItems = upload.definitionConfigItem();
    }

    public UploaderVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getBean() {
        return bean;
    }

    public void setBean(String bean) {
        this.bean = bean;
    }

    public List<ConfigItem> getConfigItems() {
        return configItems;
    }

    public void setConfigItems(List<ConfigItem> configItems) {
        this.configItems = configItems;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }


}
