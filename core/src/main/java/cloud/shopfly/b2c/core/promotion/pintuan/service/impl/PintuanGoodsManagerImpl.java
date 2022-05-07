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
package cloud.shopfly.b2c.core.promotion.pintuan.service.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.promotion.model.PromotionAbnormalGoods;
import cloud.shopfly.b2c.core.promotion.pintuan.model.PinTuanGoodsVO;
import cloud.shopfly.b2c.core.promotion.pintuan.model.Pintuan;
import cloud.shopfly.b2c.core.promotion.pintuan.model.PintuanGoodsDO;
import cloud.shopfly.b2c.core.promotion.pintuan.model.PintuanGoodsDTO;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PinTuanSearchManager;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PintuanGoodsManager;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PintuanManager;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionStatusEnum;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.promotion.pintuan.exception.PintuanErrorCode;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Group commodity business class
 *
 * @author admin
 * @version vv1.0.0
 * @since vv7.1.0
 * 2019-01-22 11:20:56
 */
@Service
public class PintuanGoodsManagerImpl implements PintuanGoodsManager {


    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    
    private DaoSupport tradeDaoSupport;


    @Autowired
    private PinTuanSearchManager pinTuanSearchManager;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private PintuanManager pintuanManager;

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_pintuan_goods ";
        Page webPage = this.tradeDaoSupport.queryForPage(sql, page, pageSize, PintuanGoodsDO.class);

        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PintuanGoodsDO add(PintuanGoodsDO pintuanGoods) {

        this.tradeDaoSupport.insert(pintuanGoods);
        if (logger.isDebugEnabled()) {
            logger.debug("Goods will be grouped" + pintuanGoods + "Write to database");
        }

        return pintuanGoods;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void save(Integer pintuanId, List<PintuanGoodsDO> pintuanGoodsList) {

        Pintuan pintuan = pintuanManager.getModel(pintuanId);


        Integer[] skuIds = new Integer[pintuanGoodsList.size()];

        for (int i = 0; i < pintuanGoodsList.size(); i++) {
            skuIds[i] = pintuanGoodsList.get(i).getSkuId();
        }
        // Group promotion
        List<PromotionAbnormalGoods> promotionAbnormalGoods = this.checkPromotion(skuIds, pintuan.getStartTime(), pintuan.getEndTime(), pintuan.getPromotionId());

        StringBuffer stringBuffer = new StringBuffer();
        if (promotionAbnormalGoods.size() > 0) {
            promotionAbnormalGoods.forEach(pGoods -> {
                stringBuffer.append("product【" + pGoods.getGoodsName() + "】To participate in【" + PromotionTypeEnum.valueOf(pGoods.getPromotionType()).getPromotionName() + "】activity,");
                stringBuffer.append("Time conflict【" + DateUtil.toString(pGoods.getStartTime(), "yyyy-MM-dd HH:mm:ss") + "~" + DateUtil.toString(pGoods.getEndTime(), "yyyy-MM-dd HH:mm:ss") + "】;");
            });
            throw new ServiceException(PintuanErrorCode.E5015.code(), stringBuffer.toString());
        }

        tradeDaoSupport.execute("delete from es_pintuan_goods where pintuan_id=?", pintuan.getPromotionId());

        pintuanGoodsList.forEach(pintuanGoodsDO -> {

            pintuanGoodsDO.setPintuanId(pintuan.getPromotionId());
            GoodsSkuVO skuVo = goodsClient.getSkuFromCache(pintuanGoodsDO.getSkuId());
            // Verify group price and original price
            if (pintuanGoodsDO.getSalesPrice() > skuVo.getPrice()) {
                throw new ServiceException(PintuanErrorCode.E5015.code(), skuVo.getGoodsName() + ",The group price of this commodity shall not be greater than the sales price of the commodity");
            }
            pintuanGoodsDO.setSellerId(skuVo.getSellerId());
            pintuanGoodsDO.setSellerName(skuVo.getSellerName());
            pintuanGoodsDO.setThumbnail(skuVo.getThumbnail());
            pintuanGoodsDO.setSpecs(skuVo.getSpecs());
            pintuanGoodsDO.setOriginPrice(skuVo.getPrice());
            pintuanGoodsDO.setGoodsId(skuVo.getGoodsId());
            pintuanGoodsDO.setGoodsName(skuVo.getGoodsName());
            pintuanGoodsDO.setLockedQuantity(0);
            pintuanGoodsDO.setSoldQuantity(0);
            pintuanGoodsDO.setSn(skuVo.getSn());
            add(pintuanGoodsDO);

        });

        // Synchronizes goods if the group activity is in progress
        if (pintuan.getStatus().equals(PromotionStatusEnum.UNDERWAY.name())) {
            // Synchronous es
            pinTuanSearchManager.syncIndexByPinTuanId(pintuanId);
        }


    }


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PintuanGoodsDO edit(PintuanGoodsDO pintuanGoods, Integer id) {
        this.tradeDaoSupport.update(pintuanGoods, id);
        return pintuanGoods;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        this.tradeDaoSupport.delete(PintuanGoodsDO.class, id);
    }

    @Override
    public PintuanGoodsDO getModel(Integer id) {
        return this.tradeDaoSupport.queryForObject(PintuanGoodsDO.class, id);
    }

    /**
     * Get group goods
     *
     * @param pintuanId
     * @param skuId
     * @return
     */
    @Override
    public PintuanGoodsDO getModel(Integer pintuanId, Integer skuId) {
        return this.tradeDaoSupport.queryForObject("select * from es_pintuan_goods where pintuan_id = ? and sku_id = ?", PintuanGoodsDO.class, pintuanId, skuId);
    }


    @Override
    public PinTuanGoodsVO getDetail(Integer skuId) {
        String sql = "select g.*,pt.required_num,pt.limit_num,pt.end_time from es_pintuan_goods g ,es_pintuan pt where g.pintuan_id= pt.promotion_id and g.sku_id=? and pt.start_time<? and pt.end_time>? ";

        Long now = DateUtil.getDateline();

        List<PinTuanGoodsVO> list = tradeDaoSupport.queryForList(sql, PinTuanGoodsVO.class, skuId, now, now);
        PinTuanGoodsVO pinTuanGoodsVO = null;
        if (list.size() > 0) {
            pinTuanGoodsVO = list.get(0);
        }
        if (pinTuanGoodsVO == null) {
            throw new ResourceNotFoundException("skuidfor" + skuId + "Group goods do not exist");
        }

        // Calculate the remaining seconds as the end time minus the current time (seconds)
        long currTime = DateUtil.getDateline();
        pinTuanGoodsVO.setTimeLeft(pinTuanGoodsVO.getEndTime() - currTime);

        return pinTuanGoodsVO;
    }

    @Override
    public List<GoodsSkuVO> skus(Integer goodsId, Integer pintuanId) {

        CacheGoods cacheGoods = goodsClient.getFromCache(goodsId);
        List<GoodsSkuVO> skuList = cacheGoods.getSkuList();
        String sql = "select sku_id from es_pintuan_goods where goods_id=? and pintuan_id = ?";
        List<Map> list = tradeDaoSupport.queryForList(sql, goodsId, pintuanId);

        // Push the skUS that participated in the group into the new list
        List<GoodsSkuVO> newList = new ArrayList<>();
        skuList.forEach(goodsSkuVO -> {
            list.forEach(map -> {
                Integer dbSkuId = (Integer) map.get("sku_id");
                Integer skuId = goodsSkuVO.getSkuId();
                if (skuId.equals(dbSkuId)) {
                    newList.add(goodsSkuVO);
                }
            });
        });

        return newList;
    }

    /**
     * Update the number of groups
     *
     * @param id  Spell mass goodsid
     * @param num Quantity
     */
    @Override
    public void addQuantity(Integer id, Integer num) {
        this.tradeDaoSupport.execute("update es_pintuan_goods set sold_quantity = sold_quantity + ? where id = ? ", num, id);
    }

    /**
     * Get all goods for an event
     *
     * @param promotionId
     * @return
     */
    @Override
    public List<PinTuanGoodsVO> all(Integer promotionId) {
        String sql = "select * from es_pintuan_goods where pintuan_id=?";
        List<PinTuanGoodsVO> pinTuanGoodsVOS = tradeDaoSupport.queryForList(sql, PinTuanGoodsVO.class, promotionId);

        pinTuanGoodsVOS.forEach(pinTuanGoodsVO -> {

            Integer skuId = pinTuanGoodsVO.getSkuId();
            GoodsSkuVO goodsSkuVO = goodsClient.getSkuFromCache(skuId);
            Integer quantity = 0;
            if (null == goodsSkuVO) {
                logger.error("error：skuThe data is empty and the related information is" + pinTuanGoodsVO.toString());
                // Cant use break, lambda can only use reburn
                // break;
                return;
            } else {
                quantity = goodsSkuVO.getEnableQuantity();
            }
            pinTuanGoodsVO.setEnableQuantity(quantity);

        });

        return pinTuanGoodsVOS;

    }

    /**
     * Close the promotional items index for an event
     *
     * @param promotionId
     */
    @Override
    public void delIndex(Integer promotionId) {
        pinTuanSearchManager.deleteByPintuanId(promotionId);
    }

    /**
     * Open an index of promotional items for an event
     *
     * @param promotionId
     */
    @Override
    public boolean addIndex(Integer promotionId) {
        List<PinTuanGoodsVO> pinTuanGoodsVOS = this.all(promotionId);

        boolean hasError = false;

        for (PinTuanGoodsVO pintuanGoods : pinTuanGoodsVOS) {
            boolean result = pinTuanSearchManager.addIndex(pintuanGoods);
            hasError = result && hasError;
        }

        return hasError;

    }


    /**
     * Goods query
     *
     * @param page        The page number
     * @param pageSize    Page size
     * @param promotionId Sales promotionid
     * @param name        Name
     * @return
     */
    @Override
    public Page page(Integer page, Integer pageSize, Integer promotionId, String name) {

        String sql = "select * from es_pintuan_goods where pintuan_id = ? ";
        List params = new ArrayList();
        params.add(promotionId);
        if (!StringUtil.isEmpty(name)) {
            sql += " and goods_name like ?";
            params.add("%" + name + "%");
        }
        Page webPage = this.tradeDaoSupport.queryForPage(sql, page, pageSize, PinTuanGoodsVO.class, params.toArray());

        return webPage;
    }

    /**
     * Queries whether you have participated in other activities within the specified time range
     *
     * @param skuIds    SKUidA collection of
     * @param startTime The start time
     * @param endTime   The end of time
     */
    @Override
    public List<PromotionAbnormalGoods> checkPromotion(Integer[] skuIds, Long startTime, Long endTime, Integer promotionId) {


        // What is a conflict
        //（1）
        // Check the time
        //                  --》【              】《--
        // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * the timeline
        // Activity already exists
        //       --》【              】《--
        // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * the timeline

        //（2）
        // Check the time
        //                  --》【              】《--
        // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * the timeline
        // Activity already exists
        //                              --》【              】《--
        // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * the timeline

        //（3）
        // Check the time
        //                  --》【              】《--
        // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * the timeline
        // Activity already exists
        //        --》【                               】《--
        // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * the timeline

        //（4）
        // Check the time
        //                  --》【              】《--
        // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * the timeline
        // Activity already exists
        //                      --》【     】《--
        // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * the timeline
        List term = new ArrayList();
        for (Integer skuid :
                skuIds) {
            term.add(skuid);
        }

        term.add(startTime);
        term.add(startTime);
        term.add(endTime);
        term.add(endTime);
        term.add(startTime);
        term.add(endTime);
        term.add(endTime);
        term.add(startTime);
        term.add(promotionId);
        // Query for promotional items outside the timeline
        List<PintuanGoodsDTO> promotionGoodsDOS = this.tradeDaoSupport.queryForList("select * from es_pintuan_goods pg left join es_pintuan p on pg.pintuan_id=p.promotion_id " +
                        " where sku_id in (" + SqlUtil.getInSql(skuIds) + ") and ( (start_time < ? and end_time > ?) or (start_time < ? and end_time > ?) or(start_time < ? and end_time > ?) or(start_time < ? and end_time > ?))" +
                        " and pg.pintuan_id != ? ",

                PintuanGoodsDTO.class, term.toArray()
        );
        List<PromotionAbnormalGoods> promotionAbnormalGoods = new ArrayList<>();
        promotionGoodsDOS.forEach(goods -> {
            PromotionAbnormalGoods paGoods = new PromotionAbnormalGoods();
            paGoods.setPromotionType(PromotionTypeEnum.PINTUAN.name());
            paGoods.setGoodsName(goods.getGoodsName());
            paGoods.setGoodsId(goods.getGoodsId());
            paGoods.setEndTime(goods.getEndTime());
            paGoods.setStartTime(goods.getStartTime());
            promotionAbnormalGoods.add(paGoods);
        });
        return promotionAbnormalGoods;
    }
}
