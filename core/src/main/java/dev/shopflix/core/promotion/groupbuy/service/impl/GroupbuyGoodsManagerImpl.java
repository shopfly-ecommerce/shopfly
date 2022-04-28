/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.groupbuy.service.impl;

import dev.shopflix.core.base.rabbitmq.TimeExecute;
import dev.shopflix.core.client.goods.GoodsClient;
import dev.shopflix.core.goods.model.vo.CacheGoods;
import dev.shopflix.core.promotion.PromotionErrorCode;
import dev.shopflix.core.promotion.groupbuy.model.dos.GroupbuyActiveDO;
import dev.shopflix.core.promotion.groupbuy.model.dos.GroupbuyGoodsDO;
import dev.shopflix.core.promotion.groupbuy.model.dos.GroupbuyQuantityLog;
import dev.shopflix.core.promotion.groupbuy.model.enums.GroupBuyGoodsStatusEnum;
import dev.shopflix.core.promotion.groupbuy.model.enums.GroupbuyQuantityLogEnum;
import dev.shopflix.core.promotion.groupbuy.model.vo.GroupbuyGoodsVO;
import dev.shopflix.core.promotion.groupbuy.model.vo.GroupbuyQueryParam;
import dev.shopflix.core.promotion.groupbuy.service.GroupbuyActiveManager;
import dev.shopflix.core.promotion.groupbuy.service.GroupbuyGoodsManager;
import dev.shopflix.core.promotion.groupbuy.service.GroupbuyQuantityLogManager;
import dev.shopflix.core.promotion.tool.model.dto.PromotionDTO;
import dev.shopflix.core.promotion.tool.model.dto.PromotionDetailDTO;
import dev.shopflix.core.promotion.tool.model.dto.PromotionPriceDTO;
import dev.shopflix.core.promotion.tool.model.enums.PromotionTypeEnum;
import dev.shopflix.core.promotion.tool.service.PromotionGoodsManager;
import dev.shopflix.core.promotion.tool.service.impl.AbstractPromotionRuleManagerImpl;
import dev.shopflix.core.promotion.tool.support.PromotionCacheKeys;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.NoPermissionException;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.trigger.Interface.TimeTrigger;
import dev.shopflix.framework.util.DateUtil;
import dev.shopflix.framework.util.SqlUtil;
import dev.shopflix.framework.util.StringUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.locks.Lock;

/**
 * 团购商品业务类
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

        //活动id
        Integer actId = goodsDO.getActId();

        GroupbuyActiveDO activeDO = groupbuyActiveManager.getModel(actId);
        if (activeDO == null) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "参与的活动不存在");
        }
        //如果团购价格 大于等于商品原价，则抛出异常
        if (goodsDO.getPrice() >= goodsDO.getOriginalPrice()) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "参与活动的商品促销价格不得大于或等于商品原价");
        }
        //校验限购数量是否超过商品总数
        if (goodsDO.getLimitNum() > goodsDO.getGoodsNum()) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "商品限购数量不能大于商品总数");
        }
        //校验团购是否已经失效
        long datetime = DateUtil.getDateline();
        if (datetime >= activeDO.getStartTime()) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "团购活动已开始，不能添加活动商品");
        }

        /**
         * *************两种情况：******************
         * 秒杀时间段：      |________________|
         * 团购时间段：  |_____|           |_______|
         *
         * ************第三种情况：******************
         * 秒杀时间段：        |______|
         * 团购时间段：   |________________|
         *
         * ************第四种情况：******************
         * 秒杀时间段：   |________________|
         * 团购时间段：        |______|
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
            throw new ServiceException(PromotionErrorCode.E400.code(), "该商品已经在重叠的时间段参加了限时抢购活动，不能参加团购活动");
        }

        goodsDO.setGbStatus(GroupBuyGoodsStatusEnum.APPROVED.status());

        this.daoSupport.insert(goodsDO);
        int id = this.daoSupport.getLastId("es_groupbuy_goods");
        goodsDO.setGbId(id);

        //修改已参与团购活动的商品数量
        this.daoSupport.execute("update es_groupbuy_active set goods_num=goods_num+1 where act_id=?", actId);

        //活动信息DTO
        PromotionDetailDTO detailDTO = new PromotionDetailDTO(activeDO);
        //入库到活动商品对照表
        this.promotionGoodsManager.addModel(goodsDO.getGoodsId(), detailDTO);
        //插入缓存
        this.cache.put(PromotionCacheKeys.getGroupbuyKey(actId), goodsDO);
        //将此商品加入延迟加载队列，到指定的时间将索引价格变成最新的优惠价格
        PromotionPriceDTO promotionPriceDTO = new PromotionPriceDTO(goodsDO.getGoodsId(),goodsDO.getPrice());

        timeTrigger.add(TimeExecute.PROMOTION_EXECUTER, promotionPriceDTO, activeDO.getStartTime(), null);
        //此活动结束后将索引的优惠价格重置为0
        promotionPriceDTO.setPrice(0.0);
        timeTrigger.add(TimeExecute.PROMOTION_EXECUTER, promotionPriceDTO, activeDO.getEndTime(), null);

        return goodsDO;
    }

    @Override
    public GroupbuyGoodsDO edit(GroupbuyGoodsDO goodsDO, Integer id) {

        //校验团购是否已经开始
        GroupbuyActiveDO activeDO = groupbuyActiveManager.getModel(goodsDO.getActId());
        long datetime = DateUtil.getDateline();
        if (activeDO == null || datetime >= activeDO.getStartTime()) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "团购活动已开始，不能修改活动商品");
        }
        //如果团购价格 大于等于商品原价，则抛出异常
        if (goodsDO.getPrice() >= goodsDO.getOriginalPrice()) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "参与活动的商品促销价格不得大于或等于商品原价");
        }
        //校验限购数量是否超过商品总数
        if (goodsDO.getLimitNum() > goodsDO.getGoodsNum()) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "商品限购数量不能大于商品总数");
        }
        // 将团购商品状态重置为已通过
        goodsDO.setGbStatus(GroupBuyGoodsStatusEnum.APPROVED.status());
        this.daoSupport.update(goodsDO, id);
        //删除原有的延时任务
        timeTrigger.delete(TimeExecute.PROMOTION_EXECUTER,activeDO.getStartTime(),null);

        PromotionPriceDTO promotionPriceDTO = new PromotionPriceDTO(goodsDO.getGoodsId(),goodsDO.getPrice());

        timeTrigger.add(TimeExecute.PROMOTION_EXECUTER, promotionPriceDTO, activeDO.getStartTime(), null);

        return goodsDO;
    }

    @Override
    public void delete(Integer id) {

        //删除活动商品表中的团购信息
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
            throw new NoPermissionException("无权操作");
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
                //可购数量
                int canBuyNum = goodsDO.getGoodsNum() < 0 ? 0 : goodsDO.getGoodsNum();
                //可购买数量 小于等于0 && 要扣减的数量大于库存数量
                if (canBuyNum <= 0 || promotionDTO.getNum() > canBuyNum) {
                    throw new ServiceException(PromotionErrorCode.E400.code(), "团购商品库存不足");
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
     * 记录日志并清空缓存
     *
     * @param promotionDTO
     * @param orderSn
     */
    private void logAndCleanCache(PromotionDTO promotionDTO, String orderSn) {
        GroupbuyQuantityLog groupbuyQuantityLog = new GroupbuyQuantityLog();
        groupbuyQuantityLog.setOpTime(DateUtil.getDateline());
        groupbuyQuantityLog.setQuantity(promotionDTO.getNum());
        groupbuyQuantityLog.setReason("团购销售");
        groupbuyQuantityLog.setGbId(promotionDTO.getActId());
        groupbuyQuantityLog.setLogType(GroupbuyQuantityLogEnum.BUY.name());
        groupbuyQuantityLog.setOrderSn(orderSn);
        groupbuyQuantityLog.setGoodsId(promotionDTO.getGoodsId());
        groupbuyQuantityLogManager.add(groupbuyQuantityLog);
        this.promotionGoodsManager.reputCache(promotionDTO.getGoodsId());
    }


}
