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

import cloud.shopfly.b2c.core.goods.model.dos.DraftGoodsSkuDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsSkuDO;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * productsku
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018years3month21The morning of11:50:42
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsSkuVO extends GoodsSkuDO {

    /**
     *
     */
    private static final long serialVersionUID = -666090547834195127L;

    @ApiModelProperty(name = "spec_list", value = "Specification list", required = false)
    private List<SpecValueVO> specList;

    @ApiModelProperty(name = "goods_transfee_charge", value = "Who bears the freight0：The buyer bears,1：The seller bear", hidden = true)
    private Integer goodsTransfeeCharge;

    @ApiModelProperty(value = "Deleted or not0 delete1 未delete", hidden = true)
    private Integer disabled;

    @ApiModelProperty(value = "On state0off1save", hidden = true)
    private Integer marketEnable;

    @ApiModelProperty(name = "goods_type", value = "TypeNORMALordinaryPOINTpoint")
    private String goodsType;

    @ApiModelProperty(value = "Last Modified time", hidden = true)
    private Long lastModify;

    public List<SpecValueVO> getSpecList() {

        if (this.getSpecs() != null) {
            return JsonUtil.jsonToList(this.getSpecs(), SpecValueVO.class);
        }

        return specList;
    }

    public void setSpecList(List<SpecValueVO> specList) {
        this.specList = specList;
    }

    public GoodsSkuVO() {
    }

    public GoodsSkuVO(DraftGoodsSkuDO draftSku) {
        this.setCost(draftSku.getCost());
        this.setPrice(draftSku.getPrice());
        this.setQuantity(draftSku.getQuantity());
        this.setSkuId(draftSku.getDraftSkuId());
        this.setSn(draftSku.getSn());
        this.setWeight(draftSku.getWeight());
        this.setSpecList(JsonUtil.jsonToList(draftSku.getSpecs(), SpecValueVO.class));
    }

    public Integer getGoodsTransfeeCharge() {
        return goodsTransfeeCharge;
    }

    public void setGoodsTransfeeCharge(Integer goodsTransfeeCharge) {
        this.goodsTransfeeCharge = goodsTransfeeCharge;
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

    public Long getLastModify() {
        return lastModify;
    }

    public void setLastModify(Long lastModify) {
        this.lastModify = lastModify;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    @Override
    public String toString() {
        return "GoodsSkuVO{" +
                "specList=" + specList +
                ", goodsTransfeeCharge=" + goodsTransfeeCharge +
                ", disabled=" + disabled +
                ", marketEnable=" + marketEnable +
                ", goodsType='" + goodsType + '\'' +
                ", lastModify=" + lastModify +
                "} " + super.toString();
    }
}
