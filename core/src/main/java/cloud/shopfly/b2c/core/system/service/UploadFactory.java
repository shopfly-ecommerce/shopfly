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
 * 存储方案VO
 *
 * @author zh
 * @version v1.0
 * @since v1.0
 * 2018年3月22日 上午9:39:08
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
     * 获取存储方案对象
     *
     * @return 实例化的存储方案对象
     */
    public Uploader getUploader() {
        UploaderVO uploaderVo = (UploaderVO) cache.get(CachePrefix.UPLOADER.getPrefix().toString());
        //如果为空则要到库中读取
        if (uploaderVo == null) {
            //由数据库中查询存储方案
            String sql = "SELECT * FROM es_uploader WHERE open = 1";
            UploaderDO upload = this.systemDaoSupport.queryForObject(sql, UploaderDO.class);
            if (upload == null) {
                throw new ResourceNotFoundException("未找到开启的存储方案");
            }
            uploaderVo = new UploaderVO();
            uploaderVo.setConfig(upload.getConfig());
            uploaderVo.setBean(upload.getBean());
            cache.put(CachePrefix.UPLOADER.getPrefix(), uploaderVo);
        }
        return this.findByBeanid(uploaderVo.getBean());
    }

    /**
     * 根据beanid获取出存储方案
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
        //如果走到这里，说明找不到可用的存储方案
        throw new ResourceNotFoundException("未找到可用的文件存储方案");
    }


}
