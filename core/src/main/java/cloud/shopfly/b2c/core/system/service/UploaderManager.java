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
 * 存储方案业务层
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-22 09:31:56
 */
public interface UploaderManager {

    /**
     * 查询存储方案列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加存储方案
     *
     * @param uploader 存储方案
     * @return Uploader 存储方案
     */
    UploaderDO add(UploaderVO uploader);

    /**
     * 修改存储方案
     *
     * @param uploader 存储方案
     * @return Uploader 存储方案
     */
    UploaderVO edit(UploaderVO uploader);

    /**
     * 获取存储方案
     *
     * @param id 存储方案主键
     * @return Uploader  存储方案
     */
    UploaderDO getUploader(Integer id);

    /**
     * 获取存储方案
     *
     * @param bean 存储方案beanid
     * @return Uploader  存储方案
     */
    UploaderDO getUploader(String bean);

    /**
     * 开启某个存储方案
     *
     * @param bean 存储方案bean
     * @return Uploader  存储方案
     */
    void openUploader(String bean);

    /**
     * 根据存储方案的beanid 获取存储方案的配置项
     *
     * @param bean 存储方案beanid
     * @return 存储方案
     */
    UploaderVO getUploadConfig(String bean);

}