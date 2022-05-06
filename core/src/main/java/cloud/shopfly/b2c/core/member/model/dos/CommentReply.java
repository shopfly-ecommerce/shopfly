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
 * 评论回复实体
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 16:34:50
 */
@Table(name = "es_comment_reply")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommentReply implements Serializable {

    private static final long serialVersionUID = 8995158403058181L;

    /**
     * 主键
     */
    @Id(name = "reply_id")
    @ApiModelProperty(hidden = true)
    private Integer replyId;
    /**
     * 评论id
     */
    @Column(name = "comment_id")
    @ApiModelProperty(name = "comment_id", value = "评论id", required = false)
    private Integer commentId;
    /**
     * 回复父id
     */
    @Column(name = "parent_id")
    @ApiModelProperty(name = "parent_id", value = "回复父id", required = false)
    private Integer parentId;
    /**
     * 回复内容
     */
    @Column(name = "content")
    @ApiModelProperty(name = "content", value = "回复内容", required = false)
    private String content;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "创建时间", required = false)
    private Long createTime;
    /**
     * 商家或者买家
     */
    @Column(name = "role")
    @ApiModelProperty(name = "role", value = "商家或者买家", required = false)
    private String role;
    /**
     * 父子路径0|10|
     */
    @Column(name = "path")
    @ApiModelProperty(name = "path", value = "父子路径0|10|", required = false)
    private String path;

    @PrimaryKeyField
    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommentReply that = (CommentReply) o;
        if (replyId != null ? !replyId.equals(that.replyId) : that.replyId != null) {
            return false;
        }
        if (commentId != null ? !commentId.equals(that.commentId) : that.commentId != null) {
            return false;
        }
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) {
            return false;
        }
        if (content != null ? !content.equals(that.content) : that.content != null) {
            return false;
        }
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) {
            return false;
        }
        if (role != null ? !role.equals(that.role) : that.role != null) {
            return false;
        }
        return path != null ? path.equals(that.path) : that.path == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (replyId != null ? replyId.hashCode() : 0);
        result = 31 * result + (commentId != null ? commentId.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommentReply{" +
                "replyId=" + replyId +
                ", commentId=" + commentId +
                ", parentId=" + parentId +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", role='" + role + '\'' +
                ", path='" + path + '\'' +
                '}';
    }


}