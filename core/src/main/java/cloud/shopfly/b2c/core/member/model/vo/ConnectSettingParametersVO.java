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
package cloud.shopfly.b2c.core.member.model.vo;

import java.util.List;

/**
 * @author zjp
 * @version v7.0
 * @Description Trust login parameterVO
 * @ClassName ConnectSettingParametersVO
 * @since v7.0 In the afternoon7:56 2018/6/28
 */
public class ConnectSettingParametersVO {
    private String name;
    private List<ConnectSettingConfigItem> configList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ConnectSettingConfigItem> getConfigList() {
        return configList;
    }

    public void setConfigList(List<ConnectSettingConfigItem> configList) {
        this.configList = configList;
    }

    @Override
    public String toString() {
        return "ConnectSettingParametersVO{" +
                "name='" + name + '\'' +
                ", configList=" + configList +
                '}';
    }
}
