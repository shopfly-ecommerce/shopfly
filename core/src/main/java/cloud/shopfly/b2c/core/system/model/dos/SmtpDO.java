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
 * Mail entity
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

	/**A primary keyID*/
	@Id(name = "id")
	@ApiModelProperty(hidden=true)
	private Integer id;
	/**The host*/
	@Column(name = "host")
	@ApiModelProperty(name="host",value="The host",required=false)
	private String host;
	/**Username*/
	@Column(name = "username")
	@NotEmpty(message="The user name cannot be empty")
	@ApiModelProperty(name="username",value="Username",required=true)
	private String username;
	/**Password*/
	@Column(name = "password")
	@ApiModelProperty(name="password",value="Password",required=false)
	@JsonIgnore
	private String password;
	/**Last mailing time*/
	@Column(name = "last_send_time")
	@ApiModelProperty(name="last_send_time",value="Last mailing time",required=false,hidden=true)
	private Long lastSendTime;
	/**Number of issued*/
	@Column(name = "send_count")
	@Min(message="Must be a number", value = 0)
	@ApiModelProperty(name="send_count",value="Number of issued",required=false,hidden=true)
	private Integer sendCount;
	/**Maximum number of messages sent*/
	@Column(name = "max_count")
	@ApiModelProperty(name="max_count",value="Maximum number of messages sent",required=false)
	private Integer maxCount;
	/**Sender email*/
	@Column(name = "mail_from")
	@NotEmpty(message="The mailbox cannot be empty")
	@Email(message="The mailbox format is incorrect")
	@ApiModelProperty(name="mail_from",value="Sender email",required=false)
	private String mailFrom;
	/**port*/
	@Column(name = "port")
	@Min(message="Must be a number", value = 0)
	@ApiModelProperty(name="port",value="port",required=false)
	private Integer port;
	/**sslWhether open*/
	@Column(name = "open_ssl")
	@Min(message="Must be a number", value = 0)
	@ApiModelProperty(name="open_ssl",value="sslWhether open",required=false)
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
