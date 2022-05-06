/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.system.model.vo;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * 站内消息实体
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-04 21:50:52
 */
public class MessageVO {
    /**
     * 标题
     */
    @Length(min = 2, max = 30, message = "站内消息标题必须在2-30个字符之间")
    @ApiModelProperty(name = "title", value = "标题", required = false)
    private String title;
    /**
     * 消息内容
     */
    @NotEmpty(message = "站内消息内容不能为空")
    @Length(min = 0, max = 500, message = "站内消息内容不能超过500个字符")
    @ApiModelProperty(name = "content", value = "消息内容", required = false)
    private String content;
    /**
     * 会员id
     */
    @ApiModelProperty(name = "member_ids", value = "会员id", required = false)
    private String memberIds;
    /**
     * 发送类型
     */
    @NotNull(message = "发送类型不能为空")
    @Range(min = 0, max = 1, message = "发送类型参数错误")
    @ApiModelProperty(name = "send_type", value = "发送类型,0全站，1指定会员", required = false)
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