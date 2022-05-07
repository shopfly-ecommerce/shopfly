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
package cloud.shopfly.b2c.core.system.model.vo;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * Intra-site message entity
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-04 21:50:52
 */
public class MessageVO {
    /**
     *  title
     */
    @Length(min = 2, max = 30, message = "In-site message headers must be in2-30Between characters")
    @ApiModelProperty(name = "title", value = " title", required = false)
    private String title;
    /**
     * The message content
     */
    @NotEmpty(message = "The station message content cannot be empty")
    @Length(min = 0, max = 500, message = "The station message content cannot exceed500A character")
    @ApiModelProperty(name = "content", value = "The message content", required = false)
    private String content;
    /**
     * membersid
     */
    @ApiModelProperty(name = "member_ids", value = "membersid", required = false)
    private String memberIds;
    /**
     * Send type
     */
    @NotNull(message = "The send type cannot be empty")
    @Range(min = 0, max = 1, message = "The send type parameter is incorrect")
    @ApiModelProperty(name = "send_type", value = "Send type,0Total station,1The specified member", required = false)
    private Integer sendType;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(String memberIds) {
        this.memberIds = memberIds;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageVO that = (MessageVO) o;
        if (title != null ? !title.equals(that.title) : that.title != null) {
            return false;
        }
        if (content != null ? !content.equals(that.content) : that.content != null) {
            return false;
        }
        if (memberIds != null ? !memberIds.equals(that.memberIds) : that.memberIds != null) {
            return false;
        }
        return sendType != null ? sendType.equals(that.sendType) : that.sendType == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (memberIds != null ? memberIds.hashCode() : 0);
        result = 31 * result + (sendType != null ? sendType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", memberIds='" + memberIds + '\'' +
                ", sendType=" + sendType +
                '}';
    }


}
