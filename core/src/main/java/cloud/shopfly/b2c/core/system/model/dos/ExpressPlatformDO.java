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

import cloud.shopfly.b2c.core.system.model.vo.ExpressPlatformVO;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * Express platform entity
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-11 14:42:50
 */
@Table(name = "es_express_platform")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExpressPlatformDO implements Serializable {

    private static final long serialVersionUID = 9536539335454134L;

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

    public ExpressPlatformDO(ExpressPlatformVO expressPlatformVO) {
        this.id = expressPlatformVO.getId();
        this.name = expressPlatformVO.getName();
        this.open = expressPlatformVO.getOpen();
        this.bean = expressPlatformVO.getBean();
        Gson gson = new Gson();
        this.config = gson.toJson(expressPlatformVO.getConfigItems());
    }

    public ExpressPlatformDO() {

    }

    @PrimaryKeyField
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExpressPlatformDO that = (ExpressPlatformDO) o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (open != null ? !open.equals(that.open) : that.open != null) {
            return false;
        }
        if (config != null ? !config.equals(that.config) : that.config != null) {
            return false;
        }
        return bean != null ? bean.equals(that.bean) : that.bean == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (open != null ? open.hashCode() : 0);
        result = 31 * result + (config != null ? config.hashCode() : 0);
        result = 31 * result + (bean != null ? bean.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExpressPlatformDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", open=" + open +
                ", config='" + config + '\'' +
                ", bean='" + bean + '\'' +
                '}';
    }


}
