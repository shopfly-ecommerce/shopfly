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
package cloud.shopfly.b2c.core.pagedata.model.vo;

import cloud.shopfly.b2c.core.pagedata.model.enums.ArticleShowPosition;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * The article entity
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-12 10:43:18
 */
@Table(name = "es_article")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ArticleDetail implements Serializable {

    private static final long serialVersionUID = 5105404520203401L;

    /**
     * A primary key
     */
    @Id(name = "article_id")
    @ApiModelProperty(hidden = true)
    private Integer articleId;
    /**
     * The article name
     */
    @Column(name = "article_name")
    @ApiModelProperty(name = "article_name", value = "The article name")
    private String articleName;
    /**
     * name
     */
    @Column(name = "category_name")
    @ApiModelProperty(name = "category_name", value = "name")
    private String categoryName;
    /**
     * Display position
     */
    @Column(name = "show_position")
    @ApiModelProperty(name = "show_position", value = "Display position",hidden = true )
    private String showPosition;

    /**
     * Display position
     */
    @ApiModelProperty(name = "show_position_text", value = "Display position, text")
    private String showPositionText;

    /**
     * Whether to deletetrue allowfalse 不allow
     */
    @Column(name = "allow_delete")
    @ApiModelProperty(name = "allow_delete", value = "Whether to delete")
    private Boolean allowDelete;

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getShowPosition() {
        return showPosition;
    }

    public void setShowPosition(String showPosition) {
        this.showPosition = showPosition;
    }

    public Boolean getAllowDelete() {

        if(this.showPosition!=null){
            return ArticleShowPosition.valueOf(this.showPosition).equals(ArticleShowPosition.OTHER);
        }

        return allowDelete;
    }

    public void setAllowDelete(Boolean allowDelete) {
        this.allowDelete = allowDelete;
    }

    public String getShowPositionText() {
        if(this.showPosition!=null){
            return ArticleShowPosition.valueOf(this.showPosition).description();
        }
        return showPositionText;
    }

    public void setShowPositionText(String showPositionText) {
        this.showPositionText = showPositionText;
    }
}
