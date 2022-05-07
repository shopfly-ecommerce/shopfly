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
import cloud.shopfly.b2c.core.promotion.pintuan.model.PinTuanGoodsVO;
import cloud.shopfly.b2c.core.promotion.pintuan.model.PtGoodsDoc;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PinTuanSearchManager;
import cloud.shopfly.b2c.core.promotion.tool.support.SkuNameUtil;
import cloud.shopfly.b2c.core.goods.model.dos.CategoryDO;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.goodssearch.util.HexUtil;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.elasticsearch.EsConfig;
import cloud.shopfly.b2c.framework.elasticsearch.EsSettings;
import cloud.shopfly.b2c.framework.logs.Debugger;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingapex on 2019-01-21.
 * Group search business implementer
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-21
 */

@Service
public class PinTuanSearchManagerImpl implements PinTuanSearchManager {


    @Autowired
    
    private DaoSupport tradeDaoSupport;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private EsConfig esConfig;

    @Autowired
    private GoodsClient goodsClient;

    protected final Log logger = LogFactory.getLog(this.getClass());

    public PinTuanSearchManagerImpl() {
    }

    public PinTuanSearchManagerImpl(ElasticsearchTemplate elasticsearchTemplate, EsConfig esConfig) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.esConfig = esConfig;

    }

    @SuppressWarnings("Duplicates")
    @Override
    public List<PtGoodsDoc> search(Integer categoryId, Integer pageNo, Integer pageSize) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // Classification retrieval
        if (categoryId != null) {

            CategoryDO category = goodsClient.getCategory(categoryId);
            if (category != null) {
                boolQueryBuilder.must(QueryBuilders.wildcardQuery("categoryPath", HexUtil.encode(category.getCategoryPath()) + "*"));
            }

        }
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        String indexName = esConfig.getIndexName() + "_" + EsSettings.PINTUAN_INDEX_NAME;
        searchQuery.withIndices(indexName).withTypes(EsSettings.PINTUAN_TYPE_NAME).withPageable(pageable).withQuery(boolQueryBuilder);

        List<PtGoodsDoc> resultlist = elasticsearchTemplate.queryForList(searchQuery.build(), PtGoodsDoc.class);

        for (PtGoodsDoc goodsDoc : resultlist) {
            GoodsSkuVO skuVO = goodsClient.getSkuFromCache(goodsDoc.getSkuId());
            if (skuVO.getEnableQuantity() > 0) {
                goodsDoc.setIsEnableSale(true);
            } else {
                goodsDoc.setIsEnableSale(false);
            }
        }

        return resultlist;
    }

    @Override
    public void addIndex(PtGoodsDoc goodsDoc) {

        // Special processing for CAT path
        CategoryDO categoryDO = goodsClient.getCategory(goodsDoc.getCategoryId());
        goodsDoc.setCategoryPath(HexUtil.encode(categoryDO.getCategoryPath()));

        IndexQuery indexQuery = new IndexQuery();
        String indexName = esConfig.getIndexName() + "_" + EsSettings.PINTUAN_INDEX_NAME;
        indexQuery.setIndexName(indexName);
        indexQuery.setType("pintuan_goods");
        indexQuery.setId(goodsDoc.getSkuId().toString());
        indexQuery.setObject(goodsDoc);

        elasticsearchTemplate.index(indexQuery);
        if (logger.isDebugEnabled()) {
            logger.debug("Group goods will be group goodsID[" + goodsDoc.getGoodsId() + "] " + goodsDoc + " Write the index");
        }
    }

    @Autowired
    private Debugger debugger;

    @Override
    public boolean addIndex(PinTuanGoodsVO pintuanGoods) {

        String goodsName = pintuanGoods.getGoodsName() + SkuNameUtil.createSkuName(pintuanGoods.getSpecs());

        try {

            CacheGoods cacheGoods = goodsClient.getFromCache(pintuanGoods.getGoodsId());
            PtGoodsDoc ptGoodsDoc = new PtGoodsDoc();
            ptGoodsDoc.setCategoryId(cacheGoods.getCategoryId());
            ptGoodsDoc.setThumbnail(pintuanGoods.getThumbnail());
            ptGoodsDoc.setSalesPrice(pintuanGoods.getSalesPrice());
            ptGoodsDoc.setOriginPrice(pintuanGoods.getPrice());
            ptGoodsDoc.setGoodsName(goodsName);
            ptGoodsDoc.setBuyCount(pintuanGoods.getSoldQuantity());
            ptGoodsDoc.setGoodsId(pintuanGoods.getGoodsId());
            ptGoodsDoc.setSkuId(pintuanGoods.getSkuId());
            ptGoodsDoc.setPinTuanId(pintuanGoods.getPintuanId());

            this.addIndex(ptGoodsDoc);

            return true;
        } catch (Exception e) {
            logger.error("For group goods[" + goodsName + "]Error generating index", e);
            debugger.log("For group goods[" + goodsName + "]Error generating index", StringUtil.getStackTrace(e));
            return false;
        }


    }

    /**
     * toesWrite the index
     *
     * @param skuId
     */
    @Override
    public void delIndex(Integer skuId) {
        String indexName = esConfig.getIndexName() + "_" + EsSettings.PINTUAN_INDEX_NAME;

        elasticsearchTemplate.delete(indexName, EsSettings.PINTUAN_TYPE_NAME, "" + skuId);

        if (logger.isDebugEnabled()) {
            logger.debug("Goods will be groupedID[" + skuId + "]Remove the index");
        }
    }

    @Override
    public void deleteByGoodsId(Integer goodsId) {
        DeleteQuery dq = dq();
        dq.setQuery(QueryBuilders.termQuery("goodsId", goodsId));

        elasticsearchTemplate.delete(dq);
    }

    private DeleteQuery dq() {
        DeleteQuery dq = new DeleteQuery();
        String indexName = esConfig.getIndexName() + "_" + EsSettings.PINTUAN_INDEX_NAME;
        dq.setIndex(indexName);
        dq.setType(EsSettings.PINTUAN_TYPE_NAME);
        return dq;
    }

    @Override
    public void deleteByPintuanId(Integer pinTuanId) {
        DeleteQuery dq = dq();

        dq.setQuery(QueryBuilders.termQuery("pinTuanId", pinTuanId));

        elasticsearchTemplate.delete(dq);
    }

    @Override
    public void syncIndexByPinTuanId(Integer pinTuanId) {
        // Query the cluster goods in the database
        String sql = "select * from es_pintuan_goods where pintuan_id=? ";
        List<PinTuanGoodsVO> dbList = tradeDaoSupport.queryForList(sql, PinTuanGoodsVO.class, pinTuanId);

        // Query for indexed items
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        bqb.must(QueryBuilders.termQuery("pinTuanId", pinTuanId));

        List<PtGoodsDoc> esList = queryList(bqb);

        // Synchronous data
        sync(dbList, esList);

    }

    @Override
    public void syncIndexByGoodsId(Integer goodsId) {

        CacheGoods goods = goodsClient.getFromCache(goodsId);
        List<GoodsSkuVO> skuList = goods.getSkuList();

        Long now = DateUtil.getDateline();

        // Query the cluster goods in the database
        String sql = "select g.* from es_pintuan_goods g,es_pintuan pt where g.pintuan_id = pt.promotion_id and  g.goods_id=? and   pt.start_time<? and pt.end_time>?  ";
        List<PinTuanGoodsVO> dbTempList = tradeDaoSupport.queryForList(sql, PinTuanGoodsVO.class, goodsId, now, now);

        List<PinTuanGoodsVO> dbList = new ArrayList<>();

        for (PinTuanGoodsVO ptGoods : dbTempList) {
            if (findSku(ptGoods.getSkuId(), skuList)) {
                dbList.add(ptGoods);
            }
        }


        // Query the item in the index based on the item ID
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        bqb.must(QueryBuilders.termQuery("goodsId", goodsId));

        List<PtGoodsDoc> esList = queryList(bqb);
        // Synchronous data
        sync(dbList, esList);

    }


    private boolean findSku(int skuId, List<GoodsSkuVO> skuList) {
        for (GoodsSkuVO sku : skuList) {
            if (sku.getSkuId().equals(skuId)) {
                return true;
            }
        }
        return false;
    }


    /**
     * byesQuery groupskuThe list of
     *
     * @param bqb BoolQueryBuilder
     * @return
     */
    private List<PtGoodsDoc> queryList(BoolQueryBuilder bqb) {

        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
        String indexName = esConfig.getIndexName() + "_" + EsSettings.PINTUAN_INDEX_NAME;

        searchQuery.withIndices(indexName).withTypes(EsSettings.PINTUAN_TYPE_NAME).withQuery(bqb);

        List<PtGoodsDoc> esList = elasticsearchTemplate.queryForList(searchQuery.build(), PtGoodsDoc.class);

        return esList;
    }

    /**
     * Compare databases withesThe two collections, whichever is the database, are synchronizedesThe data in the
     *
     * @param dbList Database collection
     * @param esList esIn the collection
     */
    private void sync(List<PinTuanGoodsVO> dbList, List<PtGoodsDoc> esList) {

        // Loop through the database
        dbList.forEach(dbGoods -> {

            PtGoodsDoc goodsDoc = findFromList(esList, dbGoods.getSkuId());

            // I cant find it in the index
            if (goodsDoc == null) {
                this.addIndex(dbGoods);

            } else {

                // See if the price has changed
                Double salesPrice = goodsDoc.getSalesPrice();
                Double dbPrice = dbGoods.getSalesPrice();

                // The price has changed
                if (!salesPrice.equals(dbPrice)) {
                    goodsDoc.setSalesPrice(dbPrice);
                    IndexQuery indexQuery = new IndexQueryBuilder().withId("" + goodsDoc.getSkuId()).withObject(goodsDoc).build();
                    elasticsearchTemplate.index(indexQuery);
                }
            }

        });

        // The size is different, indicating that deletion may have occurred
        if (esList.size() != dbList.size()) {
            esList.forEach(goodsDoc -> {
                boolean result = findFormList(dbList, goodsDoc.getSkuId());

                // If its not found, its deleted
                if (!result) {
                    delIndex(goodsDoc.getSkuId());
                }

            });

        }

    }

    /**
     * Not in the list of query databases
     *
     * @param dbList
     * @param skuId
     * @return
     */
    private boolean findFormList(List<PinTuanGoodsVO> dbList, Integer skuId) {
        for (PinTuanGoodsVO goodsVO : dbList) {
            if (goodsVO.getSkuId().equals(skuId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Look in the index list
     *
     * @param list
     * @param skuId
     * @return
     */
    private PtGoodsDoc findFromList(List<PtGoodsDoc> list, Integer skuId) {
        for (PtGoodsDoc goodsDoc : list) {
            if (goodsDoc.getSkuId().equals(skuId)) {
                return goodsDoc;
            }
        }

        return null;
    }
}
