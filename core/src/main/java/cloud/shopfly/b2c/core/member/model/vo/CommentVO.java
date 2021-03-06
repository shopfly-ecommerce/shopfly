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
package cloud.shopfly.b2c.core.member.model.vo;

import cloud.shopfly.b2c.core.member.model.dos.CommentReply;
import cloud.shopfly.b2c.core.member.model.dos.MemberComment;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: commentsvo
 * @date 2018/5/31 5:03
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommentVO extends MemberComment {

    @ApiModelProperty(name = "images", value = "Review images", required = false)
    private List<String> images;

    @ApiModelProperty(name = "reply", value = "Comment back", required = false)
    private CommentReply reply;

    @ApiModelProperty(name = "add_images", value = "Append comment images", required = false)
    private List<String> addImages;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public CommentReply getReply() {
        return reply;
    }

    public void setReply(CommentReply reply) {
        this.reply = reply;
    }

    public List<String> getAddImages() {
        return addImages;
    }

    public void setAddImages(List<String> addImages) {
        this.addImages = addImages;
    }

    @Override
    public String toString() {
        return "CommentVO{" +
                "images=" + images +
                ", reply=" + reply +
                ", addImages=" + addImages +
                '}';
    }
}
