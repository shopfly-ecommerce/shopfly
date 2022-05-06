/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.base;

import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.base.service.SettingManager;
import cloud.shopfly.b2c.core.system.model.vo.InformationSetting;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 联系方式设置api
 *
 * @author zh
 * @version v7.0
 * @date 18/5/18 下午6:55
 * @since v7.0
 */
@RestController
@RequestMapping("/settings")
@Api(description = "联系方式设置")
@Validated
public class InfoSettingBaseController {
    @Autowired
    private SettingManager settingManager;


    @GetMapping(value = "/info")
    @ApiOperation(value = "获取联系方式设置", response = InformationSetting.class)
    public InformationSetting getInfoSetting() {
        String infoSettingJson = settingManager.get(SettingGroup.INFO);

        InformationSetting infoSetting = JsonUtil.jsonToObject(infoSettingJson, InformationSetting.class);
        if (infoSetting == null) {
            return new InformationSetting();
        }
        return infoSetting;
    }
}
