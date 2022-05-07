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
package cloud.shopfly.b2c.core.goods.model.vo;

import cloud.shopfly.b2c.core.goods.model.dos.GoodsGalleryDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Product details page use
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018years3month29The morning of9:54:05
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsShowDetail extends CacheGoods implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3531885212488390703L;
    @ApiModelProperty(name = "name")
    private String categoryName;

    @ApiModelProperty(name = "Whether to remove or delete,1normal0 Removed or deleted")
    private Integer goodsOff;

    @ApiModelProperty(name = "Product parameters")
    private List<GoodsParamsGroupVO> paramList;

    @ApiModelProperty(name = "Photo album")
    private List<GoodsGalleryDO> galleryList;

    @ApiModelProperty(name = "Goods have a good flat rate")
    private Double grade;
    /**
     * seo title
     */
    @ApiModelProperty(name = "page_title", value = "seo title", required = false)
    private String pageTitle;
    /**
     * seokeyword
     */
    @ApiModelProperty(name = "meta_keywords", value = "seokeyword", required = false)
    private String metaKeywords;
    /**
     * seo describe
     */
    @ApiModelProperty(name = "meta_description", value = "seo describe", required = false)
    private String metaDescription;

    /**
     * Who bears the freight0：The buyer bears,1：The seller bear
     */
    @ApiModelProperty(name = "goods_transfee_charge", value = "Who bears the freight0：The buyer bears,1：The seller bear")
    private Integer goodsTransfeeCharge;

    @Override
    public Integer getGoodsTransfeeCharge() {
        return goodsTransfeeCharge;
    }

    @Override
    public void setGoodsTransfeeCharge(Integer goodsTransfeeCharge) {
        this.goodsTransfeeCharge = goodsTransfeeCharge;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getGoodsOff() {
        return goodsOff;
    }

    public void setGoodsOff(Integer goodsOff) {
        this.goodsOff = goodsOff;
    }

    public List<GoodsParamsGroupVO> getParamList() {
        return paramList;
    }

    public void setParamList(List<GoodsParamsGroupVO> paramList) {
        this.paramList = paramList;
    }

    public List<GoodsGalleryDO> getGalleryList() {
        return galleryList;
    }

    public void setGalleryList(List<GoodsGalleryDO> galleryList) {
        this.galleryList = galleryList;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }
}
