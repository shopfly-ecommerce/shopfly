/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.halfprice.model.vo;

import dev.shopflix.core.promotion.halfprice.model.dos.HalfPriceDO;
import dev.shopflix.core.promotion.tool.model.dto.PromotionGoodsDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 第二件半价活动vo实体
 * @author dongxin
 * @version v1.0
 * @since v6.4.0
 * 2017年8月21日 下午7:40:13
 */
@ApiModel(value="HalfPriceVO", description = "第二件半价活动VO实体")
public class HalfPriceVO extends HalfPriceDO implements Serializable {


	@ApiModelProperty(name = "goods_list",value = "促销商品列表")
	private List<PromotionGoodsDTO> goodsList;

	@ApiModelProperty(name = "status_text",value = "活动状态")
	private String statusText;

	@ApiModelProperty(name = "status",value = "活动状态标识,expired表示已失效")
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
