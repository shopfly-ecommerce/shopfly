/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.system.impl;

import dev.shopflix.core.base.SettingGroup;
import dev.shopflix.core.base.service.SettingManager;
import dev.shopflix.core.client.system.SettingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author fk
 * @version v1.0
 * @Description: 系统设置
 * @date 2018/7/30 10:49
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value = "shopflix.product", havingValue = "stand")
public class SettingClientDefaultImpl implements SettingClient {

    @Autowired
    private SettingManager settingManager;

    @Override
    public void save(SettingGroup group, Object settings) {

        this.settingManager.save(group, settings);

    }

    @Override
    public String get(SettingGroup group) {
        return this.settingManager.get(group);
    }
}