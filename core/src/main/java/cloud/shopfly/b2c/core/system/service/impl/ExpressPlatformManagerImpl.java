/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 快递平台业务类
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
            throw new ResourceNotFoundException("该快递方案不存在");
        }
        //校验之前开启状态是未开启，改为开启
        if (expressPlatformDO.getOpen().equals(0) && expressPlatformVO.getOpen().equals(1)) {
            //修改开启状态
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
     * 获取所有的快递查询方案
     *
     * @return 所有的快递方案
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
            throw new ResourceNotFoundException("该快递平台不存在");
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
            throw new ResourceNotFoundException("该快递平台不存在");
        }
        this.systemDaoSupport.execute("UPDATE es_express_platform SET open=0");
        this.systemDaoSupport.execute("UPDATE es_express_platform SET open=1 WHERE bean = ?", bean);
        // 更新缓存
        cache.remove(CachePrefix.EXPRESS.getPrefix());
    }

    @Override
    public ExpressDetailVO getExpressDetail(Integer id, String nu) {
        //获取物流公司
        LogiCompanyDO logiCompanyDO = logiCompanyClient.getModel(id);
        if (logiCompanyDO == null || StringUtil.isEmpty(logiCompanyDO.getCode())) {
            logiCompanyDO.setCode("shunfeng");
        }
        //从缓存中获取开启的快递平台
        ExpressPlatformVO expressPlatformVO = (ExpressPlatformVO) cache.get(CachePrefix.EXPRESS.getPrefix());
        //如果没有找到则从数据库查询，将查询到的开启的快递平台放入缓存
        if (expressPlatformVO == null) {
            ExpressPlatformDO expressPlatformDO = this.systemDaoSupport.queryForObject("select * from es_express_platform where open = 1", ExpressPlatformDO.class);
            if (expressPlatformDO == null) {
                throw new ResourceNotFoundException("未找到开启的快递平台");
            }
            expressPlatformVO = new ExpressPlatformVO();
            expressPlatformVO.setConfig(expressPlatformDO.getConfig());
            expressPlatformVO.setBean(expressPlatformDO.getBean());
            cache.put(CachePrefix.EXPRESS.getPrefix(), expressPlatformVO);
        }
        //得到开启的快递平台方案
        ExpressPlatform expressPlatform = this.findByBeanid(expressPlatformVO.getBean());
        //调用查询接口返回查询到的物流信息
        ExpressDetailVO expressDetailVO = expressPlatform.getExpressDetail(logiCompanyDO.getCode(), nu, this.getConfig());
        if(expressDetailVO == null ){
            expressDetailVO = new ExpressDetailVO();
            expressDetailVO.setCourierNum(nu);
            expressDetailVO.setName(logiCompanyDO.getName());
        }
        return expressDetailVO;
    }

    /**
     * 获取开启的快递平台方案
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
     * 根据bean查询出可用的快递平台
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
        //如果走到这里，说明找不到可用的快递平台
        throw new ResourceNotFoundException("未找到可用的快递平台");
    }

}
