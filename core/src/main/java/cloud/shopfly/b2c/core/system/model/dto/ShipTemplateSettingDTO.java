/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.system.model.dto;

import cloud.shopfly.b2c.core.system.model.dos.ShipTemplateSettingDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 扩展用于与商品相关的属性
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018年8月23日 下午4:02:52
 *
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ShipTemplateSettingDTO  implements Serializable {


    private static final long serialVersionUID = 1548871339469347053L;



    @ApiModelProperty(name = "items", value = "价格条件设置", required = false)
    private List<ShipTemplateSettingDO> priceSettings;


    @ApiModelProperty(name = "items", value = "重量条件设置", required = false)
    private List<ShipTemplateSettingDO> weightSettings;

    @ApiModelProperty(name = "items", value = "数量条件设置", required = false)
    private List<ShipTemplateSettingDO> itemsSettings;


    public List<ShipTemplateSettingDO> getPriceSettings() {
        return priceSettings;
    }

    public void setPriceSettings(List<ShipTemplateSettingDO> priceSettings) {
        this.priceSettings = priceSettings;
    }

    public List<ShipTemplateSettingDO> getWeightSettings() {
        return weightSettings;
    }

    public void setWeightSettings(List<ShipTemplateSettingDO> weightSettings) {
        this.weightSettings = weightSettings;
    }

    public List<ShipTemplateSettingDO> getItemsSettings() {
        return itemsSettings;
    }

    public void setItemsSettings(List<ShipTemplateSettingDO> itemsSettings) {
        this.itemsSettings = itemsSettings;
    }
}
