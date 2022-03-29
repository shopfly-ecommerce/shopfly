/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.system.model.vo;

import com.enation.app.javashop.core.base.model.vo.ConfigItem;
import com.enation.app.javashop.core.base.plugin.waybill.WayBillEvent;
import com.enation.app.javashop.core.system.model.dos.WayBillDO;
import com.enation.app.javashop.framework.database.annotation.PrimaryKeyField;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;


/**
 * 电子面单实体
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-06-08 16:26:05
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WayBillVO implements Serializable {

    private static final long serialVersionUID = 6990005251050581L;

    /**
     * 电子面单id
     */
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * 名称
     */
    @ApiModelProperty(name = "name", value = "名称", required = false)
    private String name;
    /**
     * 是否开启
     */
    @ApiModelProperty(name = "open", value = "是否开启", required = true, hidden = true)
    private Integer open;
    /**
     * 电子面单配置
     */
    @ApiModelProperty(name = "config", value = "电子面单配置", required = false)
    private String config;

    @ApiModelProperty(name = "configItems", value = "电子面单配置项", required = true)
    private List<ConfigItem> configItems;
    /**
     * beanid
     */
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

    public List<ConfigItem> getConfigItems() {
        return configItems;
    }

    public void setConfigItems(List<ConfigItem> configItems) {
        this.configItems = configItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WayBillVO that = (WayBillVO) o;
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

    public WayBillVO(WayBillDO wayBillDO) {
        this.id = wayBillDO.getId();
        this.name = wayBillDO.getName();
        this.open = wayBillDO.getOpen();
        this.bean = wayBillDO.getBean();
        Gson gson = new Gson();
        this.configItems = gson.fromJson(wayBillDO.getConfig(), new TypeToken<List<ConfigItem>>() {
        }.getType());
    }

    public WayBillVO(WayBillEvent wayBillEvent) {
        this.id = 0;
        this.name = wayBillEvent.getPluginName();
        this.open = wayBillEvent.getOpen();
        this.bean = wayBillEvent.getPluginId();
        this.configItems = wayBillEvent.definitionConfigItem();
    }

    public WayBillVO() {
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