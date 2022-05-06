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
package cloud.shopfly.b2c.api.seller.distribution;

import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.base.service.SettingManager;
import cloud.shopfly.b2c.core.distribution.model.dos.DistributionSetting;
import cloud.shopfly.b2c.core.system.model.vo.PointSetting;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
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
 * 分销设置
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018/6/12 上午4:26
 * @Description:
 *
 */
@RestController
@RequestMapping("/seller/distribution")
@Api(description = "分销设置")
@Validated
public class DistributionSettingSellerController {
    @Autowired
    private SettingManager settingManager;

    @GetMapping(value = "/settings")
    @ApiOperation(value = "获取分销设置", response = DistributionSetting.class)
    public DistributionSetting getDistributionSetting() {
        String json = settingManager.get(SettingGroup.DISTRIBUTION);


        if (StringUtil.isEmpty(json)) {
            return new DistributionSetting();
        }
        DistributionSetting distributionSetting = JsonUtil.jsonToObject(json, DistributionSetting.class);

        return distributionSetting;
    }

    @PutMapping(value = "/settings")
    @ApiOperation(value = "修改分销设置", response = PointSetting.class)
    public DistributionSetting editDistributionSetting(@Valid DistributionSetting distributionSetting) {
        settingManager.save(SettingGroup.DISTRIBUTION, distributionSetting);
        return distributionSetting;
    }

}
