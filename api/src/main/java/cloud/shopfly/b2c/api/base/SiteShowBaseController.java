/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.base;

import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.base.service.SettingManager;
import cloud.shopfly.b2c.core.system.model.vo.SiteSetting;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 站点信息首页展示控制器
 *
 * @author zh
 * @version v7.0
 * @date 18/7/13 上午11:21
 * @since v7.0
 */
@RestController
@RequestMapping("/site-show")
@Api(description = "站点展示")
public class SiteShowBaseController {

    @Autowired
    private SettingManager settingManager;

    @GetMapping
    @ApiOperation(value = "获取站点设置")
    public SiteSetting getSiteSetting() {

        String siteJson = settingManager.get(SettingGroup.SITE);

        return JsonUtil.jsonToObject(siteJson,SiteSetting.class);
    }
}
