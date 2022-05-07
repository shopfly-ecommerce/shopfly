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
package cloud.shopfly.b2c.core.base.service;

import cloud.shopfly.b2c.core.base.SettingGroup;

/**
 * System Settings
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018years3month19On the afternoon4:02:40
 */
public interface SettingManager {
    /**
     * System Parameter Configuration
     *
     * @param group    Grouping of system Settings
     * @param settings The Settings object to save
     */
    void save(SettingGroup group, Object settings);

    /**
     * Access to the configuration
     *
     * @param group Group name
     * @return Store the object
     */
    String get(SettingGroup group);


}
