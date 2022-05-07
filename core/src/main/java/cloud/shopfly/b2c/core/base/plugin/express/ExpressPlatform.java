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
 * Express Platform Setting
 *
 * @author zh
 * @version v1.0
 * @since v1.0
 * 2018years3month23On the afternoon3:07:05
 */
public interface ExpressPlatform {
    /**
     * Configure the parameters of each storage solution
     *
     * @return The list of parameters
     */
    List<ConfigItem> definitionConfigItem();

    /**
     * To get the pluginID
     *
     * @return The plug-inbeanId
     */
    String getPluginId();

    /**
     * Get the plug-in name
     *
     * @return The plug-in name
     */
    String getPluginName();

    /**
     * Whether the express platform is enabled
     *
     * @return 0 Dont open1 open
     */
    Integer getIsOpen();

    /**
     * Query logistics Information
     *
     * @param abbreviation Abbreviation of Express Company
     * @param num          Courier number
     * @param config       parameter
     * @return Logistics in detail
     */
    ExpressDetailVO getExpressDetail(String abbreviation, String num, Map config);
}
