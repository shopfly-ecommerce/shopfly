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
package cloud.shopfly.b2c.core.system.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;


/**
 * Template configuration details
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-08-22 15:10:51
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@Table(name = "es_ship_template_setting")
public class ShipTemplateSettingDO implements Serializable {

    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     */
    private static final long serialVersionUID = -2310849247997108107L;

    @Id
    @ApiModelProperty(hidden = true)
    private Integer id;

    @ApiModelProperty(hidden = true)
    @Column(name = "template_id")
    private Integer templateId;

    @ApiModelProperty(hidden = true)
    @Column(name = "rate_area_id")
    private Integer rateAreaId;

    @ApiModelProperty(hidden = true)
    @Column(name = "rate_area_name")
    private String rateAreaName;

    @ApiParam("Price type：absolute:The absolute value；percentage:The percentage")
    @Column(name = "amt_type")
    private String amtType;

    @ApiParam("Price")
    @Column(name = "amt")
    private Double amt;

    @ApiParam("Conditions in the：price:Price;weight:Weight;items:Quantity")
    @Column(name = "conditions_type")
    private String conditionsType;


    @ApiParam("Interval to")
    @Column(name = "region_start")
    private Double regionStart;

    @ApiParam("End of the range")
    @Column(name = "region_end")
    private Double regionEnd;


    @ApiParam("The serial number")
    @Column(name = "sort")
    private Integer sort;


    public String getRateAreaName() {
        return rateAreaName;
    }

    public void setRateAreaName(String rateAreaName) {
        this.rateAreaName = rateAreaName;
    }

    public Integer getRateAreaId() {
        return rateAreaId;
    }

    public void setRateAreaId(Integer rateAreaId) {
        this.rateAreaId = rateAreaId;
    }

    public String getAmtType() {
        return amtType;
    }

    public void setAmtType(String amtType) {
        this.amtType = amtType;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public String getConditionsType() {
        return conditionsType;
    }

    public void setConditionsType(String conditionsType) {
        this.conditionsType = conditionsType;
    }

    public Double getRegionStart() {
        return regionStart;
    }

    public void setRegionStart(Double regionStart) {
        this.regionStart = regionStart;
    }

    public Double getRegionEnd() {
        return regionEnd;
    }

    public void setRegionEnd(Double regionEnd) {
        this.regionEnd = regionEnd;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }
}
