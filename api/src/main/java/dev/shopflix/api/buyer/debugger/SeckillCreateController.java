/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.debugger;

import dev.shopflix.core.base.rabbitmq.TimeExecute;
import dev.shopflix.core.client.goods.GoodsClient;
import dev.shopflix.core.goods.model.vo.CacheGoods;
import dev.shopflix.core.promotion.PromotionErrorCode;
import dev.shopflix.core.promotion.seckill.model.dos.SeckillApplyDO;
import dev.shopflix.core.promotion.seckill.model.dos.SeckillDO;
import dev.shopflix.core.promotion.seckill.model.dto.SeckillDTO;
import dev.shopflix.core.promotion.seckill.model.enums.SeckillStatusEnum;
import dev.shopflix.core.promotion.seckill.model.vo.SeckillGoodsVO;
import dev.shopflix.core.promotion.seckill.model.vo.SeckillVO;
import dev.shopflix.core.promotion.seckill.service.SeckillRangeManager;
import dev.shopflix.core.promotion.tool.model.dos.PromotionGoodsDO;
import dev.shopflix.core.promotion.tool.model.dto.PromotionDetailDTO;
import dev.shopflix.core.promotion.tool.model.dto.PromotionPriceDTO;
import dev.shopflix.core.promotion.tool.model.enums.PromotionTypeEnum;
import dev.shopflix.core.promotion.tool.service.PromotionGoodsManager;
import dev.shopflix.core.promotion.tool.support.PromotionCacheKeys;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.exception.SystemErrorCodeV1;
import dev.shopflix.framework.trigger.Interface.TimeTrigger;
import dev.shopflix.framework.util.DateUtil;
import dev.shopflix.framework.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@ConditionalOnProperty(value = "shopflix.debugger", havingValue = "true")
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
    @Qualifier("tradeDaoSupport")
    private DaoSupport daoSupport;

    @Autowired
    private PromotionGoodsManager promotionGoodsManager;



    @ApiOperation(value = "添加限时抢购入库", response = SeckillVO.class)
    @GetMapping
    @Transactional(value = "tradeTransactionManager",propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, ServiceException.class})
    public SeckillDTO add(@NotNull String startTime,@NotNull String endTime,@NotNull String times,@NotNull String skuIds) {
        SeckillDTO seckill = new SeckillDTO();
        seckill.setApplyEndTime(DateUtil.getDateline(endTime,"yyyy-MM-dd HH:mm:ss"));
        seckill.setStartDay(DateUtil.getDateline(startTime,"yyyy-MM-dd"));
        seckill.setSeckillName("测试" + startTime);
        seckill.setSeckillName("测试" + startTime);
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
    @Transactional(value = "tradeTransactionManager",propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class, ServiceException.class})
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
                //判断参加活动的数量和库存数量
                if (seckillApplyDO.getSoldQuantity() > goodsSkuVO.getEnableQuantity()) {
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
                    throw new ServiceException(PromotionErrorCode.E400.code(), "商品[" + goodsSkuVO.getGoodsName() + "]已经在重叠的时间段参加了团购活动，不能参加限时抢购活动");
                }
                daoSupport.insert(seckillApplyDO);
                Integer applyId = seckillApplyDO.getApplyId();
                seckillApplyDO.setApplyId(applyId);


                //参与限时抢购促销活动并且已被平台审核通过的商品集合
                List<SeckillApplyDO> goodsList = new ArrayList<>();
                //审核通过的限时抢购商品集合
                List<PromotionGoodsDO> promotionGoodsDOS = new ArrayList<>();
                Long actId = 0L;

                SeckillApplyDO apply = seckillApplyDO;

                //查询商品
                CacheGoods goods = goodsClient.getFromCache(apply.getGoodsId());
                //将审核通过的商品放入集合中
                goodsList.add(apply);

                //促销商品表
                PromotionGoodsDO promotion = new PromotionGoodsDO();
                promotion.setTitle("限时抢购");
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


                //从缓存读取限时抢购的活动的商品
                String redisKey = getRedisKey(apply.getStartDay());
                Map<Integer, List<SeckillGoodsVO>> map = this.cache.getHash(redisKey);
                //如果redis中有当前审核商品参与的限时抢购活动商品信息，就删除掉
                if (map != null && !map.isEmpty()) {
                    this.cache.remove(redisKey);
                }

                //活动信息DTO
                PromotionDetailDTO detailDTO = new PromotionDetailDTO();
                detailDTO.setActivityId(seckill.getSeckillId());
                detailDTO.setStartTime(startTime);
                detailDTO.setEndTime(endTime);
                detailDTO.setPromotionType(PromotionTypeEnum.SECKILL.name());
                detailDTO.setTitle("限时抢购");
                this.promotionGoodsManager.addModel(goodsId,detailDTO);

                //设置延迟加载任务，到活动开始时间后将搜索引擎中的优惠价格设置为0
                PromotionPriceDTO promotionPriceDTO = new PromotionPriceDTO();
                promotionPriceDTO.setGoodsId(apply.getGoodsId());
                promotionPriceDTO.setPrice(apply.getPrice());
                timeTrigger.add(TimeExecute.PROMOTION_EXECUTER, promotionPriceDTO, startTime, null);
                //此活动结束后将索引的优惠价格重置为0
                promotionPriceDTO.setPrice(0.0);
                timeTrigger.add(TimeExecute.PROMOTION_EXECUTER, promotionPriceDTO, endTime, null);

            }
        }
    }

    /**
     * 转换商品ID数据
     * @param skuIds
     * @return
     */
    private List<String> getSkuIdList(String skuIds,String split){
        if (StringUtil.isEmpty(skuIds)) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER,"参数错误");
        }
        String[] timesS = skuIds.split(split);
        List<String> list = new ArrayList<>();

        for (int i = 0; i < timesS.length; i++) {
            list.add(timesS[i]);
        }
        return list;
    }

    /**
     * 转换时刻数据结构
     * @param times
     * @return
     */
    private List<Integer> getRangeList(String times){
        if (StringUtil.isEmpty(times)) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER,"参数错误");
        }
        String[] timesS = times.split(",");
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < timesS.length; i++) {
            list.add(StringUtil.toInt(timesS[i]));
        }
        return list;
    }


    /**
     * 获取限时抢购key
     *
     * @param dateline
     * @return
     */
    private String getRedisKey(long dateline) {
        return PromotionCacheKeys.getSeckillKey(DateUtil.toString(dateline, "yyyyMMdd"));
    }

}
