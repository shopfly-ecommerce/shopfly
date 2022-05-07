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
 * Locale formatter
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
     * Area converter
     *
     * @param regionId regionid(只允许第三级或者第四级regionid)
     * @param locale   internationalizationUS
     * @return Region object
     * @throws ParseException
     */
    @Override
    public Region parse(String regionId, Locale locale) throws ParseException {
        Regions regions = regionsClient.getModel(Integer.valueOf(regionId));
        if (regions == null || regions.getRegionGrade() < 3) {
            throw new IllegalArgumentException("The area is invalid. Please contact your administrator");
        }
        // Invert the upper-level ID based on the lower-level region ID
        String regionPath = regions.getRegionPath();
        regionPath = regionPath.substring(1, regionPath.length());
        String[] regionPathArray = regionPath.split(",");
        // Assign a value to the locale
        List rList = new ArrayList();
        for (String path : regionPathArray) {
            Regions region = regionsClient.getModel(Integer.valueOf(path));
            if (regions == null) {
                throw new IllegalArgumentException("The area is invalid. Please contact your administrator");
            }
            rList.add(region);
        }
        return this.createRegion(rList);
    }

    /**
     * Organization area data
     *
     * @param list In the collection
     * @return region
     */
    private Region createRegion(List<Regions> list) {
        // Organize the Region data and store it in a Region object
        Region region = new Region();
        region.setProvinceId(list.get(0).getId());
        region.setProvince(list.get(0).getLocalName());
        region.setCityId(list.get(1).getId());
        region.setCity(list.get(1).getLocalName());
        region.setCountyId(list.get(2).getId());
        region.setCounty(list.get(2).getLocalName());
        // If the region data is level 4, assign a value to the level 4 region
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
     * Will format the regiontoStringThe output
     *
     * @param object Region object
     * @param locale internationalizationUS
     * @return
     */
    @Override
    public String print(Region object, Locale locale) {
        return object.toString();
    }


}
