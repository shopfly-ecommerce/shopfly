/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.model.vo;

import dev.shopflix.core.base.model.vo.ConfigItem;
import dev.shopflix.core.base.plugin.sms.SmsPlatform;
import dev.shopflix.core.system.model.dos.SmsPlatformDO;
import dev.shopflix.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;


/**
 * 短信网关表实体
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-23 11:31:05
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SmsPlatformVO implements Serializable{
	private static final long serialVersionUID = 1568078050994075591L;
	/**id*/
	@ApiModelProperty(hidden=true)
	private Integer id;
	/**平台名称*/
	@Column(name = "name")
	@NotEmpty(message="平台名称不能为空")
	@ApiModelProperty(name="name",value="平台名称",required=true)
	private String name;
	/**是否开启*/
	@Column(name = "open")
	@Min(message="必须为数字", value = 0)
	@ApiModelProperty(name="open",value="是否开启",required=false)
	private Integer open;
	/**配置*/
	@Column(name = "config")
	@ApiModelProperty(name="config",value="配置",required=false)
	private String config;
	/**编码*/
	@Column(name = "bean")
	@NotEmpty(message="插件id")
	@ApiModelProperty(name="bean",value="编码",required=true)
	private String bean;
	@ApiModelProperty(name="configItems" ,value = "短信配置项",required=true)
	private List<ConfigItem> configItems;

	public SmsPlatformVO(SmsPlatformDO smsPlatformDO) {
		this.id = smsPlatformDO.getId();
		this.name = smsPlatformDO.getName();
		this.open = smsPlatformDO.getOpen();
		this.bean = smsPlatformDO.getBean();
		Gson gson = new Gson();
		this.configItems = gson.fromJson(smsPlatformDO.getConfig(),  new TypeToken< List<ConfigItem> >() {  }.getType());
	}
	public SmsPlatformVO() {

	}


	@Override
	public String toString() {
		return "PlatformVO [id=" + id + ", name=" + name + ", open=" + open + ", config=" + config + ", bean=" + bean
				+ ", configItems=" + configItems + "]";
	}


	public SmsPlatformVO(SmsPlatform smsPlatform) {
		this.id = 0;
		this.name = smsPlatform.getPluginName();
		this.open = smsPlatform.getIsOpen();
		this.bean = smsPlatform.getPluginId();
		this.configItems = smsPlatform.definitionConfigItem();
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


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public List<ConfigItem> getConfigItems() {
		return configItems;
	}


	public void setConfigItems(List<ConfigItem> configItems) {
		this.configItems = configItems;
	}





}