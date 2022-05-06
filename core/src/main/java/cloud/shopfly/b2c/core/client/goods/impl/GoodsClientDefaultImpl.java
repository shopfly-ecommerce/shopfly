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
package cloud.shopfly.b2c.core.client.goods.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.goods.model.vo.*;
import cloud.shopfly.b2c.core.goods.service.*;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderSkuVO;
import cloud.shopfly.b2c.core.base.message.GoodsChangeMsg;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.client.trade.ExchangeGoodsClient;
import cloud.shopfly.b2c.core.goods.model.dos.BrandDO;
import cloud.shopfly.b2c.core.goods.model.dos.CategoryDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsGalleryDO;
import cloud.shopfly.b2c.core.goods.model.enums.GoodsType;
import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeDO;
import cloud.shopfly.b2c.core.goods.model.vo.*;
import cloud.shopfly.b2c.core.goods.service.*;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author fk
 * @version v2.0
 * @Description: 商品对外的接口实现
 * @date 2018/7/26 10:43
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class GoodsClientDefaultImpl implements GoodsClient {

    @Autowired
    private GoodsQueryManager goodsQueryManager;

    @Autowired
    private GoodsSkuManager goodsSkuManager;

    @Autowired
    
    private DaoSupport daoSupport;
    @Autowired
    private MessageSender messageSender;

    @Autowired
    private CategoryManager categoryManager;

    @Autowired
    private GoodsManager goodsManager;

    @Autowired
    private ExchangeGoodsClient exchangeGoodsClient;

    @Autowired
    private GoodsParamsManager goodsParamsManager;

    @Autowired
    private BrandManager brandManager;

    @Autowired
    private GoodsGalleryManager goodsGalleryManager;

    @Override
    public CacheGoods getFromCache(Integer goodsId) {

        return this.goodsQueryManager.getFromCache(goodsId);
    }


    @Override
    public List<GoodsSelectLine> query(Integer[] goodsIds) {

        return this.goodsQueryManager.query(goodsIds);
    }

    @Override
    public GoodsSkuVO getSkuFromCache(Integer skuId) {

        return this.goodsSkuManager.getSkuFromCache(skuId);
    }

    @Override
    public void updateCommentCount(Integer goodsId) {
        String updateSql = "update es_goods set comment_num=comment_num + 1 where goods_id=?";
        this.daoSupport.execute(updateSql, goodsId);
        // 发送商品消息变化消息
        GoodsChangeMsg goodsChangeMsg = new GoodsChangeMsg(new Integer[]{goodsId},
                GoodsChangeMsg.AUTO_UPDATE_OPERATION);
        this.messageSender.send(new MqMessage(AmqpExchange.GOODS_CHANGE, AmqpExchange.GOODS_CHANGE + "_ROUTING", goodsChangeMsg));

    }

    @Override
    public List<Map<String, Object>> getGoods(Integer[] goodsIds) {
        return goodsQueryManager.getGoods(goodsIds);
    }

    @Override
    public void updateBuyCount(List<OrderSkuVO> list) {
        Set<Integer> set = new HashSet<>();
        for (OrderSkuVO sku : list) {
            String sql = "update es_goods set buy_count=buy_count+? where goods_id=?";
            this.daoSupport.execute(sql, sku.getNum(), sku.getGoodsId());
            set.add(sku.getGoodsId());
        }
        // 发送修改商品消息
        GoodsChangeMsg goodsChangeMsg = new GoodsChangeMsg(set.toArray(new Integer[set.size()]),
                GoodsChangeMsg.AUTO_UPDATE_OPERATION);
        this.messageSender.send(new MqMessage(AmqpExchange.GOODS_CHANGE, AmqpExchange.GOODS_CHANGE + "_ROUTING", goodsChangeMsg));

    }

    @Override
    public List<Map> queryGoodsByRange(Integer pageNo, Integer pageSize) {
        StringBuffer sqlBuffer = new StringBuffer("select g.* from es_goods  g order by goods_id desc");
        List<Map> goodsList = this.daoSupport.queryForListPage(sqlBuffer.toString(), pageNo, pageSize);
        return goodsList;
    }

    @Override
    public CategoryDO getCategory(Integer id) {

        return categoryManager.getModel(id);
    }

    @Override
    public GoodsDO checkShipTemplate(Integer templateId) {
        return goodsManager.checkShipTemplate(templateId);
    }

    @Override
    public Integer queryGoodsCountByParam(Integer status) {
        return this.goodsQueryManager.getGoodsCountByParam(status, 1);
    }

    @Override
    public List<Map<String, Object>> getGoodsAndParams(Integer[] goodsIds) {
        return this.goodsQueryManager.getGoodsAndParams(goodsIds);
    }

    @Override
    public Integer queryGoodsCount() {
        return this.goodsQueryManager.getGoodsCountByParam(null, null);
    }


    @Override
    public GoodsSnapshotVO queryGoodsSnapShotInfo(Integer goodsId) {

        //商品
        GoodsDO goods = this.goodsQueryManager.getModel(goodsId);

        //判断是否为积分商品
        if (GoodsType.POINT.name().equals(goods.getGoodsType())) {
            //积分兑换信息
            ExchangeDO exchangeDO = this.exchangeGoodsClient.getModelByGoods(goodsId);
            goods.setPoint(exchangeDO.getExchangePoint());
        }


        //参数
        List<GoodsParamsGroupVO> paramList = goodsParamsManager.queryGoodsParams(goods.getCategoryId(), goodsId);
        //品牌
        BrandDO brand = brandManager.getModel(goods.getBrandId());
        //分类
        CategoryDO category = categoryManager.getModel(goods.getCategoryId());
        //相册
        List<GoodsGalleryDO> galleryList = goodsGalleryManager.list(goodsId);

        return new GoodsSnapshotVO(goods, paramList, brand, category, galleryList);
    }

    @Override
    public void updateGoodsGrade() {

        this.goodsManager.updateGoodsGrade();
    }

}
