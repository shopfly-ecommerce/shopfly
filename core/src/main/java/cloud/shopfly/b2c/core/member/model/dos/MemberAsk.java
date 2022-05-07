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
 * Consulting entities
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-07 16:13:32
 */
@Table(name = "es_member_ask")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MemberAsk implements Serializable {

    private static final long serialVersionUID = 1642694855238993L;

    /**
     * A primary key
     */
    @Id(name = "ask_id")
    @ApiModelProperty(hidden = true)
    private Integer askId;
    /**
     * productid
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "productid", required = false)
    private Integer goodsId;
    /**
     * membersid
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id", value = "membersid", required = false)
    private Integer memberId;
    /**
     * Consulting content
     */
    @Column(name = "content")
    @ApiModelProperty(name = "content", value = "Consulting content", required = false)
    private String content;
    /**
     * Consultation time
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "Consultation time", required = false)
    private Long createTime;
    /**
     * Reply content
     */
    @Column(name = "reply")
    @ApiModelProperty(name = "reply", value = "Reply content", required = false)
    private String reply;
    /**
     * Recovery time
     */
    @Column(name = "reply_time")
    @ApiModelProperty(name = "reply_time", value = "Recovery time", required = false)
    private Long replyTime;
    /**
     * Reply to state1 Have to reply0Did not return
     */
    @Column(name = "reply_status")
    @ApiModelProperty(name = "reply_status", value = "Reply to state1 Have to reply0 Did not return", required = false)
    private Integer replyStatus;
    /**
     * Status
     */
    @Column(name = "status")
    @ApiModelProperty(name = "status", value = "Status", required = false)
    private Integer status;
    /**
     * Name
     */
    @Column(name = "goods_name")
    @ApiModelProperty(name = "goods_name", value = "Name", required = false)
    private String goodsName;

    /**
     * Name of the buyer
     */
    @Column(name = "member_name")
    @ApiModelProperty(name = "member_name", value = "Name of the buyer", required = false)
    private String memberName;

    /**
     * Member of the head
     */
    @Column(name = "member_face")
    @ApiModelProperty(name = "member_face", value = "Member of the head", required = false)
    private String memberFace;

    /**
     * Review the status
     */
    @Column(name = "auth_status")
    @ApiModelProperty(name = "auth_status", value = "Review the status", required = false)
    private String authStatus;

    @PrimaryKeyField
    public Integer getAskId() {
        return askId;
    }

    public void setAskId(Integer askId) {
        this.askId = askId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Long getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Long replyTime) {
        this.replyTime = replyTime;
    }

    public Integer getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(Integer replyStatus) {
        this.replyStatus = replyStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getMemberFace() {
        return memberFace;
    }

    public void setMemberFace(String memberFace) {
        this.memberFace = memberFace;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberAsk that = (MemberAsk) o;
        if (askId != null ? !askId.equals(that.askId) : that.askId != null) {
            return false;
        }
        if (goodsId != null ? !goodsId.equals(that.goodsId) : that.goodsId != null) {
            return false;
        }
        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) {
            return false;
        }
        if (content != null ? !content.equals(that.content) : that.content != null) {
            return false;
        }
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) {
            return false;
        }
        if (reply != null ? !reply.equals(that.reply) : that.reply != null) {
            return false;
        }
        if (replyTime != null ? !replyTime.equals(that.replyTime) : that.replyTime != null) {
            return false;
        }
        if (replyStatus != null ? !replyStatus.equals(that.replyStatus) : that.replyStatus != null) {
            return false;
        }
        if (status != null ? !status.equals(that.status) : that.status != null) {
            return false;
        }
        if (memberName != null ? !memberName.equals(that.memberName) : that.memberName != null) {
            return false;
        }
        if (goodsName != null ? !goodsName.equals(that.goodsName) : that.goodsName != null) {
            return false;
        }
        return memberFace != null ? memberFace.equals(that.memberFace) : that.memberFace == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (askId != null ? askId.hashCode() : 0);
        result = 31 * result + (goodsId != null ? goodsId.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (reply != null ? reply.hashCode() : 0);
        result = 31 * result + (replyTime != null ? replyTime.hashCode() : 0);
        result = 31 * result + (replyStatus != null ? replyStatus.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (memberName != null ? memberName.hashCode() : 0);
        result = 31 * result + (goodsName != null ? goodsName.hashCode() : 0);
        result = 31 * result + (memberFace != null ? memberFace.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MemberAsk{" +
                "askId=" + askId +
                ", goodsId=" + goodsId +
                ", memberId=" + memberId +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", reply='" + reply + '\'' +
                ", replyTime=" + replyTime +
                ", replyStatus=" + replyStatus +
                ", status=" + status +
                ", memberName='" + memberName + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", memberFace='" + memberFace + '\'' +
                '}';
    }


}
