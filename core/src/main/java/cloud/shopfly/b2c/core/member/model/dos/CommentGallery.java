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
package cloud.shopfly.b2c.core.member.model.dos;

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
 * 评论图片实体
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 14:11:46
 */
@Table(name="es_comment_gallery")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommentGallery implements Serializable {
			
    private static final long serialVersionUID = 8048552718399969L;
    
    /**主键*/
    @Id(name = "img_id")
    @ApiModelProperty(hidden=true)
    private Integer imgId;
    /**主键*/
    @Column(name = "comment_id")
    @ApiModelProperty(name="comment_id",value="评论主键",required=false)
    private Integer commentId;
    /**图片路径*/
    @Column(name = "original")
    @ApiModelProperty(name="original",value="图片路径",required=false)
    private String original;
    /**排序*/
    @Column(name = "sort")
    @ApiModelProperty(name="sort",value="排序",required=false)
    private Integer sort;
    /**图片所属 0：初评，1：追评*/
    @Column(name = "img_belong")
    @ApiModelProperty(name="img_belong",value="图片所属 0：初评，1：追评",required=false)
    private Integer imgBelong;

    @PrimaryKeyField
    public Integer getImgId() {
        return imgId;
    }
    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public Integer getCommentId() {
        return commentId;
    }
    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getOriginal() {
        return original;
    }
    public void setOriginal(String original) {
        this.original = original;
    }

    public Integer getSort() {
        return sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getImgBelong() {
        return imgBelong;
    }

    public void setImgBelong(Integer imgBelong) {
        this.imgBelong = imgBelong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        CommentGallery that = (CommentGallery) o;
        if (imgId != null ? !imgId.equals(that.imgId) : that.imgId != null) {return false;}
        if (commentId != null ? !commentId.equals(that.commentId) : that.commentId != null) {return false;}
        if (original != null ? !original.equals(that.original) : that.original != null) {return false;}
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) {return false;}
        return imgBelong != null ? imgBelong.equals(that.imgBelong) : that.imgBelong == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (imgId != null ? imgId.hashCode() : 0);
        result = 31 * result + (commentId != null ? commentId.hashCode() : 0);
        result = 31 * result + (original != null ? original.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (imgBelong != null ? imgBelong.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommentGallery{" +
                "imgId=" + imgId +
                ", commentId=" + commentId +
                ", original='" + original + '\'' +
                ", sort=" + sort +
                ", imgBelong=" + imgBelong +
                '}';
    }

	
}