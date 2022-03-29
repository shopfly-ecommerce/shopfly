/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.goods.model.vo;

import com.enation.app.javashop.core.goods.model.dos.CategoryDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 商品分类vo
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月16日 下午4:53:23
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CategoryVO extends CategoryDO {

    private static final long serialVersionUID = 3843585201476087204L;

    @ApiModelProperty("子分类列表")
    private List<CategoryVO> children;

    @ApiModelProperty("分类关联的品牌列表")
    private List<BrandVO> brandList;

    public CategoryVO() {

    }

    public CategoryVO(CategoryDO cat) {
        this.setCategoryId(cat.getCategoryId());
        this.setCategoryPath(cat.getCategoryPath());
        this.setName(cat.getName());
        this.setParentId(cat.getParentId());
        this.setImage(cat.getImage());
    }

    public List<CategoryVO> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryVO> children) {
        this.children = children;
    }


    public List<BrandVO> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<BrandVO> brandList) {
        this.brandList = brandList;
    }

    @Override
    public String toString() {
        return "CategoryVO{" +
                "children=" + children +
                ", brandList=" + brandList +
                '}';
    }
}
