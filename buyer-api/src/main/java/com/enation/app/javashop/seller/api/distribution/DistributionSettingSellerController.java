/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.seller.api.distribution;

import com.enation.app.javashop.core.base.SettingGroup;
import com.enation.app.javashop.core.base.service.SettingManager;
import com.enation.app.javashop.core.distribution.model.dos.DistributionSetting;
import com.enation.app.javashop.core.system.model.vo.PointSetting;
import com.enation.app.javashop.framework.util.JsonUtil;
import com.enation.app.javashop.framework.util.StringUtil;
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
