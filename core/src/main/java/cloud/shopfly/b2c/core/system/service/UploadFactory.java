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
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.plugin.upload.Uploader;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Storage solutionVO
 *
 * @author zh
 * @version v1.0
 * @since v1.0
 * 2018years3month22The morning of9:39:08
 */
@Component
public class UploadFactory {


    @Autowired
    private List<Uploader> uploads;

    @Autowired
    private Cache cache;

    @Autowired
    
    private DaoSupport systemDaoSupport;


    /**
     * Gets the storage scheme object
     *
     * @return Instantiated storage scheme object
     */
    public Uploader getUploader() {
        UploaderVO uploaderVo = (UploaderVO) cache.get(CachePrefix.UPLOADER.getPrefix().toString());
        // If it is empty, read from the library
        if (uploaderVo == null) {
            // Query the storage scheme from the database
            String sql = "SELECT * FROM es_uploader WHERE open = 1";
            UploaderDO upload = this.systemDaoSupport.queryForObject(sql, UploaderDO.class);
            if (upload == null) {
                throw new ResourceNotFoundException("The enabled storage scheme is not found");
            }
            uploaderVo = new UploaderVO();
            uploaderVo.setConfig(upload.getConfig());
            uploaderVo.setBean(upload.getBean());
            cache.put(CachePrefix.UPLOADER.getPrefix(), uploaderVo);
        }
        return this.findByBeanid(uploaderVo.getBean());
    }

    /**
     * According to thebeanidObtain the storage scheme
     *
     * @param beanid
     * @return
     */
    private Uploader findByBeanid(String beanid) {
        for (Uploader iUploader : uploads) {
            if (iUploader.getPluginId().equals(beanid)) {
                return iUploader;
            }
        }
        // If you go to this point, it indicates that no available storage scheme is found
        throw new ResourceNotFoundException("No available file storage scheme was found");
    }


}
