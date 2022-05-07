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
package cloud.shopfly.b2c.core.goods.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Commodity inquiry conditions
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018years3month21On the afternoon3:46:04
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsQueryParam {
    /**
     * The page number
     */
    @ApiModelProperty(name = "page_no", value = "The page number", required = false)
    private Integer pageNo;
    /**
     * Number of pages
     */
    @ApiModelProperty(name = "page_size", value = "Number of pages", required = false)
    private Integer pageSize;
    /**
     * Whether the shelf0The representative has been removed from the shelves,1Representative is on the shelf
     */
    @ApiModelProperty(name = "market_enable", value = "Whether the shelf0The representative has been removed from the shelves,1Representative is on the shelf")
    @Min(value = 0 , message = "The audit status is incorrect")
    @Max(value = 2 , message = "The audit status is incorrect")
    private Integer marketEnable;

    /**
     * keyword
     */
    @ApiModelProperty(name = "keyword", value = "keyword")
    private String keyword;
    /**
     * Name
     */
    @ApiModelProperty(name = "goods_name", value = "Name")
    private String goodsName;
    /**
     * SN
     */
    @ApiModelProperty(name = "goods_sn", value = "SN")
    private String goodsSn;

    @ApiModelProperty(name = "category_path", value = "Commodity classification path, for example0|10|")
    private String categoryPath;

    @ApiModelProperty(name = "disabled", value = "0 Inquire about recycle bin goods")
    @Min(value = 0 , message = "Values are not correct")
    @Max(value = 0 , message = "Values are not correct")
    private Integer disabled;

    @ApiModelProperty(name = "goods_type", value = "TypeNORMAL Normal goodsPOINT Integral goods")
    private String  goodsType;


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

    public Integer getMarketEnable() {
        return marketEnable;
    }

    public void setMarketEnable(Integer marketEnable) {
        this.marketEnable = marketEnable;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }
}
