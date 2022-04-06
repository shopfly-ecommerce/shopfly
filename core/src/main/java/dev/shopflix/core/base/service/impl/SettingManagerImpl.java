/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.base.service.impl;

import dev.shopflix.core.base.CachePrefix;
import dev.shopflix.core.base.SettingGroup;
import dev.shopflix.core.base.model.dos.SettingsDO;
import dev.shopflix.core.base.service.SettingManager;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.util.JsonUtil;
import dev.shopflix.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 系统设置接口实现
 *
 * @author zh
 * @version v7.0
 * @since v7.0
 * 2018年3月27日 下午4:50:15
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
        //将要保存的对象 转换成json
        String setting = JsonUtil.objectToJson(settings);
        if (settingsDO == null) {
            this.systemDaoSupport.execute("insert into es_settings set cfg_value = ?,cfg_group = ?", setting, group.name());
        } else {
            this.systemDaoSupport.execute("update es_settings set cfg_value = ? where cfg_group = ?", setting, group.name());
        }
        //清除缓存
        cache.remove(CachePrefix.SETTING.getPrefix() + group.name());
    }


    @Override
    public String get(SettingGroup group) {
        //从缓存中获取参数配置
        String setting = StringUtil.toString(cache.get(CachePrefix.SETTING.getPrefix() + group.name()), false);
        //如果没有获取到从数据库获取
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
