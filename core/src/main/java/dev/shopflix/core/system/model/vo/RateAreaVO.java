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
import dev.shopflix.core.system.model.dos.ShipTemplateDO;
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
 * @author cs
 * @version v2.0
 * @Description: 区域VO
 * @date 2018/8/22 15:16
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RateAreaVO extends RateAreaDO implements Serializable {

    @ApiParam("地区列表")
    @Column(name = "areas")
    private List<AreaVO> areas;



    public RateAreaVO() {


    }

    public RateAreaVO(RateAreaDO rateAreaDO) {
        this.areas = JSON.parseArray(rateAreaDO.getAreaJson()).toJavaList(AreaVO.class);
        this.setId(rateAreaDO.getId());
        this.setArea(rateAreaDO.getArea());
        this.setAreaId(rateAreaDO.getAreaId());
        this.setCreateTime(rateAreaDO.getCreateTime());
        this.setName(rateAreaDO.getName());
        this.setAreaJson(rateAreaDO.getAreaJson());


    }

    public List<AreaVO> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaVO> areas) {
        this.areas = areas;
    }
}
