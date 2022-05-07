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
package cloud.shopfly.b2c.api.buyer.debugger;

import cloud.shopfly.b2c.core.base.rabbitmq.TimeExecute;
import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.promotion.PromotionErrorCode;
import cloud.shopfly.b2c.core.promotion.seckill.model.dos.SeckillApplyDO;
import cloud.shopfly.b2c.core.promotion.seckill.model.dos.SeckillDO;
import cloud.shopfly.b2c.core.promotion.seckill.model.dto.SeckillDTO;
import cloud.shopfly.b2c.core.promotion.seckill.model.enums.SeckillStatusEnum;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.SeckillGoodsVO;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.SeckillVO;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillRangeManager;
import cloud.shopfly.b2c.core.promotion.tool.model.dos.PromotionGoodsDO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionDetailDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionPriceDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.service.PromotionGoodsManager;
import cloud.shopfly.b2c.core.promotion.tool.support.PromotionCacheKeys;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.exception.SystemErrorCodeV1;
import cloud.shopfly.b2c.framework.trigger.Interface.TimeTrigger;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/debugger/seckill")
@ConditionalOnProperty(value = "shopfly.debugger", havingValue = "true")
public class SeckillCreateController {

    @Autowired
    private SeckillRangeManager seckillRangeManager;

    @Autowired
    private TimeTrigger timeTrigger;


    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private Cache cache;

    @Autowired

    private DaoSupport daoSupport;

    @Autowired
    private PromotionGoodsManager promotionGoodsManager;



    @ApiOperation(value = "Add flash sale to store", response = SeckillVO.class)
    @GetMapping
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, ServiceException.class})
    public SeckillDTO add(@NotNull String startTime,@NotNull String endTime,@NotNull String times,@NotNull String skuIds) {
        SeckillDTO seckill = new SeckillDTO();
        seckill.setApplyEndTime(DateUtil.getDateline(endTime,"yyyy-MM-dd HH:mm:ss"));
        seckill.setStartDay(DateUtil.getDateline(startTime,"yyyy-MM-dd"));
        seckill.setSeckillName("test" + startTime);
        seckill.setSeckillName("test" + startTime);
        seckill.setRangeList(getRangeList(times));
        seckill.setSeckillStatus(SeckillStatusEnum.RELEASE.name());
        SeckillDO seckillDO = new SeckillDO();
        BeanUtils.copyProperties(seckill, seckillDO);
        daoSupport.insert(seckillDO);
        int id = daoSupport.getLastId("");
        seckill.setSeckillId(id);
        this.seckillRangeManager.addList(seckill.getRangeList(), id);
        addSeckillGoods(seckill,skuIds);
        return seckill;
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, ServiceException.class})
    void addSeckillGoods(SeckillDTO seckill, String skuIds){
        List<String> skuIdList = this.getSkuIdList(skuIds,";");
        for (int i = 0; i < skuIdList.size(); i++) {
            List<String> skuidList = this.getSkuIdList(skuIdList.get(i),",");
            for (String skuidStr: skuidList) {
                Integer goodsId = StringUtil.toInt(skuidStr,0);
                CacheGoods goodsSkuVO = goodsClient.getFromCache(goodsId);
                if (goodsSkuVO == null ){
                    continue;
                }

                SeckillApplyDO seckillApplyDO = new SeckillApplyDO();
                seckillApplyDO.setSeckillId(seckill.getSeckillId());
                seckillApplyDO.setGoodsId(goodsSkuVO.getGoodsId());
                seckillApplyDO.setGoodsName(goodsSkuVO.getGoodsName());
                seckillApplyDO.setOriginalPrice(goodsSkuVO.getPrice());
                seckillApplyDO.setPrice(0.01);
                seckillApplyDO.setSalesNum(0);
                seckillApplyDO.setGoodsId(goodsId);
                seckillApplyDO.setSoldQuantity(1);
                seckillApplyDO.setStartDay(seckill.getStartDay());
                seckillApplyDO.setTimeLine(seckill.getRangeList().get(i));
                // Judge the number of activities and inventory
                if (seckillApplyDO.getSoldQuantity() > goodsSkuVO.getEnableQuantity()) {
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
                String date = DateUtil.toString(seckill.getStartDay(), "yyyy-MM-dd");
                long startTime = DateUtil.getDateline(date + " " + seckillApplyDO.getTimeLine() + ":00:00", "yyyy-MM-dd HH:mm:ss");
                long endTime = DateUtil.getDateline(date + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
                String sql = "SELECT COUNT(0) FROM es_promotion_goods WHERE promotion_type = 'GROUPBUY' AND goods_id= ? AND (" +
                        "            ( start_time <= ? AND end_time >= ? ) OR \n" +
                        "            ( start_time <= ? AND end_time >= ? ) OR\n" +
                        "            ( start_time <= ? AND end_time >= ? ) OR\n" +
                        "            ( start_time >= ? AND end_time <= ? )\n" +
                        "        )";
                int count = daoSupport.queryForInt(sql,goodsId,startTime,startTime,endTime,endTime,startTime,endTime,startTime,endTime);

                if (count > 0) {
                    throw new ServiceException(PromotionErrorCode.E400.code(), "product[" + goodsSkuVO.getGoodsName() + "]You have participated in group purchase activities in overlapping periods and cannot participate in flash sale activities");
                }
                daoSupport.insert(seckillApplyDO);
                Integer applyId = seckillApplyDO.getApplyId();
                seckillApplyDO.setApplyId(applyId);


                // The collection of products that participate in the flash sale promotion and have been approved by the platform
                List<SeckillApplyDO> goodsList = new ArrayList<>();
                // A collection of approved flash sale items
                List<PromotionGoodsDO> promotionGoodsDOS = new ArrayList<>();
                Long actId = 0L;

                SeckillApplyDO apply = seckillApplyDO;

                // Query the goods
                CacheGoods goods = goodsClient.getFromCache(apply.getGoodsId());
                // Put approved items into the collection
                goodsList.add(apply);

                // Promotional list
                PromotionGoodsDO promotion = new PromotionGoodsDO();
                promotion.setTitle("flash");
                promotion.setGoodsId(apply.getGoodsId());
                promotion.setPromotionType(PromotionTypeEnum.SECKILL.name());
                promotion.setActivityId(apply.getSeckillId());
                promotion.setNum(apply.getSoldQuantity());
                promotion.setPrice(apply.getPrice());
                promotion.setStartTime(startTime);
                promotion.setEndTime(endTime);
                promotion.setPrice(seckillApplyDO.getPrice());
                promotion.setNum(seckillApplyDO.getSalesNum());
                promotionGoodsDOS.add(promotion);


                // Read flash sale active items from cache
                String redisKey = getRedisKey(apply.getStartDay());
                Map<Integer, List<SeckillGoodsVO>> map = this.cache.getHash(redisKey);
                // If redis has flash sale information for the currently reviewed item, delete it
                if (map != null && !map.isEmpty()) {
                    this.cache.remove(redisKey);
                }

                // Activity information DTO
                PromotionDetailDTO detailDTO = new PromotionDetailDTO();
                detailDTO.setActivityId(seckill.getSeckillId());
                detailDTO.setStartTime(startTime);
                detailDTO.setEndTime(endTime);
                detailDTO.setPromotionType(PromotionTypeEnum.SECKILL.name());
                detailDTO.setTitle("flash");
                this.promotionGoodsManager.addModel(goodsId,detailDTO);

                // Set the lazy load task and set the search engine discount to 0 after the start time of the activity
                PromotionPriceDTO promotionPriceDTO = new PromotionPriceDTO();
                promotionPriceDTO.setGoodsId(apply.getGoodsId());
                promotionPriceDTO.setPrice(apply.getPrice());
                timeTrigger.add(TimeExecute.PROMOTION_EXECUTER, promotionPriceDTO, startTime, null);
                // Reset the indexs discount price to 0 after this activity ends
                promotionPriceDTO.setPrice(0.0);
                timeTrigger.add(TimeExecute.PROMOTION_EXECUTER, promotionPriceDTO, endTime, null);

            }
        }
    }

    /**
     * Transformation of goodsIDdata
     * @param skuIds
     * @return
     */
    private List<String> getSkuIdList(String skuIds,String split){
        if (StringUtil.isEmpty(skuIds)) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER,"Parameter error");
        }
        String[] timesS = skuIds.split(split);
        List<String> list = new ArrayList<>();

        for (int i = 0; i < timesS.length; i++) {
            list.add(timesS[i]);
        }
        return list;
    }

    /**
     * Transform time data structures
     * @param times
     * @return
     */
    private List<Integer> getRangeList(String times){
        if (StringUtil.isEmpty(times)) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER,"Parameter error");
        }
        String[] timesS = times.split(",");
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < timesS.length; i++) {
            list.add(StringUtil.toInt(timesS[i]));
        }
        return list;
    }


    /**
     * Get a flash salekey
     *
     * @param dateline
     * @return
     */
    private String getRedisKey(long dateline) {
        return PromotionCacheKeys.getSeckillKey(DateUtil.toString(dateline, "yyyyMMdd"));
    }

}
