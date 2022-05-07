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

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import java.io.Serializable;


/**
 * Mail deliveryVO
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-26 14:38:05
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EmailVO implements Serializable {

	private static final long serialVersionUID = 5817183105391564L;

	/**The mail recordid*/
	@ApiModelProperty(hidden=true)
	private Integer id;
	/**Email title*/
	@Column(name = "title")
	@ApiModelProperty(name="title",value="Email title",required=false)
	private String title;
	/**Email type*/
	@Column(name = "type")
	@ApiModelProperty(name="type",value="Email type",required=false)
	private String type;
	/**The success of*/
	@Column(name = "success")
	@Min(message="Must be a number", value = 0)
	@ApiModelProperty(name="success",value="The success of",required=false)
	private Integer success;
	/**Mail recipient*/
	@Column(name = "email")
	@Email(message="Malformed")
	@ApiModelProperty(name="email",value="Mail recipient",required=false)
	private String email;
	/**Email content*/
	@Column(name = "context")
	@ApiModelProperty(name="context",value="Email content",required=false)
	private String content;
	/**Wrong number*/
	@Column(name = "error_num")
	@Min(message="Must be a number", value = 0)
	@ApiModelProperty(name="error_num",value="Wrong number",required=false)
	private Integer errorNum;
	/**Last sending time*/
	@Column(name = "last_send")
	@ApiModelProperty(name="last_send",value="Last sending time",required=false)
	private Long lastSend;

	@Override
	public String toString() {
		return "EmailVO [id=" + id + ", title=" + title + ", type=" + type + ", success=" + success + ", email=" + email
				+ ", context=" + content + ", errorNum=" + errorNum + ", lastSend=" + lastSend + "]";
	}
	@PrimaryKeyField
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Integer getSuccess() {
		return success;
	}
	public void setSuccess(Integer success) {
		this.success = success;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Integer getErrorNum() {
		return errorNum;
	}
	public void setErrorNum(Integer errorNum) {
		this.errorNum = errorNum;
	}

	public Long getLastSend() {
		return lastSend;
	}
	public void setLastSend(Long lastSend) {
		this.lastSend = lastSend;
	}

}
