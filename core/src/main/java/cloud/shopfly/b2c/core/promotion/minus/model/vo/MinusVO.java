/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.promotion.minus.model.vo;

import cloud.shopfly.b2c.core.promotion.minus.model.dos.MinusDO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionGoodsDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 单品立减VO
 * @author mengyuanming
 * @version v1.0
 * @since v6.4.0
 * @date 2017年8月18日下午8:39:27
 *
 */
@ApiModel(description = "单品立减VO")
public class MinusVO extends MinusDO implements Serializable {

	private static final long serialVersionUID = 2262185663510143477L;

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

		MinusVO minusVO = (MinusVO) o;

		return new EqualsBuilder()
				.appendSuper(super.equals(o))
				.append(goodsList, minusVO.goodsList)
				.append(statusText, minusVO.statusText)
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
		return "MinusVO{" +
				"goodsList=" + goodsList +
				", statusText='" + statusText + '\'' +
				'}';
	}
}
