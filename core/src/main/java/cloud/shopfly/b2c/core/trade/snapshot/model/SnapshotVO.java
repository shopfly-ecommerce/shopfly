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
package cloud.shopfly.b2c.core.trade.snapshot.model;

import cloud.shopfly.b2c.core.goods.model.dos.GoodsGalleryDO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsParamsGroupVO;
import cloud.shopfly.b2c.core.goods.model.vo.SpecValueVO;
import cloud.shopfly.b2c.core.promotion.coupon.model.dos.CouponDO;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: The snapshot
 * @date 2018/8/1 16:41
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SnapshotVO extends GoodsSnapshot {

    @ApiModelProperty(value = "Album list")
    private List<GoodsGalleryDO> galleryList;

    @ApiModelProperty(value = "The list of parameters")
    private List<GoodsParamsGroupVO> paramList;

    @ApiModelProperty(value = "Specification list")
    private List<SpecValueVO> specList;

    @ApiModelProperty(value = "The promotion list")
    private List<PromotionVO> promotionList;

    @ApiModelProperty(value = "Coupon list")
    private List<CouponDO> couponList;

    public SnapshotVO() {
    }


    public List<GoodsGalleryDO> getGalleryList() {

        if (!StringUtil.isEmpty(this.getImgJson())) {
            return JsonUtil.jsonToList(this.getImgJson(), GoodsGalleryDO.class);
        }

        return galleryList;
    }

    public void setGalleryList(List<GoodsGalleryDO> galleryList) {
        this.galleryList = galleryList;
    }

    public List<GoodsParamsGroupVO> getParamList() {
        if (!StringUtil.isEmpty(this.getParamsJson())) {
            return JsonUtil.jsonToList(this.getParamsJson(), GoodsParamsGroupVO.class);
        }
        return paramList;
    }

    public void setParamList(List<GoodsParamsGroupVO> paramList) {
        this.paramList = paramList;
    }

    public List<SpecValueVO> getSpecList() {

        return specList;
    }

    public void setSpecList(List<SpecValueVO> specList) {

        this.specList = specList;
    }

    public List<PromotionVO> getPromotionList() {

        if (!StringUtil.isEmpty(this.getPromotionJson())) {
            return JsonUtil.jsonToList(this.getPromotionJson(), PromotionVO.class);
        }

        return promotionList;
    }

    public void setPromotionList(List<PromotionVO> promotionList) {
        this.promotionList = promotionList;
    }

    public List<CouponDO> getCouponList() {

        if (!StringUtil.isEmpty(this.getCouponJson())) {
            return JsonUtil.jsonToList(this.getCouponJson(), CouponDO.class);
        }
        return couponList;
    }

    public void setCouponList(List<CouponDO> couponList) {
        this.couponList = couponList;
    }

    @Override
    public String toString() {
        return "SnapshotVO{" +
                "galleryList=" + galleryList +
                ", paramList=" + paramList +
                ", specList=" + specList +
                '}';
    }
}
