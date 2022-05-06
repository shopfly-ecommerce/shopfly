/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.system.model.vo;

import cloud.shopfly.b2c.framework.database.annotation.Column;

import java.io.Serializable;

/**
 * @author fk
 * @version v2.0
 * @Description:
 * @date 2018/10/26 16:32
 * @since v7.0.0
 */
public class ShipTemplateChildBuyerVO extends ShipTemplateChildBaseVO implements Serializable {

    @Column(name = "area_id")
    private String areaId;


    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "ShipTemplateChildBuyerVO{" +
                "areaId='" + areaId + '\'' +
                '}';
    }
}
