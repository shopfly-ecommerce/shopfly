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
 * Electron surface single parameter excuse
 *
 * @author dongxin
 * @version v1.0
 * @since v6.4.0
 * 2017years8month10On the afternoon2:29:05
 */
public interface WayBillEvent {


    /**
     * Configure the parameters of each electronic sheet
     *
     * @return Single parameter of the electronic surface loaded in the page
     */
    List<ConfigItem> definitionConfigItem();

    /**
     * To get the pluginID
     *
     * @return
     */
    String getPluginId();

    /**
     * Create an electron sheet
     *
     * @param orderSn Order no.
     * @param logId   Logistics companyid
     * @param config  Parameter configuration
     * @return
     * @throws Exception
     */
    String createPrintData(String orderSn, Integer logId, Map config) throws Exception;

    /**
     * Get the plug-in name
     *
     * @return The plug-in name
     */
    String getPluginName();

    /**
     * Whether the electronic sheet is open
     *
     * @return 0 Dont open1 open
     */
    Integer getOpen();

}
