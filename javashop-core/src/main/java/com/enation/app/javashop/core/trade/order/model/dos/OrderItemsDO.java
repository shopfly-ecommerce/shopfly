/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.order.model.dos;

import com.enation.app.javashop.core.goods.model.vo.SpecValueVO;
import com.enation.app.javashop.core.trade.cart.model.vo.CartSkuVO;
import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.PrimaryKeyField;
import com.enation.app.javashop.framework.database.annotation.Table;
import com.enation.app.javashop.framework.util.JsonUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;


/**
 * 订单货物表实体
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
     * 主键ID
     */
    @Id(name = "item_id")
    @ApiModelProperty(hidden = true)
    private Integer itemId;
    /**
     * 商品ID
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "商品ID", required = false)
    private Integer goodsId;
    /**
     * 货品ID
     */
    @Column(name = "product_id")
    @ApiModelProperty(name = "product_id", value = "货品ID", required = false)
    private Integer productId;
    /**
     * 销售量
     */
    @Column(name = "num")
    @ApiModelProperty(name = "num", value = "销售量", required = false)
    private Integer num;
    /**
     * 发货量
     */
    @Column(name = "ship_num")
    @ApiModelProperty(name = "ship_num", value = "发货量", required = false)
    private Integer shipNum;
    /**
     * 交易编号
     */
    @Column(name = "trade_sn")
    @ApiModelProperty(name = "trade_sn", value = "交易编号", required = false)
    private String tradeSn;
    /**
     * 订单编号
     */
    @Column(name = "order_sn")
    @ApiModelProperty(name = "order_sn", value = "订单编号", required = false)
    private String orderSn;
    /**
     * 图片
     */
    @Column(name = "image")
    @ApiModelProperty(name = "image", value = "图片", required = false)
    private String image;
    /**
     * 商品名称
     */
    @Column(name = "name")
    @ApiModelProperty(name = "name", value = "商品名称", required = false)
    private String name;
    /**
     * 销售金额
     */
    @Column(name = "price")
    @ApiModelProperty(name = "price", value = "销售金额", required = false)
    private Double price;
    /**
     * 分类ID
     */
    @Column(name = "cat_id")
    @ApiModelProperty(name = "cat_id", value = "分类ID", required = false)
    private Integer catId;
    /**
     * 状态
     */
    @Column(name = "state")
    @ApiModelProperty(name = "state", value = "状态", required = false)
    private Integer state;
    /**
     * 快照id
     */
    @Column(name = "snapshot_id")
    @ApiModelProperty(name = "snapshot_id", value = "快照id", required = false)
    private Integer snapshotId;
    /**
     * 规格json
     */
    @Column(name = "spec_json")
    @ApiModelProperty(name = "spec_json", value = "规格json", required = false)
    private String specJson;
    /**
     * 促销类型
     */
    @Column(name = "promotion_type")
    @ApiModelProperty(name = "promotion_type", value = "促销类型", required = false)
    private String promotionType;
    /**
     * 促销id
     */
    @Column(name = "promotion_id")
    @ApiModelProperty(name = "promotion_id", value = "促销id", required = false)
    private Integer promotionId;
    /**
     * 可退款金额
     */
    @Column(name = "refund_price")
    @ApiModelProperty(name = "refund_price", value = "可退款金额", required = false)
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
     * 订单货物构造器
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
