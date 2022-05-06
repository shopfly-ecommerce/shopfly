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
package cloud.shopfly.b2c.core.pagedata.model;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 文章实体
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-12 14:50:17
 */
@Table(name = "es_article")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Article implements Serializable {

    private static final long serialVersionUID = 9461532743001614L;

    /**
     * 主键
     */
    @Id(name = "article_id")
    @ApiModelProperty(hidden = true)
    private Integer articleId;
    /**
     * 文章名称
     */
    @Column(name = "article_name")
    @ApiModelProperty(name = "article_name", value = "文章名称", required = true)
    @NotEmpty(message = "文章名称不能为空")
    @Length(max = 20,message = "文章名称不能超过20个字符")
    private String articleName;
    /**
     * 分类id
     */
    @Column(name = "category_id")
    @ApiModelProperty(name = "category_id", value = "分类id", required = true)
    @NotNull(message = "文章分类不能为空")
    private Integer categoryId;
    /**
     * 文章排序
     */
    @Column(name = "sort")
    @ApiModelProperty(name = "sort", value = "文章排序")
    private Integer sort;
    /**
     * 外链url
     */
    @Column(name = "outside_url")
    @ApiModelProperty(name = "outside_url", value = "外链url", required = false)
    private String outsideUrl;
    /**
     * 文章内容
     */
    @Column(name = "content")
    @ApiModelProperty(name = "content", value = "文章内容", required = true)
    @NotEmpty(message = "文章内容不能为空")
    private String content;
    /**
     * 显示位置
     */
    @Column(name = "show_position")
    @ApiModelProperty(name = "show_position", value = "显示位置", hidden = true)
    private String showPosition;
    /**
     * 添加时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "添加时间", hidden = true)
    private Long createTime;
    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    @ApiModelProperty(name = "modify_time", value = "修改时间", hidden = true)
    private Long modifyTime;

    @PrimaryKeyField
    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getOutsideUrl() {
        return outsideUrl;
    }

    public void setOutsideUrl(String outsideUrl) {
        this.outsideUrl = outsideUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShowPosition() {
        return showPosition;
    }

    public void setShowPosition(String showPosition) {
        this.showPosition = showPosition;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Article that = (Article) o;
        if (articleId != null ? !articleId.equals(that.articleId) : that.articleId != null) {
            return false;
        }
        if (articleName != null ? !articleName.equals(that.articleName) : that.articleName != null) {
            return false;
        }
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) {
            return false;
        }
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) {
            return false;
        }
        if (outsideUrl != null ? !outsideUrl.equals(that.outsideUrl) : that.outsideUrl != null) {
            return false;
        }
        if (content != null ? !content.equals(that.content) : that.content != null) {
            return false;
        }
        if (showPosition != null ? !showPosition.equals(that.showPosition) : that.showPosition != null) {
            return false;
        }
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) {
            return false;
        }
        return modifyTime != null ? modifyTime.equals(that.modifyTime) : that.modifyTime == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
        result = 31 * result + (articleName != null ? articleName.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (outsideUrl != null ? outsideUrl.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (showPosition != null ? showPosition.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", articleName='" + articleName + '\'' +
                ", categoryId=" + categoryId +
                ", sort=" + sort +
                ", outsideUrl='" + outsideUrl + '\'' +
                ", content='" + content + '\'' +
                ", showPosition='" + showPosition + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }


}