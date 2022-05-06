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
package cloud.shopfly.b2c.core.base.plugin.express;

import cloud.shopfly.b2c.core.base.model.vo.ConfigItem;
import cloud.shopfly.b2c.core.system.model.vo.ExpressDetailVO;

import java.util.List;
import java.util.Map;

/**
 * 快递平台设置
 *
 * @author zh
 * @version v1.0
 * @since v1.0
 * 2018年3月23日 下午3:07:05
 */
public interface ExpressPlatform {
    /**
     * 配置各个存储方案的参数
     *
     * @return 参数列表
     */
    List<ConfigItem> definitionConfigItem();

    /**
     * 获取插件ID
     *
     * @return 插件beanId
     */
    String getPluginId();

    /**
     * 获取插件名称
     *
     * @return 插件名称
     */
    String getPluginName();

    /**
     * 快递平台是否开启
     *
     * @return 0 不开启  1 开启
     */
    Integer getIsOpen();

    /**
     * 查询物流信息
     *
     * @param abbreviation 快递公司简称
     * @param num          快递单号
     * @param config       参数
     * @return 物流详细
     */
    ExpressDetailVO getExpressDetail(String abbreviation, String num, Map config);
}
