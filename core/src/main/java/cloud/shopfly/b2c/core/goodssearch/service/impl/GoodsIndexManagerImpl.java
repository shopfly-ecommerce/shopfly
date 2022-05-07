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
package cloud.shopfly.b2c.core.goodssearch.service.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.client.goods.GoodsWordsClient;
import cloud.shopfly.b2c.core.goodssearch.model.GoodsIndex;
import cloud.shopfly.b2c.core.goodssearch.model.Param;
import cloud.shopfly.b2c.core.goodssearch.service.GoodsIndexManager;
import cloud.shopfly.b2c.core.goodssearch.util.HexUtil;
import cloud.shopfly.b2c.core.system.model.TaskProgressConstant;
import cloud.shopfly.b2c.core.system.model.vo.TaskProgress;
import cloud.shopfly.b2c.core.system.service.ProgressManager;
import cloud.shopfly.b2c.core.ShopflyRunner;
import cloud.shopfly.b2c.core.goods.model.dos.CategoryDO;
import cloud.shopfly.b2c.framework.elasticsearch.EsConfig;
import cloud.shopfly.b2c.framework.elasticsearch.EsSettings;
import cloud.shopfly.b2c.framework.logs.Debugger;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * esCommodity index implementation
 *
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017years9month18The morning of11:41:44
 */
@Service
public class GoodsIndexManagerImpl implements GoodsIndexManager {


    @Autowired
    protected GoodsClient goodsClient;

    @Autowired
    protected ElasticsearchTemplate elasticsearchOperations;

    protected final Logger logger = LoggerFactory.getLogger(ShopflyRunner.class);


    @Autowired
    protected EsConfig esConfig;

    @Autowired
    protected Debugger debugger;

    @Autowired
    protected GoodsWordsClient goodsWordsClient;

    @Autowired
    protected ProgressManager progressManager;

    @Override
    public void addIndex(Map goods) {
        String goodsName = goods.get("goods_name").toString();
        try {

            // The index name defined in the configuration file
            String indexName = esConfig.getIndexName() + "_" + EsSettings.GOODS_INDEX_NAME;

            GoodsIndex goodsIndex = this.getSource(goods);

            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setIndexName(indexName);
            indexQuery.setType(EsSettings.GOODS_TYPE_NAME);
            indexQuery.setId(goodsIndex.getGoodsId().toString());
            indexQuery.setObject(goodsIndex);

            // Approved and not removed and not deleted
//            boolean flag = goodsIndex.getDisabled() == 1 && goodsIndex.getMarketEnable() == 1 ;
//            if (flag) {
//
//
//                List<String> wordsList = toWordsList(goodsName);
//
//                // Participles warehousing
//                this.wordsToDb(wordsList);
//            }

            elasticsearchOperations.index(indexQuery);
            if (logger.isDebugEnabled()) {
                logger.debug("For the goods["+goodsName+"]Index generation succeeded");
            }
        } catch (Exception e) {
            logger.error("For the goods["+goodsName+"]Generate index exception",e);
            debugger.log("For the goods["+goodsName+"]Generate index exception", StringUtil.getStackTrace(e));
            throw new RuntimeException("For the goods["+goodsName+"]Generate index exception", e);
        }

    }

    @Override
    public void updateIndex(Map goods) {

        // delete
        this.deleteIndex(goods);
        // add
        this.addIndex(goods);

    }

    @Override
    public void deleteIndex(Map goods) {

        // The index name defined in the configuration file
        String indexName = esConfig.getIndexName()+"_"+ EsSettings.GOODS_INDEX_NAME;
        elasticsearchOperations.delete(indexName, EsSettings.GOODS_TYPE_NAME, goods.get("goods_id").toString());

//        String goodsName = goods.get("goods_name").toString();
//        List<String> wordsList = toWordsList(goodsName);
//        this.deleteWords(wordsList);

    }

    /**
     * willlistThe participle in minus one
     *
     * @param wordsList
     */
    protected void deleteWords(List<String> wordsList) {
        wordsList = removeDuplicate(wordsList);
        for (String words : wordsList) {
            this.goodsWordsClient.delete(words);
        }
    }

    /**
     * Encapsulation into memory requires formatted data
     *
     * @param goods
     * @return
     */
    protected GoodsIndex getSource(Map goods) {
        GoodsIndex goodsIndex = new GoodsIndex();
        goodsIndex.setGoodsId(StringUtil.toInt(goods.get("goods_id").toString(), 0));
        goodsIndex.setGoodsName(goods.get("goods_name").toString());
        goodsIndex.setThumbnail(goods.get("thumbnail") == null ? "" : goods.get("thumbnail").toString());
        goodsIndex.setSmall(goods.get("small") == null ? "" : goods.get("small").toString());
        Double p = goods.get("price") == null ? 0d : StringUtil.toDouble(goods.get("price").toString(), 0d);
        goodsIndex.setPrice(p);
        Double discountPrice = goods.get("discount_price") == null ? 0d : StringUtil.toDouble(goods.get("discount_price").toString(), 0d);
        goodsIndex.setDiscountPrice(discountPrice);
        goodsIndex.setBuyCount(goods.get("buy_count") == null ? 0 : StringUtil.toInt(goods.get("buy_count").toString(), 0));
        goodsIndex.setCommentNum(goods.get("comment_num") == null ? 0 : StringUtil.toInt(goods.get("comment_num").toString(), 0));
        goodsIndex.setGrade(goods.get("grade") == null ? 100 : StringUtil.toDouble(goods.get("grade").toString(), 100d));

        goodsIndex.setBrand(goods.get("brand_id") == null ? 0 : StringUtil.toInt(goods.get("brand_id").toString(), 0));
        goodsIndex.setCategoryId(goods.get("category_id") == null ? 0 : StringUtil.toInt(goods.get("category_id").toString(), 0));
        CategoryDO cat = goodsClient.getCategory(Integer.parseInt(goods.get("category_id").toString()));
        goodsIndex.setCategoryPath(HexUtil.encode(cat.getCategoryPath()));
        goodsIndex.setDisabled(StringUtil.toInt(goods.get("disabled").toString(), 0));
        goodsIndex.setMarketEnable(StringUtil.toInt(goods.get("market_enable").toString(), 0));
        goodsIndex.setIntro(goods.get("intro") == null ? "" : goods.get("intro").toString());
        goodsIndex.setSelfOperated(goods.get("self_operated") == null ? 0 : StringUtil.toInt(goods.get("self_operated").toString(), 0));

        // Parameter dimension, parameter has been entered
        List<Map> params = (List<Map>) goods.get("params");
        List<Param> paramsList = this.convertParam(params);
        goodsIndex.setParams(paramsList);

        return goodsIndex;
    }

    /**
     * Get word segmentation results
     *
     * @param txt
     * @return participleslist
     */
    protected List<String> toWordsList(String txt) {


        // The index name defined in the configuration file
        String indexName = esConfig.getIndexName()+"_"+ EsSettings.GOODS_INDEX_NAME;

        List<String> list = new ArrayList<String>();

        IndicesAdminClient indicesAdminClient = elasticsearchOperations.getClient().admin().indices();
        AnalyzeRequestBuilder request = new AnalyzeRequestBuilder(indicesAdminClient, AnalyzeAction.INSTANCE, indexName, txt);
        // participles
//        request.setAnalyzer("ik_max_word");
//        request.setTokenizer("ik_max_word");
        List<AnalyzeResponse.AnalyzeToken> listAnalysis = request.execute().actionGet().getTokens();
        for (AnalyzeResponse.AnalyzeToken token : listAnalysis) {
            list.add(token.getTerm());
        }
        return list;
    }

    /**
     * Transformation parameters
     *
     * @param params
     * @return
     */
    protected List<Param> convertParam(List<Map> params) {
        List<Param> paramIndices = new ArrayList<>();
        if (params != null && params.size() > 0) {

            for (Map param : params) {
                Param index = new Param();
                index.setName(param.get("param_name") == null ? "" : param.get("param_name").toString());
                index.setValue(param.get("param_value") == null ? "" : param.get("param_value").toString());
                paramIndices.add(index);
            }

        }
        return paramIndices;
    }


    /**
     * Writes the word segmentation results to the database
     *
     * @param wordsList
     */
    protected void wordsToDb(List<String> wordsList) {
        wordsList = removeDuplicate(wordsList);
        for (String words : wordsList) {
            goodsWordsClient.addWords(words);
        }
    }

    @Override
    public boolean addAll(List<Map<String, Object>> list, int index) {
        // The index name defined in the configuration file
        String indexName = esConfig.getIndexName()+"_"+ EsSettings.GOODS_INDEX_NAME;
        // Delete all indexes
        if (index == 1) {
            if (elasticsearchOperations.indexExists(indexName)) {
                // Delete all indexes of goods
                DeleteQuery deleteQuery = new DeleteQuery();
                deleteQuery.setIndex(indexName);
                deleteQuery.setType(EsSettings.GOODS_TYPE_NAME);
                // Remove the index
                elasticsearchOperations.delete(deleteQuery);
                // Delete the word
                goodsWordsClient.delete();
            }
        }

        boolean hasError =false;

        // Cyclic index generation
        for (Map goods : list) {

            // If the task stops, the index generation stops
            TaskProgress tk = progressManager.getProgress(TaskProgressConstant.GOODS_INDEX);

            if (tk != null) {

                try {
                    /** Generate index messages*/
                    progressManager.taskUpdate(TaskProgressConstant.GOODS_INDEX, "Being generated[" + StringUtil.toString(goods.get("goods_name")) + "]");
                    /** Generate an index of good prices*/
                    goods.put("discount_price", 0L);
                    this.addIndex(goods);
                } catch (Exception e) {
                    hasError =true;
                    logger.error( StringUtil.toString(goods.get("goods_name"))+"Index generation exception" ,e);
                }


            } else {
                return true;
            }
        }

        return hasError;
    }

    /**
     * listduplicate removal
     * @param list
     * @return
     */
    protected List<String> removeDuplicate(List<String> list){
        List<String> listTemp = new ArrayList();
        for (String words:list) {
            if(!listTemp.contains(words)){
                listTemp.add(words);
            }
        }
        return listTemp;
    }


}
