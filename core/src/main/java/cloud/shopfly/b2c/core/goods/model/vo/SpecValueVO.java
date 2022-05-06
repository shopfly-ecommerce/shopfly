/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goods.model.vo;

import cloud.shopfly.b2c.core.goods.model.dos.SpecValuesDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 规格值实体
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月22日 上午9:17:33
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SpecValueVO extends SpecValuesDO implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1426807099688672502L;
	/**
	 * 商品sku——id
	 */
	@ApiModelProperty(name="sku_id",hidden=true)
	private Integer skuId;
	/**
	 * 商品大图
	 */
	@ApiModelProperty(hidden=true)
	private String big;
	/**
	 * 商品小图
	 */
	@ApiModelProperty(hidden=true)
	private String small;
	/**
	 * 商品缩略图
	 */
	@ApiModelProperty(hidden=true)
	private String thumbnail;
	/**
	 * 商品极小图
	 */
	@ApiModelProperty(hidden=true)
	private String tiny;
	
	/**
	 * 规格类型，1图片  0 非图片
	 */
	@ApiModelProperty(name="spec_type",value="该规格是否有图片，1 有 0 没有")
	private Integer specType;
	/**
	 * 规格图片
	 */
	@ApiModelProperty(name="spec_image",value="规格的图片")
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
