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
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 标签商品关联实体
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-28 17:02:59
 */
@Table(name = "es_tag_goods")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TagGoodsDO implements Serializable {

    private static final long serialVersionUID = 9467335201085494L;

    /**
     * 标签id
     */
    @Column(name = "tag_id")
    @ApiModelProperty(name = "tag_id", value = "标签id", required = false)
    private Integer tagId;
    /**
     * 商品id
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "商品id", required = false)
    private Integer goodsId;

    public TagGoodsDO() {
    }


    public TagGoodsDO(Integer tagId, Integer goodsId) {
        super();
        this.tagId = tagId;
        this.goodsId = goodsId;
    }


    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "TagGoodsDO{" +
                "tagId=" + tagId +
                ", goodsId=" + goodsId +
                '}';
    }
}