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
package cloud.shopfly.b2c.core.promotion.seckill.service.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.promotion.PromotionErrorCode;
import cloud.shopfly.b2c.core.promotion.seckill.model.dos.SeckillApplyDO;
import cloud.shopfly.b2c.core.promotion.seckill.model.dos.SeckillDO;
import cloud.shopfly.b2c.core.promotion.seckill.model.dto.SeckillQueryParam;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.SeckillApplyVO;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.SeckillGoodsVO;
import cloud.shopfly.b2c.core.promotion.tool.model.dos.PromotionGoodsDO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionPriceDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.service.impl.AbstractPromotionRuleManagerImpl;
import cloud.shopfly.b2c.core.promotion.tool.support.PromotionCacheKeys;
import cloud.shopfly.b2c.core.base.rabbitmq.TimeExecute;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillGoodsManager;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillManager;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.trigger.Interface.TimeTrigger;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * Flash sale application business category
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 17:30:09
 */
@Service
public class SeckillGoodsManagerImpl extends AbstractPromotionRuleManagerImpl implements SeckillGoodsManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SeckillManager seckillManager;

    @Autowired
    private Cache cache;

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private TimeTrigger timeTrigger;

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Override
    public Page list(SeckillQueryParam queryParam) {
        List param = new ArrayList();

        StringBuffer sql = new StringBuffer();
        sql.append("select * from es_seckill_apply where seckill_id=? ");
        param.add(queryParam.getSeckillId());

        if (queryParam.getStatus() != null) {
            sql.append(" and status =? ");
            param.add(queryParam.getStatus());
        }
        sql.append(" order by apply_id desc");
        Page webPage = this.daoSupport.queryForPage(sql.toString(), queryParam.getPageNo(), queryParam.getPageSize(), SeckillApplyVO.class, param.toArray());

        return webPage;
    }


    @Override
    public void delete(Integer id) {

        this.daoSupport.delete(SeckillApplyDO.class, id);
    }

    @Override
    public SeckillApplyDO getModel(Integer id) {

        return this.daoSupport.queryForObject(SeckillApplyDO.class, id);
    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, ServiceException.class})
    public void addApply(List<SeckillApplyDO> list) {


        SeckillDO seckillVO = this.seckillManager.getModel(list.get(0).getSeckillId());
        // Query the activity ID of the application form for deletion
        String sql = "select apply_id from es_seckill_apply where seckill_id = ?";
        List<Map> listApply = this.daoSupport.queryForList(sql,list.get(0).getSeckillId());

        if(StringUtil.isNotEmpty(listApply)){
            Integer[] applyIds = new Integer[listApply.size()];
            for(int i = 0 ; i<listApply.size();i++){
                Integer applyId = Integer.parseInt(listApply.get(i).get("apply_id").toString());
                applyIds[i] = applyId;
            }
            // Delete the original flash sale application
            sql = "delete from es_seckill_apply where seckill_id = ?";
            this.daoSupport.execute(sql, list.get(0).getSeckillId());
            // Delete the original promotional list activity
            List<Object> term = new ArrayList<>();
            String sqlString = SqlUtil.getInSql(applyIds, term);
            term.add(PromotionTypeEnum.SECKILL.name());

            sql = "delete from es_promotion_goods where activity_id in ("+sqlString+") and promotion_type = ? ";
            this.daoSupport.execute(sql,term.toArray());
        }
        // Loop to add flash sale applications
        for (SeckillApplyDO seckillApplyDO : list) {
            Integer goodsId = seckillApplyDO.getGoodsId();
            // Query the goods
            CacheGoods goods = goodsClient.getFromCache(goodsId);
            // Judge the number of activities and inventory
            if (seckillApplyDO.getSoldQuantity() > goods.getEnableQuantity()) {
                throw new ServiceException(PromotionErrorCode.E402.code(), seckillApplyDO.getGoodsName() + ",This article is out of stock");
            }

            /**
             * *************Two cases：******************
             * Group purchase period：      |________________|
             * Seckill time：  |_____|           |_______|
             *
             * ************The third case：******************
             * Group purchase period：        |______|
             * Seckill time：   |________________|
             *
             * ************The fourth case：******************
             * Group purchase period：   |________________|
             * Seckill time：        |______|
             *
             */
            // The start time of this product shall be calculated according to the time segment he participates in, and the end time is 23:59:59 p.m. of the same day
            String date = DateUtil.toString(seckillVO.getStartDay(), "yyyy-MM-dd");
            long startTime = DateUtil.getDateline(date + " " + seckillApplyDO.getTimeLine() + ":00:00", "yyyy-MM-dd HH:mm:ss");
            long endTime = DateUtil.getDateline(date + " 23:59:59", "yyyy-MM-dd HH:mm:ss");

            sql = "select count(0) from es_promotion_goods where promotion_type='GROUPBUY' and goods_id=? and (" +
                    " ( start_time<?  && end_time>? )" +
                    " || ( start_time<?  && end_time>? )" +
                    " || ( start_time<?  && end_time>? )" +
                    " || ( start_time>?  && end_time<? ))";
            int count = daoSupport.queryForInt(sql, goodsId,
                    startTime, startTime,
                    endTime, endTime,
                    startTime, endTime,
                    startTime, endTime
            );
            if (count > 0) {
                throw new ServiceException(PromotionErrorCode.E400.code(), "product[" + goods.getGoodsName() + "]You have participated in group purchase activities in overlapping periods and cannot participate in flash sale activities");
            }


            // The original price of a commodity
            seckillApplyDO.setOriginalPrice(goods.getPrice());
            seckillApplyDO.setSalesNum(0);
            this.daoSupport.insert(seckillApplyDO);
            int applyId = this.daoSupport.getLastId("es_seckill_apply");
            seckillApplyDO.setApplyId(applyId);

            // Promotional list
            PromotionGoodsDO promotion = new PromotionGoodsDO(seckillApplyDO, startTime, endTime);

            this.daoSupport.insert(promotion);
            // Set the lazy load task and set the search engine discount to 0 after the start time of the activity
            PromotionPriceDTO promotionPriceDTO = new PromotionPriceDTO(seckillApplyDO.getGoodsId(),seckillApplyDO.getPrice());
            timeTrigger.add(TimeExecute.PROMOTION_EXECUTER, promotionPriceDTO, startTime, null);
            // Reset the indexs discount price to 0 after this activity ends
            promotionPriceDTO.setPrice(0.0);
            timeTrigger.add(TimeExecute.PROMOTION_EXECUTER, promotionPriceDTO, endTime, null);

        }


    }


    @Override
    public boolean addSoldNum(List<PromotionDTO> promotionDTOList) {

        // Classes that traverse the relationship between activities and goods
        for (PromotionDTO promotionDTO : promotionDTOList) {

            // lock
            Lock lock = getGoodsQuantityLock(promotionDTO.getGoodsId());
            lock.lock();
            try {

                Map<Integer, List<SeckillGoodsVO>> map = this.getSeckillGoodsList();

                // Record the moment at which the item belongs

                for (Map.Entry<Integer, List<SeckillGoodsVO>> entry : map.entrySet()) {

                    List<SeckillGoodsVO> seckillGoodsDTOList = entry.getValue();

                    for (SeckillGoodsVO goodsVO : seckillGoodsDTOList) {
                        if (goodsVO.getGoodsId().equals(promotionDTO.getGoodsId())) {
                            // The number of purchases by users
                            int num = promotionDTO.getNum();

                            // Quantity sold
                            int soldNum = goodsVO.getSoldNum();

                            // The number sold out
                            int soldQuantity = goodsVO.getSoldQuantity();

                            // Plus the number of purchases the user just ordered
                            soldNum = soldNum + num;

                            // If the number sold is greater than the number sold, change the number sold to the number sold to prevent the page from showing more than 100% of the percentage sold
                            if (soldNum >= soldQuantity) {
                                soldNum = soldQuantity;
                            }
                            // Set the quantity sold
                            goodsVO.setSoldNum(soldNum);

                            String sql = "update es_seckill_apply set sold_quantity = sold_quantity-?,sales_num = sales_num +? where goods_id = ? and seckill_id=? and sold_quantity>=?";
                            int rowNum = this.daoSupport.execute(sql, num, num, goodsVO.getGoodsId(), goodsVO.getSeckillId(), num);

                            // Insufficient inventory
                            if (rowNum <= 0) {
                                return false;
                            }

                        }
                    }
                }
                this.cache.remove(PromotionCacheKeys.getSeckillKey(DateUtil.toString(DateUtil.getDateline(), "yyyyMMdd")));

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                lock.unlock();
                if (logger.isDebugEnabled()) {
                    logger.debug(Thread.currentThread() + " unlocked [" + promotionDTO.getGoodsId() + "] at " + DateUtil.toString(new Date(), "HH:MM:ss SS"));
                }
            }
        }

        return true;
    }

    @Override
    public Map<Integer, List<SeckillGoodsVO>> getSeckillGoodsList() {

        // Read todays time
        long today = DateUtil.startOfTodDay();
        // Read flash sale active items from cache
        String redisKey = PromotionCacheKeys.getSeckillKey(DateUtil.toString(DateUtil.getDateline(), "yyyyMMdd"));
        Map<Integer, List<SeckillGoodsVO>> map = this.cache.getHash(redisKey);

        // If not in Redis, fetch from the data
        if (map == null || map.isEmpty()) {

            // Read items from live flash sales that are taking place that day
            String sql = "select * from es_seckill_apply where start_day = ? ";
            List<SeckillApplyDO> list = this.daoSupport.queryForList(sql, SeckillApplyDO.class, today);

            // Walk through all the goods and save all the different moments
            for (SeckillApplyDO applyDO : list) {
                map.put(applyDO.getTimeLine(), new ArrayList());
            }

            // All moments are traversed and goods are assigned to each moment
            for (SeckillApplyDO applyDO : list) {
                for (Map.Entry<Integer, List<SeckillGoodsVO>> entry : map.entrySet()) {
                    if (applyDO.getTimeLine().equals(entry.getKey())) {

                        // Time stamp of the activity start date (day)
                        long startDay = applyDO.getStartDay();
                        // Form 2018090910 string like this
                        String timeStr = DateUtil.toString(startDay, "yyyyMMdd") + applyDO.getTimeLine();
                        // Get the timestamp of the start date
                        long startTime = DateUtil.getDateline(timeStr, "yyyyMMddHH");

                        // Query the goods
                        CacheGoods goods = goodsClient.getFromCache(applyDO.getGoodsId());
                        SeckillGoodsVO seckillGoods = new SeckillGoodsVO();
                        seckillGoods.setGoodsId(goods.getGoodsId());
                        seckillGoods.setGoodsName(goods.getGoodsName());
                        seckillGoods.setOriginalPrice(goods.getPrice());
                        seckillGoods.setSeckillPrice(applyDO.getPrice());
                        seckillGoods.setSoldNum(applyDO.getSalesNum());
                        seckillGoods.setSoldQuantity(applyDO.getSoldQuantity());
                        seckillGoods.setGoodsImage(goods.getThumbnail());
                        seckillGoods.setStartTime(startTime);
                        seckillGoods.setSeckillId(applyDO.getSeckillId());
                        seckillGoods.setRemainQuantity(applyDO.getSoldQuantity() - applyDO.getSalesNum());

                        if (entry.getValue() == null) {
                            entry.setValue(new ArrayList<>());
                        }
                        entry.getValue().add(seckillGoods);
                    }
                }
            }

            // Pressure into the cache
            for (Map.Entry<Integer, List<SeckillGoodsVO>> entry : map.entrySet()) {
                this.cache.putHash(redisKey, entry.getKey(), entry.getValue());
            }
        }

        return map;
    }

    public static void main(String[] args) {
        // Time stamp of the activity start date (day)
        long startDay = DateUtil.getDateline("20180101000000", "yyyyMMddHHMMss");
        // Form 2018090910 string like this
        String timeStr = DateUtil.toString(startDay, "yyyyMMdd") + 10;
        // Get the timestamp of the start date
        long startTime = DateUtil.getDateline(timeStr, "yyyyMMddHH");

        String str = DateUtil.toString(startTime, "yyyyMMddHH");
        System.out.println(timeStr);
        System.out.println(str);

    }

    @Override
    public List getSeckillGoodsList(Integer rangeTime, Integer pageNo, Integer pageSize) {

        // Read flash sale active items
        Map<Integer, List<SeckillGoodsVO>> map = this.getSeckillGoodsList();
        List<SeckillGoodsVO> totalList = new ArrayList();

        // Walk through live goods
        for (Map.Entry<Integer, List<SeckillGoodsVO>> entry : map.entrySet()) {
            if (rangeTime.intValue() == entry.getKey().intValue()) {
                totalList = entry.getValue();
                break;
            }
        }

        // Redis cannot page manually read data based on pages
        List<SeckillGoodsVO> list = new ArrayList<SeckillGoodsVO>();
        int currIdx = (pageNo > 1 ? (pageNo - 1) * pageSize : 0);
        for (int i = 0; i < pageSize && i < totalList.size() - currIdx; i++) {
            SeckillGoodsVO goods = totalList.get(currIdx + i);
            list.add(goods);
        }

        return list;
    }


    private Lock getGoodsQuantityLock(Integer gbId) {
        RLock lock = redisson.getLock("seckill_goods_quantity_lock_" + gbId);
        return lock;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, ServiceException.class})
    public void rollbackStock(List<PromotionDTO> promotionDTOList) {

        List<SeckillGoodsVO> lockedList = new ArrayList<>();

        // Classes that traverse the relationship between activities and goods
        for (PromotionDTO promotionDTO : promotionDTOList) {

            Map<Integer, List<SeckillGoodsVO>> map = this.getSeckillGoodsList();

            for (Map.Entry<Integer, List<SeckillGoodsVO>> entry : map.entrySet()) {

                List<SeckillGoodsVO> seckillGoodsDTOList = entry.getValue();
                for (SeckillGoodsVO goodsVO : seckillGoodsDTOList) {

                    if (goodsVO.getGoodsId().equals(promotionDTO.getGoodsId())) {
                        // The number of purchases by users
                        int num = promotionDTO.getNum();
                        goodsVO.setSoldNum(num);
                        lockedList.add(goodsVO);

                    }
                }
            }
        }

        this.cache.remove(PromotionCacheKeys.getSeckillKey(DateUtil.toString(DateUtil.getDateline(), "yyyyMMdd")));

        innerRollbackStock(lockedList);

    }

    @Override
    public List<SeckillApplyDO> getListBySeckill(Integer id) {

        String sql = "select * from es_seckill_apply where seckill_id = ? ";

        return this.daoSupport.queryForList(sql, SeckillApplyDO.class, id);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, ServiceException.class})
    public void deleteSeckillGoods(Integer goodsId) {

        // Delete flash sales already started and not yet
        this.daoSupport.execute("delete from es_seckill_apply where goods_id = ? and start_day >= ? ",goodsId,DateUtil.startOfTodDay());

        // Removes data from the cache
        String redisKey = getRedisKey(DateUtil.getDateline());

        this.cache.remove(redisKey);

    }


    /**
     * Roll back snapkill inventory
     *
     * @param goodsList
     */
    private void innerRollbackStock(List<SeckillGoodsVO> goodsList) {
        for (SeckillGoodsVO goodsVO : goodsList) {
            int num = goodsVO.getSoldNum();
            String sql = "update es_seckill_apply set sold_quantity = sold_quantity+?,sales_num = sales_num - ? where goods_id = ? and seckill_id=?";
            this.daoSupport.execute(sql, num, num, goodsVO.getGoodsId(), goodsVO.getSeckillId());
        }

    }

    /**
     * Get a flash salekey
     * @param dateline
     * @return
     */
    private String getRedisKey(long dateline) {
        return PromotionCacheKeys.getSeckillKey(DateUtil.toString(dateline, "yyyyMMdd"));
    }


}
