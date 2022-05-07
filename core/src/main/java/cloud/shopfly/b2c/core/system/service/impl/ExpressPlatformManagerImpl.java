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

import cloud.shopfly.b2c.core.client.system.LogiCompanyClient;
import cloud.shopfly.b2c.core.system.model.dos.ExpressPlatformDO;
import cloud.shopfly.b2c.core.system.model.dos.LogiCompanyDO;
import cloud.shopfly.b2c.core.system.model.vo.ExpressDetailVO;
import cloud.shopfly.b2c.core.system.model.vo.ExpressPlatformVO;
import cloud.shopfly.b2c.core.system.service.ExpressPlatformManager;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.model.vo.ConfigItem;
import cloud.shopfly.b2c.core.base.plugin.express.ExpressPlatform;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Express platform business class
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-11 14:42:50
 */
@Service
public class ExpressPlatformManagerImpl implements ExpressPlatformManager {

    @Autowired
    
    private DaoSupport systemDaoSupport;

    @Autowired
    private List<ExpressPlatform> expressPlatforms;

    @Autowired
    private LogiCompanyClient logiCompanyClient;

    @Autowired
    private Cache cache;

    @Override
    public Page list(int page, int pageSize) {
        List<ExpressPlatformVO> resultList = this.getPlatform();
        for (ExpressPlatformVO vo : resultList) {
            this.add(vo);
        }
        return new Page(page, (long) resultList.size(), pageSize, resultList);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ExpressPlatformVO add(ExpressPlatformVO expressPlatformVO) {
        ExpressPlatformDO expressPlatformDO = new ExpressPlatformDO(expressPlatformVO);
        if (expressPlatformDO.getId() == null || expressPlatformDO.getId() == 0) {
            ExpressPlatformDO platformDO = this.getExpressPlatform(expressPlatformDO.getBean());
            if (platformDO != null) {
                expressPlatformVO.setId(platformDO.getId());
                return expressPlatformVO;
            }
            this.systemDaoSupport.insert("es_express_platform", expressPlatformDO);
            Integer id = this.systemDaoSupport.getLastId("es_express_platform");
            expressPlatformVO.setId(id);
        }
        return expressPlatformVO;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ExpressPlatformVO edit(ExpressPlatformVO expressPlatformVO) {
        List<ExpressPlatformVO> vos = this.getPlatform();
        for (ExpressPlatformVO vo : vos) {
            this.add(vo);
        }
        ExpressPlatformDO expressPlatformDO = this.getExpressPlatform(expressPlatformVO.getBean());
        if (expressPlatformDO == null) {
            throw new ResourceNotFoundException("The Courier scheme does not exist");
        }
        // Before the verification, the status is disabled
        if (expressPlatformDO.getOpen().equals(0) && expressPlatformVO.getOpen().equals(1)) {
            // Changing the Status of the Switch
            this.open(expressPlatformDO.getBean());
        }
        expressPlatformVO.setId(expressPlatformDO.getId());
        this.systemDaoSupport.update(new ExpressPlatformDO(expressPlatformVO), expressPlatformDO.getId());
        cache.remove(CachePrefix.EXPRESS.getPrefix());
        return expressPlatformVO;
    }

    @Override
    public ExpressPlatformDO getExpressPlatform(String bean) {
        String sql = "select * from es_express_platform where bean = ?";
        return this.systemDaoSupport.queryForObject(sql, ExpressPlatformDO.class, bean);
    }


    /**
     * Get all express query schemes
     *
     * @return All delivery options
     */
    private List<ExpressPlatformVO> getPlatform() {
        List<ExpressPlatformVO> resultList = new ArrayList<>();
        String sql = "select * from es_express_platform";
        List<ExpressPlatformDO> list = this.systemDaoSupport.queryForList(sql, ExpressPlatformDO.class);
        Map<String, ExpressPlatformDO> map = new HashMap<>(16);
        for (ExpressPlatformDO expressPlatformDO : list) {
            map.put(expressPlatformDO.getBean(), expressPlatformDO);
        }
        for (ExpressPlatform plugin : expressPlatforms) {
            ExpressPlatformDO expressPlatformDO = map.get(plugin.getPluginId());
            ExpressPlatformVO result = null;

            if (expressPlatformDO != null) {
                result = new ExpressPlatformVO(expressPlatformDO);
            } else {
                result = new ExpressPlatformVO(plugin);
            }
            resultList.add(result);
        }
        return resultList;
    }


    @Override
    public ExpressPlatformVO getExoressConfig(String bean) {
        List<ExpressPlatformVO> vos = this.getPlatform();
        for (ExpressPlatformVO vo : vos) {
            this.add(vo);
        }
        ExpressPlatformDO expressPlatformDO = this.getExpressPlatform(bean);
        if (expressPlatformDO == null) {
            throw new ResourceNotFoundException("The delivery platform does not exist");
        }
        return new ExpressPlatformVO(expressPlatformDO);
    }

    @Override
    public void open(String bean) {
        List<ExpressPlatformVO> vos = this.getPlatform();
        for (ExpressPlatformVO vo : vos) {
            this.add(vo);
        }
        ExpressPlatformDO expressPlatformDO = this.getExpressPlatform(bean);
        if (expressPlatformDO == null) {
            throw new ResourceNotFoundException("The delivery platform does not exist");
        }
        this.systemDaoSupport.execute("UPDATE es_express_platform SET open=0");
        this.systemDaoSupport.execute("UPDATE es_express_platform SET open=1 WHERE bean = ?", bean);
        // Update the cache
        cache.remove(CachePrefix.EXPRESS.getPrefix());
    }

    @Override
    public ExpressDetailVO getExpressDetail(Integer id, String nu) {
        // Acquisition logistics company
        LogiCompanyDO logiCompanyDO = logiCompanyClient.getModel(id);
        if (logiCompanyDO == null || StringUtil.isEmpty(logiCompanyDO.getCode())) {
            logiCompanyDO.setCode("shunfeng");
        }
        // Retrieves the enabled express platform from the cache
        ExpressPlatformVO expressPlatformVO = (ExpressPlatformVO) cache.get(CachePrefix.EXPRESS.getPrefix());
        // If not, query from the database, and put the open express platform found in the cache
        if (expressPlatformVO == null) {
            ExpressPlatformDO expressPlatformDO = this.systemDaoSupport.queryForObject("select * from es_express_platform where open = 1", ExpressPlatformDO.class);
            if (expressPlatformDO == null) {
                throw new ResourceNotFoundException("No open express platform found");
            }
            expressPlatformVO = new ExpressPlatformVO();
            expressPlatformVO.setConfig(expressPlatformDO.getConfig());
            expressPlatformVO.setBean(expressPlatformDO.getBean());
            cache.put(CachePrefix.EXPRESS.getPrefix(), expressPlatformVO);
        }
        // Get the open express platform solution
        ExpressPlatform expressPlatform = this.findByBeanid(expressPlatformVO.getBean());
        // Call the query interface to return the queried logistics information
        ExpressDetailVO expressDetailVO = expressPlatform.getExpressDetail(logiCompanyDO.getCode(), nu, this.getConfig());
        if(expressDetailVO == null ){
            expressDetailVO = new ExpressDetailVO();
            expressDetailVO.setCourierNum(nu);
            expressDetailVO.setName(logiCompanyDO.getName());
        }
        return expressDetailVO;
    }

    /**
     * Get the open express platform solution
     *
     * @return
     */
    private Map getConfig() {
        ExpressPlatformVO expressPlatformVO = (ExpressPlatformVO) cache.get(CachePrefix.EXPRESS.getPrefix());
        if (StringUtil.isEmpty(expressPlatformVO.getConfig())) {
            return new HashMap<>(16);
        }
        Gson gson = new Gson();
        List<ConfigItem> list = gson.fromJson(expressPlatformVO.getConfig(), new TypeToken<List<ConfigItem>>() {
        }.getType());
        Map<String, String> result = new HashMap<>(16);
        if (list != null) {
            for (ConfigItem item : list) {
                result.put(item.getName(), StringUtil.toString(item.getValue()));
            }
        }
        return result;
    }

    /**
     * According to thebeanQuery available delivery platforms
     *
     * @param beanId
     * @return
     */
    private ExpressPlatform findByBeanid(String beanId) {
        for (ExpressPlatform expressPlatform : expressPlatforms) {
            if (expressPlatform.getPluginId().equals(beanId)) {
                return expressPlatform;
            }
        }
        // If you come here, you cannot find an available delivery platform
        throw new ResourceNotFoundException("No available delivery platform was found");
    }

}
