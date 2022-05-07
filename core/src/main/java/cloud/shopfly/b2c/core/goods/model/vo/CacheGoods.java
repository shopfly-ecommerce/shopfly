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

import cloud.shopfly.b2c.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Caching commodity objects
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018years3month29The morning of11:50:02
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CacheGoods implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3642358108471082387L;
    @ApiModelProperty(name = "goods_id", value = "productid")
    @Column(name = "goods_id")
    private Integer goodsId;

    @ApiModelProperty(name = "category_id", value = "Categoriesid")
    private Integer categoryId;

    @ApiModelProperty(name = "goods_name", value = "Name")
    @Column(name = "goods_name")
    private String goodsName;

    @ApiModelProperty(name = "sn", value = "SN")
    @Column(name = "sn")
    private String sn;

    @ApiModelProperty(name = "price", value = "Price")
    @Column(name = "price")
    private Double price;

    @ApiModelProperty(name = "weight", value = "Weight")
    @Column(name = "weight")
    private Double weight;

    @ApiModelProperty(name = "intro", value = "details")
    private String intro;

    @ApiModelProperty(name = "goods_transfee_charge", value = "Who bears the freight0：The buyer bears,1：The seller bear")
    @Column(name = "goods_transfee_charge")
    private Integer goodsTransfeeCharge;

    @ApiModelProperty(name = "template_id", value = "The freight templateid,不需要The freight template时值是0")
    @Column(name = "template_id")
    private Integer templateId;

    @ApiModelProperty(name = "market_enable", value = "Whether its on the shelf,1save0off")
    @Column(name = "market_enable")
    private Integer marketEnable;

    @ApiModelProperty(name = "disabled", value = "Whether to put in the recycle bin0 delete1未delete")
    @Column(name = "disabled")
    private Integer disabled;

    @ApiModelProperty(value = "Available")
    @Column(name = "enable_quantity")
    private Integer enableQuantity;

    @ApiModelProperty(name = "quantity", value = "Inventory")
    private Integer quantity;

    @ApiModelProperty(name = "sku_list", value = "skuThe list of")
    private List<GoodsSkuVO> skuList;

    @ApiModelProperty(name = "thumbnail", value = "Product thumbnail")
    private String thumbnail;

    @ApiModelProperty(name = "last_modify", value = "The last modification time of the product")
    private Long lastModify;

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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getGoodsTransfeeCharge() {
        return goodsTransfeeCharge;
    }

    public void setGoodsTransfeeCharge(Integer goodsTransfeeCharge) {
        this.goodsTransfeeCharge = goodsTransfeeCharge;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getMarketEnable() {
        return marketEnable;
    }

    public void setMarketEnable(Integer marketEnable) {
        this.marketEnable = marketEnable;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public List<GoodsSkuVO> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<GoodsSkuVO> skuList) {
        this.skuList = skuList;
    }

    public Integer getEnableQuantity() {
        return enableQuantity;
    }

    public void setEnableQuantity(Integer enableQuantity) {
        this.enableQuantity = enableQuantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Long getLastModify() {
        return lastModify;
    }

    public void setLastModify(Long lastModify) {
        this.lastModify = lastModify;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CacheGoods that = (CacheGoods) o;

        return new EqualsBuilder()
                .append(goodsId, that.goodsId)
                .append(categoryId, that.categoryId)
                .append(goodsName, that.goodsName)
                .append(sn, that.sn)
                .append(price, that.price)
                .append(weight, that.weight)
                .append(intro, that.intro)
                .append(goodsTransfeeCharge, that.goodsTransfeeCharge)
                .append(templateId, that.templateId)
                .append(marketEnable, that.marketEnable)
                .append(disabled, that.disabled)
                .append(enableQuantity, that.enableQuantity)
                .append(quantity, that.quantity)
//                .append(skuList, that.skuList)
                .append(thumbnail, that.thumbnail)
                .append(lastModify, that.lastModify)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(goodsId)
                .append(categoryId)
                .append(goodsName)
                .append(sn)
                .append(price)
                .append(weight)
                .append(intro)
                .append(goodsTransfeeCharge)
                .append(templateId)
                .append(marketEnable)
                .append(disabled)
                .append(enableQuantity)
                .append(quantity)
                //.append(skuList)
                .append(thumbnail)
                .append(lastModify)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "CacheGoods{" +
                "goodsId=" + goodsId +
                ", categoryId=" + categoryId +
                ", goodsName='" + goodsName + '\'' +
                ", sn='" + sn + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", intro='" + intro + '\'' +
                ", goodsTransfeeCharge=" + goodsTransfeeCharge +
                ", templateId=" + templateId +
                ", marketEnable=" + marketEnable +
                ", disabled=" + disabled +
                ", enableQuantity=" + enableQuantity +
                ", quantity=" + quantity +
                ", skuList=" + skuList +
                ", thumbnail='" + thumbnail + '\'' +
                ", lastModify=" + lastModify +
                '}';
    }
}
