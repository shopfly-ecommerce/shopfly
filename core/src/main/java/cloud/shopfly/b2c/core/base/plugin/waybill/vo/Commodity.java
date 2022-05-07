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
package cloud.shopfly.b2c.core.base.plugin.waybill.vo;

/**
 * Sending commodity entity
 *
 * @author dongxin
 * @version v1.0
 * @since v6.4.0
 * 2017years8month12On the afternoon7:03:45
 */
public class Commodity {
    /**
     * Name
     */
    private String goodsName;
    /**
     * number
     */
    private Integer goodsquantity;
    /**
     * Weightkg
     */
    private Double goodsWeight;
    /**
     * Commodity code
     */
    private String goodsCode;
    /**
     * Commodity description
     */
    private String goodsDesc;
    /**
     * Volume of goodsm3
     */
    private Double goodsVol;
    /**
     * Price
     */
    private Double goodsPrice;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsquantity() {
        return goodsquantity;
    }

    public void setGoodsquantity(Integer goodsquantity) {
        this.goodsquantity = goodsquantity;
    }

    public Double getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(Double goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Double getGoodsVol() {
        return goodsVol;
    }

    public void setGoodsVol(Double goodsVol) {
        this.goodsVol = goodsVol;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "goodsName='" + goodsName + '\'' +
                ", goodsquantity=" + goodsquantity +
                ", goodsWeight=" + goodsWeight +
                ", goodsCode='" + goodsCode + '\'' +
                ", goodsDesc='" + goodsDesc + '\'' +
                ", goodsVol=" + goodsVol +
                ", goodsPrice=" + goodsPrice +
                '}';
    }
}
