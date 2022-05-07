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
package cloud.shopfly.b2c.core.goodssearch.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author fk
 * @version v2.0
 * @Description: Commodity search transfer object
 * @date 2018/6/19 16:15
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsSearchDTO implements Serializable {

    @ApiModelProperty(name = "page_no", value = "The page number")
    private Integer pageNo;
    @ApiModelProperty(name = "page_size", value = "Number each page")
    private Integer pageSize;
    @ApiModelProperty(name = "keyword", value = "keyword")
    private String keyword;
    @ApiModelProperty(name = "category", value = "Categories")
    private Integer category;
    @ApiModelProperty(name = "brand", value = "brand")
    private Integer brand;
    @ApiModelProperty(name = "price", value = "Price",example = "10_30")
    private String price;
    @ApiModelProperty(name = "sort", value = "sort:keyword_sort",allowableValues = "def_asc,def_desc,price_asc,price_desc,buynum_asc,buynum_desc,grade_asc,grade_desc")
    private String sort;
    @ApiModelProperty(name = "prop", value = "attribute:Parameter names_The parameter value@Parameter names_The parameter value",example = "Screen type_LED@The screen size_15inches")
    private String prop;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getBrand() {
        return brand;
    }

    public void setBrand(Integer brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    @Override
    public String toString() {
        return "GoodsSearchDTO{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", keyword='" + keyword + '\'' +
                ", category='" + category + '\'' +
                ", brand=" + brand +
                ", price='" + price + '\'' +
                ", sort='" + sort + '\'' +
                ", prop='" + prop + '\'' +
                '}';
    }
}
