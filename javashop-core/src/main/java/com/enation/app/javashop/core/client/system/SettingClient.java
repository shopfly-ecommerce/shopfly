/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.client.system;

import com.enation.app.javashop.core.base.SettingGroup;

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
