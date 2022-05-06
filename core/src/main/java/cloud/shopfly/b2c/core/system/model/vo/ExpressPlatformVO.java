/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 快递平台实体
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-11 14:42:50
 */

public class ExpressPlatformVO implements Serializable {


    private static final long serialVersionUID = -6909967652948921476L;
    /**
     * 快递平台id
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * 快递平台名称
     */
    @Column(name = "name")
    @ApiModelProperty(name = "name", value = "快递平台名称", required = false)
    private String name;
    /**
     * 是否开启快递平台,1开启，0未开启
     */
    @Column(name = "open")
    @ApiModelProperty(name = "open", value = "是否开启快递平台,1开启，0未开启", required = false)
    private Integer open;
    /**
     * 快递平台配置
     */
    @Column(name = "config")
    @ApiModelProperty(name = "config", value = "快递平台配置", required = false)
    private String config;
    /**
     * 快递平台beanid
     */
    @Column(name = "bean")
    @ApiModelProperty(name = "bean", value = "快递平台beanid", required = false)
    private String bean;
    /**
     * 快递平台配置项
     */
    @ApiModelProperty(name = "configItems", value = "快递平台配置项", required = true)
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