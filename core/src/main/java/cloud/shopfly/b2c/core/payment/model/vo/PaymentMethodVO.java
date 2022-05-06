/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * @Description: 支付方式VO 简要
 * @date 2018/4/2317:06
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaymentMethodVO implements Serializable {

    /**
     * 支付方式名称
     */
    @Column(name = "method_name")
    @ApiModelProperty(name = "method_name", value = "支付方式名称", required = false)
    private String methodName;
    /**
     * 支付插件id
     */
    @Column(name = "plugin_id")
    @ApiModelProperty(name = "plugin_id", value = "支付插件名称", required = false)
    private String pluginId;

    /**
     * 是否支持原路退回
     */
    @Column(name = "is_retrace")
    @ApiModelProperty(name = "is_retrace", value = "是否支持原路退回", required = false)
    private Integer isRetrace;

    /**
     * 支付方式图片
     */
    @Column(name = "image")
    @ApiModelProperty(name = "image", value = "支付方式图片", required = false)
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
