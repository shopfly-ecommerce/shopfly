/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.system.service.impl;

import com.enation.app.javashop.core.base.CachePrefix;
import com.enation.app.javashop.core.base.plugin.sms.SmsPlatform;
import com.enation.app.javashop.core.system.SystemErrorCode;
import com.enation.app.javashop.core.system.model.dos.SmsPlatformDO;
import com.enation.app.javashop.core.system.model.vo.SmsPlatformVO;
import com.enation.app.javashop.core.system.service.SmsPlatformManager;
import com.enation.app.javashop.framework.cache.Cache;
import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.database.Page;
import com.enation.app.javashop.framework.exception.ResourceNotFoundException;
import com.enation.app.javashop.framework.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信网关表业务类
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-23 11:31:05
 */
@Service
public class SmsPlatformManagerImpl implements SmsPlatformManager {

    @Autowired
    @Qualifier("systemDaoSupport")
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
     * 添加短信网关
     *
     * @param smsPlatform 短信网关参数
     * @return 短信网关对象
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SmsPlatformVO add(SmsPlatformVO smsPlatform) {
        SmsPlatformDO smsPlatformDO = new SmsPlatformDO(smsPlatform);
        if (smsPlatformDO.getId() == null || smsPlatformDO.getId() == 0) {
            SmsPlatformDO platformDO = this.getSmsPlateform(smsPlatformDO.getBean());
            if (platformDO != null) {
                throw new ServiceException(SystemErrorCode.E900.code(), "该存储方案已经存在");
            }
            this.systemDaoSupport.insert("es_sms_platform", smsPlatformDO);
        }
        // 更新缓存
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
            throw new ResourceNotFoundException("该短信方案不存在");
        }
        this.systemDaoSupport.execute("update es_sms_platform set open=0");
        this.systemDaoSupport.execute("update es_sms_platform set open=1 where bean=?", bean);
        // 更新缓存
        cache.remove(CachePrefix.SPlATFORM.getPrefix());

    }


    /**
     * 获取所有的短信方案
     *
     * @return 所有的短信方案
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
            throw new ResourceNotFoundException("该短信网关方案不存在");
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
                throw new ResourceNotFoundException("未找到可用的短信网关");
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
            throw new ResourceNotFoundException("该短信方案不存在");
        }
        smsPlatform.setId(up.getId());
        this.systemDaoSupport.update(new SmsPlatformDO(smsPlatform), up.getId());
        return smsPlatform;
    }
}
