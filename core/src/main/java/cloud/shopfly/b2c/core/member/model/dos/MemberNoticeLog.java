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
 * Member site message history entity
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-05 14:10:16
 */

/**
 * Member site message history entity
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-23 18:11:21
 */
@Table(name = "es_member_notice_log")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MemberNoticeLog implements Serializable {

    private static final long serialVersionUID = 2703003634632585L;

    /**
     * Member Historyid
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * membersid
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id", value = "membersid", required = false)
    private Integer memberId;
    /**
     * Station message content
     */
    @Column(name = "content")
    @ApiModelProperty(name = "content", value = "Station message content", required = false)
    private String content;
    /**
     * Send time
     */
    @Column(name = "send_time")
    @ApiModelProperty(name = "send_time", value = "Send time", required = false)
    private Long sendTime;
    /**
     * Whether to delete,0To delete,1There is no delete
     */
    @Column(name = "is_del")
    @ApiModelProperty(name = "is_del", value = "Whether to delete,0To delete,1There is no delete", required = false)
    private Integer isDel;
    /**
     * Has it been read,0Not read,1read
     */
    @Column(name = "is_read")
    @ApiModelProperty(name = "is_read", value = "Has it been read,0Not read,1read", required = false)
    private Integer isRead;
    /**
     * Receiving time
     */
    @Column(name = "receive_time")
    @ApiModelProperty(name = "receive_time", value = "Receiving time", required = false)
    private Long receiveTime;
    /**
     *  title
     */
    @Column(name = "title")
    @ApiModelProperty(name = "title", value = " title", required = false)
    private String title;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Long getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Long receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberNoticeLog that = (MemberNoticeLog) o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) {
            return false;
        }
        if (content != null ? !content.equals(that.content) : that.content != null) {
            return false;
        }
        if (sendTime != null ? !sendTime.equals(that.sendTime) : that.sendTime != null) {
            return false;
        }
        if (isDel != null ? !isDel.equals(that.isDel) : that.isDel != null) {
            return false;
        }
        if (isRead != null ? !isRead.equals(that.isRead) : that.isRead != null) {
            return false;
        }
        if (receiveTime != null ? !receiveTime.equals(that.receiveTime) : that.receiveTime != null) {
            return false;
        }
        return title != null ? title.equals(that.title) : that.title == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (sendTime != null ? sendTime.hashCode() : 0);
        result = 31 * result + (isDel != null ? isDel.hashCode() : 0);
        result = 31 * result + (isRead != null ? isRead.hashCode() : 0);
        result = 31 * result + (receiveTime != null ? receiveTime.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MemberNoticeLog{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", content='" + content + '\'' +
                ", sendTime=" + sendTime +
                ", isDel=" + isDel +
                ", isRead=" + isRead +
                ", receiveTime=" + receiveTime +
                ", title='" + title + '\'' +
                '}';
    }


}
