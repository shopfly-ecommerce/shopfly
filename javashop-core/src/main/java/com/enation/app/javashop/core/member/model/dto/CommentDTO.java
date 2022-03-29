/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.member.model.dto;

import com.enation.app.javashop.core.member.constraint.annotation.GradeType;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 评论VO
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 10:38:00
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "会员评论vo")
public class CommentDTO implements Serializable {

	@ApiModelProperty(name = "content", value = "评论内容", required = false)
	private String content;

	@ApiModelProperty(name = "grade", value = "好中差评", required = true,allowableValues = "good,neutral,bad")
	@GradeType
	private String grade;
	
	@ApiModelProperty(value = "会员评论的图片")
	private List<String> images;
	
	@ApiModelProperty(value = "会员评论商品规格id",name = "sku_id", required = true)
	@NotNull(message = "评论的商品不能为空")
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
