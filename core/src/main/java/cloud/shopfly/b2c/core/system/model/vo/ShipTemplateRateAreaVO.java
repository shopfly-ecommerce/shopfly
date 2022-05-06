package cloud.shopfly.b2c.core.system.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import cloud.shopfly.b2c.core.system.model.dos.RateAreaDO;
import cloud.shopfly.b2c.core.system.model.dos.ShipTemplateSettingDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 运费模板区域设置详情VO
 * @description:
 * @author: cs
 * @create: 2022-04-24 16:00
 **/
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ShipTemplateRateAreaVO extends RateAreaDO  implements Serializable {


    private static final long serialVersionUID = -7038993592437698929L;

    @ApiModelProperty(name = "items", value = "指定配送区域", required = true)
    private List<ShipTemplateSettingDO> items;

    public List<ShipTemplateSettingDO> getItems() {
        return items;
    }

    public void setItems(List<ShipTemplateSettingDO> items) {
        this.items = items;
    }
}
