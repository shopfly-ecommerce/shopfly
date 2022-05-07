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
package cloud.shopfly.b2c.core.goods.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author fk
 * @version v2.0
 * @Description: Commodity related Settingsvo
 * @date 2018/4/911:10
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsSettingVO implements Serializable {

    @ApiModelProperty(name = "thumbnail_width", value = "Thumbnail width", required = true)
    @NotNull(message = "The thumbnail width cannot be empty")
    private Integer thumbnailWidth;

    @ApiModelProperty(name = "thumbnail_height", value = "Thumbnail height", required = true)
    @NotNull(message = "The thumbnail height cannot be empty")
    private Integer thumbnailHeight;

    @ApiModelProperty(name = "small_width", value = "The width of the picture", required = true)
    @NotNull(message = "Small image width cannot be empty")
    private Integer smallWidth;

    @ApiModelProperty(name = "small_height", value = "Insets height", required = true)
    @NotNull(message = "The height of the small map cannot be empty")
    private Integer smallHeight;

    @ApiModelProperty(name = "big_width", value = "A larger width", required = true)
    @NotNull(message = "Large image width cannot be empty")
    private Integer bigWidth;

    @ApiModelProperty(name = "big_height", value = "A larger image height", required = true)
    @NotNull(message = "Large map height cannot be empty")
    private Integer bigHeight;

    @ApiModelProperty(name="goods_warning_count",value="Number of goods warning",required=true)
    @NotNull(message = "The commodity warning number cannot be empty")
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
