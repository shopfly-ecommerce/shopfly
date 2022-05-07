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
package cloud.shopfly.b2c.core.trade.order.model.dos;

import cloud.shopfly.b2c.core.goods.model.vo.SpecValueVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;


/**
 * Order goods list entity
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-10 16:05:21
 */
@Table(name = "es_order_items")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderItemsDO implements Serializable {

    private static final long serialVersionUID = 5055400562756190L;

    /**
     * A primary keyID
     */
    @Id(name = "item_id")
    @ApiModelProperty(hidden = true)
    private Integer itemId;
    /**
     * productID
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "productID", required = false)
    private Integer goodsId;
    /**
     * goodsID
     */
    @Column(name = "product_id")
    @ApiModelProperty(name = "product_id", value = "goodsID", required = false)
    private Integer productId;
    /**
     * sales
     */
    @Column(name = "num")
    @ApiModelProperty(name = "num", value = "sales", required = false)
    private Integer num;
    /**
     * shipments
     */
    @Column(name = "ship_num")
    @ApiModelProperty(name = "ship_num", value = "shipments", required = false)
    private Integer shipNum;
    /**
     * Transaction number
     */
    @Column(name = "trade_sn")
    @ApiModelProperty(name = "trade_sn", value = "Transaction number", required = false)
    private String tradeSn;
    /**
     * Order no.
     */
    @Column(name = "order_sn")
    @ApiModelProperty(name = "order_sn", value = "Order no.", required = false)
    private String orderSn;
    /**
     * Image
     */
    @Column(name = "image")
    @ApiModelProperty(name = "image", value = "Image", required = false)
    private String image;
    /**
     * Name
     */
    @Column(name = "name")
    @ApiModelProperty(name = "name", value = "Name", required = false)
    private String name;
    /**
     * The sales amount
     */
    @Column(name = "price")
    @ApiModelProperty(name = "price", value = "The sales amount", required = false)
    private Double price;
    /**
     * CategoriesID
     */
    @Column(name = "cat_id")
    @ApiModelProperty(name = "cat_id", value = "CategoriesID", required = false)
    private Integer catId;
    /**
     * Status
     */
    @Column(name = "state")
    @ApiModelProperty(name = "state", value = "Status", required = false)
    private Integer state;
    /**
     * The snapshotid
     */
    @Column(name = "snapshot_id")
    @ApiModelProperty(name = "snapshot_id", value = "The snapshotid", required = false)
    private Integer snapshotId;
    /**
     * specificationsjson
     */
    @Column(name = "spec_json")
    @ApiModelProperty(name = "spec_json", value = "specificationsjson", required = false)
    private String specJson;
    /**
     * Promotion type
     */
    @Column(name = "promotion_type")
    @ApiModelProperty(name = "promotion_type", value = "Promotion type", required = false)
    private String promotionType;
    /**
     * Sales promotionid
     */
    @Column(name = "promotion_id")
    @ApiModelProperty(name = "promotion_id", value = "Sales promotionid", required = false)
    private Integer promotionId;
    /**
     * Refundable amount
     */
    @Column(name = "refund_price")
    @ApiModelProperty(name = "refund_price", value = "Refundable amount", required = false)
    private Double refundPrice;

    @PrimaryKeyField
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getShipNum() {
        return shipNum;
    }

    public void setShipNum(Integer shipNum) {
        this.shipNum = shipNum;
    }

    public String getTradeSn() {
        return tradeSn;
    }

    public void setTradeSn(String tradeSn) {
        this.tradeSn = tradeSn;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(Integer snapshotId) {
        this.snapshotId = snapshotId;
    }

    public String getSpecJson() {
        return specJson;
    }

    public void setSpecJson(String specJson) {
        this.specJson = specJson;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public Double getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(Double refundPrice) {
        this.refundPrice = refundPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderItemsDO that = (OrderItemsDO) o;

        return new EqualsBuilder()
                .append(itemId, that.itemId)
                .append(goodsId, that.goodsId)
                .append(productId, that.productId)
                .append(num, that.num)
                .append(shipNum, that.shipNum)
                .append(tradeSn, that.tradeSn)
                .append(orderSn, that.orderSn)
                .append(image, that.image)
                .append(name, that.name)
                .append(price, that.price)
                .append(catId, that.catId)
                .append(state, that.state)
                .append(snapshotId, that.snapshotId)
                .append(specJson, that.specJson)
                .append(promotionType, that.promotionType)
                .append(promotionId, that.promotionId)
                .append(refundPrice, that.refundPrice)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(itemId)
                .append(goodsId)
                .append(productId)
                .append(num)
                .append(shipNum)
                .append(tradeSn)
                .append(orderSn)
                .append(image)
                .append(name)
                .append(price)
                .append(catId)
                .append(state)
                .append(snapshotId)
                .append(specJson)
                .append(promotionType)
                .append(promotionId)
                .append(refundPrice)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "OrderItemsDO{" +
                "itemId=" + itemId +
                ", goodsId=" + goodsId +
                ", productId=" + productId +
                ", num=" + num +
                ", shipNum=" + shipNum +
                ", tradeSn='" + tradeSn + '\'' +
                ", orderSn='" + orderSn + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", catId=" + catId +
                ", state=" + state +
                ", snapshotId=" + snapshotId +
                ", specJson='" + specJson + '\'' +
                ", promotionType='" + promotionType + '\'' +
                ", promotionId=" + promotionId +
                ", refundPrice=" + refundPrice +
                '}';
    }

    public OrderItemsDO() {

    }

    /**
     * Order goods constructor
     *
     * @param skuVO
     */
    public OrderItemsDO(CartSkuVO skuVO) {
        this.setGoodsId(skuVO.getGoodsId());
        this.setProductId(skuVO.getSkuId());
        this.setCatId(skuVO.getCatId());
        this.setImage(skuVO.getGoodsImage());
        this.setOrderSn(skuVO.getSkuSn());
        this.setName(skuVO.getName());
        this.setNum(skuVO.getNum());
        this.setPrice(skuVO.getPurchasePrice());
        List<SpecValueVO> specList = skuVO.getSpecList();
        String specJson = JsonUtil.objectToJson(specList);
        this.setSpecJson(specJson);

    }

}
