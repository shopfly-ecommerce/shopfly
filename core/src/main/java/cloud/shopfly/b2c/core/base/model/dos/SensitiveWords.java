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
package cloud.shopfly.b2c.core.base.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * 敏感词实体
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-08-02 11:30:59
 */
@Table(name = "es_sensitive_words")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SensitiveWords implements Serializable {

    private static final long serialVersionUID = 1245656010769588L;

    /**
     * 主键
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * 敏感词名称
     */
    @Column(name = "word_name")
    @ApiModelProperty(name = "word_name", value = "敏感词名称", required = true)
    @NotEmpty(message = "敏感词必填")
    private String wordName;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "创建时间", hidden = true)
    private Long createTime;
    /**
     * 删除状态  1正常 0 删除
     */
    @Column(name = "is_delete")
    @ApiModelProperty(name = "is_delete", value = "删除状态  1正常 0 删除", hidden = true)
    private Integer isDelete;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SensitiveWords that = (SensitiveWords) o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (wordName != null ? !wordName.equals(that.wordName) : that.wordName != null) {
            return false;
        }
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) {
            return false;
        }
        return isDelete != null ? isDelete.equals(that.isDelete) : that.isDelete == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (wordName != null ? wordName.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SensitiveWords{" +
                "id=" + id +
                ", wordName='" + wordName + '\'' +
                ", createTime=" + createTime +
                ", isDelete=" + isDelete +
                '}';
    }


}