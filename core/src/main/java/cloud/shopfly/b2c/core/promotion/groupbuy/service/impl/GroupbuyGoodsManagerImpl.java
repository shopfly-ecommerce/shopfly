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
package cloud.shopfly.b2c.core.promotion.groupbuy.service.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.promotion.PromotionErrorCode;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyActiveDO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyGoodsDO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyQuantityLog;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.enums.GroupBuyGoodsStatusEnum;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.enums.GroupbuyQuantityLogEnum;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.vo.GroupbuyGoodsVO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.vo.GroupbuyQueryParam;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyActiveManager;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyQuantityLogManager;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionDetailDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionPriceDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.service.PromotionGoodsManager;
import cloud.shopfly.b2c.core.promotion.tool.service.impl.AbstractPromotionRuleManagerImpl;
import cloud.shopfly.b2c.core.promotion.tool.support.PromotionCacheKeys;
import cloud.shopfly.b2c.core.base.rabbitmq.TimeExecute;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyGoodsManager;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.NoPermissionException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.trigger.Interface.TimeTrigger;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.locks.Lock;

/**
 * Group purchase commodity business class
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 16:57:26
 */
@Service
public class GroupbuyGoodsManagerImpl extends AbstractPromotionRuleManagerImpl implements GroupbuyGoodsManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private GroupbuyActiveManager groupbuyActiveManager;


    @Autowired
    private GroupbuyQuantityLogManager groupbuyQuantityLogManager;

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private GoodsClient goodsClient;


    @Autowired
    private PromotionGoodsManager promotionGoodsManager;

    @Autowired
    private TimeTrigger timeTrigger;

    @Autowired
    private Cache cache;

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());


    @Override
    public Page listPage(GroupbuyQueryParam param) {

        List whereParam = new ArrayList();
        StringBuffer sql = new StringBuffer("select gg.*,ga.act_name as title,ga.start_time,ga.end_time from es_groupbuy_goods as gg " +
                "left join es_groupbuy_active as ga on gg.act_id=ga.act_id ");

        List<String> sqlList = new ArrayList();

        sqlList.add(" gg.act_id=? ");
        whereParam.add(param.getActId());


        if (!StringUtil.isEmpty(param.getKeywords())) {
            sqlList.add(" (gg.goods_name like ? or gg.gb_name like ? or gg.gb_title like ?) ");
            whereParam.add("%" + param.getKeywords() + "%");
            whereParam.add("%" + param.getKeywords() + "%");
            whereParam.add("%" + param.getKeywords() + "%");
        }

        if (param.getKeywords() == null && !StringUtil.isEmpty(param.getGoodsName())) {
            sqlList.add(" gg.goods_name like ? ");
            whereParam.add(param.getGoodsName());
        }

        if (param.getStartTime() != null && param.getEndTime() != null) {
            sqlList.add("  gg.add_time >? and gg.add_time < ?  ");
            whereParam.add(param.getStartTime());
            whereParam.add(param.getEndTime());
        }

        sql.append(SqlUtil.sqlSplicing(sqlList));

        sql.append(" order by ga.start_time desc ");

        Page webPage = this.daoSupport.queryForPage(sql.toString(), param.getPage(), param.getPageSize(), GroupbuyGoodsVO.class, whereParam.toArray());

        List<GroupbuyGoodsVO> groupbuyGoodsVOList = webPage.getData();
        webPage.setData(groupbuyGoodsVOList);
        return webPage;
    }

    @Override
    public Page listPageByBuyer(GroupbuyQueryParam param) {

        List whereParam = new ArrayList();
        StringBuffer sql = new StringBuffer("select gg.*,pg.start_time,pg.end_time,pg.title from es_promotion_goods pg " +
                " left join es_groupbuy_goods gg on pg.goods_id =gg.goods_id and pg.activity_id = gg.act_id " +
                " where pg.promotion_type = ? ");
        whereParam.add(PromotionTypeEnum.GROUPBUY.name());

        if (param.getStartTime() != null && param.getEndTime() != null) {
            sql.append(" and ? > pg.start_time and ? < pg.end_time ");
            whereParam.add(param.getStartTime());
            whereParam.add(param.getEndTime());
        }
        if (param.getCatId() != null ) {
            sql.append(" and cat_id = ?");
            whereParam.add(param.getCatId());
        }
        Page webPage = this.daoSupport.queryForPage(sql.toString(), param.getPage(), param.getPageSize(), GroupbuyGoodsVO.class, whereParam.toArray());
        return webPage;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class})
    public GroupbuyGoodsDO add(GroupbuyGoodsDO goodsDO) {

        // Activity id
        Integer actId = goodsDO.getActId();

        GroupbuyActiveDO activeDO = groupbuyActiveManager.getModel(actId);
        if (activeDO == null) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "The participating activity does not exist");
        }
        // If the group purchase price is greater than or equal to the original price of the item, an exception is thrown
        if (goodsDO.getPrice() >= goodsDO.getOriginalPrice()) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "The promotional price of the commodities participating in the activities shall not be greater than or equal to the original price of the commodities");
        }
        // Verify whether the purchase limit exceeds the total number of goods
        if (goodsDO.getLimitNum() > goodsDO.getGoodsNum()) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "The limited quantity of goods should not be greater than the total number of goods");
        }
        // Verify whether group purchase has expired
        long datetime = DateUtil.getDateline();
        if (datetime >= activeDO.getStartTime()) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "Group purchase activity has started, cannot add active goods");
        }

        /**
         * *************Two cases：******************
         * Seckill time：      |________________|
         * Group purchase period：  |_____|           |_______|
         *
         * ************The third case：******************
         * Seckill time：        |______|
         * Group purchase period：   |________________|
         *
         * ************The fourth case：******************
         * Seckill time：   |________________|
         * Group purchase period：        |______|
         */


        String sql = "select count(0) from es_promotion_goods where promotion_type='SECKILL' and goods_id=? and (" +
                " ( start_time<?  && end_time>? )" +
                " || ( start_time<?  && end_time>? )" +
                " || ( start_time<?  && end_time>? )" +
                " || ( start_time>?  && end_time<? ))";
        int count = daoSupport.queryForInt(sql, goodsDO.getGoodsId(),
                activeDO.getStartTime(), activeDO.getStartTime(),
                activeDO.getEndTime(), activeDO.getEndTime(),
                activeDO.getStartTime(), activeDO.getEndTime(),
                activeDO.getStartTime(), activeDO.getEndTime()
        );

        if (count > 0) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "This product has participated in the flash sale activity in the overlapping time period, and cannot participate in the group purchase activity");
        }

        goodsDO.setGbStatus(GroupBuyGoodsStatusEnum.APPROVED.status());

        this.daoSupport.insert(goodsDO);
        int id = this.daoSupport.getLastId("es_groupbuy_goods");
        goodsDO.setGbId(id);

        // Modify the number of commodities that have participated in the group purchase activity
        this.daoSupport.execute("update es_groupbuy_active set goods_num=goods_num+1 where act_id=?", actId);

        // Activity information DTO
        PromotionDetailDTO detailDTO = new PromotionDetailDTO(activeDO);
        // Inventory to activity commodity comparison table
        this.promotionGoodsManager.addModel(goodsDO.getGoodsId(), detailDTO);
        // Inserted into the cache
        this.cache.put(PromotionCacheKeys.getGroupbuyKey(actId), goodsDO);
        // Add the item to the lazy load queue and turn the index price into the latest offer price at the specified time
        PromotionPriceDTO promotionPriceDTO = new PromotionPriceDTO(goodsDO.getGoodsId(),goodsDO.getPrice());

        timeTrigger.add(TimeExecute.PROMOTION_EXECUTER, promotionPriceDTO, activeDO.getStartTime(), null);
        // Reset the indexs discount price to 0 after this activity ends
        promotionPriceDTO.setPrice(0.0);
        timeTrigger.add(TimeExecute.PROMOTION_EXECUTER, promotionPriceDTO, activeDO.getEndTime(), null);

        return goodsDO;
    }

    @Override
    public GroupbuyGoodsDO edit(GroupbuyGoodsDO goodsDO, Integer id) {

        // Verify that the group purchase has started
        GroupbuyActiveDO activeDO = groupbuyActiveManager.getModel(goodsDO.getActId());
        long datetime = DateUtil.getDateline();
        if (activeDO == null || datetime >= activeDO.getStartTime()) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "Group purchase activity has started, can not modify the activity of goods");
        }
        // If the group purchase price is greater than or equal to the original price of the item, an exception is thrown
        if (goodsDO.getPrice() >= goodsDO.getOriginalPrice()) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "The promotional price of the commodities participating in the activities shall not be greater than or equal to the original price of the commodities");
        }
        // Verify whether the purchase limit exceeds the total number of goods
        if (goodsDO.getLimitNum() > goodsDO.getGoodsNum()) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "The limited quantity of goods should not be greater than the total number of goods");
        }
        // Reset group purchase status to Approved
        goodsDO.setGbStatus(GroupBuyGoodsStatusEnum.APPROVED.status());
        this.daoSupport.update(goodsDO, id);
        // Delete the original delayed task
        timeTrigger.delete(TimeExecute.PROMOTION_EXECUTER,activeDO.getStartTime(),null);

        PromotionPriceDTO promotionPriceDTO = new PromotionPriceDTO(goodsDO.getGoodsId(),goodsDO.getPrice());

        timeTrigger.add(TimeExecute.PROMOTION_EXECUTER, promotionPriceDTO, activeDO.getStartTime(), null);

        return goodsDO;
    }

    @Override
    public void delete(Integer id) {

        // Delete the group purchase information from the active product list
        GroupbuyGoodsVO groupbuyGoods = this.getModel(id);
        Integer goodsId = groupbuyGoods.getGoodsId();
        this.promotionGoodsManager.delete(goodsId, groupbuyGoods.getActId(), PromotionTypeEnum.GROUPBUY.name());

        this.daoSupport.delete(GroupbuyGoodsDO.class, id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class})
    public GroupbuyGoodsVO getModel(Integer gbId) {

        String sql = "select gg.*,ga.act_name as title,ga.start_time,ga.end_time from es_groupbuy_goods as gg " +
                "left join es_groupbuy_active as ga on gg.act_id=ga.act_id where gb_id = ?";

        GroupbuyGoodsVO groupbuyGoodsVO = this.daoSupport.queryForObject(sql.toString(), GroupbuyGoodsVO.class, gbId);
        return groupbuyGoodsVO;
    }

    @Override
    public GroupbuyGoodsDO getModel(Integer actId, Integer goodsId) {
        String sql = "select * from es_groupbuy_goods where act_id = ? and goods_id=?";
        GroupbuyGoodsDO groupbuyGoodsDO = this.daoSupport.queryForObject(sql, GroupbuyGoodsDO.class, actId, goodsId);
        return groupbuyGoodsDO;
    }


    @Override
    public void verifyAuth(Integer id) {
        GroupbuyGoodsDO groupbuyGoodsDO = this.getModel(id);
        if (groupbuyGoodsDO == null) {
            throw new NoPermissionException("Have the right to operate");
        }

    }


    @Override
    public void updateStatus(Integer gbId, Integer status) {
        String sql = "update es_groupbuy_goods set gb_status=? where gb_id=?";
        this.daoSupport.execute(sql, status, gbId);
    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean cutQuantity(String orderSn, List<PromotionDTO> promotionDTOList) {

        for (PromotionDTO promotionDTO : promotionDTOList) {

            int num = promotionDTO.getNum();
            int goodsId = promotionDTO.getGoodsId();
            int actId = promotionDTO.getActId();

            Lock lock = getGoodsQuantityLock(goodsId);
            lock.lock();
            try {
                GroupbuyGoodsDO goodsDO = this.getModel(actId, goodsId);
                // Can purchase quantity
                int canBuyNum = goodsDO.getGoodsNum() < 0 ? 0 : goodsDO.getGoodsNum();
                // Quantity available for purchase is less than or equal to 0 && Quantity to be deducted is greater than quantity in stock
                if (canBuyNum <= 0 || promotionDTO.getNum() > canBuyNum) {
                    throw new ServiceException(PromotionErrorCode.E400.code(), "Group purchase goods are not in stock");
                }
                String sql = "update es_groupbuy_goods set buy_num=buy_num+?,goods_num=goods_num-? where goods_id=? and act_id=? and goods_num >=?";
                int rowNum = this.daoSupport.execute(sql, num, num, goodsId, actId, num);

                if (rowNum <= 0) {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                lock.unlock();
                if (logger.isDebugEnabled()) {
                    logger.debug(Thread.currentThread() + " unlocked [" + actId + "] at " + DateUtil.toString(new Date(), "HH:MM:ss SS"));
                }
            }
        }
        for (PromotionDTO promotionDTO : promotionDTOList) {

            this.logAndCleanCache(promotionDTO, orderSn);
        }

        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class})
    public void addQuantity(String orderSn) {

        List<GroupbuyQuantityLog> logs = groupbuyQuantityLogManager.rollbackReduce(orderSn);
        for (GroupbuyQuantityLog log : logs) {
            int num = log.getQuantity();
            int goodsId = log.getGoodsId();
            int gbId = log.getGbId();
            String sql = "update es_groupbuy_goods set buy_num=buy_num-?,goods_num=goods_num+? where goods_id=? and act_id=?";
            this.daoSupport.execute(sql, num, num, goodsId, gbId);
        }
    }

    @Override
    public GroupbuyGoodsVO getModelAndQuantity(Integer id) {

        GroupbuyGoodsDO groupbuyGoods = this.getModel(id);
        if (groupbuyGoods != null) {
            GroupbuyGoodsVO res = new GroupbuyGoodsVO();
            BeanUtils.copyProperties(groupbuyGoods, res);
            CacheGoods goods = goodsClient.getFromCache(groupbuyGoods.getGoodsId());
            res.setEnableQuantity(goods.getEnableQuantity());
            res.setQuantity(goods.getQuantity());
            return res;
        }

        return null;
    }

    @Override
    public void updateGoodsInfo(Integer[] goodsIds) {
        if (goodsIds == null) {
            return;
        }
        List<Map<String, Object>> result = goodsClient.getGoods(goodsIds);
        if (result == null || result.isEmpty()) {
            return;
        }
        for (Map<String, Object> map : result) {
            Map<String, Object> whereMap = new HashMap<>();
            whereMap.put("goods_id", map.get("goods_id"));
            this.daoSupport.update("es_groupbuy_goods", map, whereMap);
        }


    }

    private Lock getGoodsQuantityLock(Integer gbId) {
        RLock lock = redisson.getLock("groupbuy_goods_quantity_lock_" + gbId);
        return lock;
    }


    @Override
    public void rollbackStock(List<PromotionDTO> promotionDTOList, String orderSn) {
        for (PromotionDTO promotionDTO : promotionDTOList) {

            int num = promotionDTO.getNum();
            int goodsId = promotionDTO.getGoodsId();
            int actId = promotionDTO.getActId();

            String sql = "update es_groupbuy_goods set buy_num=buy_num-?,goods_num=goods_num+? where goods_id=? and act_id=? ";
            this.daoSupport.execute(sql, num, num, goodsId, actId);
            logAndCleanCache(promotionDTO, orderSn);

        }
    }

    /**
     * Log and clear the cache
     *
     * @param promotionDTO
     * @param orderSn
     */
    private void logAndCleanCache(PromotionDTO promotionDTO, String orderSn) {
        GroupbuyQuantityLog groupbuyQuantityLog = new GroupbuyQuantityLog();
        groupbuyQuantityLog.setOpTime(DateUtil.getDateline());
        groupbuyQuantityLog.setQuantity(promotionDTO.getNum());
        groupbuyQuantityLog.setReason("A bulk sales");
        groupbuyQuantityLog.setGbId(promotionDTO.getActId());
        groupbuyQuantityLog.setLogType(GroupbuyQuantityLogEnum.BUY.name());
        groupbuyQuantityLog.setOrderSn(orderSn);
        groupbuyQuantityLog.setGoodsId(promotionDTO.getGoodsId());
        groupbuyQuantityLogManager.add(groupbuyQuantityLog);
        this.promotionGoodsManager.reputCache(promotionDTO.getGoodsId());
    }


}
