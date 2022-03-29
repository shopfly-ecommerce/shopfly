/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.seckill.model.vo;

/**
 * 注释
 *
 * @author Snow create in 2018/3/20
 * @version v2.0
 * @since v7.0.0
 */
@SuppressWarnings("AlibabaPojoMustOverrideToString")
public class SeckillConvertGoodsVO {

    private String goodsName;
    private Double price;
    private String thumbnail;
    private Integer goodsId;
    private Integer soldNum;

    @Override
    public String toString() {
        return "SeckillConvertGoodsVO{" +
                "goodsName='" + goodsName + '\'' +
                ", price=" + price +
                ", thumbnail='" + thumbnail + '\'' +
                ", goodsId=" + goodsId +
                ", soldNum=" + soldNum +
                '}';
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(Integer soldNum) {
        this.soldNum = soldNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
