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
package cloud.shopfly.b2c.consumer.shop.message;

import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.system.model.vo.InformationSetting;
import cloud.shopfly.b2c.core.system.model.vo.SiteSetting;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消息设置获取公有方法
 *
 * @author zh
 * @version v7.0
 * @date 19/7/31 下午2:13
 * @since v7.0
 */

@Component
public class AbstractMessage {

    @Autowired
    private SettingClient settingClient;

    /**
     * 获取站点设置
     *
     * @return
     */
    protected SiteSetting getSiteSetting() {
        String siteSettingJson = settingClient.get(SettingGroup.SITE);
        //获取系统配置
        return JsonUtil.jsonToObject(siteSettingJson, SiteSetting.class);
    }

    /**
     * 获取平台联系方式
     *
     * @return
     */
    protected InformationSetting getInfoSetting() {
        //系统联系方式
        String infoSettingJson = settingClient.get(SettingGroup.INFO);
        return JsonUtil.jsonToObject(infoSettingJson, InformationSetting.class);
    }

    /**
     * 替换中的内容
     *
     * @param content
     * @param map     替换的文本内容
     * @return
     */
    protected String replaceContent(String content, Map map) {
        StrSubstitutor strSubstitutor = new StrSubstitutor(map);
        return strSubstitutor.replace(content);
    }

}
