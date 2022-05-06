/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 支付插件vo
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

    @ApiModelProperty(value = "支付方式名称", hidden = true)
    private String methodName;

    @ApiModelProperty(value = "支付插件id", hidden = true)
    private String pluginId;

    @ApiModelProperty(value = "支付方式图片")
    private String image;

    @ApiModelProperty(value = "是否支持原路退回，0不支持  1支持")
    @NotNull(message = "请选择是否支持原路退回")
    @Min(value = 0, message = "是否支持原路退回值不正确")
    @Max(value = 1, message = "是否支持原路退回值不正确")
    private Integer isRetrace;

    @ApiModelProperty(value = "配置项")
    @NotNull(message = "客户端开启情况不能为空")
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
