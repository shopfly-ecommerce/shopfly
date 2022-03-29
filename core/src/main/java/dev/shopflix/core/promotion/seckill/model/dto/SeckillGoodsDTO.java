/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.seckill.model.dto;

import dev.shopflix.core.promotion.seckill.model.dos.SeckillApplyDO;
import dev.shopflix.core.promotion.seckill.model.vo.SeckillConvertGoodsVO;
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
 * 2017年12月14日 16:58:55
 */
@SuppressWarnings("AlibabaPojoMustOverrideToString")
@ApiModel(description = "活动商品vo")
public class SeckillGoodsDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5327491947477140478L;

	@ApiModelProperty(value = "商品id")
	private Integer goodsId;

	@ApiModelProperty(value = "商品图片")
	private String goodsImage;

	@ApiModelProperty(value = "商品名称")
	private String goodsName;

	@ApiModelProperty(value = "商品普通价格")
	private Double originalPrice;

	@ApiModelProperty(value = "是否可销售")
	private Boolean salesEnable;

	@ApiModelProperty(value = "秒杀活动价格")
	private Double seckillPrice;

	@ApiModelProperty(value = "商品规格id")
	private Integer skuId;

	@ApiModelProperty(value = "已售数量")
	private Integer soldNum;

	@ApiModelProperty(value = "售空数量")
	private Integer soldQuantity;

	@ApiModelProperty(value = "不同规格下的页面url")
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
