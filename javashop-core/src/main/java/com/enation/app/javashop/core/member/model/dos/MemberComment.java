/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.member.model.dos;

import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.PrimaryKeyField;
import com.enation.app.javashop.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 评论实体
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
     * 评论主键
     */
    @Id(name = "comment_id")
    @ApiModelProperty(hidden = true)
    private Integer commentId;
    /**
     * 商品id
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "商品id", required = false)
    private Integer goodsId;
    /**
     * skuid
     */
    @Column(name = "sku_id")
    @ApiModelProperty(name = "sku_id", value = "skuid", required = false)
    private Integer skuId;
    /**
     * 会员id
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id", value = "会员id", required = false)
    private Integer memberId;
    /**
     * 会员名称
     */
    @Column(name = "member_name")
    @ApiModelProperty(name = "member_name", value = "会员名称", required = false)
    private String memberName;
    /**
     * 会员头像
     */
    @Column(name = "member_face")
    @ApiModelProperty(name = "member_face", value = "会员头像", required = false)
    private String memberFace;
    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    @ApiModelProperty(name = "goods_name", value = "商品名称", required = false)
    private String goodsName;
    /**
     * 评论内容
     */
    @Column(name = "content")
    @ApiModelProperty(name = "content", value = "评论内容", required = false)
    private String content;
    /**
     * 评论时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "评论时间", required = false)
    private Long createTime;
    /**
     * 是否有图片 1 有 0 没有
     */
    @Column(name = "have_image")
    @ApiModelProperty(name = "have_image", value = "是否有图片 1 有 0 没有", required = false)
    private Integer haveImage;
    /**
     * 状态  1 正常 0 删除
     */
    @Column(name = "status")
    @ApiModelProperty(name = "status", value = "状态  1 正常 0 删除 ", required = false)
    private Integer status;
    /**
     * 好中差评
     */
    @Column(name = "grade")
    @ApiModelProperty(name = "grade", value = "好中差评", required = false)
    private String grade;
    /**
     * 订单编号
     */
    @Column(name = "order_sn")
    @ApiModelProperty(name = "order_sn", value = "订单编号", required = false)
    private String orderSn;
    /**
     * 是否回复 1 是 0 否
     */
    @Column(name = "reply_status")
    @ApiModelProperty(name = "reply_status", value = "是否回复 1 是 0 否", required = false)
    private Integer replyStatus;

    /**
     * 追加评论状态 0：未追加，1：已追加
     */
    @Column(name = "additional_status")
    @ApiModelProperty(name = "additional_status", value = "追加评论状态 0：未追加，1：已追加", required = false)
    private Integer additionalStatus;

    /**
     * 追加评论内容
     */
    @Column(name = "additional_content")
    @ApiModelProperty(name = "additional_content", value = "追加评论内容", required = false)
    private String additionalContent;

    /**
     * 追加评论时间
     */
    @Column(name = "additional_time")
    @ApiModelProperty(name = "additional_time", value = "追加评论时间", required = false)
    private Long additionalTime;

    /**
     * 追加的评论是否上传了图片 0：未上传，1：已上传
     */
    @Column(name = "additional_have_image")
    @ApiModelProperty(name = "additional_have_image", value = "追加的评论是否上传了图片 0：未上传，1：已上传", required = false)
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