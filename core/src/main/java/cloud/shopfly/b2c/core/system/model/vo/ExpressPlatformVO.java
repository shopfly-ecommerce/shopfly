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
import cloud.shopfly.b2c.core.base.plugin.express.ExpressPlatform;
import cloud.shopfly.b2c.core.system.model.dos.ExpressPlatformDO;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;


/**
 * Express platform entity
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-11 14:42:50
 */

public class ExpressPlatformVO implements Serializable {


    private static final long serialVersionUID = -6909967652948921476L;
    /**
     * Delivery platformid
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * Express Platform Name
     */
    @Column(name = "name")
    @ApiModelProperty(name = "name", value = "Express Platform Name", required = false)
    private String name;
    /**
     * Whether to enable the express delivery platform,1Open,0Did not open
     */
    @Column(name = "open")
    @ApiModelProperty(name = "open", value = "Whether to enable the express delivery platform,1Open,0Did not open", required = false)
    private Integer open;
    /**
     * Express Platform Configuration
     */
    @Column(name = "config")
    @ApiModelProperty(name = "config", value = "Express Platform Configuration", required = false)
    private String config;
    /**
     * Delivery platformbeanid
     */
    @Column(name = "bean")
    @ApiModelProperty(name = "bean", value = "Delivery platformbeanid", required = false)
    private String bean;
    /**
     * Express platform configuration item
     */
    @ApiModelProperty(name = "configItems", value = "Express platform configuration item", required = true)
    private List<ConfigItem> configItems;

    public ExpressPlatformVO(ExpressPlatformDO expressPlatformDO) {
        this.id = expressPlatformDO.getId();
        this.name = expressPlatformDO.getName();
        this.open = expressPlatformDO.getOpen();
        this.bean = expressPlatformDO.getBean();
        Gson gson = new Gson();
        this.configItems = gson.fromJson(expressPlatformDO.getConfig(), new TypeToken<List<ConfigItem>>() {
        }.getType());
    }

    public ExpressPlatformVO() {

    }

    public ExpressPlatformVO(ExpressPlatform expressPlatform) {
        this.id = 0;
        this.name = expressPlatform.getPluginName();
        this.open = expressPlatform.getIsOpen();
        this.bean = expressPlatform.getPluginId();
        this.configItems = expressPlatform.definitionConfigItem();
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

    @Override
    public String toString() {
        return "ExpressPlatformVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", open=" + open +
                ", config='" + config + '\'' +
                ", bean='" + bean + '\'' +
                ", configItems=" + configItems +
                '}';
    }
}
