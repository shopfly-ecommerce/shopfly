/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.payment.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: 客户端配置
 * @date 2018/4/1117:05
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ClientConfig implements Serializable {

    @ApiModelProperty(name = "字段name")
    @NotEmpty(message = "客户端key不能为空")
    private String key;

    @ApiModelProperty(name = "字段文本提示")
    private String name;

    @ApiModelProperty(name = "字段文本提示", value = "config_list")
    private List<PayConfigItem> configList;

    @ApiModelProperty(name = "是否开启 1开启 0关闭", value = "is_open")
    @NotNull(message = "是否开启某客户端不能为空")
    @Min(value = 0, message = "是否开启某客户端值不正确")
    @Max(value = 1, message = "是否开启某客户端值不正确")
    private Integer isOpen;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PayConfigItem> getConfigList() {
        return configList;
    }

    public void setConfigList(List<PayConfigItem> configList) {
        this.configList = configList;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }
}
