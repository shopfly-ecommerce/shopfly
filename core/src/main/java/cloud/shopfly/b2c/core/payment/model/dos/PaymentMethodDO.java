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
package cloud.shopfly.b2c.core.payment.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 支付方式实体
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-04-16 15:01:49
 */
@Table(name = "es_payment_method")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaymentMethodDO implements Serializable {

    private static final long serialVersionUID = 6216378920396390L;

    /**
     * 支付方式id
     */
    @Id(name = "method_id")
    @ApiModelProperty(hidden = true)
    private Integer methodId;
    /**
     * 支付方式名称
     */
    @Column(name = "method_name")
    @ApiModelProperty(name = "method_name", value = "支付方式名称", required = false)
    private String methodName;
    /**
     * 支付插件名称
     */
    @Column(name = "plugin_id")
    @ApiModelProperty(name = "plugin_id", value = "支付插件名称", required = false)
    private String pluginId;
    /**
     * pc是否可用
     */
    @Column(name = "pc_config")
    @ApiModelProperty(name = "pc_config", value = "pc是否可用", required = false)
    private String pcConfig;
    /**
     * wap是否可用
     */
    @Column(name = "wap_config")
    @ApiModelProperty(name = "wap_config", value = "wap是否可用", required = false)
    private String wapConfig;
    /**
     * app 原生是否可用
     */
    @Column(name = "app_native_config")
    @ApiModelProperty(name = "app_native_config", value = "app 原生是否可用", required = false)
    private String appNativeConfig;
    /**
     * 支付方式图片
     */
    @Column(name = "image")
    @ApiModelProperty(name = "image", value = "支付方式图片", required = false)
    private String image;
    /**
     * 是否支持原路退回
     */
    @Column(name = "is_retrace")
    @ApiModelProperty(name = "is_retrace", value = "是否支持原路退回", required = false)
    private Integer isRetrace;
    /**
     * app RN是否可用
     */
    @Column(name = "app_react_config")
    @ApiModelProperty(name = "app_react_config", value = "app RN是否可用", required = false)
    private String appReactConfig;
    /**
     * 小程序是否可用
     */
    @Column(name = "mini_config")
    @ApiModelProperty(name = "mini_config", value = "pc是否可用", required = false)
    private String miniConfig;

    @PrimaryKeyField
    public Integer getMethodId() {
        return methodId;
    }

    public void setMethodId(Integer methodId) {
        this.methodId = methodId;
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

    public String getPcConfig() {
        return pcConfig;
    }

    public void setPcConfig(String pcConfig) {
        this.pcConfig = pcConfig;
    }

    public String getWapConfig() {
        return wapConfig;
    }

    public void setWapConfig(String wapConfig) {
        this.wapConfig = wapConfig;
    }

    public String getAppNativeConfig() {
        return appNativeConfig;
    }

    public void setAppNativeConfig(String appNativeConfig) {
        this.appNativeConfig = appNativeConfig;
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

    public String getAppReactConfig() {
        return appReactConfig;
    }

    public void setAppReactConfig(String appReactConfig) {
        this.appReactConfig = appReactConfig;
    }

    public String getMiniConfig() {
        return miniConfig;
    }

    public void setMiniConfig(String miniConfig) {
        this.miniConfig = miniConfig;
    }

    @Override
    public String toString() {
        return "PaymentMethodDO{" +
                "methodId=" + methodId +
                ", methodName='" + methodName + '\'' +
                ", pluginId='" + pluginId + '\'' +
                ", pcConfig='" + pcConfig + '\'' +
                ", wapConfig='" + wapConfig + '\'' +
                ", appNativeConfig='" + appNativeConfig + '\'' +
                ", image='" + image + '\'' +
                ", isRetrace=" + isRetrace +
                ", appReactConfig='" + appReactConfig + '\'' +
                ", miniConfig='" + miniConfig + '\'' +
                '}';
    }

}