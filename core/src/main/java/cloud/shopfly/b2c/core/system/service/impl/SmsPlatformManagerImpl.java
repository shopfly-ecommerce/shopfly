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
import cloud.shopfly.b2c.core.system.model.dos.SmsPlatformDO;
import cloud.shopfly.b2c.core.system.model.vo.SmsPlatformVO;
import cloud.shopfly.b2c.core.system.service.SmsPlatformManager;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.plugin.sms.SmsPlatform;
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
 * SMS gateway table service class
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-23 11:31:05
 */
@Service
public class SmsPlatformManagerImpl implements SmsPlatformManager {

    @Autowired
    
    private DaoSupport systemDaoSupport;

    @Autowired
    private List<SmsPlatform> smsPlatforms;

    @Autowired
    private Cache cache;

    @Override
    public Page list(int pageNo, int pageSize) {
        List<SmsPlatformVO> resultList = this.getPlatform();
        for (SmsPlatformVO vo : resultList) {
            this.add(vo);
        }
        return new Page(pageNo, (long) resultList.size(), pageSize, resultList);
    }

    /**
     * Adding an SMS Gateway
     *
     * @param smsPlatform SMS gateway parameters
     * @return SMS Gateway Object
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SmsPlatformVO add(SmsPlatformVO smsPlatform) {
        SmsPlatformDO smsPlatformDO = new SmsPlatformDO(smsPlatform);
        if (smsPlatformDO.getId() == null || smsPlatformDO.getId() == 0) {
            SmsPlatformDO platformDO = this.getSmsPlateform(smsPlatformDO.getBean());
            if (platformDO != null) {
                throw new ServiceException(SystemErrorCode.E900.code(), "The storage scheme already exists");
            }
            this.systemDaoSupport.insert("es_sms_platform", smsPlatformDO);
        }
        // Update the cache
        cache.remove(CachePrefix.SPlATFORM.getPrefix());
        return smsPlatform;
    }

    @Override
    public SmsPlatformDO getModel(Integer id) {
        return this.systemDaoSupport.queryForObject(SmsPlatformDO.class, id);
    }

    @Override
    public void openPlatform(String bean) {
        List<SmsPlatformVO> vos = this.getPlatform();
        for (SmsPlatformVO vo : vos) {
            this.add(vo);
        }
        SmsPlatformDO smsPlatformDO = this.getSmsPlateform(bean);
        if (smsPlatformDO == null) {
            throw new ResourceNotFoundException("The SMS scheme does not exist");
        }
        this.systemDaoSupport.execute("update es_sms_platform set open=0");
        this.systemDaoSupport.execute("update es_sms_platform set open=1 where bean=?", bean);
        // Update the cache
        cache.remove(CachePrefix.SPlATFORM.getPrefix());

    }


    /**
     * Get all SMS schemes
     *
     * @return All SMS schemes
     */
    private List<SmsPlatformVO> getPlatform() {
        List<SmsPlatformVO> resultList = new ArrayList<>();

        String sql = "select * from es_sms_platform";

        List<SmsPlatformDO> list = this.systemDaoSupport.queryForList(sql, SmsPlatformDO.class);

        Map<String, SmsPlatformDO> map = new HashMap<>(16);

        for (SmsPlatformDO smsPlatformDO : list) {
            map.put(smsPlatformDO.getBean(), smsPlatformDO);
        }
        for (SmsPlatform plugin : smsPlatforms) {
            SmsPlatformDO smsPlatformDO = map.get(plugin.getPluginId());
            SmsPlatformVO result = null;

            if (smsPlatformDO != null) {
                result = new SmsPlatformVO(smsPlatformDO);
            } else {
                result = new SmsPlatformVO(plugin);
            }

            resultList.add(result);
        }
        return resultList;
    }

    @Override
    public SmsPlatformVO getConfig(String bean) {
        List<SmsPlatformVO> vos = this.getPlatform();
        for (SmsPlatformVO vo : vos) {
            this.add(vo);
        }
        SmsPlatformDO smsPlatformDO = this.getSmsPlateform(bean);
        if (smsPlatformDO == null) {
            throw new ResourceNotFoundException("The SMS gateway scheme does not exist");
        }
        return new SmsPlatformVO(smsPlatformDO);
    }

    @Override
    public SmsPlatformVO getOpen() {
        SmsPlatformVO smsPlatformVO = (SmsPlatformVO) this.cache.get(CachePrefix.SPlATFORM.getPrefix());
        if (smsPlatformVO == null) {
            String sql = "select * from es_sms_platform where open = 1";
            SmsPlatformDO smsPlatformDO = this.systemDaoSupport.queryForObject(sql, SmsPlatformDO.class);
            if (smsPlatformDO == null) {
                throw new ResourceNotFoundException("No available SMS gateway was found");
            }
            smsPlatformVO = new SmsPlatformVO();
            smsPlatformVO.setConfig(smsPlatformDO.getConfig());
            smsPlatformVO.setBean(smsPlatformDO.getBean());
            cache.put(CachePrefix.SPlATFORM.getPrefix(), smsPlatformVO);
        }
        return smsPlatformVO;
    }


    @Override
    public SmsPlatformDO getSmsPlateform(String bean) {
        String sql = "select * from es_sms_platform where bean = ?";
        return this.systemDaoSupport.queryForObject(sql, SmsPlatformDO.class, bean);
    }

    @Override
    public SmsPlatformVO edit(SmsPlatformVO smsPlatform) {
        List<SmsPlatformVO> vos = this.getPlatform();
        for (SmsPlatformVO vo : vos) {
            this.add(vo);
        }
        SmsPlatformDO up = this.getSmsPlateform(smsPlatform.getBean());
        if (up == null) {
            throw new ResourceNotFoundException("The SMS scheme does not exist");
        }
        smsPlatform.setId(up.getId());
        this.systemDaoSupport.update(new SmsPlatformDO(smsPlatform), up.getId());
        return smsPlatform;
    }
}
