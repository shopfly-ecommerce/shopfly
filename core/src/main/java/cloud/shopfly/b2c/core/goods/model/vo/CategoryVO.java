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
package cloud.shopfly.b2c.core.goods.model.vo;

import cloud.shopfly.b2c.core.goods.model.dos.CategoryDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Categoryvo
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018years3month16On the afternoon4:53:23
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CategoryVO extends CategoryDO {

    private static final long serialVersionUID = 3843585201476087204L;

    @ApiModelProperty("Subclassification list")
    private List<CategoryVO> children;

    @ApiModelProperty("Category associated brand list")
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
