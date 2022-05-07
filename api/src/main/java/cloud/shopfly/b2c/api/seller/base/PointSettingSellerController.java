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
package cloud.shopfly.b2c.api.seller.base;

import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.system.model.vo.PointSetting;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Integralapi
 *
 * @author zh
 * @version v7.0
 * @date 18/5/18 In the afternoon6:55
 * @since v7.0
 */
@RestController
@RequestMapping("/seller/settings")
@Api(description = "Integral")
@Validated
public class PointSettingSellerController {
    @Autowired
    private SettingClient settingClient;


    @GetMapping(value = "/point")
    @ApiOperation(value = "Get the integral setting", response = PointSetting.class)
    public PointSetting getPointSetting() {
        String pointSettingJson = settingClient.get( SettingGroup.POINT);
        PointSetting pointSetting = JsonUtil.jsonToObject(pointSettingJson,PointSetting.class);
        if (pointSetting == null) {
            return new PointSetting();
        }
        return pointSetting;
    }

    @PutMapping(value = "/point")
    @ApiOperation(value = "Modify the integral Settings", response = PointSetting.class)
    public PointSetting editPointSetting(@Valid PointSetting pointSetting) {
        settingClient.save(SettingGroup.POINT, pointSetting);
        return pointSetting;
    }

}
