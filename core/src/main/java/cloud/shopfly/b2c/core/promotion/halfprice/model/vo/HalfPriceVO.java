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
package cloud.shopfly.b2c.core.promotion.halfprice.model.vo;

import cloud.shopfly.b2c.core.promotion.halfprice.model.dos.HalfPriceDO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionGoodsDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * The second half price eventvoentity
 * @author dongxin
 * @version v1.0
 * @since v6.4.0
 * 2017years8month21On the afternoon7:40:13
 */
@ApiModel(value="HalfPriceVO", description = "The second half price eventVOentity")
public class HalfPriceVO extends HalfPriceDO implements Serializable {


	@ApiModelProperty(name = "goods_list",value = "List of promotional items")
	private List<PromotionGoodsDTO> goodsList;

	@ApiModelProperty(name = "status_text",value = "Active state")
	private String statusText;

	@ApiModelProperty(name = "status",value = "Activity status identification,expiredIndicates invalid")
	private String status;

	public List<PromotionGoodsDTO> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<PromotionGoodsDTO> goodsList) {
		this.goodsList = goodsList;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o){
			return true;
		}

		if (o == null || getClass() != o.getClass()){
			return false;
		}

		HalfPriceVO that = (HalfPriceVO) o;

		return new EqualsBuilder()
				.appendSuper(super.equals(o))
				.append(goodsList, that.goodsList)
				.append(statusText, that.statusText)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.appendSuper(super.hashCode())
				.append(goodsList)
				.append(statusText)
				.toHashCode();
	}

	@Override
	public String toString() {
		return "HalfPriceVO{" +
				"goodsList=" + goodsList +
				", statusText='" + statusText + '\'' +
				'}';
	}
}
