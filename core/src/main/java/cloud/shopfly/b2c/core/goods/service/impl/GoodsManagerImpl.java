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
 * 商品业务类
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

        // 没有规格给这个字段塞0
        goodsVo.setHaveSpec(StringUtil.isNotEmpty(goodsVo.getSkuList()) ? 1 : 0);

        GoodsDO goods = new GoodsDO(goodsVo);
        // 判断是否添加的是积分商品
        if (goodsVo.getExchange() != null && goodsVo.getExchange().getEnableExchange() == 1) {
            goods.setGoodsType(GoodsType.POINT.name());
        } else {
            goods.setGoodsType(GoodsType.NORMAL.name());
        }

        // 商品状态 是否可用
        goods.setDisabled(1);
        // 商品创建时间
        goods.setCreateTime(DateUtil.getDateline());
        // 商品浏览次数
        goods.setViewCount(0);
        // 商品购买数量
        goods.setBuyCount(0);
        // 评论次数
        goods.setCommentNum(0);
        // 商品评分
        goods.setGrade(100.0);
        // 商品最后更新时间
        goods.setLastModify(DateUtil.getDateline());
        // 商品库存
        goods.setQuantity(goodsVo.getQuantity() == null ? 0 : goodsVo.getQuantity());
        // 可用库存
        goods.setEnableQuantity(goods.getQuantity());
        // 向goods加入图片
        GoodsGalleryDO goodsGalley = goodsGalleryManager
                .getGoodsGallery(goodsVo.getGoodsGalleryList().get(0).getOriginal());
        goods.setOriginal(goodsGalley.getOriginal());
        goods.setBig(goodsGalley.getBig());
        goods.setSmall(goodsGalley.getSmall());
        goods.setThumbnail(goodsGalley.getThumbnail());
        //如果有规格，则将规格中最低的价格赋值到商品价格中 update by liuyulei  2019-05-21
        if (goods.getHaveSpec() == 1) {

            this.pushGoodsPrice(goodsVo, goods);
        }
        // 添加商品
        this.daoSupport.insert(goods);
        // 获取添加商品的商品ID
        Integer goodsId = this.daoSupport.getLastId("es_goods");
        goods.setGoodsId(goodsId);
        // 添加商品参数
        this.goodsParamsManager.addParams(goodsVo.getGoodsParamsList(), goodsId);
        // 添加商品sku信息
        this.goodsSkuManager.add(goodsVo.getSkuList(), goods);
        // 添加相册
        this.goodsGalleryManager.add(goodsVo.getGoodsGalleryList(), goodsId);
        // 添加积分换购商品
        if (goods.getGoodsType().equals(GoodsType.POINT.name())) {
            PromotionGoodsDTO goodsDTO = new PromotionGoodsDTO();
            BeanUtils.copyProperties(goods, goodsDTO);
            ExchangeDO exchange = new ExchangeDO();
            BeanUtils.copyProperties(goodsVo.getExchange(), exchange);
            //校验积分兑换的价格不能高于商品销售价
            if (exchange.getExchangeMoney() > goods.getPrice()) {
                throw new ServiceException(GoodsErrorCode.E301.code(), "积分商品价格不能高于商品原价");
            }
            exchangeGoodsClient.add(new ExchangeClientDTO(exchange, goodsDTO));
        }
        // 发送增加商品消息，店铺增加自身商品数量，静态页使用
        GoodsChangeMsg goodsChangeMsg = new GoodsChangeMsg(new Integer[]{goods.getGoodsId()},
                GoodsChangeMsg.ADD_OPERATION);

        this.messageSender.send(new MqMessage(AmqpExchange.GOODS_CHANGE, AmqpExchange.GOODS_CHANGE + "_ROUTING", goodsChangeMsg));

        return goods;
    }

    @Override
    public GoodsDO edit(GoodsDTO goodsVO, Integer id) {

        GoodsDO goodsDO = goodsQueryManager.getModel(id);
        if (goodsDO == null) {
            throw new ServiceException(GoodsErrorCode.E301.code(), "没有操作权限");
        }

        goodsVO.setGoodsId(id);
        GoodsDO goods = new GoodsDO(goodsVO);
        // 判断是否把商品修改成积分商品,自营店
        goods.setGoodsType(goodsVO.getExchange() != null && goodsVO.getExchange().getEnableExchange() == 1 ? GoodsType.POINT.name() : GoodsType.NORMAL.name());
        // 添加商品更新时间
        goods.setLastModify(DateUtil.getDateline());
        // 修改相册信息
        List<GoodsGalleryDO> goodsGalleys = goodsVO.getGoodsGalleryList();
        this.goodsGalleryManager.edit(goodsGalleys, goodsVO.getGoodsId());
        // 向goods加入图片
        goods.setOriginal(goodsGalleys.get(0).getOriginal());
        goods.setBig(goodsGalleys.get(0).getBig());
        goods.setSmall(goodsGalleys.get(0).getSmall());
        goods.setThumbnail(goodsGalleys.get(0).getThumbnail());
        goods.setQuantity(goodsDO.getQuantity());
        goods.setEnableQuantity(goodsDO.getEnableQuantity());

        //如果有规格，则将规格中最低的价格赋值到商品价格中 update by liuyulei  2019-05-21
        if (StringUtil.isNotEmpty(goodsVO.getSkuList())) {
            pushGoodsPrice(goodsVO, goods);
        }

        // 更新商品
        this.daoSupport.update(goods, id);
        // 处理参数信息
        this.goodsParamsManager.addParams(goodsVO.getGoodsParamsList(), id);
        // 处理规格信息
        this.goodsSkuManager.edit(goodsVO.getSkuList(), goods);
        // 添加商品的积分换购信息
        PromotionGoodsDTO goodsDTO = new PromotionGoodsDTO();
        BeanUtils.copyProperties(goods, goodsDTO);
        if (goodsVO.getExchange() == null) {
            exchangeGoodsClient.edit(new ExchangeClientDTO(null, goodsDTO));
        } else {
            ExchangeDO exchange = new ExchangeDO();
            BeanUtils.copyProperties(goodsVO.getExchange(), exchange);
            if (exchange.getExchangeMoney() > goods.getPrice()) {
                throw new ServiceException(GoodsErrorCode.E301.code(), "积分商品价格不能高于商品原价");
            }
            exchangeGoodsClient.edit(new ExchangeClientDTO(exchange, goodsDTO));
        }

        //清除该商品关联的东西
        this.cleanGoodsAssociated(id, goodsVO.getMarketEnable());


        // 发送增加商品消息，店铺增加自身商品数量，静态页使用
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

        //清除相关的关联
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
        //修改最后修改时间
        term.add(DateUtil.getDateline());
        String idStr = getIdStr(goodsIds, term);
        String sql = "update  es_goods set disabled = 0 ,market_enable=0 , last_modify=?  where goods_id in (" + idStr + ")";
        this.daoSupport.execute(sql, term.toArray());

        //清除相关的关联
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
        //删除商品发送商品删除消息DEL_OPERATION
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

        //查看是否允许上架
        String sql = "select disabled,market_enable from es_goods where goods_id = ?";
        Map map = this.daoSupport.queryForMap(sql, goodsId);

        Integer disabled = (Integer) map.get("disabled");
        Integer marketEnable = (Integer) map.get("market_enable");

        OperateAllowable operateAllowable = new OperateAllowable(marketEnable, disabled);
        if (!operateAllowable.getAllowMarket()) {
            throw new ServiceException(GoodsErrorCode.E301.code(), "商品不能上架操作");
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
                // 发送商品消息变化消息
                GoodsChangeMsg goodsChangeMsg = new GoodsChangeMsg(new Integer[]{goods.getGoodsId()},
                        GoodsChangeMsg.AUTO_UPDATE_OPERATION);
                this.messageSender.send(new MqMessage(AmqpExchange.GOODS_CHANGE, AmqpExchange.GOODS_CHANGE + "_ROUTING", goodsChangeMsg));
            }
        }
    }

    /**
     * 获取商品id的拼接，且删除缓存中的商品信息
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
     * 查看商品是否属于当前登陆用户
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
                        throw new ServiceException(GoodsErrorCode.E301.code(), "存在不能删除的商品，不能操作");
                    }
                    break;
                case RECYCLE:
                    if (!operateAllowable.getAllowRecycle()) {
                        throw new ServiceException(GoodsErrorCode.E301.code(), "存在不能放入回收站的商品，不能操作");
                    }
                    break;
                case REVRET:
                    if (!operateAllowable.getAllowRevert()) {
                        throw new ServiceException(GoodsErrorCode.E301.code(), "存在不能还原的商品，不能操作");
                    }
                    break;
                case UNDER:
                    if (!operateAllowable.getAllowUnder()) {
                        throw new ServiceException(GoodsErrorCode.E301.code(), "存在不能下架的商品，不能操作");
                    }
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * 如果有规格，则将规格中最低的价格赋值到商品价格中
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
     * 清除商品的关联<br/>
     * 在商品删除、下架要进行调用
     *
     * @param goodsId
     */
    private void cleanGoodsAssociated(int goodsId, Integer markenable) {

        if (logger.isDebugEnabled()) {
            logger.debug("清除goodsid[" + goodsId + "]相关的缓存，包括促销的缓存");
        }

        this.cache.remove(CachePrefix.GOODS.getPrefix() + goodsId);

        // 删除这个商品的sku缓存(必须要在删除库中sku前先删缓存),首先查出商品对应的sku_id
        String sql = "select sku_id from es_goods_sku where goods_id = ?";
        List<Map> skuIds = this.daoSupport.queryForList(sql, goodsId);
        for (Map map : skuIds) {
            cache.remove(CachePrefix.SKU.getPrefix() + map.get("sku_id"));
        }

        //不再读一次缓存竟然清不掉？？所以在这里又读了一下
        this.cache.get(CachePrefix.GOODS.getPrefix() + goodsId);

        //删除该商品关联的活动缓存
        long currTime = DateUtil.getDateline();
        String currDate = DateUtil.toString(currTime, "yyyyMMdd");

        //清除此商品的缓存
        this.cache.remove(CachePrefix.PROMOTION_KEY.getPrefix() + currDate + goodsId);

        if (markenable == 0) {
            this.deleteExchange(goodsId);
        }

    }

    /**
     * 删除积分商品
     *
     * @param goodsId
     */
    private void deleteExchange(Integer goodsId) {

        //删除积分商品
        exchangeGoodsClient.del(goodsId);
    }
}

