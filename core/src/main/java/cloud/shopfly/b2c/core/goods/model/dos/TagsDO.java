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
 * 商品标签实体
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-28 14:49:36
 */
@Table(name = "es_tags")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TagsDO implements Serializable {

    private static final long serialVersionUID = 1899720595535600L;

    /**
     * 主键
     */
    @Id(name = "tag_id")
    @ApiModelProperty(hidden = true)
    private Integer tagId;
    /**
     * 标签名字
     */
    @Column(name = "tag_name")
    @ApiModelProperty(name = "tag_name", value = "标签名字", required = false)
    private String tagName;
    /**
     * 关键字
     */
    @Column(name = "mark")
    @ApiModelProperty(name = "mark", value = "关键字", required = false)
    private String mark;


    public TagsDO() {
    }

    public TagsDO(String tagName, Integer sellerId, String mark) {
        super();
        this.tagName = tagName;
        this.mark = mark;
    }


    @PrimaryKeyField
    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "TagsDO{" +
                "tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }
}