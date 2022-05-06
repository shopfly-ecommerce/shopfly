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
package cloud.shopfly.b2c.core.aftersale.model.dos;

import cloud.shopfly.b2c.core.trade.sdk.model.OrderSkuDTO;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 退货商品表实体
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-02 15:42:57
 */
@Table(name = "es_refund_goods")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RefundGoodsDO implements Serializable {

    private static final long serialVersionUID = 1033900510490635L;

    public RefundGoodsDO() {

    }

    public RefundGoodsDO(OrderSkuDTO orderSku) {
        this.goodsId = orderSku.getGoodsId();
        this.skuId = orderSku.getSkuId();
        this.returnNum = orderSku.getNum();
        this.goodsSn = orderSku.getSkuSn();
        this.goodsName = orderSku.getName();
        this.goodsImage = orderSku.getGoodsImage();
        this.shipNum = orderSku.getNum();
        Gson gson = new Gson();
        this.specJson = gson.toJson(orderSku.getSpecList());
    }

    /**
     * 退货表id
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * 退货(款)编号
     */
    @Column(name = "refund_sn")
    @ApiModelProperty(name = "refund_sn", value = "退货(款)编号", required = false)
    private String refundSn;
    /**
     * 商品id
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "商品id", required = false)
    private Integer goodsId;
    /**
     * 产品id
     */
    @Column(name = "sku_id")
    @ApiModelProperty(name = "sku_id", value = "产品id", required = false)
    private Integer skuId;
    /**
     * 发货数量
     */
    @Column(name = "ship_num")
    @ApiModelProperty(name = "ship_num", value = "发货数量", required = false)
    private Integer shipNum;
    /**
     * 商品价格
     */
    @Column(name = "price")
    @ApiModelProperty(name = "price", value = "商品价格", required = false)
    private Double price;
    /**
     * 退货数量
     */
    @Column(name = "return_num")
    @ApiModelProperty(name = "return_num", value = "退货数量", required = false)
    private Integer returnNum;
    /**
     * 入库数量
     */
    @Column(name = "storage_num")
    @ApiModelProperty(name = "storage_num", value = "入库数量", required = false)
    private Integer storageNum;
    /**
     * 商品编号
     */
    @Column(name = "goods_sn")
    @ApiModelProperty(name = "goods_sn", value = "商品编号", required = false)
    private String goodsSn;
    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    @ApiModelProperty(name = "goods_name", value = "商品名称", required = false)
    private String goodsName;
    /**
     * 商品图片
     */
    @Column(name = "goods_image")
    @ApiModelProperty(name = "goods_image", value = "商品图片", required = false)
    private String goodsImage;

    /**
     * 规格数据
     */
    @Column(name = "spec_json")
    @ApiModelProperty(name = "spec_json", value = "规格数据", required = false)
    private String specJson;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRefundSn() {
        return refundSn;
    }

    public void setRefundSn(String refundSn) {
        this.refundSn = refundSn;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getShipNum() {
        return shipNum;
    }

    public void setShipNum(Integer shipNum) {
        this.shipNum = shipNum;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(Integer returnNum) {
        this.returnNum = returnNum;
    }

    public Integer getStorageNum() {
        return storageNum;
    }

    public void setStorageNum(Integer storageNum) {
        this.storageNum = storageNum;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getSpecJson() {
        return specJson;
    }

    public void setSpecJson(String specJson) {
        this.specJson = specJson;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RefundGoodsDO that = (RefundGoodsDO) o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (refundSn != null ? !refundSn.equals(that.refundSn) : that.refundSn != null) {
            return false;
        }
        if (goodsId != null ? !goodsId.equals(that.goodsId) : that.goodsId != null) {
            return false;
        }
        if (skuId != null ? !skuId.equals(that.skuId) : that.skuId != null) {
            return false;
        }
        if (shipNum != null ? !shipNum.equals(that.shipNum) : that.shipNum != null) {
            return false;
        }
        if (price != null ? !price.equals(that.price) : that.price != null) {
            return false;
        }
        if (returnNum != null ? !returnNum.equals(that.returnNum) : that.returnNum != null) {
            return false;
        }
        if (storageNum != null ? !storageNum.equals(that.storageNum) : that.storageNum != null) {
            return false;
        }
        if (goodsSn != null ? !goodsSn.equals(that.goodsSn) : that.goodsSn != null) {
            return false;
        }
        if (goodsName != null ? !goodsName.equals(that.goodsName) : that.goodsName != null) {
            return false;
        }
        if (goodsImage != null ? !goodsImage.equals(that.goodsImage) : that.goodsImage != null) {
            return false;
        }
        return specJson != null ? specJson.equals(that.specJson) : that.specJson == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (refundSn != null ? refundSn.hashCode() : 0);
        result = 31 * result + (goodsId != null ? goodsId.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (shipNum != null ? shipNum.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (returnNum != null ? returnNum.hashCode() : 0);
        result = 31 * result + (storageNum != null ? storageNum.hashCode() : 0);
        result = 31 * result + (goodsSn != null ? goodsSn.hashCode() : 0);
        result = 31 * result + (goodsName != null ? goodsName.hashCode() : 0);
        result = 31 * result + (goodsImage != null ? goodsImage.hashCode() : 0);
        result = 31 * result + (specJson != null ? specJson.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RefundGoodsDO{" +
                "id=" + id +
                ", refundSn='" + refundSn + '\'' +
                ", goodsId=" + goodsId +
                ", skuId=" + skuId +
                ", shipNum=" + shipNum +
                ", price=" + price +
                ", returnNum=" + returnNum +
                ", storageNum=" + storageNum +
                ", goodsSn='" + goodsSn + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsImage='" + goodsImage + '\'' +
                ", specJson='" + specJson + '\'' +
                '}';
    }


}