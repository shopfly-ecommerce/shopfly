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
package cloud.shopfly.b2c.core.member.model.dto;

import cloud.shopfly.b2c.core.member.constraint.annotation.GradeType;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * commentsVO
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 10:38:00
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "Member commentsvo")
public class CommentDTO implements Serializable {

	@ApiModelProperty(name = "content", value = "Comment on the content", required = false)
	private String content;

	@ApiModelProperty(name = "grade", value = "Good to bad", required = true,allowableValues = "good,neutral,bad")
	@GradeType
	private String grade;
	
	@ApiModelProperty(value = "Pictures of member comments")
	private List<String> images;
	
	@ApiModelProperty(value = "Members comment on product specificationsid",name = "sku_id", required = true)
	@NotNull(message = "Review items cannot be empty")
	private Integer skuId;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}


	@Override
	public String toString() {
		return "CommentDTO{" +
				"content='" + content + '\'' +
				", grade='" + grade + '\'' +
				", images=" + images +
				", skuId=" + skuId +
				'}';
	}
}
