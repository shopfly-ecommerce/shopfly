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
package cloud.shopfly.b2c.core.member.model;

import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.base.service.SettingManager;
import cloud.shopfly.b2c.core.system.model.vo.SiteSetting;
import cloud.shopfly.b2c.framework.context.ApplicationContextHolder;
import cloud.shopfly.b2c.framework.util.JsonUtil;

/**
 * Random verification code generation
 *
 * @author zh
 * @version v7.0
 * @date 18/4/24 In the afternoon8:06
 * @since v7.0
 */

public class RandomCreate {

    public static String getRandomCode() {
        // Randomly generated dynamic code
        String dynamicCode = "" + (int) ((Math.random() * 9 + 1) * 100000);
        // In test mode, the verification code is 1111
        SettingManager settingManager = (SettingManager) ApplicationContextHolder.getBean("settingManagerImpl");
        String siteSettingJson = settingManager.get(SettingGroup.SITE);

        SiteSetting setting = JsonUtil.jsonToObject(siteSettingJson,SiteSetting.class);
        if (setting == null || setting.getTestMode() == null || setting.getTestMode().equals(1)) {
            dynamicCode = "1111";
        }
        return dynamicCode;
    }

}
