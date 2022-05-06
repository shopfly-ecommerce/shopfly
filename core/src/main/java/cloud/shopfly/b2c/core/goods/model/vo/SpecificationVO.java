/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goods.model.vo;

import cloud.shopfly.b2c.core.goods.model.dos.SpecValuesDO;
import cloud.shopfly.b2c.core.goods.model.dos.SpecificationDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 规格vo
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月20日 上午11:28:48
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SpecificationVO extends SpecificationDO {

    /**
     *
     */
    private static final long serialVersionUID = 6722899699412983854L;
    /**
     * 规格值
     */
    @ApiModelProperty("规格值")
    private List<SpecValuesDO> valueList;

    public List<SpecValuesDO> getValueList() {
        return valueList;
    }

    public void setValueList(List<SpecValuesDO> valueList) {
        this.valueList = valueList;
    }

    @Override
    public String toString() {
        return "SpecificationVO [valueList=" + valueList + "]";
    }


}
