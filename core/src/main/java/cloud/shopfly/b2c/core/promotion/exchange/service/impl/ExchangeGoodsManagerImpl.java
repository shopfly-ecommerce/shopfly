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
package cloud.shopfly.b2c.core.promotion.exchange.service.impl;

import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeDO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionDetailDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.dto.PromotionGoodsDTO;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.promotion.tool.service.PromotionGoodsManager;
import cloud.shopfly.b2c.core.promotion.tool.support.PromotionCacheKeys;
import cloud.shopfly.b2c.core.promotion.exchange.model.dto.ExchangeQueryParam;
import cloud.shopfly.b2c.core.promotion.exchange.service.ExchangeGoodsManager;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Integral commodity business class
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-21 11:47:18
 */
@Service
public class ExchangeGoodsManagerImpl implements ExchangeGoodsManager {

    @Autowired

    private DaoSupport daoSupport;

    @Autowired
    private PromotionGoodsManager promotionGoodsManager;

    @Autowired
    private Cache cache;


    @Override
    public Page list(ExchangeQueryParam param) {

        StringBuffer sql = new StringBuffer();
        List paramList = new ArrayList();
        sql.append("select e.*,pg.start_time,pg.end_time,pg.title from es_promotion_goods pg " +
                " left join es_exchange e on e.exchange_id = pg.activity_id " +
                " where pg.promotion_type = ? and exchange_id is not null ");
        paramList.add(PromotionTypeEnum.EXCHANGE.name());

        if (param.getCatId() != null) {
            sql.append(" and e.category_id = ?");
            paramList.add(param.getCatId());
        }

        if (param.getStartTime() != null && param.getEndTime() != null) {
            sql.append("  and ? > pg.start_time and ? < pg.end_time  ");
            paramList.add(param.getStartTime());
            paramList.add(param.getEndTime());
        }

        Page page = this.daoSupport.queryForPage(sql.toString(), param.getPageNo(), param.getPageSize(), ExchangeDO.class, paramList.toArray());
        return page;
    }

    @Override
    public ExchangeDO add(ExchangeDO exchangeSetting, PromotionGoodsDTO goodsDTO) {

        exchangeSetting.setGoodsId(goodsDTO.getGoodsId());
        exchangeSetting.setGoodsName(goodsDTO.getGoodsName());
        exchangeSetting.setGoodsPrice(goodsDTO.getPrice());
        exchangeSetting.setGoodsImg(goodsDTO.getThumbnail());

        if (exchangeSetting != null && exchangeSetting.getEnableExchange() == 1) {
            if (exchangeSetting.getCategoryId() == null) {
                exchangeSetting.setCategoryId(0);
            }

            this.daoSupport.insert(exchangeSetting);
            int settingId = this.daoSupport.getLastId("es_exchange");

            exchangeSetting.setExchangeId(settingId);
            long nowTime = DateUtil.getDateline();
            long endTime = DateUtil.endOfSomeDay(365);

            PromotionDetailDTO detailDTO = new PromotionDetailDTO();
            detailDTO.setTitle("Integral activities");
            detailDTO.setPromotionType(PromotionTypeEnum.EXCHANGE.name());
            detailDTO.setActivityId(settingId);
            detailDTO.setStartTime(nowTime);
            detailDTO.setEndTime(endTime);

            this.promotionGoodsManager.addModel(goodsDTO.getGoodsId(), detailDTO);
            this.cache.put(PromotionCacheKeys.getExchangeKey(settingId), exchangeSetting);
        }

        return exchangeSetting;
    }

    @Override
    public ExchangeDO edit(ExchangeDO exchangeSetting, PromotionGoodsDTO goodsDTO) {

        if (exchangeSetting != null && exchangeSetting.getEnableExchange() == 1) {
            // Delete previous information
            this.deleteByGoods(goodsDTO.getGoodsId());
            // add
            this.add(exchangeSetting, goodsDTO);

        } else {

            this.deleteByGoods(goodsDTO.getGoodsId());
        }
        return exchangeSetting;
    }

    @Override
    public void delete(Integer id) {
        // Deleting database Information
        this.daoSupport.delete(ExchangeDO.class, id);
        // Delete the active goods comparison table relationship
        this.promotionGoodsManager.delete(id, PromotionTypeEnum.EXCHANGE.name());
        // Delete the cache
        this.cache.remove(PromotionCacheKeys.getExchangeKey(id));
    }

    @Override
    public ExchangeDO getModel(Integer id) {
        ExchangeDO exchangeDO = (ExchangeDO) this.cache.get(PromotionCacheKeys.getExchangeKey(id));
        if (exchangeDO == null) {
            exchangeDO = this.daoSupport.queryForObject(ExchangeDO.class, id);
        }
        return exchangeDO;
    }

    @Override
    public ExchangeDO getModelByGoods(Integer goodsId) {
        String sql = "select * from es_exchange where goods_id = ? ";
        return this.daoSupport.queryForObject(sql, ExchangeDO.class, goodsId);
    }

    @Override
    public void deleteByGoods(Integer goodsId) {
        ExchangeDO exchangeDO = this.getModelByGoods(goodsId);
        if (exchangeDO == null) {
            return;
        }
        this.delete(exchangeDO.getExchangeId());
    }

    @Override
    public List<ExchangeDO> getModelByCategoryId(Integer categoryId) {

        String sql = "select * from es_exchange where category_id = ? ";

        return this.daoSupport.queryForList(sql, ExchangeDO.class, categoryId);
    }
}
