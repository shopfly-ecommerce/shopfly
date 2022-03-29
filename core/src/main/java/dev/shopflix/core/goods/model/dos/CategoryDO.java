/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.model.dos;

import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.PrimaryKeyField;
import dev.shopflix.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 商品分类实体
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-15 17:22:06
 */
@Table(name = "es_category")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CategoryDO implements Serializable {

    private static final long serialVersionUID = 1964321416223565L;

    /**
     * 主键
     */
    @Id(name = "category_id")
    @ApiModelProperty(hidden = true)
    private Integer categoryId;

    /**
     * 分类名称
     */
    @Column()
    @ApiModelProperty(value = "分类名称", required = true)
    @NotEmpty(message = "分类名称不能为空")
    private String name;

    /**
     * 分类父id
     */
    @Column(name = "parent_id")
    @ApiModelProperty(name = "parent_id", value = "分类父id，顶 0", required = true)
    @NotNull(message = "父分类不能为空")
    private Integer parentId;

    /**
     * 分类父子路径
     */
    @Column(name = "category_path")
    @ApiModelProperty(hidden = true)
    private String categoryPath;

    /**
     * 该分类下商品数量
     */
    @Column(name = "goods_count")
    @ApiModelProperty(hidden = true)
    private Integer goodsCount;

    /**
     * 分类排序
     */
    @Column(name = "category_order")
    @ApiModelProperty(name = "category_order", value = "分类排序", required = false)
    private Integer categoryOrder;

    /**
     * 分类图标
     */
    @Column()
    @ApiModelProperty(value = "分类图标", required = false)
    private String image;

    @PrimaryKeyField
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Integer getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(Integer categoryOrder) {
        this.categoryOrder = categoryOrder;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "CategoryDO [categoryId=" + categoryId + ", name=" + name + ", parentId=" + parentId + ", categoryPath="
                + categoryPath + ", goodsCount=" + goodsCount + ", categoryOrder=" + categoryOrder + ", image=" + image
                + "]";
    }


}