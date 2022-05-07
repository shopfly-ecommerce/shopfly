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
package cloud.shopfly.b2c.core.payment.model.vo;

import cloud.shopfly.b2c.core.payment.model.dos.PaymentMethodDO;
import cloud.shopfly.b2c.core.payment.service.PaymentPluginManager;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Pay the plug-invo
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-04-11 16:06:57
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaymentPluginVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8418953348138006672L;

    @ApiModelProperty(value = "Name of Payment Method", hidden = true)
    private String methodName;

    @ApiModelProperty(value = "Pay the plug-inid", hidden = true)
    private String pluginId;

    @ApiModelProperty(value = "Picture of payment method")
    private String image;

    @ApiModelProperty(value = "Whether the original way back is supported,0Does not support1support")
    @NotNull(message = "Select whether the original route is supported")
    @Min(value = 0, message = "Whether the original route is supported The return value is incorrect")
    @Max(value = 1, message = "Whether the original route is supported The return value is incorrect")
    private Integer isRetrace;

    @ApiModelProperty(value = "Configuration items")
    @NotNull(message = "The client startup status cannot be empty")
    @Valid
    private List<ClientConfig> enableClient;


    public PaymentPluginVO(PaymentMethodDO payment) {

        this.methodName = payment.getMethodName();
        this.pluginId = payment.getPluginId();
        this.image = payment.getImage();
        this.isRetrace = payment.getIsRetrace();

    }

    public PaymentPluginVO(PaymentPluginManager plugin) {

        this.methodName = plugin.getPluginName();
        this.pluginId = plugin.getPluginId();
        this.isRetrace = plugin.getIsRetrace();
    }

    public PaymentPluginVO() {
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getPluginId() {
        return pluginId;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getIsRetrace() {
        return isRetrace;
    }

    public void setIsRetrace(Integer isRetrace) {
        this.isRetrace = isRetrace;
    }

    public List<ClientConfig> getEnableClient() {
        return enableClient;
    }

    public void setEnableClient(List<ClientConfig> enableClient) {
        this.enableClient = enableClient;
    }

    @Override
    public String toString() {
        return "PaymentPluginVO{" +
                "methodName='" + methodName + '\'' +
                ", pluginId='" + pluginId + '\'' +
                ", image='" + image + '\'' +
                ", isRetrace=" + isRetrace +
                ", enableClient=" + enableClient +
                '}';
    }
}
