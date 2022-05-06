/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.system.model.vo;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * 地区实体
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-28 13:49:38
 */
public class RegionsVO {

    /**
     * 父地区id
     */
    @Column(name = "parent_id")
    @Min(message = "必须为数字", value = 0)
    @NotNull(message = "父id不能为空")
    @ApiModelProperty(name = "parent_id", value = "父地区id，顶级分类填0", required = true)
    private Integer parentId;
    /**
     * 名称
     */
    @Column(name = "local_name")
    @ApiModelProperty(name = "local_name", value = "名称")
    @NotEmpty(message = "地区名称不能为空")
    private String localName;
    /**
     * 邮编
     */
    @Column(name = "zipcode")
    @ApiModelProperty(name = "zipcode", value = "邮编", required = false)
    private String zipcode;
    /**
     * 是否支持货到付款
     */
    @Column(name = "cod")
    @ApiModelProperty(name = "cod", value = "是否支持货到付款,1支持,0不支持")
    @Min(message = "是否支持货到付款,1支持,0不支持", value = 0)
    @Max(message = "是否支持货到付款,1支持,0不支持", value = 1)
    @NotNull(message = "是否支持货到付款不能为空")
    private Integer cod;


    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    @Override
    public String toString() {
        return "RegionsVO{" +
                "parentId=" + parentId +
                ", localName='" + localName + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", cod=" + cod +
                '}';
    }
}