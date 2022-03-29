/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.base.service;

import com.enation.app.javashop.core.base.SettingGroup;

/**
 * 系统设置
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018年3月19日 下午4:02:40
 */
public interface SettingManager {
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
