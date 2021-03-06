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
package cloud.shopfly.b2c.core.system.model.vo;

import cloud.shopfly.b2c.framework.util.StringUtil;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * The progress bar
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-05-02 In the afternoon3:40
 */
public class ProgressVo implements Serializable {


    @ApiModelProperty("The percentage")
    private Integer percentage = 0;
    @ApiModelProperty("Status：The enumerationProgressEnum")
    private String status = "";
    @ApiModelProperty("Being generated")
    private String text = "";
    @ApiModelProperty("The message")
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
