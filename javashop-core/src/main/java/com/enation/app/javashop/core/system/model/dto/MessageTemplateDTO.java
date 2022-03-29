/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.system.model.dto;

import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.PrimaryKeyField;
import com.enation.app.javashop.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * 消息模版实体
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-05 16:38:43
 */
@Table(name = "es_message_template")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MessageTemplateDTO implements Serializable {

    private static final long serialVersionUID = 2098633720936223L;

    /**
     * 模版ID
     */
    @Id(name = "id")
    @ApiModelProperty(required = true)
    private Integer id;
    /**
     * 模板名称
     */
    @Column(name = "tpl_name")
    @ApiModelProperty(name = "tpl_name", value = "模板名称", required = true)
    @NotEmpty(message = "模板名称必填")
    private String tplName;
    /**
     * 邮件标题
     */
    @Column(name = "email_title")
    @ApiModelProperty(name = "email_title", value = "邮件标题", required = true)
    @NotEmpty(message = "邮件标题必填")
    private String emailTitle;
    /**
     * 短信提醒是否开启
     */
    @Column(name = "sms_state")
    @ApiModelProperty(name = "sms_state", value = "短信提醒是否开启", required = true, allowableValues = "OPEN,CLOSED")
    @NotEmpty(message = "短信提醒是否开启必填")
    private String smsState;
    /**
     * 站内信提醒是否开启
     */
    @Column(name = "notice_state")
    @ApiModelProperty(name = "notice_state", value = "站内信提醒是否开启", required = true, allowableValues = "OPEN,CLOSED")
    @NotEmpty(message = "站内信提醒是否开启必填")
    private String noticeState;
    /**
     * 邮件提醒是否开启
     */
    @Column(name = "email_state")
    @ApiModelProperty(name = "email_state", value = "邮件提醒是否开启", required = true, allowableValues = "OPEN,CLOSED")
    @NotEmpty(message = "邮件提醒是否开启必填")
    private String emailState;
    /**
     * 站内信内容
     */
    @Column(name = "content")
    @ApiModelProperty(name = "content", value = "站内信内容", required = true)
    @NotEmpty(message = "站内信内容必填")
    private String content;
    /**
     * 短信内容
     */
    @Column(name = "sms_content")
    @ApiModelProperty(name = "sms_content", value = "短信内容", required = true)
    @NotEmpty(message = "短信内容必填")
    private String smsContent;
    /**
     * 邮件内容
     */
    @Column(name = "email_content")
    @ApiModelProperty(name = "email_content", value = "邮件内容", required = true)
    @NotEmpty(message = "邮件内容必填")
    private String emailContent;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTplName() {
        return tplName;
    }

    public void setTplName(String tplName) {
        this.tplName = tplName;
    }

    public String getEmailTitle() {
        return emailTitle;
    }

    public void setEmailTitle(String emailTitle) {
        this.emailTitle = emailTitle;
    }

    public String getSmsState() {
        return smsState;
    }

    public void setSmsState(String smsState) {
        this.smsState = smsState;
    }

    public String getNoticeState() {
        return noticeState;
    }

    public void setNoticeState(String noticeState) {
        this.noticeState = noticeState;
    }

    public String getEmailState() {
        return emailState;
    }

    public void setEmailState(String emailState) {
        this.emailState = emailState;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageTemplateDTO that = (MessageTemplateDTO) o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (tplName != null ? !tplName.equals(that.tplName) : that.tplName != null) {
            return false;
        }
        if (emailTitle != null ? !emailTitle.equals(that.emailTitle) : that.emailTitle != null) {
            return false;
        }
        if (smsState != null ? !smsState.equals(that.smsState) : that.smsState != null) {
            return false;
        }
        if (noticeState != null ? !noticeState.equals(that.noticeState) : that.noticeState != null) {
            return false;
        }
        if (emailState != null ? !emailState.equals(that.emailState) : that.emailState != null) {
            return false;
        }
        if (content != null ? !content.equals(that.content) : that.content != null) {
            return false;
        }
        if (smsContent != null ? !smsContent.equals(that.smsContent) : that.smsContent != null) {
            return false;
        }
        return emailContent != null ? emailContent.equals(that.emailContent) : that.emailContent == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (tplName != null ? tplName.hashCode() : 0);
        result = 31 * result + (emailTitle != null ? emailTitle.hashCode() : 0);
        result = 31 * result + (smsState != null ? smsState.hashCode() : 0);
        result = 31 * result + (noticeState != null ? noticeState.hashCode() : 0);
        result = 31 * result + (emailState != null ? emailState.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (smsContent != null ? smsContent.hashCode() : 0);
        result = 31 * result + (emailContent != null ? emailContent.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageTemplateDO{" +
                "id=" + id +
                ", tplName='" + tplName + '\'' +
                ", emailTitle='" + emailTitle + '\'' +
                ", smsState='" + smsState + '\'' +
                ", noticeState='" + noticeState + '\'' +
                ", emailState='" + emailState + '\'' +
                ", content='" + content + '\'' +
                ", smsContent='" + smsContent + '\'' +
                ", emailContent='" + emailContent + '\'' +
                '}';
    }


}