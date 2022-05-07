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
package cloud.shopfly.b2c.core.goods.service.impl;

import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.message.GoodsChangeMsg;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.client.member.MemberCommentClient;
import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.client.trade.ExchangeGoodsClient;
import cloud.shopfly.b2c.core.goods.GoodsErrorCode;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsGalleryDO;
import cloud.shopfly.b2c.core.goods.model.dto.ExchangeClientDTO;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsDTO;
import cloud.shopfly.b2c.core.goods.model.enums.GoodsOperate;
import cloud.shopfly.b2c.core.goods.model.enums.GoodsType;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.goods.model.vo.OperateAllowable;
import cloud.shopfly.b2c.core.goods.service.*;
import cloud.shopfly.b2c.core.member.model.vo.GoodsGrade;
import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeDO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionGoodsDTO;
import cloud.shopfly.b2c.core.goods.service.*;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Commodity business category
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-21 11:23:10
 */
@Service
public class GoodsManagerImpl implements GoodsManager {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired

    private DaoSupport daoSupport;
    @Autowired
    private GoodsGalleryManager goodsGalleryManager;
    @Autowired
    private GoodsParamsManager goodsParamsManager;
    @Autowired
    private GoodsSkuManager goodsSkuManager;
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private Cache cache;

    @Autowired
    private GoodsQueryManager goodsQueryManager;
    @Autowired
    private SettingClient settingClient;
    @Autowired
    private ExchangeGoodsClient exchangeGoodsClient;

    @Autowired
    private MemberCommentClient memberCommentClient;


    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public GoodsDO add(GoodsDTO goodsVo) {

        // There is no specification to fill this field with 0
        goodsVo.setHaveSpec(StringUtil.isNotEmpty(goodsVo.getSkuList()) ? 1 : 0);

        GoodsDO goods = new GoodsDO(goodsVo);
        // Determine whether the added goods are points
        if (goodsVo.getExchange() != null && goodsVo.getExchange().getEnableExchange() == 1) {
            goods.setGoodsType(GoodsType.POINT.name());
        } else {
            goods.setGoodsType(GoodsType.NORMAL.name());
        }

        // Whether the status of the item is available
        goods.setDisabled(1);
        // Product creation time
        goods.setCreateTime(DateUtil.getDateline());
        // Number of product views
        goods.setViewCount(0);
        // Quantity of goods purchased
        goods.setBuyCount(0);
        // Comment number
        goods.setCommentNum(0);
        // Commodity grade
        goods.setGrade(100.0);
        // Product last updated time
        goods.setLastModify(DateUtil.getDateline());
        // inventory
        goods.setQuantity(goodsVo.getQuantity() == null ? 0 : goodsVo.getQuantity());
        // Available in stock
        goods.setEnableQuantity(goods.getQuantity());
        // Add images to Goods
        GoodsGalleryDO goodsGalley = goodsGalleryManager
                .getGoodsGallery(goodsVo.getGoodsGalleryList().get(0).getOriginal());
        goods.setOriginal(goodsGalley.getOriginal());
        goods.setBig(goodsGalley.getBig());
        goods.setSmall(goodsGalley.getSmall());
        goods.setThumbnail(goodsGalley.getThumbnail());
        // If there is a specification, assign the lowest price from the specification to the commodity price
        if (goods.getHaveSpec() == 1) {

            this.pushGoodsPrice(goodsVo, goods);
        }
        // Add the goods
        this.daoSupport.insert(goods);
        // Gets the ID of the item to which the item is added
        Integer goodsId = this.daoSupport.getLastId("es_goods");
        goods.setGoodsId(goodsId);
        // Add commodity parameters
        this.goodsParamsManager.addParams(goodsVo.getGoodsParamsList(), goodsId);
        // Add product SKU information
        this.goodsSkuManager.add(goodsVo.getSkuList(), goods);
        // Add a photo album
        this.goodsGalleryManager.add(goodsVo.getGoodsGalleryList(), goodsId);
        // Add points to exchange for goods
        if (goods.getGoodsType().equals(GoodsType.POINT.name())) {
            PromotionGoodsDTO goodsDTO = new PromotionGoodsDTO();
            BeanUtils.copyProperties(goods, goodsDTO);
            ExchangeDO exchange = new ExchangeDO();
            BeanUtils.copyProperties(goodsVo.getExchange(), exchange);
            // The price of checking points shall not be higher than the selling price of goods
            if (exchange.getExchangeMoney() > goods.getPrice()) {
                throw new ServiceException(GoodsErrorCode.E301.code(), "The price of integral goods cannot be higher than the original price of goods");
            }
            exchangeGoodsClient.add(new ExchangeClientDTO(exchange, goodsDTO));
        }
        // Send to add goods message, the store to increase the number of their own goods, static page use
        GoodsChangeMsg goodsChangeMsg = new GoodsChangeMsg(new Integer[]{goods.getGoodsId()},
                GoodsChangeMsg.ADD_OPERATION);

        this.messageSender.send(new MqMessage(AmqpExchange.GOODS_CHANGE, AmqpExchange.GOODS_CHANGE + "_ROUTING", goodsChangeMsg));

        return goods;
    }

    @Override
    public GoodsDO edit(GoodsDTO goodsVO, Integer id) {

        GoodsDO goodsDO = goodsQueryManager.getModel(id);
        if (goodsDO == null) {
            throw new ServiceException(GoodsErrorCode.E301.code(), "No operation permission");
        }

        goodsVO.setGoodsId(id);
        GoodsDO goods = new GoodsDO(goodsVO);
        // Judge whether to modify the goods into integral goods, self-owned stores
        goods.setGoodsType(goodsVO.getExchange() != null && goodsVO.getExchange().getEnableExchange() == 1 ? GoodsType.POINT.name() : GoodsType.NORMAL.name());
        // Add product update time
        goods.setLastModify(DateUtil.getDateline());
        // Modifying album Information
        List<GoodsGalleryDO> goodsGalleys = goodsVO.getGoodsGalleryList();
        this.goodsGalleryManager.edit(goodsGalleys, goodsVO.getGoodsId());
        // Add images to Goods
        goods.setOriginal(goodsGalleys.get(0).getOriginal());
        goods.setBig(goodsGalleys.get(0).getBig());
        goods.setSmall(goodsGalleys.get(0).getSmall());
        goods.setThumbnail(goodsGalleys.get(0).getThumbnail());
        goods.setQuantity(goodsDO.getQuantity());
        goods.setEnableQuantity(goodsDO.getEnableQuantity());

        // If there is a specification, assign the lowest price from the specification to the commodity price
        if (StringUtil.isNotEmpty(goodsVO.getSkuList())) {
            pushGoodsPrice(goodsVO, goods);
        }

        // Update the goods
        this.daoSupport.update(goods, id);
        // Processing parameter Information
        this.goodsParamsManager.addParams(goodsVO.getGoodsParamsList(), id);
        // Processing specification information
        this.goodsSkuManager.edit(goodsVO.getSkuList(), goods);
        // Add the information of redeemable points
        PromotionGoodsDTO goodsDTO = new PromotionGoodsDTO();
        BeanUtils.copyProperties(goods, goodsDTO);
        if (goodsVO.getExchange() == null) {
            exchangeGoodsClient.edit(new ExchangeClientDTO(null, goodsDTO));
        } else {
            ExchangeDO exchange = new ExchangeDO();
            BeanUtils.copyProperties(goodsVO.getExchange(), exchange);
            if (exchange.getExchangeMoney() > goods.getPrice()) {
                throw new ServiceException(GoodsErrorCode.E301.code(), "The price of integral goods cannot be higher than the original price of goods");
            }
            exchangeGoodsClient.edit(new ExchangeClientDTO(exchange, goodsDTO));
        }

        // Clear things associated with the item
        this.cleanGoodsAssociated(id, goodsVO.getMarketEnable());


        // Send to add goods message, the store to increase the number of their own goods, static page use
        GoodsChangeMsg goodsChangeMsg = new GoodsChangeMsg(new Integer[]{id}, GoodsChangeMsg.MANUAL_UPDATE_OPERATION);
        this.messageSender.send(new MqMessage(AmqpExchange.GOODS_CHANGE, AmqpExchange.GOODS_CHANGE + "_ROUTING", goodsChangeMsg));

        return goods;
    }

    @Override
    public void under(Integer[] goodsIds, String reason) {

        List<Object> term = new ArrayList<>();
        String idStr = SqlUtil.getInSql(goodsIds, term);

        this.checkPermission(goodsIds, GoodsOperate.UNDER);

        term.add(0, reason);
        term.add(1, DateUtil.getDateline());
        String sql = "update es_goods set market_enable = 0,under_message = ?, last_modify=?  where goods_id in (" + idStr + ")";
        this.daoSupport.execute(sql, term.toArray());

        // Clears associated associations
        for (int goodsId : goodsIds) {
            this.cleanGoodsAssociated(goodsId, 0);
        }

        GoodsChangeMsg goodsChangeMsg = new GoodsChangeMsg(goodsIds, GoodsChangeMsg.UNDER_OPERATION, reason);
        this.messageSender.send(new MqMessage(AmqpExchange.GOODS_CHANGE, AmqpExchange.GOODS_CHANGE + "_ROUTING", goodsChangeMsg));

    }

    @Override
    public void inRecycle(Integer[] goodsIds) {
        this.checkPermission(goodsIds, GoodsOperate.RECYCLE);

        List<Object> term = new ArrayList<>();
        // Modify the last modification time
        term.add(DateUtil.getDateline());
        String idStr = getIdStr(goodsIds, term);
        String sql = "update  es_goods set disabled = 0 ,market_enable=0 , last_modify=?  where goods_id in (" + idStr + ")";
        this.daoSupport.execute(sql, term.toArray());

        // Clears associated associations
        for (int goodsId : goodsIds) {
            this.cleanGoodsAssociated(goodsId, 0);
        }

        GoodsChangeMsg goodsChangeMsg = new GoodsChangeMsg(goodsIds, GoodsChangeMsg.INRECYCLE_OPERATION);
        this.messageSender.send(new MqMessage(AmqpExchange.GOODS_CHANGE, AmqpExchange.GOODS_CHANGE + "_ROUTING", goodsChangeMsg));


    }

    @Override
    public void delete(Integer[] goodsIds) {

        this.checkPermission(goodsIds, GoodsOperate.DELETE);

        List<Object> term = new ArrayList<>();
        String idStr = getIdStr(goodsIds, term);

        String sql = "update es_goods set disabled = -1  where goods_id in (" + idStr + ")";
        this.daoSupport.execute(sql, term.toArray());
        // Deleting a product The DEL_OPERATION message for deleting a product is sent
        GoodsChangeMsg goodsChangeMsg = new GoodsChangeMsg(goodsIds, GoodsChangeMsg.DEL_OPERATION);
        this.messageSender.send(new MqMessage(AmqpExchange.GOODS_CHANGE, AmqpExchange.GOODS_CHANGE + "_ROUTING", goodsChangeMsg));

    }

    @Override
    public void revert(Integer[] goodsIds) {

        this.checkPermission(goodsIds, GoodsOperate.REVRET);
        List<Object> term = new ArrayList<>();
        String sql = "update  es_goods set disabled = 1  where goods_id in (" + getIdStr(goodsIds, term) + ")";
        this.daoSupport.execute(sql, term.toArray());

        GoodsChangeMsg goodsChangeMsg = new GoodsChangeMsg(goodsIds, GoodsChangeMsg.REVERT_OPERATION);
        this.messageSender.send(new MqMessage(AmqpExchange.GOODS_CHANGE, AmqpExchange.GOODS_CHANGE + "_ROUTING", goodsChangeMsg));



    }

    @Override
    public void up(Integer goodsId) {

        // Check to see if shelves are allowed
        String sql = "select disabled,market_enable from es_goods where goods_id = ?";
        Map map = this.daoSupport.queryForMap(sql, goodsId);

        Integer disabled = (Integer) map.get("disabled");
        Integer marketEnable = (Integer) map.get("market_enable");

        OperateAllowable operateAllowable = new OperateAllowable(marketEnable, disabled);
        if (!operateAllowable.getAllowMarket()) {
            throw new ServiceException(GoodsErrorCode.E301.code(), "Goods can not be shelves operation");
        }

        sql = "update es_goods set market_enable = 1 and disabled = 1 where goods_id  = ?";
        this.daoSupport.execute(sql, goodsId);

        cache.remove(CachePrefix.GOODS.getPrefix() + goodsId);

        GoodsChangeMsg goodsChangeMsg = new GoodsChangeMsg(new Integer[]{goodsId}, GoodsChangeMsg.MANUAL_UPDATE_OPERATION);
        this.messageSender.send(new MqMessage(AmqpExchange.GOODS_CHANGE, AmqpExchange.GOODS_CHANGE + "_ROUTING", goodsChangeMsg));

    }

    @Override
    public GoodsDO checkShipTemplate(Integer templateId) {
        List<GoodsDO> goodsDOS = this.daoSupport.queryForList("select * from es_goods where template_id = ?", GoodsDO.class, templateId);
        if (goodsDOS != null && goodsDOS.size() > 0) {
            return goodsDOS.get(0);
        }
        return null;
    }

    @Override
    public void updateGoodsGrade() {
        List<GoodsGrade> list = this.memberCommentClient.queryGoodsGrade();

        if (StringUtil.isNotEmpty(list)) {
            for (GoodsGrade goods : list) {
                String updateSql = "update es_goods set grade=? where goods_id=?";
                double grade = CurrencyUtil.mul(goods.getGoodRate(), 100);
                this.daoSupport.execute(updateSql, CurrencyUtil.round(grade, 1), goods.getGoodsId());
                cache.put(CachePrefix.GOODS_GRADE.getPrefix() + goods.getGoodsId(), CurrencyUtil.round(grade, 1));
                // Send a commodity message change message
                GoodsChangeMsg goodsChangeMsg = new GoodsChangeMsg(new Integer[]{goods.getGoodsId()},
                        GoodsChangeMsg.AUTO_UPDATE_OPERATION);
                this.messageSender.send(new MqMessage(AmqpExchange.GOODS_CHANGE, AmqpExchange.GOODS_CHANGE + "_ROUTING", goodsChangeMsg));
            }
        }
    }

    /**
     * Access to goodsidAnd delete the item information in the cache
     *
     * @param goodsIds
     * @param term
     * @return
     */
    private String getIdStr(Integer[] goodsIds, List<Object> term) {

        String[] goods = new String[goodsIds.length];
        for (int i = 0; i < goodsIds.length; i++) {
            goods[i] = "?";
            term.add(goodsIds[i]);

        }

        return StringUtil.arrayToString(goods, ",");
    }

    /**
     * Check whether the item belongs to the current login user
     *
     * @param goodsIds
     */
    private void checkPermission(Integer[] goodsIds, GoodsOperate goodsOperate) {

        List<Object> term = new ArrayList<>();
        String idStr = SqlUtil.getInSql(goodsIds, term);

        String sql = "select disabled,market_enable from es_goods where goods_id in (" + idStr + ") ";
        List<Map> list = this.daoSupport.queryForList(sql, term.toArray());


        for (Map map : list) {
            Integer disabled = (Integer) map.get("disabled");
            Integer marketEnable = (Integer) map.get("market_enable");
            OperateAllowable operateAllowable = new OperateAllowable(marketEnable, disabled);
            switch (goodsOperate) {
                case DELETE:
                    if (!operateAllowable.getAllowDelete()) {
                        throw new ServiceException(GoodsErrorCode.E301.code(), "An item cannot be deleted");
                    }
                    break;
                case RECYCLE:
                    if (!operateAllowable.getAllowRecycle()) {
                        throw new ServiceException(GoodsErrorCode.E301.code(), "There are goods that cannot be put into the recycle bin and cannot be operated");
                    }
                    break;
                case REVRET:
                    if (!operateAllowable.getAllowRevert()) {
                        throw new ServiceException(GoodsErrorCode.E301.code(), "Unrecoverable goods exist and cannot be operated");
                    }
                    break;
                case UNDER:
                    if (!operateAllowable.getAllowUnder()) {
                        throw new ServiceException(GoodsErrorCode.E301.code(), "There are goods that cannot be removed from the shelf and cannot be operated");
                    }
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * If there is a specification, the lowest price from the specification is assigned to the commodity price
     * @param goodsVO
     * @param goods
     */
    private void pushGoodsPrice(GoodsDTO goodsVO, GoodsDO goods) {
        GoodsSkuVO sku = goodsVO.getSkuList().get(0);
        goods.setPrice(sku.getPrice());
        goods.setCost(sku.getCost());
        goods.setWeight(sku.getWeight());

        goodsVO.getSkuList().forEach(skuVo -> {
            if (skuVo.getPrice() < goods.getPrice()) {
                goods.setPrice(skuVo.getPrice());
                goods.setCost(skuVo.getCost());
                goods.setWeight(skuVo.getWeight());
            }
        });
    }

    /**
     * Clear the association of goods<br/>
     * Item Deletion、Takedown calls are made
     *
     * @param goodsId
     */
    private void cleanGoodsAssociated(int goodsId, Integer markenable) {

        if (logger.isDebugEnabled()) {
            logger.debug("removegoodsid[" + goodsId + "]Relevant caches, including promotional caches");
        }

        this.cache.remove(CachePrefix.GOODS.getPrefix() + goodsId);

        // Delete the SKU cache of this item (you must delete the cache before deleting the SKU in the database). Check the SKu_ID of the item first
        String sql = "select sku_id from es_goods_sku where goods_id = ?";
        List<Map> skuIds = this.daoSupport.queryForList(sql, goodsId);
        for (Map map : skuIds) {
            cache.remove(CachePrefix.SKU.getPrefix() + map.get("sku_id"));
        }

        // Cache cannot be cleared again? So I read it again here
        this.cache.get(CachePrefix.GOODS.getPrefix() + goodsId);

        // Deletes the active cache associated with this item
        long currTime = DateUtil.getDateline();
        String currDate = DateUtil.toString(currTime, "yyyyMMdd");

        // Clear the cache for this item
        this.cache.remove(CachePrefix.PROMOTION_KEY.getPrefix() + currDate + goodsId);

        if (markenable == 0) {
            this.deleteExchange(goodsId);
        }

    }

    /**
     * Delete integral goods
     *
     * @param goodsId
     */
    private void deleteExchange(Integer goodsId) {

        // Delete integral goods
        exchangeGoodsClient.del(goodsId);
    }
}

