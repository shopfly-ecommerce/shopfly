/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.model.dos;

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
 * 站内消息实体
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-04 21:50:52
 */
@Table(name = "es_message")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Message implements Serializable {

    private static final long serialVersionUID = 8197127057448115L;

    /**
     * 站内消息主键
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * 标题
     */
    @Column(name = "title")
    @ApiModelProperty(name = "title", value = "标题", required = false)
    private String title;
    /**
     * 消息内容
     */
    @Column(name = "content")
    @ApiModelProperty(name = "content", value = "消息内容", required = false)
    private String content;
    /**
     * 会员id
     */
    @Column(name = "member_ids")
    @ApiModelProperty(name = "member_ids", value = "会员id", required = false)
    private String memberIds;
    /**
     * 管理员id
     */
    @Column(name = "admin_id")
    @ApiModelProperty(name = "admin_id", value = "管理员id", required = false)
    private Integer adminId;
    /**
     * 管理员名称
     */
    @Column(name = "admin_name")
    @ApiModelProperty(name = "admin_name", value = "管理员名称", required = false)
    private String adminName;
    /**
     * 发送时间
     */
    @Column(name = "send_time")
    @ApiModelProperty(name = "send_time", value = "发送时间", required = false)
    private Long sendTime;
    /**
     * 发送类型
     */
    @Column(name = "send_type")
    @ApiModelProperty(name = "send_type", value = "发送类型,0全站，1指定会员", required = false)
    private Integer sendType;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
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
        Message that = (Message) o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (title != null ? !title.equals(that.title) : that.title != null) {
            return false;
        }
        if (content != null ? !content.equals(that.content) : that.content != null) {
            return false;
        }
        if (memberIds != null ? !memberIds.equals(that.memberIds) : that.memberIds != null) {
            return false;
        }
        if (adminId != null ? !adminId.equals(that.adminId) : that.adminId != null) {
            return false;
        }
        if (adminName != null ? !adminName.equals(that.adminName) : that.adminName != null) {
            return false;
        }
        if (sendTime != null ? !sendTime.equals(that.sendTime) : that.sendTime != null) {
            return false;
        }
        return sendType != null ? sendType.equals(that.sendType) : that.sendType == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (memberIds != null ? memberIds.hashCode() : 0);
        result = 31 * result + (adminId != null ? adminId.hashCode() : 0);
        result = 31 * result + (adminName != null ? adminName.hashCode() : 0);
        result = 31 * result + (sendTime != null ? sendTime.hashCode() : 0);
        result = 31 * result + (sendType != null ? sendType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", memberIds='" + memberIds + '\'' +
                ", adminId=" + adminId +
                ", adminName='" + adminName + '\'' +
                ", sendTime=" + sendTime +
                ", sendType=" + sendType +
                '}';
    }


}