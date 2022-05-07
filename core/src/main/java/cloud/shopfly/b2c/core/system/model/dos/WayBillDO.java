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

import cloud.shopfly.b2c.core.system.model.vo.WayBillVO;
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
 * Electron plane single entity
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-06-08 16:26:05
 */
@Table(name = "es_waybill")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WayBillDO implements Serializable {

    private static final long serialVersionUID = 6990005251050581L;

    /**
     * Electronic surface singleid
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * The name of the
     */
    @Column(name = "name")
    @ApiModelProperty(name = "name", value = "The name of the", required = false)
    private String name;
    /**
     * Whether open
     */
    @Column(name = "open")
    @ApiModelProperty(name = "open", value = "Whether open", required = false)
    private Integer open;
    /**
     * Single configuration of electronic surface
     */
    @Column(name = "config")
    @ApiModelProperty(name = "config", value = "Single configuration of electronic surface", required = false)
    private String config;
    /**
     * beanid
     */
    @Column(name = "bean")
    @ApiModelProperty(name = "bean", value = "beanid", required = false)
    private String bean;

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

    public WayBillDO(WayBillVO wayBillVO) {
        this.id = wayBillVO.getId();
        this.name = wayBillVO.getName();
        this.open = wayBillVO.getOpen();
        this.bean = wayBillVO.getBean();
        Gson gson = new Gson();
        this.config = gson.toJson(wayBillVO.getConfigItems());
    }

    public WayBillDO() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WayBillDO that = (WayBillDO) o;
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
        return "WayBillDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", open=" + open +
                ", config='" + config + '\'' +
                ", bean='" + bean + '\'' +
                '}';
    }


}
