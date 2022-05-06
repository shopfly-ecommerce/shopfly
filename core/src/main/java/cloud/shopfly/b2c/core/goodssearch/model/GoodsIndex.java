/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goodssearch.model;

import cloud.shopfly.b2c.framework.elasticsearch.EsSettings;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * Created by kingapex on 2018/7/19.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/7/19
 */
@Document(indexName = "#{esConfig.indexName}_"+  EsSettings.GOODS_INDEX_NAME, type = EsSettings.GOODS_TYPE_NAME)
public class GoodsIndex {
    public GoodsIndex() {

    }

    @Id
    private Integer goodsId;

    @Field(type = FieldType.text)
    private String goodsName;

    @Field(type = FieldType.text)
    private String thumbnail;

    @Field(type = FieldType.text)
    private String small;

    @Field(type = FieldType.Integer)
    private Integer buyCount;

    @Field(type = FieldType.Integer)
    private Integer commentNum;

    @Field(type = FieldType.Double)
    private Double grade;

    @Field(type = FieldType.Double)
    private double discountPrice;

    @Field(type = FieldType.Double)
    private double price;

    @Field(type = FieldType.Integer)
    private Integer brand;

    @Field(type = FieldType.Integer)
    private Integer categoryId;

    @Field(type = FieldType.text)
    private String categoryPath;

    @Field(type = FieldType.Integer)
    private Integer disabled;

    @Field(type = FieldType.Integer)
    private Integer marketEnable;

    @Field(type = FieldType.text)
    private String intro;

    /**
     * 是否自营商品 0否 1是
     */
    @Field(type = FieldType.Integer)
    private Integer selfOperated;

    @Field(type = FieldType.Nested, index = true, store = true)
    private List<Param> params;

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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getBrand() {
        return brand;
    }

    public void setBrand(Integer brand) {
        this.brand = brand;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public Integer getMarketEnable() {
        return marketEnable;
    }

    public void setMarketEnable(Integer marketEnable) {
        this.marketEnable = marketEnable;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }


    public Integer getSelfOperated() {
        return selfOperated;
    }

    public void setSelfOperated(Integer selfOperated) {
        this.selfOperated = selfOperated;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    @Override
    public String toString() {
        return "GoodsIndex{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", small='" + small + '\'' +
                ", buyCount=" + buyCount +
                ", commentNum=" + commentNum +
                ", grade=" + grade +
                ", discountPrice=" + discountPrice +
                ", price=" + price +
                ", brand=" + brand +
                ", categoryId=" + categoryId +
                ", categoryPath='" + categoryPath + '\'' +
                ", disabled=" + disabled +
                ", marketEnable=" + marketEnable +
                ", intro='" + intro + '\'' +
                ", selfOperated=" + selfOperated +
                ", params=" + params +
                '}';
    }
}