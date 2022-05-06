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
import cloud.shopfly.b2c.framework.database.annotation.Column;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;
import java.util.List;

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
