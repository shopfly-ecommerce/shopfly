/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.client.goods.impl;

import com.enation.app.javashop.core.base.message.GoodsChangeMsg;
import com.enation.app.javashop.core.base.rabbitmq.AmqpExchange;
import com.enation.app.javashop.core.client.goods.GoodsClient;
import com.enation.app.javashop.core.client.trade.ExchangeGoodsClient;
import com.enation.app.javashop.core.goods.model.dos.BrandDO;
import com.enation.app.javashop.core.goods.model.dos.CategoryDO;
import com.enation.app.javashop.core.goods.model.dos.GoodsDO;
import com.enation.app.javashop.core.goods.model.dos.GoodsGalleryDO;
import com.enation.app.javashop.core.goods.model.enums.GoodsType;
import com.enation.app.javashop.core.goods.model.vo.*;
import com.enation.app.javashop.core.goods.service.*;
import com.enation.app.javashop.core.promotion.exchange.model.dos.ExchangeDO;
import com.enation.app.javashop.core.trade.order.model.vo.OrderSkuVO;
import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.rabbitmq.MessageSender;
import com.enation.app.javashop.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@ConditionalOnProperty(value = "javashop.product", havingValue = "stand")
public class GoodsClientDefaultImpl implements GoodsClient {

    @Autowired
    private GoodsQueryManager goodsQueryManager;

    @Autowired
    private GoodsSkuManager goodsSkuManager;

    @Autowired
    @Qualifier("goodsDaoSupport")
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
