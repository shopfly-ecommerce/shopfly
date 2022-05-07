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
package cloud.shopfly.b2c.core.trade.snapshot.model;

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
 * Transaction snapshot entity
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-08-01 14:55:26
 */
@Table(name = "es_goods_snapshot")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsSnapshot implements Serializable {

    private static final long serialVersionUID = 8525261256312385L;

    /**
     * A primary key
     */
    @Id(name = "snapshot_id")
    @ApiModelProperty(hidden = true)
    private Integer snapshotId;
    /**
     * productid
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "productid", required = false)
    private Integer goodsId;
    /**
     * Name
     */
    @Column(name = "name")
    @ApiModelProperty(name = "name", value = "Name", required = false)
    private String name;
    /**
     * SN
     */
    @Column(name = "sn")
    @ApiModelProperty(name = "sn", value = "SN", required = false)
    private String sn;
    /**
     *  name
     */
    @Column(name = "brand_name")
    @ApiModelProperty(name = "brand_name", value = " name", required = false)
    private String brandName;
    /**
     * name
     */
    @Column(name = "category_name")
    @ApiModelProperty(name = "category_name", value = "name", required = false)
    private String categoryName;
    /**
     * Type
     */
    @Column(name = "goods_type")
    @ApiModelProperty(name = "goods_type", value = "Type", required = false)
    private String goodsType;
    /**
     * Weight
     */
    @Column(name = "weight")
    @ApiModelProperty(name = "weight", value = "Weight", required = false)
    private Double weight;
    /**
     * Product details
     */
    @Column(name = "intro")
    @ApiModelProperty(name = "intro", value = "Product details", required = false)
    private String intro;
    /**
     * Price
     */
    @Column(name = "price")
    @ApiModelProperty(name = "price", value = "Price", required = false)
    private Double price;
    /**
     * The cost of goods
     */
    @Column(name = "cost")
    @ApiModelProperty(name = "cost", value = "The cost of goods", required = false)
    private Double cost;
    /**
     * Commodity market price
     */
    @Column(name = "mktprice")
    @ApiModelProperty(name = "mktprice", value = "Commodity market price", required = false)
    private Double mktprice;
    /**
     * Whether the specification of the product is enabled1 open0 未open
     */
    @Column(name = "have_spec")
    @ApiModelProperty(name = "have_spec", value = "Whether the specification of the product is enabled1 open0 未open", required = false)
    private Integer haveSpec;
    /**
     * parameterjson
     */
    @Column(name = "params_json")
    @ApiModelProperty(name = "params_json", value = "parameterjson", required = false)
    private String paramsJson;

    /**
     * Sales promotionjson
     */
    @Column(name = "promotion_json")
    @ApiModelProperty(name = "promotion_json", value = "Sales promotionjson", required = false)
    private String promotionJson;

    /**
     * couponsjson
     */
    @Column(name = "coupon_json")
    @ApiModelProperty(name = "coupon_json", value = "couponsjson", required = false)
    private String couponJson;

    /**
     * Imagejson
     */
    @Column(name = "img_json")
    @ApiModelProperty(name = "img_json", value = "Imagejson", required = false)
    private String imgJson;
    /**
     * A snapshot of time
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "A snapshot of time", required = false)
    private Long createTime;
    /**
     * Credits for Merchandise use
     */
    @Column(name = "point")
    @ApiModelProperty(name = "point", value = "Credits for Merchandise use", required = false)
    private Integer point;


    @PrimaryKeyField
    public Integer getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(Integer snapshotId) {
        this.snapshotId = snapshotId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getMktprice() {
        return mktprice;
    }

    public void setMktprice(Double mktprice) {
        this.mktprice = mktprice;
    }

    public Integer getHaveSpec() {
        return haveSpec;
    }

    public void setHaveSpec(Integer haveSpec) {
        this.haveSpec = haveSpec;
    }

    public String getParamsJson() {
        return paramsJson;
    }

    public void setParamsJson(String paramsJson) {
        this.paramsJson = paramsJson;
    }

    public String getImgJson() {
        return imgJson;
    }

    public void setImgJson(String imgJson) {
        this.imgJson = imgJson;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getPromotionJson() {
        return promotionJson;
    }

    public void setPromotionJson(String promotionJson) {
        this.promotionJson = promotionJson;
    }

    public String getCouponJson() {
        return couponJson;
    }

    public void setCouponJson(String couponJson) {
        this.couponJson = couponJson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GoodsSnapshot that = (GoodsSnapshot) o;
        if (snapshotId != null ? !snapshotId.equals(that.snapshotId) : that.snapshotId != null) {
            return false;
        }
        if (goodsId != null ? !goodsId.equals(that.goodsId) : that.goodsId != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (sn != null ? !sn.equals(that.sn) : that.sn != null) {
            return false;
        }
        if (brandName != null ? !brandName.equals(that.brandName) : that.brandName != null) {
            return false;
        }
        if (categoryName != null ? !categoryName.equals(that.categoryName) : that.categoryName != null) {
            return false;
        }
        if (goodsType != null ? !goodsType.equals(that.goodsType) : that.goodsType != null) {
            return false;
        }
        if (weight != null ? !weight.equals(that.weight) : that.weight != null) {
            return false;
        }
        if (intro != null ? !intro.equals(that.intro) : that.intro != null) {
            return false;
        }
        if (price != null ? !price.equals(that.price) : that.price != null) {
            return false;
        }
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) {
            return false;
        }
        if (mktprice != null ? !mktprice.equals(that.mktprice) : that.mktprice != null) {
            return false;
        }
        if (haveSpec != null ? !haveSpec.equals(that.haveSpec) : that.haveSpec != null) {
            return false;
        }
        if (paramsJson != null ? !paramsJson.equals(that.paramsJson) : that.paramsJson != null) {
            return false;
        }
        if (imgJson != null ? !imgJson.equals(that.imgJson) : that.imgJson != null) {
            return false;
        }
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) {
            return false;
        }
        if (point != null ? !point.equals(that.point) : that.point != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (snapshotId != null ? snapshotId.hashCode() : 0);
        result = 31 * result + (goodsId != null ? goodsId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sn != null ? sn.hashCode() : 0);
        result = 31 * result + (brandName != null ? brandName.hashCode() : 0);
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        result = 31 * result + (goodsType != null ? goodsType.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (intro != null ? intro.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (mktprice != null ? mktprice.hashCode() : 0);
        result = 31 * result + (haveSpec != null ? haveSpec.hashCode() : 0);
        result = 31 * result + (paramsJson != null ? paramsJson.hashCode() : 0);
        result = 31 * result + (imgJson != null ? imgJson.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (point != null ? point.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GoodsSnapshot{" +
                "snapshotId=" + snapshotId +
                ", goodsId=" + goodsId +
                ", name='" + name + '\'' +
                ", sn='" + sn + '\'' +
                ", brandName='" + brandName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", goodsType='" + goodsType + '\'' +
                ", weight=" + weight +
                ", intro='" + intro + '\'' +
                ", price=" + price +
                ", cost=" + cost +
                ", mktprice=" + mktprice +
                ", haveSpec=" + haveSpec +
                ", paramsJson='" + paramsJson + '\'' +
                ", imgJson='" + imgJson + '\'' +
                ", createTime=" + createTime +
                ", point=" + point +
                '}';
    }


}
