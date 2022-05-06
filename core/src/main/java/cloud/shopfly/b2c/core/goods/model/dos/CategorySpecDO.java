/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goods.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 分类规格关联表实体
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-20 10:04:26
 */
@Table(name = "es_category_spec")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CategorySpecDO implements Serializable {

    private static final long serialVersionUID = 3127259064985991L;

    /**
     * 分类id
     */
    @Column(name = "category_id")
    @ApiModelProperty(value = "分类id", required = false)
    private Integer categoryId;
    /**
     * 规格id
     */
    @Column(name = "spec_id")
    @ApiModelProperty(value = "规格id", required = false)
    private Integer specId;

    public CategorySpecDO() {

    }

    public CategorySpecDO(Integer categoryId, Integer specId) {
        super();
        this.categoryId = categoryId;
        this.specId = specId;
    }


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    @Override
    public String toString() {
        return "CategorySpecDO [categoryId=" + categoryId + ", specId=" + specId + "]";
    }


}