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
package cloud.shopfly.b2c.core.promotion.groupbuy.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Group purchase commodity entity
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 16:57:26
 */
@Table(name = "es_groupbuy_goods")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GroupbuyGoodsDO implements Serializable {

    private static final long serialVersionUID = 870913884116003L;

    /**
     * A bulk goodsId
     */
    @Id(name = "gb_id")
    @ApiModelProperty(hidden = true)
    private Integer gbId;

    /**
     * goodsId
     */
    @Column(name = "sku_id")
    @ApiModelProperty(name = "sku_id", value = "goodsId", required = false)
    private Integer skuId;

    /**
     * activityId
     */
    @Column(name = "act_id")
    @NotNull(message = "Please select the group purchase activity to participate in")
    @ApiModelProperty(name = "act_id", value = "activityId", required = false)
    private Integer actId;

    /**
     * cat_id
     */
    @Column(name = "cat_id")
    @NotNull(message = "Please select group purchase category")
    @ApiModelProperty(name = "cat_id", value = "Categoriesid")
    private Integer catId;

    /**
     * Group name
     */
    @Column(name = "gb_name")
    @NotEmpty(message = "Please fill in the name of the group purchase")
    @ApiModelProperty(name = "gb_name", value = "Group name", required = false)
    private String gbName;

    /**
     * subtitle
     */
    @Column(name = "gb_title")
    @ApiModelProperty(name = "gb_title", value = "subtitle", required = false)
    private String gbTitle;

    /**
     * Name
     */
    @Column(name = "goods_name")
    @NotEmpty(message = "Please select goods")
    @ApiModelProperty(name = "goods_name", value = "Name", required = false)
    private String goodsName;

    /**
     * productId
     */
    @Column(name = "goods_id")
    @NotNull(message = "Please select goods")
    @ApiModelProperty(name = "goods_id", value = "productId", required = false)
    private Integer goodsId;

    /**
     * The original price
     */
    @Column(name = "original_price")
    @NotNull(message = "Please select goods")
    @ApiModelProperty(name = "original_price", value = "The original price", required = false)
    private Double originalPrice;

    /**
     * Group purchase price
     */
    @Column(name = "price")
    @NotNull(message = "Please enter group purchase price")
    @ApiModelProperty(name = "price", value = "Group purchase price", required = false)
    private Double price;

    /**
     * Picture address
     */
    @Column(name = "img_url")
    @NotEmpty(message = "Please upload pictures of group purchase")
    @ApiModelProperty(name = "img_url", value = "Picture address", required = false)
    private String imgUrl;

    /**
     * The total number of goods
     */
    @Column(name = "goods_num")
    @NotNull(message = "Please enter the total number of goods")
    @ApiModelProperty(name = "goods_num", value = "The total number of goods", required = false)
    private Integer goodsNum;

    /**
     * Number of virtual
     */
    @Column(name = "visual_num")
    @NotNull(message = "Please enter the virtual number")
    @ApiModelProperty(name = "visual_num", value = "Number of virtual", required = false)
    private Integer visualNum;

    /**
     * The amount for purchasing
     */
    @Column(name = "limit_num")
    @NotNull(message = "Please enter the limit quantity")
    @ApiModelProperty(name = "limit_num", value = "The amount for purchasing", required = false)
    private Integer limitNum;

    /**
     * Number of Group purchases
     */
    @Column(name = "buy_num")
    @ApiModelProperty(name = "buy_num", value = "Number of Group purchases", required = false)
    private Integer buyNum;

    /**
     * Browse the number
     */
    @Column(name = "view_num")
    @ApiModelProperty(name = "view_num", value = "Browse the number", required = false)
    private Integer viewNum;

    /**
     * introduce
     */
    @Column(name = "remark")
    @ApiModelProperty(name = "remark", value = "introduce", required = false)
    private String remark;

    /**
     * Status
     */
    @Column(name = "gb_status")
    @ApiModelProperty(name = "gb_status", value = "Status", required = false)
    private Integer gbStatus;

    /**
     * Add the time
     */
    @Column(name = "add_time")
    @ApiModelProperty(name = "add_time", value = "Add the time", required = false)
    private Long addTime;

    /**
     * wapThe thumbnail
     */
    @Column(name = "wap_thumbnail")
    @ApiModelProperty(name = "wap_thumbnail", value = "wapThe thumbnail", required = false)
    private String wapThumbnail;

    /**
     * pcThe thumbnail
     */
    @Column(name = "thumbnail")
    @ApiModelProperty(name = "thumbnail", value = "pcThe thumbnail", required = false)
    private String thumbnail;


    /**
     * regionId
     */
    @Column(name = "area_id")
    @ApiModelProperty(name = "area_id", value = "regionId", required = false)
    private Integer areaId;

    @PrimaryKeyField
    public Integer getGbId() {
        return gbId;
    }

    public void setGbId(Integer gbId) {
        this.gbId = gbId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getGbName() {
        return gbName;
    }

    public void setGbName(String gbName) {
        this.gbName = gbName;
    }

    public String getGbTitle() {
        return gbTitle;
    }

    public void setGbTitle(String gbTitle) {
        this.gbTitle = gbTitle;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Integer getVisualNum() {
        return visualNum;
    }

    public void setVisualNum(Integer visualNum) {
        this.visualNum = visualNum;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public Integer getBuyNum() {
        if (buyNum == null) {
            buyNum = 0;
        }
        return buyNum;
    }

    /**
     * Get the display on the purchase quantity=Actual purchase quantity+Virtual purchase quantity
     *
     * @return
     */
    @NotDbField
    public Integer getShowBuyNum() {
        if (buyNum == null) {
            buyNum = 0;
        }
        if (visualNum == null) {
            visualNum = 0;
        }
        return buyNum + visualNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getGbStatus() {
        return gbStatus;
    }

    public void setGbStatus(Integer gbStatus) {
        this.gbStatus = gbStatus;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public String getWapThumbnail() {
        return wapThumbnail;
    }

    public void setWapThumbnail(String wapThumbnail) {
        this.wapThumbnail = wapThumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "GroupbuyGoodsDO{" +
                "gbId=" + gbId +
                ", skuId=" + skuId +
                ", actId=" + actId +
                ", catId=" + catId +
                ", gbName='" + gbName + '\'' +
                ", gbTitle='" + gbTitle + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsId=" + goodsId +
                ", originalPrice=" + originalPrice +
                ", price=" + price +
                ", imgUrl='" + imgUrl + '\'' +
                ", goodsNum=" + goodsNum +
                ", visualNum=" + visualNum +
                ", limitNum=" + limitNum +
                ", buyNum=" + buyNum +
                ", viewNum=" + viewNum +
                ", remark='" + remark + '\'' +
                ", gbStatus=" + gbStatus +
                ", addTime=" + addTime +
                ", wapThumbnail='" + wapThumbnail + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", areaId=" + areaId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GroupbuyGoodsDO goodsDO = (GroupbuyGoodsDO) o;

        return new EqualsBuilder()
                .append(gbId, goodsDO.gbId)
                .append(skuId, goodsDO.skuId)
                .append(actId, goodsDO.actId)
                .append(catId, goodsDO.catId)
                .append(gbName, goodsDO.gbName)
                .append(gbTitle, goodsDO.gbTitle)
                .append(goodsName, goodsDO.goodsName)
                .append(goodsId, goodsDO.goodsId)
                .append(originalPrice, goodsDO.originalPrice)
                .append(price, goodsDO.price)
                .append(imgUrl, goodsDO.imgUrl)
                .append(goodsNum, goodsDO.goodsNum)
                .append(visualNum, goodsDO.visualNum)
                .append(limitNum, goodsDO.limitNum)
                .append(buyNum, goodsDO.buyNum)
                .append(viewNum, goodsDO.viewNum)
                .append(remark, goodsDO.remark)
                .append(gbStatus, goodsDO.gbStatus)
                .append(addTime, goodsDO.addTime)
                .append(wapThumbnail, goodsDO.wapThumbnail)
                .append(thumbnail, goodsDO.thumbnail)
                .append(areaId, goodsDO.areaId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(gbId)
                .append(skuId)
                .append(actId)
                .append(catId)
                .append(gbName)
                .append(gbTitle)
                .append(goodsName)
                .append(goodsId)
                .append(originalPrice)
                .append(price)
                .append(imgUrl)
                .append(goodsNum)
                .append(visualNum)
                .append(limitNum)
                .append(buyNum)
                .append(viewNum)
                .append(remark)
                .append(gbStatus)
                .append(addTime)
                .append(wapThumbnail)
                .append(thumbnail)
                .append(areaId)
                .toHashCode();
    }
}
