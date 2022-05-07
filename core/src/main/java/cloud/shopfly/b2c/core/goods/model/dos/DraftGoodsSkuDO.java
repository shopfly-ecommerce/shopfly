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

import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
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
 * The draft of goodsskuentity
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-26 11:38:06
 */
@Table(name = "es_draft_goods_sku")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DraftGoodsSkuDO implements Serializable {

    private static final long serialVersionUID = 5684194304207265L;

    /**
     * A primary keyID
     */
    @Id(name = "draft_sku_id")
    @ApiModelProperty(hidden = true)
    private Integer draftSkuId;
    /**
     * The draftid
     */
    @Column(name = "draft_goods_id")
    @ApiModelProperty(name = "draft_goods_id", value = "The draftid", required = false)
    private Integer draftGoodsId;
    /**
     * SN
     */
    @Column(name = "sn")
    @ApiModelProperty(name = "sn", value = "SN", required = false)
    private String sn;
    /**
     * Total inventory
     */
    @Column(name = "quantity")
    @ApiModelProperty(name = "quantity", value = "Total inventory", required = false)
    private Integer quantity;
    /**
     * Price
     */
    @Column(name = "price")
    @ApiModelProperty(name = "price", value = "Price", required = false)
    private Double price;
    /**
     * specifications
     */
    @Column(name = "specs")
    @ApiModelProperty(name = "specs", value = "specifications", required = false)
    private String specs;
    /**
     * The cost of
     */
    @Column(name = "cost")
    @ApiModelProperty(name = "cost", value = "The cost of", required = false)
    private Double cost;
    /**
     * Weight
     */
    @Column(name = "weight")
    @ApiModelProperty(name = "weight", value = "Weight", required = false)
    private Double weight;

    public DraftGoodsSkuDO() {
    }

    public DraftGoodsSkuDO(GoodsSkuVO skuVO) {
        this.setDraftGoodsId(skuVO.getGoodsId());
        this.setSn(skuVO.getSn());
        this.setPrice(skuVO.getPrice());
        this.setCost(skuVO.getCost());
        this.setWeight(skuVO.getWeight());
        this.setQuantity(skuVO.getQuantity());
        this.setSpecs(skuVO.getSpecs());
    }

    @PrimaryKeyField
    public Integer getDraftSkuId() {
        return draftSkuId;
    }

    public void setDraftSkuId(Integer draftSkuId) {
        this.draftSkuId = draftSkuId;
    }

    public Integer getDraftGoodsId() {
        return draftGoodsId;
    }

    public void setDraftGoodsId(Integer draftGoodsId) {
        this.draftGoodsId = draftGoodsId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "DraftGoodsSkuDO [draftSkuId=" + draftSkuId + ", draftGoodsId=" + draftGoodsId + ", sn=" + sn
                + ", quantity=" + quantity + ", price=" + price + ", specs=" + specs + ", cost=" + cost + ", weight="
                + weight + "]";
    }


}
