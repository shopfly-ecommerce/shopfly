/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.fulldiscount.model.dos;

import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.PrimaryKeyField;
import dev.shopflix.framework.database.annotation.Table;
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
 * 满优惠赠品实体
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-30 17:34:46
 */
@Table(name="es_full_discount_gift")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FullDiscountGiftDO implements Serializable {

    private static final long serialVersionUID = 953152013470095L;

    /**赠品id*/
    @Id(name = "gift_id")
    @ApiModelProperty(hidden=true)
    private Integer giftId;

    /**赠品名称*/
    @Column(name = "gift_name")
    @ApiModelProperty(name="gift_name",value="赠品名称",required=false)
    private String giftName;

    /**赠品金额*/
    @Column(name = "gift_price")
    @ApiModelProperty(name="gift_price",value="赠品金额",required=false)
    private Double giftPrice;

    /**赠品图片*/
    @Column(name = "gift_img")
    @NotEmpty(message = "请上传赠品图片")
    @ApiModelProperty(name="gift_img",value="赠品图片",required=false)
    private String giftImg;

    /**库存*/
    @Column(name = "actual_store")
    @NotNull(message = "请填写库存")
    @ApiModelProperty(name="actual_store",value="库存",required=false)
    private Integer actualStore;

    /**赠品类型*/
    @Column(name = "gift_type")
    @ApiModelProperty(name="gift_type",value="赠品类型",required=false)
    private Integer giftType;

    /**可用库存*/
    @Column(name = "enable_store")
    @ApiModelProperty(name="enable_store",value="可用库存",required=false)
    private Integer enableStore;

    /**活动时间*/
    @Column(name = "create_time")
    @ApiModelProperty(name="create_time",value="活动时间",required=false)
    private Long createTime;

    /**活动商品id*/
    @Column(name = "goods_id")
    @ApiModelProperty(name="goods_id",value="活动商品id",required=false)
    private Integer goodsId;

    /**是否禁用*/
    @Column(name = "disabled")
    @ApiModelProperty(name="disabled",value="是否禁用",required=false)
    private Integer disabled;

    @PrimaryKeyField
    public Integer getGiftId() {
        return giftId;
    }
    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    public String getGiftName() {
        return giftName;
    }
    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public Double getGiftPrice() {
        return giftPrice;
    }
    public void setGiftPrice(Double giftPrice) {
        this.giftPrice = giftPrice;
    }

    public String getGiftImg() {
        return giftImg;
    }
    public void setGiftImg(String giftImg) {
        this.giftImg = giftImg;
    }

    public Integer getGiftType() {
        return giftType;
    }
    public void setGiftType(Integer giftType) {
        this.giftType = giftType;
    }

    public Integer getActualStore() {
        return actualStore;
    }
    public void setActualStore(Integer actualStore) {
        this.actualStore = actualStore;
    }

    public Integer getEnableStore() {
        return enableStore;
    }
    public void setEnableStore(Integer enableStore) {
        this.enableStore = enableStore;
    }

    public Long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getDisabled() {
        return disabled;
    }
    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "FullDiscountGiftDO{" +
                "giftId=" + giftId +
                ", giftName='" + giftName + '\'' +
                ", giftPrice=" + giftPrice +
                ", giftImg='" + giftImg + '\'' +
                ", actualStore=" + actualStore +
                ", giftType=" + giftType +
                ", enableStore=" + enableStore +
                ", createTime=" + createTime +
                ", goodsId=" + goodsId +
                ", disabled=" + disabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (o == null || getClass() != o.getClass()){
            return false;
        }

        FullDiscountGiftDO giftDO = (FullDiscountGiftDO) o;

        return new EqualsBuilder()
                .append(giftId, giftDO.giftId)
                .append(giftName, giftDO.giftName)
                .append(giftPrice, giftDO.giftPrice)
                .append(giftImg, giftDO.giftImg)
                .append(actualStore, giftDO.actualStore)
                .append(giftType, giftDO.giftType)
                .append(enableStore, giftDO.enableStore)
                .append(createTime, giftDO.createTime)
                .append(goodsId, giftDO.goodsId)
                .append(disabled, giftDO.disabled)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(giftId)
                .append(giftName)
                .append(giftPrice)
                .append(giftImg)
                .append(actualStore)
                .append(giftType)
                .append(enableStore)
                .append(createTime)
                .append(goodsId)
                .append(disabled)
                .toHashCode();
    }
}
