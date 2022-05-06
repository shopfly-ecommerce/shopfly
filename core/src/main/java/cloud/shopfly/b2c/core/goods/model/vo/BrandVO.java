/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goods.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 品牌vo
 * 
 * @author fk
 * @version v1.0
 * @since v7.0 2018年3月16日 下午4:44:55
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BrandVO {

	@ApiModelProperty(value="品牌名称")
	private String name;

	@ApiModelProperty(value="品牌图标")
	private String logo;

	@ApiModelProperty(value="品牌id")
	private Integer brandId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	@Override
	public String toString() {
		return "BrandVO{" +
				"name='" + name + '\'' +
				", logo='" + logo + '\'' +
				", brandId=" + brandId +
				'}';
	}
}
