/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 积分设置api
 *
 * @author zh
 * @version v7.0
 * @date 18/5/18 下午6:55
 * @since v7.0
 */
@RestController
@RequestMapping("/seller/settings")
@Api(description = "积分设置")
@Validated
public class PointSettingSellerController {
    @Autowired
    private SettingClient settingClient;


    @GetMapping(value = "/point")
    @ApiOperation(value = "获取积分设置", response = PointSetting.class)
    public PointSetting getPointSetting() {
        String pointSettingJson = settingClient.get( SettingGroup.POINT);
        PointSetting pointSetting = JsonUtil.jsonToObject(pointSettingJson,PointSetting.class);
        if (pointSetting == null) {
            return new PointSetting();
        }
        return pointSetting;
    }

    @PutMapping(value = "/point")
    @ApiOperation(value = "修改积分设置", response = PointSetting.class)
    public PointSetting editPointSetting(@Valid PointSetting pointSetting) {
        settingClient.save(SettingGroup.POINT, pointSetting);
        return pointSetting;
    }

}
