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

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.gson.Gson;
import cloud.shopfly.b2c.framework.database.annotation.Column;
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
public class RateAreaChildVO implements Serializable {

    @ApiParam("地区‘，‘分隔   示例参数：北京，山西，天津，上海")
    @Column(name = "area")
    private String area;

    @ApiModelProperty(hidden=true)
    private List<ShipTemplateChildArea> regions;


    private List<ShipTemplateChildArea> getConvertRegions(String area) {

        List<ShipTemplateChildArea> list = new ArrayList<>();

        Gson gson = new Gson();
        Map<String, Map> map = new HashMap();
        map = gson.fromJson(area, map.getClass());
        for (String key : map.keySet()) {

            ShipTemplateChildArea childArea = new ShipTemplateChildArea();

            Map dto = map.get(key);
            if ((boolean)dto.get("selected_all")) {
                childArea.setName(dto.get("local_name").toString());
                list.add(childArea);
            } else {
                //某省份下面的几市
                Map<String, Map> citiesMap = (Map<String, Map>)dto.get("children");

                for (String cityKey : citiesMap.keySet()) {
                    Map cityMap = citiesMap.get(cityKey);
                    ShipTemplateChildArea childArea1 = new ShipTemplateChildArea();
                    childArea1.setName(cityMap.get("local_name").toString());

                    //如果市没有被全选，则赋值children
                    if (!(boolean)cityMap.get("selected_all")) {
                        List<ShipTemplateChildArea> children = new ArrayList<>();
                        Map<String, Map> regionMap = (Map<String, Map>)cityMap.get("children");
                        for (String regionKey : regionMap.keySet()) {
                            Map region = regionMap.get(regionKey);
                            ShipTemplateChildArea regionArea = new ShipTemplateChildArea();
                            regionArea.setName(region.get("local_name").toString());
                            children.add(regionArea);
                        }
                        childArea1.setChildren(children);
                    }
                    list.add(childArea1);
                }
            }
        }
        return list;

    }


    public List<ShipTemplateChildArea> getRegions() {
        return regions;
    }

    public void setRegions(List<ShipTemplateChildArea> regions) {
        this.regions = regions;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "ShipTemplateChildSellerVO{" +
                "area='" + area + '\'' +
                ", regions=" + regions +
                '}';
    }
}
