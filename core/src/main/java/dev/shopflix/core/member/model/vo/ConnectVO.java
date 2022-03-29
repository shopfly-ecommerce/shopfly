/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model.vo;

import dev.shopflix.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zjp
 * @version v7.0
 * @Description 信任登录VO
 * @ClassName ConnectDO
 * @since v7.0 下午2:43 2018/6/20
 */
@Table(name = "es_connect")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ConnectVO {

    /**
     * 信任登录类型
     */
    @ApiModelProperty(name = "union_type", value = "信任登录类型")
    private String unionType;
    /**
     * 是否绑定
     */
    @ApiModelProperty(name = "is_bind", value = "是否绑定 ：true 已绑定，false 未绑定")
    private Boolean isBind;

    public String getUnionType() {
        return unionType;
    }

    public void setUnionType(String unionType) {
        this.unionType = unionType;
    }

    public Boolean getIsBind() {
        return isBind;
    }

    public void setIsBind(Boolean bind) {
        isBind = bind;
    }

    @Override
    public String toString() {
        return "ConnectVO{" +
                ", unionType='" + unionType + '\'' +
                ", isBind=" + isBind +
                '}';
    }
}
