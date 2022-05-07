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

import cloud.shopfly.b2c.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author fk
 * @version v1.0
 * @Description: Method of paymentVO A brief
 * @date 2018/4/2317:06
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaymentMethodVO implements Serializable {

    /**
     * Name of Payment Method
     */
    @Column(name = "method_name")
    @ApiModelProperty(name = "method_name", value = "Name of Payment Method", required = false)
    private String methodName;
    /**
     * Pay the plug-inid
     */
    @Column(name = "plugin_id")
    @ApiModelProperty(name = "plugin_id", value = "Payment plug-in name", required = false)
    private String pluginId;

    /**
     * Whether to support the original way back
     */
    @Column(name = "is_retrace")
    @ApiModelProperty(name = "is_retrace", value = "Whether to support the original way back", required = false)
    private Integer isRetrace;

    /**
     * Picture of payment method
     */
    @Column(name = "image")
    @ApiModelProperty(name = "image", value = "Picture of payment method", required = false)
    private String image;

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

    public Integer getIsRetrace() {
        return isRetrace;
    }

    public void setIsRetrace(Integer isRetrace) {
        this.isRetrace = isRetrace;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "PaymentMethodVO{" +
                "methodName='" + methodName + '\'' +
                ", pluginId='" + pluginId + '\'' +
                ", isRetrace=" + isRetrace +
                ", image='" + image + '\'' +
                '}';
    }
}
