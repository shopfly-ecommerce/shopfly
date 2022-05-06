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
package cloud.shopfly.b2c.core.client.system;

import cloud.shopfly.b2c.core.base.SettingGroup;

/**
 * @author fk
 * @version v1.0
 * @Description: 系统设置
 * @date 2018/7/30 10:48
 * @since v7.0.0
 */
public interface SettingClient {

    /**
     * 系统参数配置
     *
     * @param group    系统设置的分组
     * @param settings 要保存的设置对象
     */
    void save(SettingGroup group, Object settings);

    /**
     * 获取配置
     *
     * @param group 分组名称
     * @return 存储对象
     */
    String get(SettingGroup group);

}
