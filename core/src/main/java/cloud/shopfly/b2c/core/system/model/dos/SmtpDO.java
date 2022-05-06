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

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


/**
 * 邮件实体
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-25 16:16:53
 */
@Table(name="es_smtp")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SmtpDO implements Serializable {

	private static final long serialVersionUID = 9787156257241506L;

	/**主键ID*/
	@Id(name = "id")
	@ApiModelProperty(hidden=true)
	private Integer id;
	/**主机*/
	@Column(name = "host")
	@ApiModelProperty(name="host",value="主机",required=false)
	private String host;
	/**用户名*/
	@Column(name = "username")
	@NotEmpty(message="用户名不能为空")
	@ApiModelProperty(name="username",value="用户名",required=true)
	private String username;
	/**密码*/
	@Column(name = "password")
	@ApiModelProperty(name="password",value="密码",required=false)
	@JsonIgnore
	private String password;
	/**最后发信时间*/
	@Column(name = "last_send_time")
	@ApiModelProperty(name="last_send_time",value="最后发信时间",required=false,hidden=true)
	private Long lastSendTime;
	/**已发数*/
	@Column(name = "send_count")
	@Min(message="必须为数字", value = 0)
	@ApiModelProperty(name="send_count",value="已发数",required=false,hidden=true)
	private Integer sendCount;
	/**最大发信数*/
	@Column(name = "max_count")
	@ApiModelProperty(name="max_count",value="最大发信数",required=false)
	private Integer maxCount;
	/**发信邮箱*/
	@Column(name = "mail_from")
	@NotEmpty(message="邮箱不能为空")
	@Email(message="邮箱格式不正确")
	@ApiModelProperty(name="mail_from",value="发信邮箱",required=false)
	private String mailFrom;
	/**端口*/
	@Column(name = "port")
	@Min(message="必须为数字", value = 0)
	@ApiModelProperty(name="port",value="端口",required=false)
	private Integer port;
	/**ssl是否开启*/
	@Column(name = "open_ssl")
	@Min(message="必须为数字", value = 0)
	@ApiModelProperty(name="open_ssl",value="ssl是否开启",required=false)
	private Integer openSsl;




	@Override
	public String toString() {
		return "SmtpDO [id=" + id + ", host=" + host + ", username=" + username + ", password=" + password
				+ ", lastSendTime=" + lastSendTime + ", sendCount=" + sendCount + ", maxCount=" + maxCount
				+ ", mailFrom=" + mailFrom + ", port=" + port + ", openSsl=" + openSsl + "]";
	}
	@PrimaryKeyField
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Long getLastSendTime() {
		return lastSendTime;
	}
	public void setLastSendTime(Long lastSendTime) {
		this.lastSendTime = lastSendTime;
	}

	public Integer getSendCount() {
		return sendCount;
	}
	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public Integer getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getOpenSsl() {
		return openSsl;
	}
	public void setOpenSsl(Integer openSsl) {
		this.openSsl = openSsl;
	}



}