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
package cloud.shopfly.b2c.core.system.service;

import cloud.shopfly.b2c.core.system.model.dos.UploaderDO;
import cloud.shopfly.b2c.core.system.model.vo.UploaderVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Storage solution service layer
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-22 09:31:56
 */
public interface UploaderManager {

    /**
     * Example Query the storage scheme list
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * Adding a Storage Solution
     *
     * @param uploader Storage solution
     * @return Uploader Storage solution
     */
    UploaderDO add(UploaderVO uploader);

    /**
     * Modifying a Storage Scheme
     *
     * @param uploader Storage solution
     * @return Uploader Storage solution
     */
    UploaderVO edit(UploaderVO uploader);

    /**
     * Obtaining a Storage Solution
     *
     * @param id Primary key of the storage scheme
     * @return Uploader  Storage solution
     */
    UploaderDO getUploader(Integer id);

    /**
     * Obtaining a Storage Solution
     *
     * @param bean Storage solutionbeanid
     * @return Uploader  Storage solution
     */
    UploaderDO getUploader(String bean);

    /**
     * Enable a storage scheme
     *
     * @param bean Storage solutionbean
     * @return Uploader  Storage solution
     */
    void openUploader(String bean);

    /**
     * According to the storage schemebeanid Obtain the configuration items of the storage solution
     *
     * @param bean Storage solutionbeanid
     * @return Storage solution
     */
    UploaderVO getUploadConfig(String bean);

}
