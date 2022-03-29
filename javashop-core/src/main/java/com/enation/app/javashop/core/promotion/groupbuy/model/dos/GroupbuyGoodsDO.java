/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.groupbuy.model.dos;

import com.enation.app.javashop.framework.database.annotation.*;
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
 * 团购商品实体
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
     * 团购商品Id
     */
    @Id(name = "gb_id")
    @ApiModelProperty(hidden = true)
    private Integer gbId;

    /**
     * 货品Id
     */
    @Column(name = "sku_id")
    @ApiModelProperty(name = "sku_id", value = "货品Id", required = false)
    private Integer skuId;

    /**
     * 活动Id
     */
    @Column(name = "act_id")
    @NotNull(message = "请选择要参与的团购活动")
    @ApiModelProperty(name = "act_id", value = "活动Id", required = false)
    private Integer actId;

    /**
     * cat_id
     */
    @Column(name = "cat_id")
    @NotNull(message = "请选择团购分类")
    @ApiModelProperty(name = "cat_id", value = "分类id")
    private Integer catId;

    /**
     * 团购名称
     */
    @Column(name = "gb_name")
    @NotEmpty(message = "请填写团购名称")
    @ApiModelProperty(name = "gb_name", value = "团购名称", required = false)
    private String gbName;

    /**
     * 副标题
     */
    @Column(name = "gb_title")
    @ApiModelProperty(name = "gb_title", value = "副标题", required = false)
    private String gbTitle;

    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    @NotEmpty(message = "请选择商品")
    @ApiModelProperty(name = "goods_name", value = "商品名称", required = false)
    private String goodsName;

    /**
     * 商品Id
     */
    @Column(name = "goods_id")
    @NotNull(message = "请选择商品")
    @ApiModelProperty(name = "goods_id", value = "商品Id", required = false)
    private Integer goodsId;

    /**
     * 原始价格
     */
    @Column(name = "original_price")
    @NotNull(message = "请选择商品")
    @ApiModelProperty(name = "original_price", value = "原始价格", required = false)
    private Double originalPrice;

    /**
     * 团购价格
     */
    @Column(name = "price")
    @NotNull(message = "请输入团购价格")
    @ApiModelProperty(name = "price", value = "团购价格", required = false)
    private Double price;

    /**
     * 图片地址
     */
    @Column(name = "img_url")
    @NotEmpty(message = "请上传团购图片")
    @ApiModelProperty(name = "img_url", value = "图片地址", required = false)
    private String imgUrl;

    /**
     * 商品总数
     */
    @Column(name = "goods_num")
    @NotNull(message = "请输入商品总数")
    @ApiModelProperty(name = "goods_num", value = "商品总数", required = false)
    private Integer goodsNum;

    /**
     * 虚拟数量
     */
    @Column(name = "visual_num")
    @NotNull(message = "请输入虚拟数量")
    @ApiModelProperty(name = "visual_num", value = "虚拟数量", required = false)
    private Integer visualNum;

    /**
     * 限购数量
     */
    @Column(name = "limit_num")
    @NotNull(message = "请输入限购数量")
    @ApiModelProperty(name = "limit_num", value = "限购数量", required = false)
    private Integer limitNum;

    /**
     * 已团购数量
     */
    @Column(name = "buy_num")
    @ApiModelProperty(name = "buy_num", value = "已团购数量", required = false)
    private Integer buyNum;

    /**
     * 浏览数量
     */
    @Column(name = "view_num")
    @ApiModelProperty(name = "view_num", value = "浏览数量", required = false)
    private Integer viewNum;

    /**
     * 介绍
     */
    @Column(name = "remark")
    @ApiModelProperty(name = "remark", value = "介绍", required = false)
    private String remark;

    /**
     * 状态
     */
    @Column(name = "gb_status")
    @ApiModelProperty(name = "gb_status", value = "状态", required = false)
    private Integer gbStatus;

    /**
     * 添加时间
     */
    @Column(name = "add_time")
    @ApiModelProperty(name = "add_time", value = "添加时间", required = false)
    private Long addTime;

    /**
     * wap缩略图
     */
    @Column(name = "wap_thumbnail")
    @ApiModelProperty(name = "wap_thumbnail", value = "wap缩略图", required = false)
    private String wapThumbnail;

    /**
     * pc缩略图
     */
    @Column(name = "thumbnail")
    @ApiModelProperty(name = "thumbnail", value = "pc缩略图", required = false)
    private String thumbnail;


    /**
     * 地区Id
     */
    @Column(name = "area_id")
    @ApiModelProperty(name = "area_id", value = "地区Id", required = false)
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
     * 获取显示对购买数量=真实购买数量+虚拟购买数量
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
