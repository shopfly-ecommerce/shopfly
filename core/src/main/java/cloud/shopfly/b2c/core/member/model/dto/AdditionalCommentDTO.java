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
package cloud.shopfly.b2c.core.member.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Additional comments from membersdto
 * @author duanmingyu
 * @version v1.0
 * @since v7.1.0
 * @date 2019-05-09
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(description = "Member commentsvo")
public class AdditionalCommentDTO implements Serializable {

    private static final long serialVersionUID = 3172988905896956148L;

    @ApiModelProperty(name = "content", value = "Additional comments", required = false)
    private String content;

    @ApiModelProperty(value = "Add comments to the picture")
    private List<String> images;

    @ApiModelProperty(value = "Member of the internID",name = "comment_id", required = true)
    @NotNull(message = "Member of the internIDCant be empty")
    private Integer commentId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        AdditionalCommentDTO that = (AdditionalCommentDTO) o;
        return Objects.equals(content, that.content) &&
                Objects.equals(images, that.images) &&
                Objects.equals(commentId, that.commentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, images, commentId);
    }

    @Override
    public String toString() {
        return "AdditionalCommentDTO{" +
                "content='" + content + '\'' +
                ", images=" + images +
                ", commentId=" + commentId +
                '}';
    }
}
