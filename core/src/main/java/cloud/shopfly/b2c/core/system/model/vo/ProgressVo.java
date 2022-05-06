/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.system.model.vo;

import cloud.shopfly.b2c.framework.util.StringUtil;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 进度条
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-05-02 下午3:40
 */
public class ProgressVo implements Serializable {


    @ApiModelProperty("百分比")
    private Integer percentage = 0;
    @ApiModelProperty("状态：枚举 ProgressEnum")
    private String status = "";
    @ApiModelProperty("正在生成")
    private String text = "";
    @ApiModelProperty("消息")
    private String message = "";

    public ProgressVo() {
    }

    public ProgressVo(TaskProgress taskProgress) {
        this.percentage = (int) taskProgress.getSumPer();
        this.status = taskProgress.getTaskStatus();
        if (!StringUtil.isEmpty(taskProgress.getText())) {
            this.text = taskProgress.getText();
        }
        if (!StringUtil.isEmpty(taskProgress.getMessage())) {
            this.text = taskProgress.getMessage();
        }
    }

    public ProgressVo(Integer percentage, String status, String text, String message) {
        this.percentage = percentage;
        this.status = status;
        if (!StringUtil.isEmpty(text)) {
            this.text = text;
        }
        if (!StringUtil.isEmpty(message)) {
            this.text = text;
        }
    }

    public ProgressVo(Integer percentage, String status) {
        this.percentage = percentage;
        this.status = status;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ProgressVo{" +
                "percentage=" + percentage +
                ", status='" + status + '\'' +
                ", text='" + text + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProgressVo that = (ProgressVo) o;

        if (percentage != null ? !percentage.equals(that.percentage) : that.percentage != null) {
            return false;
        }
        if (status != null ? !status.equals(that.status) : that.status != null) {
            return false;
        }
        if (text != null ? !text.equals(that.text) : that.text != null) {
            return false;
        }
        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        int result = percentage != null ? percentage.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
