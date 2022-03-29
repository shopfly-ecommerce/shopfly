/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model;

import dev.shopflix.core.base.SettingGroup;
import dev.shopflix.core.base.service.SettingManager;
import dev.shopflix.core.system.model.vo.SiteSetting;
import dev.shopflix.framework.context.ApplicationContextHolder;
import dev.shopflix.framework.util.JsonUtil;

/**
 * 随机验证码生成
 *
 * @author zh
 * @version v7.0
 * @date 18/4/24 下午8:06
 * @since v7.0
 */

public class RandomCreate {

    public static String getRandomCode() {
        // 随机生成的动态码
        String dynamicCode = "" + (int) ((Math.random() * 9 + 1) * 100000);
        //如果是测试模式，验证码为1111
        SettingManager settingManager = (SettingManager) ApplicationContextHolder.getBean("settingManagerImpl");
        String siteSettingJson = settingManager.get(SettingGroup.SITE);

        SiteSetting setting = JsonUtil.jsonToObject(siteSettingJson,SiteSetting.class);
        if (setting == null || setting.getTestMode() == null || setting.getTestMode().equals(1)) {
            dynamicCode = "1111";
        }
        return dynamicCode;
    }

}
