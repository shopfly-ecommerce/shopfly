/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.service.impl;

import dev.shopflix.core.base.CachePrefix;
import dev.shopflix.core.base.model.vo.ConfigItem;
import dev.shopflix.core.base.plugin.waybill.WayBillEvent;
import dev.shopflix.core.client.trade.OrderClient;
import dev.shopflix.core.system.SystemErrorCode;
import dev.shopflix.core.system.model.dos.WayBillDO;
import dev.shopflix.core.system.model.vo.WayBillVO;
import dev.shopflix.core.system.service.WaybillManager;
import dev.shopflix.core.trade.sdk.model.OrderDetailDTO;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.ResourceNotFoundException;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
 * 电子面单业务类
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-06-08 16:26:05
 */
@Service
public class WaybillManagerImpl implements WaybillManager {

    @Autowired
    
    private DaoSupport systemDaoSupport;

    @Autowired
    private Cache cache;

    @Autowired
    private List<WayBillEvent> wayBillEvents;

    @Autowired
    private OrderClient orderclient;

    @Override
    public Page list(int page, int pageSize) {
        List<WayBillVO> resultList = this.getWayBills();
        for (WayBillVO vo : resultList) {
            this.add(vo);
        }
        return new Page(page, (long) resultList.size(), pageSize, resultList);
    }

    @Override
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public WayBillDO add(WayBillVO wayBill) {
        WayBillDO wayBillDO = new WayBillDO(wayBill);
        if (wayBill.getId() == null || wayBill.getId().equals(0)) {
            //查询此方案是否已经存在数据库中
            WayBillDO wb = this.getWayBillByBean(wayBillDO.getBean());
            if (wb != null) {
                throw new ServiceException(SystemErrorCode.E910.code(), "该电子面单方案已经存在");
            }
            this.systemDaoSupport.insert("es_waybill", wayBillDO);
            Integer waybillId = this.systemDaoSupport.getLastId("es_waybill");
            wayBillDO.setId(waybillId);
        }
        // 更新缓存
        cache.remove(CachePrefix.WAYBILL.getPrefix());
        return wayBillDO;
    }

    @Override
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public WayBillVO edit(WayBillVO wayBill) {
        List<WayBillVO> vos = this.getWayBills();
        for (WayBillVO vo : vos) {
            this.add(vo);
        }
        WayBillDO way = this.getWayBillByBean(wayBill.getBean());
        if (way == null) {
            throw new ResourceNotFoundException("该电子面单方案不存在");
        }
        wayBill.setId(way.getId());
        this.systemDaoSupport.update(new WayBillDO(wayBill), way.getId());
        return wayBill;
    }


    @Override
    public WayBillDO getModel(Integer id) {
        return this.systemDaoSupport.queryForObject(WayBillDO.class, id);
    }

    @Override
    public void open(String bean) {
        List<WayBillVO> vos = this.getWayBills();
        for (WayBillVO vo : vos) {
            this.add(vo);
        }
        WayBillDO wayBillDO = this.getWayBillByBean(bean);
        if (wayBillDO == null) {
            throw new ResourceNotFoundException("该电子面单方案不存在");
        }
        this.systemDaoSupport.execute("UPDATE es_waybill SET open=0");
        this.systemDaoSupport.execute("UPDATE es_waybill SET open=1 WHERE bean = ?", bean);
        // 更新缓存
        cache.remove(CachePrefix.WAYBILL.getPrefix());
    }

    @Override
    public WayBillDO getWayBillByBean(String bean) {
        String sql = "select * from es_waybill where bean = ?";
        return this.systemDaoSupport.queryForObject(sql, WayBillDO.class, bean);
    }


    /**
     * 获取所有的电子面单方案
     *
     * @return 所有的电子面单方案
     */
    private List<WayBillVO> getWayBills() {
        List<WayBillVO> resultList = new ArrayList<>();
        String sql = "select * from es_waybill";
        List<WayBillDO> list = this.systemDaoSupport.queryForList(sql, WayBillDO.class);
        Map<String, WayBillDO> map = new HashMap<>(16);
        for (WayBillDO wayBillDO : list) {
            map.put(wayBillDO.getBean(), wayBillDO);
        }
        for (WayBillEvent plugin : wayBillEvents) {
            WayBillDO wayBill = map.get(plugin.getPluginId());
            WayBillVO result = null;

            if (wayBill != null) {
                result = new WayBillVO(wayBill);
            } else {
                result = new WayBillVO(plugin);
            }

            resultList.add(result);
        }
        return resultList;
    }

    @Override
    public WayBillVO getWaybillConfig(String bean) {
        List<WayBillVO> vos = this.getWayBills();
        for (WayBillVO vo : vos) {
            this.add(vo);
        }
        WayBillDO wayBillDO = this.getWayBillByBean(bean);
        if (wayBillDO == null) {
            throw new ResourceNotFoundException("该电子面单方案不存在");
        }
        return new WayBillVO(wayBillDO);
    }

    @Override
    public String createPrintData(String orderSn, Integer logisticsId) {
        OrderDetailDTO orderDetailDTO = orderclient.getModel(orderSn);
        if (orderDetailDTO == null) {
            throw new ResourceNotFoundException("订单无效");
        }
        Object object = cache.get(CachePrefix.WAYBILL.getPrefix());
        WayBillDO wayBillDO = null;
        if (object != null) {
            wayBillDO = (WayBillDO) object;
        } else {
            String sql = "select * from es_waybill where open = 1";
            wayBillDO = this.systemDaoSupport.queryForObject(sql, WayBillDO.class);
            if (wayBillDO == null) {
                throw new ResourceNotFoundException("找不到可用的电子面单方案");
            }
            cache.put(CachePrefix.WAYBILL.getPrefix(), wayBillDO);
        }
        WayBillEvent plugin = this.findByBean(wayBillDO.getBean());
        try {
            return plugin.createPrintData(orderSn, logisticsId, getConfig());
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new ServiceException(SystemErrorCode.E911.code(), "电子面单生成失败");
    }

    /**
     * 根据beanid获取电子面单方案
     *
     * @param bean 电子面单bean id
     * @return 电子面单插件
     */

    private WayBillEvent findByBean(String bean) {
        for (WayBillEvent wayBillEvent : wayBillEvents) {
            if (wayBillEvent.getPluginId().equals(bean)) {
                return wayBillEvent;
            }
        }
        //如果走到这里，说明找不到可用的电子面单方案
        throw new ResourceNotFoundException("未找到可用的电子面单方案");
    }


    /**
     * 获取存储方案配置
     *
     * @return
     */
    private Map getConfig() {
        WayBillDO wayBillDO = (WayBillDO) cache.get(CachePrefix.WAYBILL.getPrefix());
        if (StringUtil.isEmpty(wayBillDO.getConfig())) {
            return new HashMap<>(16);
        }
        Gson gson = new Gson();
        List<ConfigItem> list = gson.fromJson(wayBillDO.getConfig(), new TypeToken<List<ConfigItem>>() {
        }.getType());
        Map<String, String> result = new HashMap<>(16);
        if (list != null) {
            for (ConfigItem item : list) {
                result.put(item.getName(), item.getValue().toString());
            }
        }
        return result;
    }
}
