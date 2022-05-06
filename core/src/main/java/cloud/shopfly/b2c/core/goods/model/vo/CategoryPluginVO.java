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

import cloud.shopfly.b2c.core.goods.model.dos.CategoryDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 分类获取的插件返回的vo
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018年3月16日 下午4:53:39
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CategoryPluginVO extends CategoryDO implements Serializable {

    /**
     * 分类获取的插件返回的vo
     */
    private static final long serialVersionUID = -8428052730649034814L;

    @ApiModelProperty("分类id")
    private Integer id;
    @ApiModelProperty("分类名称")
    private String text;

    public CategoryPluginVO() {
    }

    public Integer getId() {
        id = this.getCategoryId();
        return id;
    }

    public String getText() {
        text = this.getName();
        return text;
    }

    @Override
    public String toString() {
        return "CategoryPluginVO [id=" + id + ", text=" + text + "]";
    }

}
