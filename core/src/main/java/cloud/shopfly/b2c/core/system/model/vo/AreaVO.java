/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.system.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;
import java.util.List;

/**
 * @author cs
 * @version v2.0
 * @Description: 地区VO
 * @date 2018/8/22 15:16
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AreaVO  implements Serializable {


    private static final long serialVersionUID = 5022892972090471983L;

    @ApiParam("code")
    @Column(name = "code")
    private String code;

    @ApiParam("name")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("子地区列表")
    private List<AreaVO> children;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AreaVO> getChildren() {
        return children;
    }

    public void setChildren(List<AreaVO> children) {
        this.children = children;
    }
}
