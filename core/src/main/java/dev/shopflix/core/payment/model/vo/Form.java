/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.payment.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author fk
 * @version v1.0
 * @Description: 支付请求返回的form
 * @date 2018/7/17 10:39
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Form {

    @ApiModelProperty(value = "表单请求地址")
    private String gatewayUrl;

    @ApiModelProperty(value = "表单请求内容")
    private List<FormItem> formItems;

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public List<FormItem> getFormItems() {
        return formItems;
    }

    public void setFormItems(List<FormItem> formItems) {
        this.formItems = formItems;
    }
}
