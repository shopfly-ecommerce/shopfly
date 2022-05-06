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
package cloud.shopfly.b2c.core.base.plugin.waybill;

import cloud.shopfly.b2c.core.base.model.vo.ConfigItem;

import java.util.List;
import java.util.Map;

/**
 * 电子面单参数借口
 *
 * @author dongxin
 * @version v1.0
 * @since v6.4.0
 * 2017年8月10日 下午2:29:05
 */
public interface WayBillEvent {


    /**
     * 配置各个电子面单的参数
     *
     * @return 在页面加载的电子面单参数
     */
    List<ConfigItem> definitionConfigItem();

    /**
     * 获取插件ID
     *
     * @return
     */
    String getPluginId();

    /**
     * 创建电子面单
     *
     * @param orderSn 订单编号
     * @param logId   物流公司id
     * @param config  参数配置
     * @return
     * @throws Exception
     */
    String createPrintData(String orderSn, Integer logId, Map config) throws Exception;

    /**
     * 获取插件名称
     *
     * @return 插件名称
     */
    String getPluginName();

    /**
     * 电子面单是否开启
     *
     * @return 0 不开启  1 开启
     */
    Integer getOpen();

}
