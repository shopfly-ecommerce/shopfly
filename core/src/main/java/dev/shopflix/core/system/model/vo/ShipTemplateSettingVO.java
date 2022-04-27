/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.model.vo;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.gson.Gson;
import dev.shopflix.core.system.model.dos.RateAreaDO;
import dev.shopflix.core.system.model.dos.ShipTemplateSettingDO;
import dev.shopflix.framework.database.annotation.Column;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: 运费模板子VO
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


    @ApiParam(value = "区域名称",hidden = true)
    @Column(name = "rate_area_name")
    private String rateAreaName;

    @ApiParam(value = "地区‘，‘分隔   示例参数：北京，山西，天津，上海",hidden = true)
    @Column(name = "area")
    private String area;

    @ApiModelProperty(value = "地区id‘，‘分隔  示例参数：1，2，3，4 ",hidden = true)
    @Column(name = "area_id")
    private String areaId;

    @ApiParam(value = "地区列表",hidden = true)
    @Column(name = "areas")
    private List<AreaVO> areas;

    @ApiModelProperty(value = "区域id")
    @Column(name = "rate_area_id")
    private Integer rateAreaId;

    @ApiModelProperty(name = "items", value = "指定配送价格", required = true)
    private List<ShipTemplateSettingDO> items;



    public ShipTemplateSettingVO() {

    }



    public ShipTemplateSettingVO(List<ShipTemplateSettingDO> settingDOs, RateAreaDO rateAreaDO, boolean flag) {
        this.templateId = settingDOs.get(0).getTemplateId();
        this.rateAreaName = rateAreaDO.getName();
        this.setItems(settingDOs);
        if(!flag){
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
