/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.goodssearch.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author fk
 * @version v2.0
 * @Description: 商品搜索传输对象
 * @date 2018/6/19 16:15
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsSearchDTO implements Serializable {

    @ApiModelProperty(name = "page_no", value = "页码")
    private Integer pageNo;
    @ApiModelProperty(name = "page_size", value = "每页数量")
    private Integer pageSize;
    @ApiModelProperty(name = "keyword", value = "关键字")
    private String keyword;
    @ApiModelProperty(name = "category", value = "分类")
    private Integer category;
    @ApiModelProperty(name = "brand", value = "品牌")
    private Integer brand;
    @ApiModelProperty(name = "price", value = "价格",example = "10_30")
    private String price;
    @ApiModelProperty(name = "sort", value = "排序:关键字_排序",allowableValues = "def_asc,def_desc,price_asc,price_desc,buynum_asc,buynum_desc,grade_asc,grade_desc")
    private String sort;
    @ApiModelProperty(name = "prop", value = "属性:参数名_参数值@参数名_参数值",example = "屏幕类型_LED@屏幕尺寸_15英寸")
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
