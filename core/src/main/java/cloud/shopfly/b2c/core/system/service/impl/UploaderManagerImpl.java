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
package cloud.shopfly.b2c.core.system.service.impl;

import cloud.shopfly.b2c.core.system.SystemErrorCode;
import cloud.shopfly.b2c.core.system.model.dos.UploaderDO;
import cloud.shopfly.b2c.core.system.model.vo.UploaderVO;
import cloud.shopfly.b2c.core.system.service.UploaderManager;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.plugin.upload.Uploader;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Storage solution business class
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-22 09:31:56
 */
@Service
public class UploaderManagerImpl implements UploaderManager {

    @Autowired
    
    private DaoSupport systemDaoSupport;

    @Autowired
    private List<Uploader> uploaders;

    @Autowired
    private Cache cache;


    @Override
    public Page list(int page, int pageSize) {
        List<UploaderVO> resultList = this.getUploads();
        for (UploaderVO vo : resultList) {
            this.add(vo);
        }
        return new Page(page, (long) resultList.size(), pageSize, resultList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UploaderDO add(UploaderVO uploader) {
        UploaderDO upload = new UploaderDO(uploader);
        Integer id = upload.getId();
        if (upload.getId() == null || id == 0) {
            UploaderDO up = this.getUploader(uploader.getBean());
            if (up != null) {
                throw new ServiceException(SystemErrorCode.E900.code(), "The storage scheme already exists");
            }
            this.systemDaoSupport.insert("es_uploader", upload);
            Integer waybillId = this.systemDaoSupport.getLastId("es_uploader");
            upload.setId(waybillId);
        }
        // Update the cache
        cache.remove(CachePrefix.UPLOADER.getPrefix());
        return upload;

    }


    @Override
    public UploaderDO getUploader(Integer id) {
        return this.systemDaoSupport.queryForObject(UploaderDO.class, id);
    }

    @Override
    public UploaderDO getUploader(String bean) {
        String sql = "select * from es_uploader where bean = ?";
        return this.systemDaoSupport.queryForObject(sql, UploaderDO.class, bean);
    }

    @Override
    public void openUploader(String bean) {
        List<UploaderVO> vos = this.getUploads();
        for (UploaderVO vo : vos) {
            this.add(vo);
        }
        UploaderDO upload = this.getUploader(bean);
        if (upload == null) {
            throw new ResourceNotFoundException("The storage scheme does not exist");
        }
        this.systemDaoSupport.execute("UPDATE es_uploader SET open=0");
        this.systemDaoSupport.execute("UPDATE es_uploader SET open=1 WHERE bean = ?", bean);
        // Update the cache
        cache.remove(CachePrefix.UPLOADER.getPrefix());
    }


    /**
     * Get all storage schemes
     *
     * @return All storage schemes
     */
    private List<UploaderVO> getUploads() {
        List<UploaderVO> resultList = new ArrayList<>();

        String sql = "select * from es_uploader";

        List<UploaderDO> list = this.systemDaoSupport.queryForList(sql, UploaderDO.class);

        Map<String, UploaderDO> map = new HashMap<>(16);

        for (UploaderDO upload : list) {
            map.put(upload.getBean(), upload);
        }

        for (Uploader plugin : uploaders) {
            UploaderDO upload = map.get(plugin.getPluginId());
            UploaderVO result = null;

            if (upload != null) {
                result = new UploaderVO(upload);
            } else {
                result = new UploaderVO(plugin);
            }

            resultList.add(result);
        }
        return resultList;
    }

    @Override
    public UploaderVO getUploadConfig(String bean) {
        List<UploaderVO> vos = this.getUploads();
        for (UploaderVO vo : vos) {
            this.add(vo);
        }
        UploaderDO upload = this.getUploader(bean);
        if (upload == null) {
            throw new ResourceNotFoundException("The storage scheme does not exist");
        }
        return new UploaderVO(upload);
    }


    @Override
    public UploaderVO edit(UploaderVO uploader) {
        List<UploaderVO> vos = this.getUploads();
        for (UploaderVO vo : vos) {
            this.add(vo);
        }
        UploaderDO up = this.getUploader(uploader.getBean());
        if (up == null) {
            throw new ResourceNotFoundException("The storage scheme does not exist");
        }
        uploader.setId(up.getId());
        uploader.setOpen(up.getOpen());
        this.systemDaoSupport.update(new UploaderDO(uploader), up.getId());
        return uploader;
    }
}
