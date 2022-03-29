/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model.vo;

import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.util.JsonUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author zjp
 * @version v7.0
 * @Description 信任登录参数VO
 * @ClassName ConnectSettingVO
 * @since v7.0 下午3:46 2018/6/5
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ConnectSettingVO implements Serializable {

    /**
     * Id
     */
    @Id(name = "id")
    @ApiModelProperty(name = "id",value = "id")
    private Integer id;

    /**
     * 参数配置名称
     */
    @Column(name = "name")
    @ApiModelProperty(name = "name", value = "参数配置名称")
    @NotEmpty(message="参数配置名称必填")
    private String name;
    /**
     * 信任登录类型
     */
    @Column(name = "type")
    @ApiModelProperty(name = "type", value = "授权类型",allowableValues = "QQ,WEIBO,WECHAT,ALIPAY")
    private String type;
    /**
     * 信任登录配置参数
     */
    @Column(name = "config")
    @ApiModelProperty(name = "config", value = "信任登录配置参数")
    private String config;

    private List<ConnectSettingParametersVO> clientList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public List<ConnectSettingParametersVO> getClientList(){
        return JsonUtil.jsonToList(this.config,ConnectSettingParametersVO.class);
    }

    public void setClientList(List<ConnectSettingParametersVO> clientList) {
        this.clientList = clientList;
    }

    @Override
    public String toString() {
        return "ConnectSettingDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", config='" + config + '\'' +
                '}';
    }
}
