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

import cloud.shopfly.b2c.core.goods.model.enums.QuantityType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 商品库存vo
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017年9月7日 上午11:23:16
 */
public class GoodsQuantityVO implements Cloneable{



	private Integer goodsId;

	private Integer skuId;

	private Integer quantity;

	private QuantityType quantityType;

	public GoodsQuantityVO() {}


	public GoodsQuantityVO(Integer goodsId, Integer skuId, Integer quantity ) {
		super();
		this.goodsId = goodsId;
		this.skuId = skuId;
		this.quantity = quantity;

	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		Object object = super.clone();
		return object;
	}


	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Integer getQuantity() {

		if ( quantity==null){
			return 0;
		}

		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public QuantityType getQuantityType() {
		return quantityType;
	}

	public void setQuantityType(QuantityType quantityType) {
		this.quantityType = quantityType;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		GoodsQuantityVO that = (GoodsQuantityVO) o;

		return new EqualsBuilder()
				.append(goodsId, that.goodsId)
				.append(skuId, that.skuId)
				.append(quantity, that.quantity)
				.append(quantityType, that.quantityType)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(goodsId)
				.append(skuId)
				.append(quantity)
				.append(quantityType)
				.toHashCode();
	}

	@Override
	public String toString() {
		return "GoodsQuantityVO{" +
				"goodsId=" + goodsId +
				", skuId=" + skuId +
				", quantity=" + quantity +
				", quantityType=" + quantityType +
				'}';
	}


}

