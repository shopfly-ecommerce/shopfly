/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.system.model.vo;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import cloud.shopfly.b2c.core.system.model.dos.RateAreaDO;
import cloud.shopfly.b2c.core.system.model.dos.ShipTemplateSettingDO;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;
import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: Freight templateVO
 * @date 2018/10/2416:15
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ShipTemplateSettingVO implements Serializable {


    private static final long serialVersionUID = 7715186872590727796L;


    @ApiModelProperty(hidden = true)
    @Column(name = "template_id")
    private Integer templateId;


    @ApiParam(value = "name",hidden = true)
    @Column(name = "rate_area_name")
    private String rateAreaName;

    @ApiParam(value = "region‘，‘Separate example parameters：Beijing, Shanxi, Tianjin, Shanghai",hidden = true)
    @Column(name = "area")
    private String area;

    @ApiModelProperty(value = "regionid‘，‘Separate example parameters：1，2，3，4 ",hidden = true)
    @Column(name = "area_id")
    private String areaId;

    @ApiParam(value = "In the list",hidden = true)
    @Column(name = "areas")
    private List<AreaVO> areas;

    @ApiModelProperty(value = "areaid")
    @Column(name = "rate_area_id")
    private Integer rateAreaId;

    @ApiModelProperty(name = "items", value = "Designated delivery price", required = true)
    private List<ShipTemplateSettingDO> items;



    public ShipTemplateSettingVO() {

    }



    public ShipTemplateSettingVO(List<ShipTemplateSettingDO> settingDOs, RateAreaDO rateAreaDO, boolean flag) {
        this.templateId = settingDOs.get(0).getTemplateId();
        this.rateAreaName = rateAreaDO.getName();
        this.rateAreaId = rateAreaDO.getId();
        this.setItems(settingDOs);
        if(flag){
            this.areas = JSON.parseArray(rateAreaDO.getAreaJson()).toJavaList(AreaVO.class);
            this.area = rateAreaDO.getArea();
            this.areaId = rateAreaDO.getAreaId();
        }
    }


    public Integer getRateAreaId() {
        return rateAreaId;
    }

    public void setRateAreaId(Integer rateAreaId) {
        this.rateAreaId = rateAreaId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public List<AreaVO> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaVO> areas) {
        this.areas = areas;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public List<ShipTemplateSettingDO> getItems() {
        return items;
    }

    public void setItems(List<ShipTemplateSettingDO> items) {
        this.items = items;
    }

    public String getRateAreaName() {
        return rateAreaName;
    }

    public void setRateAreaName(String rateAreaName) {
        this.rateAreaName = rateAreaName;
    }

    @Override
    public String toString() {
        return "ShipTemplateChildSellerVO{" +
                "area='" + area + '\'' +
                '}';
    }
}
