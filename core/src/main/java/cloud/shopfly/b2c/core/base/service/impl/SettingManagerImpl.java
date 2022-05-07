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
package cloud.shopfly.b2c.core.base.service.impl;

import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.base.model.dos.SettingsDO;
import cloud.shopfly.b2c.core.base.service.SettingManager;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * System setting interface implementation
 *
 * @author zh
 * @version v7.0
 * @since v7.0
 * 2018years3month27On the afternoon4:50:15
 */
@Service
public class SettingManagerImpl implements SettingManager {


    @Autowired
    
    private DaoSupport systemDaoSupport;

    @Autowired
    private Cache cache;

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void save(SettingGroup group, Object settings) {
        String sql = "select cfg_value from es_settings where cfg_group = ?";
        SettingsDO settingsDO = this.systemDaoSupport.queryForObject(sql, SettingsDO.class, group.name());
        // The object to be saved is converted to JSON
        String setting = JsonUtil.objectToJson(settings);
        if (settingsDO == null) {
            this.systemDaoSupport.execute("insert into es_settings set cfg_value = ?,cfg_group = ?", setting, group.name());
        } else {
            this.systemDaoSupport.execute("update es_settings set cfg_value = ? where cfg_group = ?", setting, group.name());
        }
        // Clear the cache
        cache.remove(CachePrefix.SETTING.getPrefix() + group.name());
    }


    @Override
    public String get(SettingGroup group) {
        // Get the parameter configuration from the cache
        String setting = StringUtil.toString(cache.get(CachePrefix.SETTING.getPrefix() + group.name()), false);
        // If not retrieved from the database
        if (StringUtil.isEmpty(setting)) {
            String sql = "select * from es_settings where cfg_group = ?";
            SettingsDO settingsDO = this.systemDaoSupport.queryForObject(sql, SettingsDO.class, group.name());
            if (settingsDO == null) {
                return null;
            }
            setting = settingsDO.getCfgValue();
            this.cache.put(CachePrefix.SETTING.getPrefix() + group.name(), setting);
        }
        return setting;
    }
}
