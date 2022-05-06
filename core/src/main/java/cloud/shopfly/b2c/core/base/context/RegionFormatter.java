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
package cloud.shopfly.b2c.core.base.context;

import cloud.shopfly.b2c.core.client.system.RegionsClient;
import cloud.shopfly.b2c.core.system.model.dos.Regions;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by kingapex on 2018/5/2.
 * <p>
 * 地区格式化器
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/5/2
 */
public class RegionFormatter implements Formatter<Region> {

    private RegionsClient regionsClient;

    public RegionFormatter(RegionsClient regionsClient) {
        this.regionsClient = regionsClient;
    }

    public RegionFormatter() {

    }

    /**
     * 地区转换器
     *
     * @param regionId 地区id(只允许第三级或者第四级地区id)
     * @param locale   国际化US
     * @return 地区对象
     * @throws ParseException
     */
    @Override
    public Region parse(String regionId, Locale locale) throws ParseException {
        Regions regions = regionsClient.getModel(Integer.valueOf(regionId));
        if (regions == null || regions.getRegionGrade() < 3) {
            throw new IllegalArgumentException("地区不合法，请联系管理员");
        }
        //根据底层地区id反推出上级id
        String regionPath = regions.getRegionPath();
        regionPath = regionPath.substring(1, regionPath.length());
        String[] regionPathArray = regionPath.split(",");
        //给地区赋值
        List rList = new ArrayList();
        for (String path : regionPathArray) {
            Regions region = regionsClient.getModel(Integer.valueOf(path));
            if (regions == null) {
                throw new IllegalArgumentException("地区不合法，请联系管理员");
            }
            rList.add(region);
        }
        return this.createRegion(rList);
    }

    /**
     * 组织地区数据
     *
     * @param list 地区集合
     * @return 地区
     */
    private Region createRegion(List<Regions> list) {
        //将地区数据组织好存入Region对象
        Region region = new Region();
        region.setProvinceId(list.get(0).getId());
        region.setProvince(list.get(0).getLocalName());
        region.setCityId(list.get(1).getId());
        region.setCity(list.get(1).getLocalName());
        region.setCountyId(list.get(2).getId());
        region.setCounty(list.get(2).getLocalName());
        //如果地区数据为四级，为第四级地区赋值
        if (list.size() == 4) {
            region.setTown(list.get(3).getLocalName());
            region.setTownId(list.get(3).getId());
        } else {
            region.setTown("");
            region.setTownId(0);
        }
        return region;
    }

    /**
     * 将格式化的地区toString输出
     *
     * @param object 地区对象
     * @param locale 国际化US
     * @return
     */
    @Override
    public String print(Region object, Locale locale) {
        return object.toString();
    }


}
