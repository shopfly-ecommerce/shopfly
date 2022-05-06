/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.model.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 微信小程序登陆传参
 *
 * @author cs
 * @version v1.0
 * @since v7.2.2
 * 2020-09-24
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WeChatMiniLoginDTO implements Serializable {


    private static final long serialVersionUID = 8061241514328873785L;

    @ApiModelProperty(name = "edata", value = "encryptedData", required = true)
    private String edata;

    @ApiModelProperty(name = "iv", value = "iv", required = true)
    private String iv;

    @ApiModelProperty(name = "code", value = "code", required = true)
    private String code;

    @ApiModelProperty(name = "uuid", value = "随机数", required = true)
    private String uuid;

    public String getEdata() {
        return edata;
    }

    public void setEdata(String edata) {
        this.edata = edata;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
