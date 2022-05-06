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
import cloud.shopfly.b2c.core.promotion.seckill.model.dos.SeckillRangeDO;
import cloud.shopfly.b2c.core.promotion.seckill.model.dto.SeckillDTO;
import cloud.shopfly.b2c.core.promotion.seckill.model.enums.SeckillStatusEnum;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.SeckillGoodsVO;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.SeckillVO;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillGoodsManager;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillManager;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillRangeManager;
import cloud.shopfly.b2c.core.promotion.tool.model.dos.PromotionGoodsDO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionPriceDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.service.impl.AbstractPromotionRuleManagerImpl;
import cloud.shopfly.b2c.core.base.rabbitmq.TimeExecute;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.trigger.Interface.TimeTrigger;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 限时抢购入库业务类
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-21 10:32:36
 */
@Service
public class SeckillManagerImpl extends AbstractPromotionRuleManagerImpl implements SeckillManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private SeckillRangeManager seckillRangeManager;

    @Autowired
    private SeckillGoodsManager seckillGoodsManager;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private TimeTrigger timeTrigger;


    @Override
    public Page list(int page, int pageSize, String keywords) {

        List params = new ArrayList();

        String sql = "select * from es_seckill ";
        if (!StringUtil.isEmpty(keywords)) {
            sql += " where seckill_name like ?";
            params.add("%" + keywords + "%");
        }
        sql += " order by start_day desc";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, SeckillDO.class, params.toArray());

        List<SeckillVO> seckillVOList = new ArrayList<>();
        List<SeckillDO> seckillDOList = webPage.getData();
        for (SeckillDO seckillDO : seckillDOList) {

            List<Integer> rangeList = new ArrayList<>();
            List<SeckillRangeDO> rangeDOList = this.seckillRangeManager.getList(seckillDO.getSeckillId());
            for (SeckillRangeDO rangeDO : rangeDOList) {
                rangeList.add(rangeDO.getRangeTime());
            }

            SeckillVO seckillVO = new SeckillVO();
            BeanUtils.copyProperties(seckillDO, seckillVO);

            if (seckillVO.getSeckillStatus() != null) {
                SeckillStatusEnum statusEnum = SeckillStatusEnum.valueOf(seckillVO.getSeckillStatus());
                //如果状态是已发布状态，则判断该活动是否已开始或者已结束
                seckillVO.setSeckillStatusText(statusEnum.description());
                if (SeckillStatusEnum.RELEASE.equals(statusEnum)) {

                    //活动开始时间
                    long startDay = seckillDO.getStartDay();

                    if (DateUtil.startOfTodDay() <= startDay && DateUtil.endOfTodDay() > startDay) {
                        //不能添加限时抢购商品了
                        seckillVO.setIsApply(1);
                        seckillVO.setSeckillStatusText("已开启");
                    } else if (startDay < DateUtil.endOfTodDay()) {
                        //已经关闭了
                        seckillVO.setIsApply(2);
                        seckillVO.setSeckillStatusText("已关闭");
                    } else if (seckillDO.getApplyEndTime() > DateUtil.getDateline()) {
                        //可以申请
                        seckillVO.setIsApply(0);
                    }

                }
            }

            seckillVOList.add(seckillVO);
        }
        webPage.setData(seckillVOList);
        return webPage;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ServiceException.class, RuntimeException.class})
    public SeckillDTO add(SeckillDTO seckill) {

        String sql = "select * from es_seckill where seckill_name = ? ";
        List list = this.daoSupport.queryForList(sql, seckill.getSeckillName());
        if (list.size() > 0) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "活动名称重复");
        }

        String date = DateUtil.toString(seckill.getStartDay(), "yyyy-MM-dd");
        long startTime = DateUtil.getDateline(date + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        long endTime = DateUtil.getDateline(date + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
        this.verifyTime(startTime, endTime, PromotionTypeEnum.SECKILL, null);

        SeckillDO seckillDO = new SeckillDO();
        BeanUtils.copyProperties(seckill, seckillDO);
        this.daoSupport.insert(seckillDO);

        Integer id = this.daoSupport.getLastId("es_seckill");
        seckill.setSeckillId(id);

        this.seckillRangeManager.addList(seckill.getRangeList(), id);
        return seckill;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ServiceException.class})
    public SeckillDTO edit(SeckillDTO seckill, Integer id) {

        String sql = "select * from es_seckill where seckill_name = ? and seckill_id != ? ";
        List list = this.daoSupport.queryForList(sql, seckill.getSeckillName(), id);
        if (list.size() > 0) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "活动名称重复");
        }

        String date = DateUtil.toString(seckill.getStartDay(), "yyyy-MM-dd");
        long startTime = DateUtil.getDateline(date + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        long endTime = DateUtil.getDateline(date + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
        this.verifyTime(startTime, endTime, PromotionTypeEnum.SECKILL, id);

        SeckillDO seckillDO = new SeckillDO();
        BeanUtils.copyProperties(seckill, seckillDO);

        this.daoSupport.update(seckillDO, id);

        this.seckillRangeManager.addList(seckill.getRangeList(), id);
        return seckill;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ServiceException.class})
    public void delete(Integer id) {

        SeckillDO seckill = this.getModel(id);

        //只要活动未开启，就可以删除活动
        String statusEnum = seckill.getSeckillStatus();
        boolean flag = false;
        if (SeckillStatusEnum.RELEASE.name().equals(statusEnum)) {
            //活动开始时间
            long startDay = seckill.getStartDay();

            if (DateUtil.startOfTodDay() > startDay) {
                flag = true;
            }
        }
        //编辑中可以删除
        if (SeckillStatusEnum.EDITING.name().equals(statusEnum)) {
            flag = true;
        }

        if (flag) {
            this.daoSupport.delete(SeckillDO.class, id);
            //将参与该活动的商品全部删除

        } else {
            throw new ServiceException(PromotionErrorCode.E400.code(), "该活动不是能删除的状态");
        }

    }

    @Override
    public SeckillDTO getModelAndRange(Integer id) {

        SeckillDO seckillDO = this.getModel(id);

        SeckillDTO seckillVO = new SeckillDTO();
        BeanUtils.copyProperties(seckillDO, seckillVO);

        //查询该限时抢购时刻表
        List<Integer> rangeList = new ArrayList<>();
        List<SeckillRangeDO> rangeDOList = this.seckillRangeManager.getList(id);
        for (SeckillRangeDO rangeDO : rangeDOList) {
            rangeList.add(rangeDO.getRangeTime());
        }
        seckillVO.setRangeList(rangeList);
        return seckillVO;
    }

    @Override
    public SeckillVO getModelAndApplys(Integer id) {
        SeckillDO seckillDO = this.getModel(id);

        SeckillVO seckillVO = new SeckillVO();
        BeanUtils.copyProperties(seckillDO, seckillVO);

        //查询该限时抢购时刻表
        List<SeckillRangeDO> rangeDOList = this.seckillRangeManager.getList(id);

        //查询该限时抢购的已经参与的商品数据
        List<SeckillApplyDO> goodsList = seckillGoodsManager.getListBySeckill(id);
        Map<Integer, List<SeckillApplyDO>> map = new HashMap<>();
        if (StringUtil.isNotEmpty(goodsList)) {
            for (SeckillApplyDO applyDO : goodsList) {
                List<SeckillApplyDO> list = map.get(applyDO.getTimeLine());
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(applyDO);
                map.put(applyDO.getTimeLine(), list);
            }
        }

        Map<Integer, List<SeckillApplyDO>> resMap = new HashMap<>();
        for (SeckillRangeDO rangeDO : rangeDOList) {
            //限时抢购时刻
            resMap.put(rangeDO.getRangeTime(),map.get(rangeDO.getRangeTime()));
        }
        seckillVO.setRangeList(resMap);
        return seckillVO;
    }

    @Override
    public SeckillDO getModel(Integer id) {

        SeckillDO seckillDO = this.daoSupport.queryForObject(SeckillDO.class, id);
        if (seckillDO == null) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "活动不存在");
        }

        return seckillDO;
    }


    @Override
    public SeckillGoodsVO getSeckillGoods(Integer goodsId) {

        Map<Integer, List<SeckillGoodsVO>> map = this.seckillGoodsManager.getSeckillGoodsList();
        SeckillGoodsVO goodsVO = null;
        for (Map.Entry<Integer, List<SeckillGoodsVO>> entry : map.entrySet()) {
            List<SeckillGoodsVO> list = entry.getValue();

            for (SeckillGoodsVO seckillGoods : list) {
                if (seckillGoods.getGoodsId().equals(goodsId)) {
                    goodsVO = new SeckillGoodsVO(seckillGoods, entry.getKey());
                }
            }
        }
        return goodsVO;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ServiceException.class, RuntimeException.class})
    public void reviewApply(Integer applyId) {

        String sql = "select *  from es_seckill_apply where apply_id = ?";
        SeckillApplyDO apply = this.daoSupport.queryForObject(sql, SeckillApplyDO.class, applyId);
        //申请不存在
        if (apply == null) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "不是可以审核的状态");
        }

        Map where = new HashMap(16);
        where.put("apply_id", applyId);
        this.daoSupport.update("es_seckill_apply", apply, where);
        //查询商品
        CacheGoods goods = goodsClient.getFromCache(apply.getGoodsId());
        //将审核通过的商品，存储到活动商品表和缓存中
        //促销商品表
        PromotionGoodsDO promotion = new PromotionGoodsDO();
        promotion.setTitle("限时抢购");
        promotion.setGoodsId(apply.getGoodsId());
        promotion.setPromotionType(PromotionTypeEnum.SECKILL.name());
        promotion.setActivityId(apply.getApplyId());
        promotion.setNum(apply.getSoldQuantity());
        promotion.setPrice(apply.getPrice());
        //商品活动的开始时间为当前商品的参加时间段
        int timeLine = apply.getTimeLine();
        String date = DateUtil.toString(apply.getStartDay(), "yyyy-MM-dd");
        long startTime = DateUtil.getDateline(date + " " + timeLine + ":00:00", "yyyy-MM-dd HH:mm:ss");
        long endTime = DateUtil.getDateline(date + " 23:59:59", "yyyy-MM-dd HH:mm:ss");

        promotion.setStartTime(startTime);
        promotion.setEndTime(endTime);
        this.daoSupport.insert("es_promotion_goods", promotion);
        //设置延迟加载任务，到活动开始时间后将搜索引擎中的优惠价格设置为0
        PromotionPriceDTO promotionPriceDTO = new PromotionPriceDTO();
        promotionPriceDTO.setGoodsId(apply.getGoodsId());
        promotionPriceDTO.setPrice(apply.getPrice());
        timeTrigger.add(TimeExecute.PROMOTION_EXECUTER, promotionPriceDTO, startTime, null);
        //此活动结束后将索引的优惠价格重置为0
        promotionPriceDTO.setPrice(0.0);
        timeTrigger.add(TimeExecute.PROMOTION_EXECUTER, promotionPriceDTO, endTime, null);


    }


    @Override
    public void close(Integer id) {

        SeckillDO seckill = this.getModel(id);
        if (seckill == null) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "活动不存在");
        }

        String statusEnum = seckill.getSeckillStatus();
        if (SeckillStatusEnum.RELEASE.name().equals(statusEnum)) {

            //活动开始时间
            long startDay = seckill.getStartDay();

            //已开启状态
            if (DateUtil.startOfTodDay() < startDay && DateUtil.endOfTodDay() > startDay) {
                //此时可以暂停

            }
        }

    }


}
