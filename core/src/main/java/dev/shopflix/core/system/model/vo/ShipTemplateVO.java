/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.model.vo;

import dev.shopflix.core.system.model.dos.ShipTemplateDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.shopflix.core.system.model.dos.ShipTemplateSettingDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: 运费模板VO
 * @date 2018/8/22 15:16
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ShipTemplateVO extends ShipTemplateDO implements Serializable {

    @ApiModelProperty(name = "items", value = "指定配送区域", required = true)
    private List<ShipTemplateSettingVO> items;

    public List<ShipTemplateSettingVO> getItems() {
        return items;
    }

    public void setItems(List<ShipTemplateSettingVO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ShipTemplateSettingDO{" +
                "items=" + items +
                '}';
    }
}
