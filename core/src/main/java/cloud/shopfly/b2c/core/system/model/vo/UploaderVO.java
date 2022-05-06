/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 存储方案实体
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
     * 储存id
     */
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * 存储名称
     */
    @NotEmpty(message = "存储名称不能为空")
    @ApiModelProperty(name = "name", value = "存储名称", required = true)
    private String name;
    /**
     * 是否开启
     */
    @ApiModelProperty(name = "open", value = "是否开启", required = true, hidden = true)
    private Integer open;
    /**
     * 存储配置
     */
    @ApiModelProperty(name = "config", value = "存储配置", required = true)
    private String config;
    /**
     * 存储插件id
     */
    @ApiModelProperty(name = "bean", value = "存储插件id", required = true)
    @NotEmpty(message = "存储插件id不能为空")
    private String bean;
    @ApiModelProperty(name = "configItems", value = "存储方案配置项", required = true)
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