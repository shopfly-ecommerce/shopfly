/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model.dos;

import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.PrimaryKeyField;
import dev.shopflix.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 咨询实体
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
     * 主键
     */
    @Id(name = "ask_id")
    @ApiModelProperty(hidden = true)
    private Integer askId;
    /**
     * 商品id
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "商品id", required = false)
    private Integer goodsId;
    /**
     * 会员id
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id", value = "会员id", required = false)
    private Integer memberId;
    /**
     * 咨询内容
     */
    @Column(name = "content")
    @ApiModelProperty(name = "content", value = "咨询内容", required = false)
    private String content;
    /**
     * 咨询时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(name = "create_time", value = "咨询时间", required = false)
    private Long createTime;
    /**
     * 回复内容
     */
    @Column(name = "reply")
    @ApiModelProperty(name = "reply", value = "回复内容", required = false)
    private String reply;
    /**
     * 回复时间
     */
    @Column(name = "reply_time")
    @ApiModelProperty(name = "reply_time", value = "回复时间", required = false)
    private Long replyTime;
    /**
     * 回复状态 1 已回复 0未回复
     */
    @Column(name = "reply_status")
    @ApiModelProperty(name = "reply_status", value = "回复状态 1 已回复 0 未回复", required = false)
    private Integer replyStatus;
    /**
     * 状态
     */
    @Column(name = "status")
    @ApiModelProperty(name = "status", value = "状态 ", required = false)
    private Integer status;
    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    @ApiModelProperty(name = "goods_name", value = "商品名称", required = false)
    private String goodsName;

    /**
     * 买家名称
     */
    @Column(name = "member_name")
    @ApiModelProperty(name = "member_name", value = "买家名称", required = false)
    private String memberName;

    /**
     * 会员头像
     */
    @Column(name = "member_face")
    @ApiModelProperty(name = "member_face", value = "会员头像", required = false)
    private String memberFace;

    /**
     * 审核状态
     */
    @Column(name = "auth_status")
    @ApiModelProperty(name = "auth_status", value = "审核状态", required = false)
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