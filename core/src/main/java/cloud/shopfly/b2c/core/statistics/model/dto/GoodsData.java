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
package cloud.shopfly.b2c.core.statistics.model.dto;

import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 统计库商品数据
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018/3/22 下午11:49
 */

public class GoodsData implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6108469000506420173L;

    @ApiModelProperty(value = "主键id")
    @Column(name = "id")
    private Integer id;

    @ApiModelProperty(value = "商品id")
    @Column(name = "goods_id")
    private Integer goodsId;

    @ApiModelProperty(value = "商品名称")
    @Column(name = "goods_name")
    private String goodsName;

    @ApiModelProperty(value = "品牌id")
    @Column(name = "brand_id")
    private Integer brandId;

    @ApiModelProperty(value = "分类id")
    @Column(name = "category_id")
    private Integer categoryId;

    @ApiModelProperty("分类路径")
    @Column(name = "category_path")
    private String categoryPath;

    @ApiModelProperty(value = "商品价格")
    @Column(name = "price")
    private Double price;


    @ApiModelProperty("收藏数量")
    @Column(name = "favorite_num")
    private Integer favoriteNum;

    @ApiModelProperty("是否上架 1 上架 0下架")
    @Column(name = "market_enable")
    private Integer marketEnable;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getFavoriteNum() {
        return favoriteNum;
    }

    public void setFavoriteNum(Integer favoriteNum) {
        this.favoriteNum = favoriteNum;
    }

    public Integer getMarketEnable() {
        return marketEnable;
    }

    public void setMarketEnable(Integer marketEnable) {
        this.marketEnable = marketEnable;
    }

    public GoodsData() {

    }

    public GoodsData(CacheGoods goods) {
        this.setGoodsId(goods.getGoodsId());
        this.setGoodsName(goods.getGoodsName());
        this.setCategoryId(goods.getCategoryId());
        this.setCategoryPath("");
        this.setFavoriteNum(0);
        this.setPrice(goods.getPrice());
        this.setMarketEnable(goods.getMarketEnable());
    }

    public GoodsData(CacheGoods goods, GoodsData gd) {
        this.setGoodsId(goods.getGoodsId());
        this.setGoodsName(goods.getGoodsName());
        this.setCategoryId(goods.getCategoryId());
        this.setPrice(goods.getPrice());
        this.setMarketEnable(goods.getMarketEnable());

        this.setCategoryPath(gd.getCategoryPath());
        this.setFavoriteNum(gd.getFavoriteNum());
        this.setId(gd.getId());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoodsData goodsData = (GoodsData) o;

        if (id != null ? !id.equals(goodsData.id) : goodsData.id != null) {
            return false;
        }
        if (goodsId != null ? !goodsId.equals(goodsData.goodsId) : goodsData.goodsId != null) {
            return false;
        }
        if (goodsName != null ? !goodsName.equals(goodsData.goodsName) : goodsData.goodsName != null) {
            return false;
        }
        if (brandId != null ? !brandId.equals(goodsData.brandId) : goodsData.brandId != null) {
            return false;
        }
        if (categoryId != null ? !categoryId.equals(goodsData.categoryId) : goodsData.categoryId != null) {
            return false;
        }
        if (categoryPath != null ? !categoryPath.equals(goodsData.categoryPath) : goodsData.categoryPath != null) {
            return false;
        }
        if (price != null ? !price.equals(goodsData.price) : goodsData.price != null) {
            return false;
        }
        if (favoriteNum != null ? !favoriteNum.equals(goodsData.favoriteNum) : goodsData.favoriteNum != null) {
            return false;
        }
        return marketEnable != null ? marketEnable.equals(goodsData.marketEnable) : goodsData.marketEnable == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (goodsId != null ? goodsId.hashCode() : 0);
        result = 31 * result + (goodsName != null ? goodsName.hashCode() : 0);
        result = 31 * result + (brandId != null ? brandId.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (categoryPath != null ? categoryPath.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (favoriteNum != null ? favoriteNum.hashCode() : 0);
        result = 31 * result + (marketEnable != null ? marketEnable.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GoodsData{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", brandId=" + brandId +
                ", categoryId=" + categoryId +
                ", categoryPath='" + categoryPath + '\'' +
                ", price=" + price +
                ", favoriteNum=" + favoriteNum +
                ", marketEnable=" + marketEnable +
                '}';
    }
}
