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
package cloud.shopfly.b2c.core.base.plugin.upload;

import cloud.shopfly.b2c.core.base.model.dto.FileDTO;
import cloud.shopfly.b2c.core.base.model.vo.ConfigItem;
import cloud.shopfly.b2c.core.base.model.vo.FileVO;

import java.util.List;
import java.util.Map;


/**
 * Storage scheme parameter interface
 *
 * @author zh
 * @version v2.0
 * @since v7.0
 * 2018years3month19On the afternoon4:54:28
 */
public interface Uploader {

    /**
     * Configure the parameters of each storage solution
     *
     * @return The list of parameters
     */
    List<ConfigItem> definitionConfigItem();

    /**
     * Upload a file
     *
     * @param input  Upload object
     * @param scene  The business scenario
     * @param config Configuration information
     * @return
     */
    FileVO upload(FileDTO input, String scene, Map config);

    /**
     * Delete the file
     *
     * @param filePath Address of the file
     * @param config   Configuration information
     */
    void deleteFile(String filePath, Map config);

    /**
     * To get the pluginID
     *
     * @return The plug-inbeanId
     */
    String getPluginId();

    /**
     * Generate thumbnail path
     *
     * @param url    Full path of the original image
     * @param width  Need to generate the width of the image size
     * @param height Need to generate the height of the image size
     * @return Generated thumbnail path
     */
    String getThumbnailUrl(String url, Integer width, Integer height);

    /**
     * Whether the storage scheme is enabled
     *
     * @return 0 Dont open1 open
     */
    Integer getIsOpen();

    /**
     * Get the plug-in name
     *
     * @return The plug-in name
     */
    String getPluginName();

}
