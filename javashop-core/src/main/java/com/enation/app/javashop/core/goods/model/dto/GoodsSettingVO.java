/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.goods.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author fk
 * @version v2.0
 * @Description: 商品相关设置vo
 * @date 2018/4/911:10
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsSettingVO implements Serializable {

    @ApiModelProperty(name = "thumbnail_width", value = "缩略图宽度", required = true)
    @NotNull(message = "缩略图宽度不能为空")
    private Integer thumbnailWidth;

    @ApiModelProperty(name = "thumbnail_height", value = "缩略图高度", required = true)
    @NotNull(message = "缩略图高度不能为空")
    private Integer thumbnailHeight;

    @ApiModelProperty(name = "small_width", value = "小图宽度", required = true)
    @NotNull(message = "小图宽度不能为空")
    private Integer smallWidth;

    @ApiModelProperty(name = "small_height", value = "小图高度", required = true)
    @NotNull(message = "小图高度不能为空")
    private Integer smallHeight;

    @ApiModelProperty(name = "big_width", value = "大图宽度", required = true)
    @NotNull(message = "大图宽度不能为空")
    private Integer bigWidth;

    @ApiModelProperty(name = "big_height", value = "大图高度", required = true)
    @NotNull(message = "大图高度不能为空")
    private Integer bigHeight;

    @ApiModelProperty(name="goods_warning_count",value="货品预警数",required=true)
    @NotNull(message = "商品预警数不能为空")
    private Integer goodsWarningCount;

    public Integer getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(Integer thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public Integer getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(Integer thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public Integer getSmallWidth() {
        return smallWidth;
    }

    public void setSmallWidth(Integer smallWidth) {
        this.smallWidth = smallWidth;
    }

    public Integer getSmallHeight() {
        return smallHeight;
    }

    public void setSmallHeight(Integer smallHeight) {
        this.smallHeight = smallHeight;
    }

    public Integer getBigWidth() {
        return bigWidth;
    }

    public void setBigWidth(Integer bigWidth) {
        this.bigWidth = bigWidth;
    }

    public Integer getBigHeight() {
        return bigHeight;
    }

    public void setBigHeight(Integer bigHeight) {
        this.bigHeight = bigHeight;
    }

    public Integer getGoodsWarningCount() {
        return goodsWarningCount;
    }

    public void setGoodsWarningCount(Integer goodsWarningCount) {
        this.goodsWarningCount = goodsWarningCount;
    }

    @Override
    public String toString() {
        return "GoodsSettingVO{" +
                ", thumbnailWidth=" + thumbnailWidth +
                ", thumbnailHeight=" + thumbnailHeight +
                ", smallWidth=" + smallWidth +
                ", smallHeight=" + smallHeight +
                ", bigWidth=" + bigWidth +
                ", bigHeight=" + bigHeight +
                '}';
    }
}
