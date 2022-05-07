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
package cloud.shopfly.b2c.core.goods.model.vo;

import cloud.shopfly.b2c.core.goods.model.dos.SpecValuesDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Specification value entity
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018years3month22The morning of9:17:33
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SpecValueVO extends SpecValuesDO implements Serializable {

	/**
	 * The serial number
	 */
	private static final long serialVersionUID = 1426807099688672502L;
	/**
	 * productsku——id
	 */
	@ApiModelProperty(name="sku_id",hidden=true)
	private Integer skuId;
	/**
	 * A larger product
	 */
	@ApiModelProperty(hidden=true)
	private String big;
	/**
	 * Commodity insets
	 */
	@ApiModelProperty(hidden=true)
	private String small;
	/**
	 * Product thumbnail
	 */
	@ApiModelProperty(hidden=true)
	private String thumbnail;
	/**
	 * Commodity minimum diagram
	 */
	@ApiModelProperty(hidden=true)
	private String tiny;
	
	/**
	 * Specification type,1Image0 非Image
	 */
	@ApiModelProperty(name="spec_type",value="Is there a picture of this specification?1 There are0 没There are")
	private Integer specType;
	/**
	 * Sku pictures
	 */
	@ApiModelProperty(name="spec_image",value="Specification picture")
	private String specImage;

	public SpecValueVO() {
		
	}

	public String getBig() {
		return big;
	}

	public void setBig(String big) {
		this.big = big;
	}

	public String getSmall() {
		return small;
	}

	public void setSmall(String small) {
		this.small = small;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getTiny() {
		return tiny;
	}

	public void setTiny(String tiny) {
		this.tiny = tiny;
	}

	public Integer getSpecType() {
		return specType;
	}

	public void setSpecType(Integer specType) {
		this.specType = specType;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public String getSpecImage() {
		return specImage;
	}

	public void setSpecImage(String specImage) {
		this.specImage = specImage;
	}

	@Override
	public String toString() {
		return "SpecValueVO [skuId=" + skuId + ", big=" + big + ", small=" + small + ", thumbnail=" + thumbnail
				+ ", tiny=" + tiny + ", specType=" + specType + ", specImage=" + specImage + "]";
	}

}
