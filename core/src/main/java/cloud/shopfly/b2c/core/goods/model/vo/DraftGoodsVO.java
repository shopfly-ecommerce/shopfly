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

import cloud.shopfly.b2c.core.goods.model.dos.DraftGoodsDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;

/**
 * 草稿箱商品vo
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/7/6 上午3:00
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DraftGoodsVO extends DraftGoodsDO {

    /**
     * 商品分类名称
     */
    @ApiModelProperty(name = "category_name", value = "商品分类名称", required = false)
    private String categoryName;

    @ApiModelProperty(name = "category_ids", value = "分类id数组")
    private Integer[] categoryIds;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer[] getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(Integer[] categoryIds) {
        this.categoryIds = categoryIds;
    }

    @Override
    public String toString() {
        return "DraftGoodsVO{" +
                "categoryName='" + categoryName + '\'' +
                ", categoryIds=" + Arrays.toString(categoryIds) +
                '}';
    }
}