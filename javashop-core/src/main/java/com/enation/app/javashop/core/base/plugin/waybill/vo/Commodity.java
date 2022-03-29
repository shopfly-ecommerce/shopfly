/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.base.plugin.waybill.vo;

/**
 * 发送商品实体
 *
 * @author dongxin
 * @version v1.0
 * @since v6.4.0
 * 2017年8月12日 下午7:03:45
 */
public class Commodity {
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 件数
     */
    private Integer goodsquantity;
    /**
     * 商品重量kg
     */
    private Double goodsWeight;
    /**
     * 商品编码
     */
    private String goodsCode;
    /**
     * 商品描述
     */
    private String goodsDesc;
    /**
     * 商品体积m3
     */
    private Double goodsVol;
    /**
     * 商品价格
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
