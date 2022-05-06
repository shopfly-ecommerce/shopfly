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
package cloud.shopfly.b2c.core.trade.snapshot.service.impl;

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderItemsDO;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderSkuVO;
import cloud.shopfly.b2c.core.trade.order.service.OrderOperateManager;
import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.goods.model.dos.BrandDO;
import cloud.shopfly.b2c.core.goods.model.dos.CategoryDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsGalleryDO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsParamsGroupVO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSnapshotVO;
import cloud.shopfly.b2c.core.goods.model.vo.SpecValueVO;
import cloud.shopfly.b2c.core.promotion.coupon.model.dos.CouponDO;
import cloud.shopfly.b2c.core.promotion.coupon.service.CouponManager;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.promotion.tool.service.PromotionGoodsManager;
import cloud.shopfly.b2c.core.trade.snapshot.model.GoodsSnapshot;
import cloud.shopfly.b2c.core.trade.snapshot.model.SnapshotVO;
import cloud.shopfly.b2c.core.trade.snapshot.service.GoodsSnapshotManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 交易快照业务类
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-08-01 14:55:26
 */
@Service
public class GoodsSnapshotManagerImpl implements GoodsSnapshotManager {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private OrderOperateManager orderOperateManager;

    @Autowired
    private PromotionGoodsManager promotionGoodsManager;
    @Autowired
    private CouponManager couponManager;


    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_goods_snapshot  ";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, GoodsSnapshot.class);

        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public GoodsSnapshot add(GoodsSnapshot goodsSnapshot) {
        this.daoSupport.insert(goodsSnapshot);

        int id = this.daoSupport.getLastId("");

        goodsSnapshot.setSnapshotId(id);

        return goodsSnapshot;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public GoodsSnapshot edit(GoodsSnapshot goodsSnapshot, Integer id) {
        this.daoSupport.update(goodsSnapshot, id);
        return goodsSnapshot;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        this.daoSupport.delete(GoodsSnapshot.class, id);
    }

    @Override
    public GoodsSnapshot getModel(Integer id) {
        return this.daoSupport.queryForObject(GoodsSnapshot.class, id);
    }

    @Override
    public void add(OrderDO orderDO) {

        //查看订单中的商品
        List<OrderSkuVO> skuList = JsonUtil.jsonToList(orderDO.getItemsJson(), OrderSkuVO.class);
        if (skuList != null) {
            for (OrderSkuVO sku : skuList) {

                GoodsSnapshotVO snapshotGoods = goodsClient.queryGoodsSnapShotInfo(sku.getGoodsId());

                //商品的促销信息
                List<PromotionVO> promotionVOList = this.promotionGoodsManager.getPromotion(sku.getGoodsId());


                GoodsDO goods = snapshotGoods.getGoods();

                //商品的优惠券信息
                List<CouponDO> couponDOList = this.couponManager.getList();

                CategoryDO category = snapshotGoods.getCategoryDO();
                List<GoodsGalleryDO> galleryList = snapshotGoods.getGalleryList();
                List<GoodsParamsGroupVO> paramList = snapshotGoods.getParamList();
                BrandDO brand = snapshotGoods.getBrandDO();

                GoodsSnapshot snapshot = new GoodsSnapshot();
                snapshot.setGoodsId(sku.getGoodsId());
                snapshot.setName(goods.getGoodsName());
                snapshot.setSn(goods.getSn());
                snapshot.setCategoryName(category.getName());
                snapshot.setBrandName(brand == null ? "" : brand.getName());
                snapshot.setGoodsType(goods.getGoodsType());
                snapshot.setHaveSpec(goods.getHaveSpec() == null ? 0 : goods.getHaveSpec());
                snapshot.setWeight(goods.getWeight());
                snapshot.setIntro(goods.getIntro());
                snapshot.setPrice(sku.getOriginalPrice());
                snapshot.setCost(goods.getCost());
                snapshot.setMktprice(goods.getMktprice());
                snapshot.setParamsJson(JsonUtil.objectToJson(paramList));
                snapshot.setImgJson(JsonUtil.objectToJson(galleryList));
                snapshot.setPoint(goods.getPoint());
                snapshot.setCreateTime(DateUtil.getDateline());
                snapshot.setPromotionJson(JsonUtil.objectToJson(promotionVOList));
                snapshot.setCouponJson(JsonUtil.objectToJson(couponDOList));
                //添加快照
                this.add(snapshot);
                Integer snapshotId = snapshot.getSnapshotId();
                sku.setSnapshotId(snapshotId);
                //更新订单项的快照id
                String sql = "update es_order_items set snapshot_id = ? where order_sn = ? and product_id = ?";
                this.daoSupport.execute(sql, snapshotId, orderDO.getSn(), sku.getSkuId());
            }

            if (logger.isDebugEnabled()) {
                logger.debug("生成商品快照信息");
            }
            //更新订单
            orderOperateManager.updateItemJson(JsonUtil.objectToJson(skuList), orderDO.getSn());
        }
    }

    @Override
    public SnapshotVO get(Integer id) {

        GoodsSnapshot model = this.getModel(id);
        SnapshotVO snapshotVO = new SnapshotVO();
        BeanUtils.copyProperties(model, snapshotVO);

        if (model.getHaveSpec() == 1) {
            //有规格
            String sql = "select * from es_order_items where snapshot_id = ?";
            OrderItemsDO items = this.daoSupport.queryForObject(sql, OrderItemsDO.class, id);
            List<SpecValueVO> specs = JsonUtil.jsonToList(items.getSpecJson(), SpecValueVO.class);
            snapshotVO.setSpecList(specs);
        }

        return snapshotVO;
    }
}
