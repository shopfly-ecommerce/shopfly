/*
 * Yi family of hui（Beijing）All Rights Reserved.
 * You may not use this file without permission.
 * The official address：www.javamall.com.cn
 */
package cloud.shopfly.b2c.core.distribution.service.impl;

import cloud.shopfly.b2c.core.distribution.model.dos.DistributionGoods;
import cloud.shopfly.b2c.core.distribution.service.DistributionGoodsManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DistributionGoodsManagerImpl
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-06-14 In the morning12:39
 */
@Service
public class DistributionGoodsManagerImpl implements DistributionGoodsManager {


    @Autowired
    
    private DaoSupport daoSupport;

    /**
     * Modify withdrawal Settings of distributed goods
     *
     * @param distributionGoods
     * @return
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public DistributionGoods edit(DistributionGoods distributionGoods) {
        DistributionGoods old = this.daoSupport.queryForObject("select * from es_distribution_goods where goods_id = ?", distributionGoods.getClass(), distributionGoods.getGoodsId());
        if (null == old) {
            daoSupport.insert("es_distribution_goods", distributionGoods);
            return distributionGoods;
        } else {
            Map<String, Object> map = new HashMap<>(16);
            map.put("id", old.getId());
            daoSupport.update("es_distribution_goods", distributionGoods, map);
            return distributionGoods;
        }
    }

    /**
     * delete
     *
     * @param goodsId
     */
    @Override
    public void delete(Integer goodsId) {
        this.daoSupport.execute("delete from es_distribution_goods where goods_id = ?", goodsId);
    }

    /**
     * To obtain
     *
     * @param goodsId
     */
    @Override
    public DistributionGoods getModel(Integer goodsId) {
        return this.daoSupport.queryForObject("select * from es_distribution_goods where goods_id = ?", DistributionGoods.class, goodsId);
    }

    @Override
    public List<DistributionGoods> getGoodsIds(List<Integer> goodsIds) {
        String goodsIdStr = goodsIds.stream().map(goodsId -> goodsId.toString()).collect(Collectors.joining(",", "(", ")"));
        return this.daoSupport.queryForList("select * from es_distribution_goods where goods_id in " + goodsIdStr, DistributionGoods.class);
    }
}
