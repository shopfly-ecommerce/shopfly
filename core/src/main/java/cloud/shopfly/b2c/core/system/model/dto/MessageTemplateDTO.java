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
package cloud.shopfly.b2c.core.system.model.dto;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * Message template entity
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
     * templateID
     */
    @Id(name = "id")
    @ApiModelProperty(required = true)
    private Integer id;
    /**
     * Template name
     */
    @Column(name = "tpl_name")
    @ApiModelProperty(name = "tpl_name", value = "Template name", required = true)
    @NotEmpty(message = "Template name This parameter is mandatory")
    private String tplName;
    /**
     * Email title
     */
    @Column(name = "email_title")
    @ApiModelProperty(name = "email_title", value = "Email title", required = true)
    @NotEmpty(message = "Email title Mandatory")
    private String emailTitle;
    /**
     * Whether SMS notification is enabled
     */
    @Column(name = "sms_state")
    @ApiModelProperty(name = "sms_state", value = "Whether SMS notification is enabled", required = true, allowableValues = "OPEN,CLOSED")
    @NotEmpty(message = "SMS notification Specifies whether to enable SMS notification")
    private String smsState;
    /**
     * Whether the in-station letter reminder is enabled
     */
    @Column(name = "notice_state")
    @ApiModelProperty(name = "notice_state", value = "Whether the in-station letter reminder is enabled", required = true, allowableValues = "OPEN,CLOSED")
    @NotEmpty(message = "Station letter reminder whether to open mandatory")
    private String noticeState;
    /**
     * Whether to enable email notification
     */
    @Column(name = "email_state")
    @ApiModelProperty(name = "email_state", value = "Whether to enable email notification", required = true, allowableValues = "OPEN,CLOSED")
    @NotEmpty(message = "Whether to enable email notification Mandatory")
    private String emailState;
    /**
     * Station message content
     */
    @Column(name = "content")
    @ApiModelProperty(name = "content", value = "Station message content", required = true)
    @NotEmpty(message = "Station letter content must be filled in")
    private String content;
    /**
     * Message content
     */
    @Column(name = "sms_content")
    @ApiModelProperty(name = "sms_content", value = "Message content", required = true)
    @NotEmpty(message = "This parameter is mandatory")
    private String smsContent;
    /**
     * Email content
     */
    @Column(name = "email_content")
    @ApiModelProperty(name = "email_content", value = "Email content", required = true)
    @NotEmpty(message = "Email Content Mandatory")
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
