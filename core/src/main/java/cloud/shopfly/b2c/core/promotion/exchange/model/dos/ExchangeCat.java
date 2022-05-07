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
package cloud.shopfly.b2c.core.promotion.exchange.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * Points exchange classification entity
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-29 16:56:22
 */
@Table(name="es_exchange_cat")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExchangeCat implements Serializable {

    private static final long serialVersionUID = 8629683606901701L;

    /**Categoriesid*/
    @Id(name = "category_id")
    @ApiModelProperty(hidden=true)
    private Integer categoryId;
    /**name*/
    @Column(name = "name")
    @ApiModelProperty(name="name",value="name",required=false)
    private String name;
    /**The parent category*/
    @Column(name = "parent_id")
    @ApiModelProperty(name="parent_id",value="The parent category",required=false)
    private Integer parentId;
    /**CategoriesidThe path*/
    @Column(name = "category_path")
    @ApiModelProperty(name="category_path",value="CategoriesidThe path",required=false)
    private String categoryPath;
    /**The number*/
    @Column(name = "goods_count")
    @ApiModelProperty(name="goods_count",value="The number",required=false)
    private Integer goodsCount;
    /**sort*/
    @Column(name = "category_order")
    @ApiModelProperty(name="category_order",value="sort",required=false)
    private Integer categoryOrder;
    /**Whether to display on the page*/
    @Column(name = "list_show")
    @ApiModelProperty(name="list_show",value="Whether to display on the page",required=false,example = "1For display,0For not showing")
    private Integer listShow;
    /**Image*/
    @Column(name = "image")
    @ApiModelProperty(name="image",value="Image",required=false)
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

    public Integer getListShow() {
        return listShow;
    }
    public void setListShow(Integer listShow) {
        this.listShow = listShow;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }


	@Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        ExchangeCat that = (ExchangeCat) o;
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) {return false;}
        if (name != null ? !name.equals(that.name) : that.name != null) {return false;}
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) {return false;}
        if (categoryPath != null ? !categoryPath.equals(that.categoryPath) : that.categoryPath != null) {return false;}
        if (goodsCount != null ? !goodsCount.equals(that.goodsCount) : that.goodsCount != null) {return false;}
        if (categoryOrder != null ? !categoryOrder.equals(that.categoryOrder) : that.categoryOrder != null) {return false;}
        if (listShow != null ? !listShow.equals(that.listShow) : that.listShow != null) {return false;}
        return image != null ? image.equals(that.image) : that.image == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (categoryPath != null ? categoryPath.hashCode() : 0);
        result = 31 * result + (goodsCount != null ? goodsCount.hashCode() : 0);
        result = 31 * result + (categoryOrder != null ? categoryOrder.hashCode() : 0);
        result = 31 * result + (listShow != null ? listShow.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExchangeCat{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", categoryPath='" + categoryPath + '\'' +
                ", goodsCount=" + goodsCount +
                ", categoryOrder=" + categoryOrder +
                ", listShow=" + listShow +
                ", image='" + image + '\'' +
                '}';
    }


}
