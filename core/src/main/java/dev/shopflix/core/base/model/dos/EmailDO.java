/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.base.model.dos;

import java.io.Serializable;

import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.PrimaryKeyField;
import dev.shopflix.framework.database.annotation.Table;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.Email;


/**
 * 邮件记录实体
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-26 16:22:11
 */
@Table(name="es_email")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EmailDO implements Serializable {

    private static final long serialVersionUID = 5515821199866313L;

    /**邮件记录id*/
    @Id(name = "id")
    @ApiModelProperty(hidden=true)
    private Integer id;
    /**邮件标题*/
    @Column(name = "title")
    @ApiModelProperty(name="title",value="邮件标题",required=false)
    private String title;
    /**邮件类型*/
    @Column(name = "type")
    @ApiModelProperty(name="type",value="邮件类型",required=false)
    private String type;
    /**是否成功*/
    @Column(name = "success")
    @Min(message="必须为数字", value = 0)
    @ApiModelProperty(name="success",value="是否成功",required=false)
    private Integer success;
    /**邮件接收者*/
    @Column(name = "email")
    @Email(message="格式不正确")
    @ApiModelProperty(name="email",value="邮件接收者",required=false)
    private String email;
    /**邮件内容*/
    @Column(name = "content")
    @ApiModelProperty(name="content",value="邮件内容",required=false)
    private String content;
    /**错误次数*/
    @Column(name = "error_num")
    @Min(message="必须为数字", value = 0)
    @ApiModelProperty(name="error_num",value="错误次数",required=false)
    private Integer errorNum;
    /**最后发送时间*/
    @Column(name = "last_send")
    @ApiModelProperty(name="last_send",value="最后发送时间",required=false)
    private Long lastSend;


    @Override
    public String toString() {
        return "EmailDO [id=" + id + ", title=" + title + ", type=" + type + ", success=" + success + ", email=" + email
                + ", context=" + content + ", errorNum=" + errorNum + ", lastSend=" + lastSend + "]";
    }
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

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Integer getSuccess() {
        return success;
    }
    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public Integer getErrorNum() {
        return errorNum;
    }
    public void setErrorNum(Integer errorNum) {
        this.errorNum = errorNum;
    }

    public Long getLastSend() {
        return lastSend;
    }
    public void setLastSend(Long lastSend) {
        this.lastSend = lastSend;
    }


}