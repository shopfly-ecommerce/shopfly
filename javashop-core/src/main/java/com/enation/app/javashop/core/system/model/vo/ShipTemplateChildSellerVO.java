/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.system.model.vo;

import com.enation.app.javashop.core.system.model.dos.ShipTemplateChild;
import com.enation.app.javashop.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.gson.Gson;
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
public class ShipTemplateChildSellerVO extends ShipTemplateChildBaseVO implements Serializable {

    @ApiParam("地区‘，‘分隔   示例参数：北京，山西，天津，上海")
    @Column(name = "area")
    private String area;

    @ApiModelProperty(hidden=true)
    private List<ShipTemplateChildArea> regions;

    public ShipTemplateChildSellerVO() {

    }

    public ShipTemplateChildSellerVO(ShipTemplateChild child, boolean flag) {
        this.setFirstCompany(child.getFirstCompany());
        this.setFirstPrice(child.getFirstPrice());
        this.setContinuedCompany(child.getContinuedCompany());
        this.setContinuedPrice(child.getContinuedPrice());
        this.area = child.getArea();
        this.regions = getConvertRegions(this.area);
        if(flag){
            this.area = null;
        }
    }

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
