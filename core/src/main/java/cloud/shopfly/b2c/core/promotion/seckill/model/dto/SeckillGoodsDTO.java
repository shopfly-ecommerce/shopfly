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
package cloud.shopfly.b2c.core.promotion.seckill.model.dto;

import cloud.shopfly.b2c.core.promotion.seckill.model.dos.SeckillApplyDO;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.SeckillConvertGoodsVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 *
 * @author Snow
 * @version 1.0
 * @since v7.0.0
 * 2017years12month14day16:58:55
 */
@SuppressWarnings("AlibabaPojoMustOverrideToString")
@ApiModel(description = "Activities of goodsvo")
public class SeckillGoodsDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5327491947477140478L;

	@ApiModelProperty(value = "productid")
	private Integer goodsId;

	@ApiModelProperty(value = "Commodity images")
	private String goodsImage;

	@ApiModelProperty(value = "Name")
	private String goodsName;

	@ApiModelProperty(value = "General commodity price")
	private Double originalPrice;

	@ApiModelProperty(value = "Marketable or not")
	private Boolean salesEnable;

	@ApiModelProperty(value = "Second kill activity price")
	private Double seckillPrice;

	@ApiModelProperty(value = "Skuid")
	private Integer skuId;

	@ApiModelProperty(value = "The number sold")
	private Integer soldNum;

	@ApiModelProperty(value = "The number sold out")
	private Integer soldQuantity;

	@ApiModelProperty(value = "Pages with different specificationsurl")
	private String url;

	public SeckillGoodsDTO(){

	}

	public SeckillGoodsDTO(SeckillConvertGoodsVO goods, SeckillApplyDO apply){
		this.goodsId = goods.getGoodsId();
		this.goodsName = goods.getGoodsName();
		this.originalPrice = goods.getPrice();
		this.seckillPrice = apply.getPrice();
		this.soldQuantity = apply.getSoldQuantity();
		this.goodsImage = goods.getThumbnail();
	}

	@Override
	public String toString() {
		return "SeckillGoodsDTO{" +
				"goodsId=" + goodsId +
				", goodsImage='" + goodsImage + '\'' +
				", goodsName='" + goodsName + '\'' +
				", originalPrice=" + originalPrice +
				", salesEnable=" + salesEnable +
				", seckillPrice=" + seckillPrice +
				", skuId=" + skuId +
				", soldNum=" + soldNum +
				", soldQuantity=" + soldQuantity +
				", url='" + url + '\'' +
				'}';
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsImage() {
		return goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Boolean getSalesEnable() {
		return salesEnable;
	}

	public void setSalesEnable(Boolean salesEnable) {
		this.salesEnable = salesEnable;
	}

	public Double getSeckillPrice() {
		return seckillPrice;
	}

	public void setSeckillPrice(Double seckillPrice) {
		this.seckillPrice = seckillPrice;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Integer getSoldNum() {
		if(soldNum == null){
			soldNum = 0;
		}
		return soldNum;
	}

	public void setSoldNum(Integer soldNum) {
		this.soldNum = soldNum;
	}

	public Integer getSoldQuantity() {
		if(soldQuantity<0){
			return 0;
		}
		return soldQuantity;
	}

	public void setSoldQuantity(Integer soldQuantity) {
		this.soldQuantity = soldQuantity;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o){
			return true;
		}

		if (o == null || getClass() != o.getClass()){
			return false;
		}

		SeckillGoodsDTO that = (SeckillGoodsDTO) o;

		return new EqualsBuilder()
				.append(goodsId, that.goodsId)
				.append(goodsImage, that.goodsImage)
				.append(goodsName, that.goodsName)
				.append(originalPrice, that.originalPrice)
				.append(salesEnable, that.salesEnable)
				.append(seckillPrice, that.seckillPrice)
				.append(skuId, that.skuId)
				.append(soldNum, that.soldNum)
				.append(soldQuantity, that.soldQuantity)
				.append(url, that.url)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(goodsId)
				.append(goodsImage)
				.append(goodsName)
				.append(originalPrice)
				.append(salesEnable)
				.append(seckillPrice)
				.append(skuId)
				.append(soldNum)
				.append(soldQuantity)
				.append(url)
				.toHashCode();
	}
}
