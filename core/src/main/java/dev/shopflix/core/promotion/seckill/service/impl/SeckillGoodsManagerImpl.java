/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.seckill.service.impl;

import dev.shopflix.core.base.rabbitmq.TimeExecute;
import dev.shopflix.core.client.goods.GoodsClient;
import dev.shopflix.core.goods.model.vo.CacheGoods;
import dev.shopflix.core.promotion.PromotionErrorCode;
import dev.shopflix.core.promotion.seckill.model.dos.SeckillApplyDO;
import dev.shopflix.core.promotion.seckill.model.dos.SeckillDO;
import dev.shopflix.core.promotion.seckill.model.dto.SeckillQueryParam;
import dev.shopflix.core.promotion.seckill.model.vo.SeckillApplyVO;
import dev.shopflix.core.promotion.seckill.model.vo.SeckillGoodsVO;
import dev.shopflix.core.promotion.seckill.service.SeckillGoodsManager;
import dev.shopflix.core.promotion.seckill.service.SeckillManager;
import dev.shopflix.core.promotion.tool.model.dos.PromotionGoodsDO;
import dev.shopflix.core.promotion.tool.model.dto.PromotionDTO;
import dev.shopflix.core.promotion.tool.model.dto.PromotionPriceDTO;
import dev.shopflix.core.promotion.tool.model.enums.PromotionTypeEnum;
import dev.shopflix.core.promotion.tool.service.impl.AbstractPromotionRuleManagerImpl;
import dev.shopflix.core.promotion.tool.support.PromotionCacheKeys;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.trigger.Interface.TimeTrigger;
import dev.shopflix.framework.util.DateUtil;
import dev.shopflix.framework.util.SqlUtil;
import dev.shopflix.framework.util.StringUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * 限时抢购申请业务类
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
        //查询申请表的活动id,用于删除使用
        String sql = "select apply_id from es_seckill_apply where seckill_id = ?";
        List<Map> listApply = this.daoSupport.queryForList(sql,list.get(0).getSeckillId());

        if(StringUtil.isNotEmpty(listApply)){
            Integer[] applyIds = new Integer[listApply.size()];
            for(int i = 0 ; i<listApply.size();i++){
                Integer applyId = Integer.parseInt(listApply.get(i).get("apply_id").toString());
                applyIds[i] = applyId;
            }
            //删除原有的限时抢购申请
            sql = "delete from es_seckill_apply where seckill_id = ?";
            this.daoSupport.execute(sql, list.get(0).getSeckillId());
            //删除原有的促销商品表活动
            List<Object> term = new ArrayList<>();
            String sqlString = SqlUtil.getInSql(applyIds, term);
            term.add(PromotionTypeEnum.SECKILL.name());

            sql = "delete from es_promotion_goods where activity_id in ("+sqlString+") and promotion_type = ? ";
            this.daoSupport.execute(sql,term.toArray());
        }
        //循环添加限时抢购申请
        for (SeckillApplyDO seckillApplyDO : list) {
            Integer goodsId = seckillApplyDO.getGoodsId();
            //查询商品
            CacheGoods goods = goodsClient.getFromCache(goodsId);
            //判断参加活动的数量和库存数量
            if (seckillApplyDO.getSoldQuantity() > goods.getEnableQuantity()) {
                throw new ServiceException(PromotionErrorCode.E402.code(), seckillApplyDO.getGoodsName() + ",此商品库存不足");
            }

            /**
             * *************两种情况：******************
             * 团购时间段：      |________________|
             * 秒杀时间段：  |_____|           |_______|
             *
             * ************第三种情况：******************
             * 团购时间段：        |______|
             * 秒杀时间段：   |________________|
             *
             * ************第四种情况：******************
             * 团购时间段：   |________________|
             * 秒杀时间段：        |______|
             *
             */
            //这个商品的开始时间计算要用他参与的时间段来计算，结束时间是当天晚上23：59：59
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
                throw new ServiceException(PromotionErrorCode.E400.code(), "商品[" + goods.getGoodsName() + "]已经在重叠的时间段参加了团购活动，不能参加限时抢购活动");
            }


            //商品的原始价格
            seckillApplyDO.setOriginalPrice(goods.getPrice());
            seckillApplyDO.setSalesNum(0);
            this.daoSupport.insert(seckillApplyDO);
            int applyId = this.daoSupport.getLastId("es_seckill_apply");
            seckillApplyDO.setApplyId(applyId);

            //促销商品表
            PromotionGoodsDO promotion = new PromotionGoodsDO(seckillApplyDO, startTime, endTime);

            this.daoSupport.insert(promotion);
            //设置延迟加载任务，到活动开始时间后将搜索引擎中的优惠价格设置为0
            PromotionPriceDTO promotionPriceDTO = new PromotionPriceDTO(seckillApplyDO.getGoodsId(),seckillApplyDO.getPrice());
            timeTrigger.add(TimeExecute.PROMOTION_EXECUTER, promotionPriceDTO, startTime, null);
            //此活动结束后将索引的优惠价格重置为0
            promotionPriceDTO.setPrice(0.0);
            timeTrigger.add(TimeExecute.PROMOTION_EXECUTER, promotionPriceDTO, endTime, null);

        }


    }


    @Override
    public boolean addSoldNum(List<PromotionDTO> promotionDTOList) {

        //遍历活动与商品关系的类
        for (PromotionDTO promotionDTO : promotionDTOList) {

            //加锁
            Lock lock = getGoodsQuantityLock(promotionDTO.getGoodsId());
            lock.lock();
            try {

                Map<Integer, List<SeckillGoodsVO>> map = this.getSeckillGoodsList();

                //记录此商品属于哪个时刻

                for (Map.Entry<Integer, List<SeckillGoodsVO>> entry : map.entrySet()) {

                    List<SeckillGoodsVO> seckillGoodsDTOList = entry.getValue();

                    for (SeckillGoodsVO goodsVO : seckillGoodsDTOList) {
                        if (goodsVO.getGoodsId().equals(promotionDTO.getGoodsId())) {
                            //用户购买的数量
                            int num = promotionDTO.getNum();

                            //已销售的数量
                            int soldNum = goodsVO.getSoldNum();

                            //售空数量
                            int soldQuantity = goodsVO.getSoldQuantity();

                            //加上用户刚下单的购买数量
                            soldNum = soldNum + num;

                            //如果已销售数量大于售空数量，则将已销售数量更改为售空数量，防止页面展示的已售百分比超过100%
                            if (soldNum >= soldQuantity) {
                                soldNum = soldQuantity;
                            }
                            //设置已销售数量
                            goodsVO.setSoldNum(soldNum);

                            String sql = "update es_seckill_apply set sold_quantity = sold_quantity-?,sales_num = sales_num +? where goods_id = ? and seckill_id=? and sold_quantity>=?";
                            int rowNum = this.daoSupport.execute(sql, num, num, goodsVO.getGoodsId(), goodsVO.getSeckillId(), num);

                            //库存不足
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

        //读取今天的时间
        long today = DateUtil.startOfTodDay();
        //从缓存读取限时抢购的活动的商品
        String redisKey = PromotionCacheKeys.getSeckillKey(DateUtil.toString(DateUtil.getDateline(), "yyyyMMdd"));
        Map<Integer, List<SeckillGoodsVO>> map = this.cache.getHash(redisKey);

        //如果redis中没有则从数据取
        if (map == null || map.isEmpty()) {

            //读取当天正在进行的活限时抢购活动的商品
            String sql = "select * from es_seckill_apply where start_day = ? ";
            List<SeckillApplyDO> list = this.daoSupport.queryForList(sql, SeckillApplyDO.class, today);

            //遍历所有的商品，并保存所有不同的时刻
            for (SeckillApplyDO applyDO : list) {
                map.put(applyDO.getTimeLine(), new ArrayList());
            }

            //遍历所有的时刻，并为每个时刻赋值商品
            for (SeckillApplyDO applyDO : list) {
                for (Map.Entry<Integer, List<SeckillGoodsVO>> entry : map.entrySet()) {
                    if (applyDO.getTimeLine().equals(entry.getKey())) {

                        //活动开始日期（天）的时间戳
                        long startDay = applyDO.getStartDay();
                        //形成 2018090910 这样的串
                        String timeStr = DateUtil.toString(startDay, "yyyyMMdd") + applyDO.getTimeLine();
                        //得到开始日期的时间戳
                        long startTime = DateUtil.getDateline(timeStr, "yyyyMMddHH");

                        //查询商品
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

            //压入缓存
            for (Map.Entry<Integer, List<SeckillGoodsVO>> entry : map.entrySet()) {
                this.cache.putHash(redisKey, entry.getKey(), entry.getValue());
            }
        }

        return map;
    }

    public static void main(String[] args) {
        //活动开始日期（天）的时间戳
        long startDay = DateUtil.getDateline("20180101000000", "yyyyMMddHHMMss");
        //形成 2018090910 这样的串
        String timeStr = DateUtil.toString(startDay, "yyyyMMdd") + 10;
        //得到开始日期的时间戳
        long startTime = DateUtil.getDateline(timeStr, "yyyyMMddHH");

        String str = DateUtil.toString(startTime, "yyyyMMddHH");
        System.out.println(timeStr);
        System.out.println(str);

    }

    @Override
    public List getSeckillGoodsList(Integer rangeTime, Integer pageNo, Integer pageSize) {

        //读取限时抢购活动商品
        Map<Integer, List<SeckillGoodsVO>> map = this.getSeckillGoodsList();
        List<SeckillGoodsVO> totalList = new ArrayList();

        //遍历活动商品
        for (Map.Entry<Integer, List<SeckillGoodsVO>> entry : map.entrySet()) {
            if (rangeTime.intValue() == entry.getKey().intValue()) {
                totalList = entry.getValue();
                break;
            }
        }

        //redis不能分页 手动根据分页读取数据
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

        //遍历活动与商品关系的类
        for (PromotionDTO promotionDTO : promotionDTOList) {

            Map<Integer, List<SeckillGoodsVO>> map = this.getSeckillGoodsList();

            for (Map.Entry<Integer, List<SeckillGoodsVO>> entry : map.entrySet()) {

                List<SeckillGoodsVO> seckillGoodsDTOList = entry.getValue();
                for (SeckillGoodsVO goodsVO : seckillGoodsDTOList) {

                    if (goodsVO.getGoodsId().equals(promotionDTO.getGoodsId())) {
                        //用户购买的数量
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

        //删除限时抢购已经开始和未开始的商品
        this.daoSupport.execute("delete from es_seckill_apply where goods_id = ? and start_day >= ? ",goodsId,DateUtil.startOfTodDay());

        //移除缓存中的数据
        String redisKey = getRedisKey(DateUtil.getDateline());

        this.cache.remove(redisKey);

    }


    /**
     * 回滚秒杀库存
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
     * 获取限时抢购key
     * @param dateline
     * @return
     */
    private String getRedisKey(long dateline) {
        return PromotionCacheKeys.getSeckillKey(DateUtil.toString(dateline, "yyyyMMdd"));
    }


}
