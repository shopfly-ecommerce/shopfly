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
 * Comment on the entity
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 10:19:14
 */
@Table(name = "es_member_comment")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MemberComment implements Serializable {

    private static final long serialVersionUID = 4761968067308018L;

    /**
     * Comment on the primary key
     */
    @Id(name = "comment_id")
    @ApiModelProperty(hidden = true)
    private Integer commentId;
    /**
     * productid
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "productid", required = false)
    private Integer goodsId;
    /**
     * skuid
     */
    @Column(name = "sku_id")
    @ApiModelProperty(name = "sku_id", value = "skuid", required = false)
    private Integer skuId;
    /**
     * membersid
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id", value = "membersid", required = false)
    private Integer memberId;
    /**
     * Member name
     */
    @Column(name = "member_name")
    @ApiModelProperty(name = "member_name", value = "Member name", required = false)
    private String memberName;
    /**
     * Member of the head
     */
    @Column(name = "member_face")
    @ApiModelProperty(name = "member_face", value = "Member of the head", required = false)
    private String memberFace;
    /**
     * Name
     */
    @Column(name = "goods_name")
    @ApiModelProperty(name = "goods_name", value = "Name", required = false)
    private String goodsName;
    /**
     * Comment on the content
     */
    @Column(name = "content")
    @ApiModelProperty(name = "content", value = "Comment on the content", required = false)
    private String content;
    /**
     * Comment on time
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "Comment on time", required = false)
    private Long createTime;
    /**
     * Is there a picture1 There are0 没There are
     */
    @Column(name = "have_image")
    @ApiModelProperty(name = "have_image", value = "Is there a picture1 There are0 没There are", required = false)
    private Integer haveImage;
    /**
     * Status1 normal0 delete
     */
    @Column(name = "status")
    @ApiModelProperty(name = "status", value = "Status1 normal0 delete", required = false)
    private Integer status;
    /**
     * Good to bad
     */
    @Column(name = "grade")
    @ApiModelProperty(name = "grade", value = "Good to bad", required = false)
    private String grade;
    /**
     * Order no.
     */
    @Column(name = "order_sn")
    @ApiModelProperty(name = "order_sn", value = "Order no.", required = false)
    private String orderSn;
    /**
     * Whether the reply1 is0 no
     */
    @Column(name = "reply_status")
    @ApiModelProperty(name = "reply_status", value = "Whether the reply1 is0 no", required = false)
    private Integer replyStatus;

    /**
     * Append Comment Status0：Additional,1：Have an additional
     */
    @Column(name = "additional_status")
    @ApiModelProperty(name = "additional_status", value = "Append Comment Status0：Additional,1：Have an additional", required = false)
    private Integer additionalStatus;

    /**
     * Additional comments
     */
    @Column(name = "additional_content")
    @ApiModelProperty(name = "additional_content", value = "Additional comments", required = false)
    private String additionalContent;

    /**
     * Additional comment time
     */
    @Column(name = "additional_time")
    @ApiModelProperty(name = "additional_time", value = "Additional comment time", required = false)
    private Long additionalTime;

    /**
     * Whether the appended comment has uploaded a picture0：Failed to upload,1：uploaded
     */
    @Column(name = "additional_have_image")
    @ApiModelProperty(name = "additional_have_image", value = "Whether the appended comment has uploaded a picture0：Failed to upload,1：uploaded", required = false)
    private Integer additionalHaveImage;

    @PrimaryKeyField
    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberFace() {
        return memberFace;
    }

    public void setMemberFace(String memberFace) {
        this.memberFace = memberFace;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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

    public Integer getHaveImage() {
        return haveImage;
    }

    public void setHaveImage(Integer haveImage) {
        this.haveImage = haveImage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(Integer replyStatus) {
        this.replyStatus = replyStatus;
    }

    public Integer getAdditionalStatus() {
        return additionalStatus;
    }

    public void setAdditionalStatus(Integer additionalStatus) {
        this.additionalStatus = additionalStatus;
    }

    public String getAdditionalContent() {
        return additionalContent;
    }

    public void setAdditionalContent(String additionalContent) {
        this.additionalContent = additionalContent;
    }

    public Long getAdditionalTime() {
        return additionalTime;
    }

    public void setAdditionalTime(Long additionalTime) {
        this.additionalTime = additionalTime;
    }

    public Integer getAdditionalHaveImage() {
        return additionalHaveImage;
    }

    public void setAdditionalHaveImage(Integer additionalHaveImage) {
        this.additionalHaveImage = additionalHaveImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberComment that = (MemberComment) o;
        if (commentId != null ? !commentId.equals(that.commentId) : that.commentId != null) {
            return false;
        }
        if (goodsId != null ? !goodsId.equals(that.goodsId) : that.goodsId != null) {
            return false;
        }
        if (skuId != null ? !skuId.equals(that.skuId) : that.skuId != null) {
            return false;
        }
        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) {
            return false;
        }
        if (memberName != null ? !memberName.equals(that.memberName) : that.memberName != null) {
            return false;
        }
        if (memberFace != null ? !memberFace.equals(that.memberFace) : that.memberFace != null) {
            return false;
        }
        if (goodsName != null ? !goodsName.equals(that.goodsName) : that.goodsName != null) {
            return false;
        }
        if (content != null ? !content.equals(that.content) : that.content != null) {
            return false;
        }
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) {
            return false;
        }
        if (haveImage != null ? !haveImage.equals(that.haveImage) : that.haveImage != null) {
            return false;
        }
        if (status != null ? !status.equals(that.status) : that.status != null) {
            return false;
        }
        if (grade != null ? !grade.equals(that.grade) : that.grade != null) {
            return false;
        }
        if (orderSn != null ? !orderSn.equals(that.orderSn) : that.orderSn != null) {
            return false;
        }
        if (replyStatus != null ? replyStatus.equals(that.replyStatus) : that.replyStatus == null) {
            return false;
        }
        if (additionalStatus != null ? !additionalStatus.equals(that.additionalStatus) : that.additionalStatus != null) {
            return false;
        }
        if (additionalContent != null ? !additionalContent.equals(that.additionalContent) : that.additionalContent != null) {
            return false;
        }
        if (additionalTime != null ? !additionalTime.equals(that.additionalTime) : that.additionalTime != null) {
            return false;
        }

        return additionalHaveImage != null ? additionalHaveImage.equals(that.additionalHaveImage) : that.additionalHaveImage == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (commentId != null ? commentId.hashCode() : 0);
        result = 31 * result + (goodsId != null ? goodsId.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (memberName != null ? memberName.hashCode() : 0);
        result = 31 * result + (memberFace != null ? memberFace.hashCode() : 0);
        result = 31 * result + (goodsName != null ? goodsName.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (haveImage != null ? haveImage.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        result = 31 * result + (orderSn != null ? orderSn.hashCode() : 0);
        result = 31 * result + (additionalStatus != null ? additionalStatus.hashCode() : 0);
        result = 31 * result + (additionalContent != null ? additionalContent.hashCode() : 0);
        result = 31 * result + (additionalTime != null ? additionalTime.hashCode() : 0);
        result = 31 * result + (additionalHaveImage != null ? additionalHaveImage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MemberComment{" +
                "commentId=" + commentId +
                ", goodsId=" + goodsId +
                ", skuId=" + skuId +
                ", memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", memberFace='" + memberFace + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", haveImage=" + haveImage +
                ", status=" + status +
                ", grade='" + grade + '\'' +
                ", orderSn='" + orderSn + '\'' +
                ", replyStatus=" + replyStatus +
                ", additionalStatus=" + additionalStatus +
                ", additionalContent='" + additionalContent + '\'' +
                ", additionalTime=" + additionalTime +
                ", additionalHaveImage=" + additionalHaveImage +
                '}';
    }
}
