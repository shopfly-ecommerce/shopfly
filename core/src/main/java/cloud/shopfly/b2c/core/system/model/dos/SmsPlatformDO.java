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

import cloud.shopfly.b2c.core.system.model.vo.SmsPlatformVO;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * Entity of the SMS gateway table
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-23 11:31:05
 */
@Table(name="es_sms_platform")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SmsPlatformDO implements Serializable {

	private static final long serialVersionUID = 5431942203889125L;

	/**A primary keyID*/
	@Id(name = "id")
	@ApiModelProperty(hidden=true)
	private Integer id;
	/**The name of the platform*/
	@Column(name = "name")
	@NotEmpty(message="The platform name cannot be empty")
	@ApiModelProperty(name="name",value="The name of the platform",required=true)
	private String name;
	/**Whether open*/
	@Column(name = "open")
	@Min(message="Must be a number", value = 0)
	@ApiModelProperty(name="open",value="Whether open",required=false)
	private Integer open;
	/**configuration*/
	@Column(name = "config")
	@ApiModelProperty(name="config",value="configuration",required=false)
	private String config;
	/**coding*/
	@Column(name = "bean")
	@NotEmpty(message="The plug-inid")
	@ApiModelProperty(name="bean",value="coding",required=true)
	private String bean;

	public SmsPlatformDO(SmsPlatformVO smsPlatformVO) {
		this.id = smsPlatformVO.getId();
		this.name = smsPlatformVO.getName();
		this.open = smsPlatformVO.getOpen();
		this.bean = smsPlatformVO.getBean();
		Gson gson = new Gson();
		this.config = gson.toJson(smsPlatformVO.getConfigItems());
	}
	public SmsPlatformDO() {

	}
	

	@Override
	public String toString() {
		return "PlatformDO [id=" + id + ", name=" + name + ", open=" + open + ", config=" + config + ", bean=" + bean
				+ "]";
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



}
