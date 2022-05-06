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
package cloud.shopfly.b2c.core.goods.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 商品相册实体
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-21 11:39:54
 */
@Table(name = "es_goods_gallery")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsGalleryDO implements Serializable {

    private static final long serialVersionUID = 8150217189133447L;

    /**
     * 主键
     */
    @Id(name = "img_id")
    @ApiModelProperty(name = "img_id", value = "图片的主键，添加时-1", required = true)
    private Integer imgId;
    /**
     * 商品主键
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "商品主键", hidden = true)
    private Integer goodsId;
    /**
     * 缩略图路径
     */
    @Column(name = "thumbnail")
    @ApiModelProperty(name = "thumbnail", value = "缩略图路径", hidden = true)
    private String thumbnail;
    /**
     * 小图路径
     */
    @Column(name = "small")
    @ApiModelProperty(name = "small", value = "小图路径", hidden = true)
    private String small;
    /**
     * 大图路径
     */
    @Column(name = "big")
    @ApiModelProperty(name = "big", value = "大图路径", hidden = true)
    private String big;
    /**
     * 原图路径
     */
    @Column(name = "original")
    @ApiModelProperty(name = "original", value = "原图路径", required = true)
    private String original;
    /**
     * 极小图路径
     */
    @Column(name = "tiny")
    @ApiModelProperty(name = "tiny", value = "极小图路径", hidden = true)
    private String tiny;
    /**
     * 是否是默认图片1   0没有默认
     */
    @Column(name = "isdefault")
    @ApiModelProperty(name = "isdefault", value = "是否是默认图片1   0没有默认", hidden = true)
    private Integer isdefault;
    /**
     * 排序
     */
    @Column(name = "sort")
    @ApiModelProperty(name = "sort", value = "排序", required = true)
    private Integer sort;

    @PrimaryKeyField
    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
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

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getTiny() {
        return tiny;
    }

    public void setTiny(String tiny) {
        this.tiny = tiny;
    }

    public Integer getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Integer isdefault) {
        this.isdefault = isdefault;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "GoodsGalleryDO [imgId=" + imgId + ", goodsId=" + goodsId + ", thumbnail=" + thumbnail + ", small="
                + small + ", big=" + big + ", original=" + original + ", tiny=" + tiny + ", isdefault=" + isdefault
                + ", sort=" + sort + "]";
    }


}