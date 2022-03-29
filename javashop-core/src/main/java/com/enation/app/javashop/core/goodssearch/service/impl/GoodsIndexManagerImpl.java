/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.goodssearch.service.impl;

import com.enation.app.javashop.core.client.goods.GoodsClient;
import com.enation.app.javashop.core.client.goods.GoodsWordsClient;
import com.enation.app.javashop.core.goods.model.dos.CategoryDO;
import com.enation.app.javashop.core.goodssearch.model.GoodsIndex;
import com.enation.app.javashop.core.goodssearch.model.Param;
import com.enation.app.javashop.core.goodssearch.service.GoodsIndexManager;
import com.enation.app.javashop.core.goodssearch.util.HexUtil;
import com.enation.app.javashop.core.system.model.TaskProgressConstant;
import com.enation.app.javashop.core.system.model.vo.TaskProgress;
import com.enation.app.javashop.core.system.service.ProgressManager;
import com.enation.app.javashop.framework.elasticsearch.EsConfig;
import com.enation.app.javashop.framework.elasticsearch.EsSettings;
import com.enation.app.javashop.framework.logs.Debugger;
import com.enation.app.javashop.framework.logs.Logger;
import com.enation.app.javashop.framework.logs.LoggerFactory;
import com.enation.app.javashop.framework.util.StringUtil;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * es的商品索引实现
 *
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017年9月18日 上午11:41:44
 */
@Service
public class GoodsIndexManagerImpl implements GoodsIndexManager {


    @Autowired
    protected GoodsClient goodsClient;

    @Autowired
    protected ElasticsearchTemplate elasticsearchOperations;

    protected final Logger logger = LoggerFactory.getLogger(getClass());


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

            //配置文件中定义的索引名字
            String indexName = esConfig.getIndexName() + "_" + EsSettings.GOODS_INDEX_NAME;

            GoodsIndex goodsIndex = this.getSource(goods);

            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setIndexName(indexName);
            indexQuery.setType(EsSettings.GOODS_TYPE_NAME);
            indexQuery.setId(goodsIndex.getGoodsId().toString());
            indexQuery.setObject(goodsIndex);

            //审核通过且没有下架且没有删除
            boolean flag = goodsIndex.getDisabled() == 1 && goodsIndex.getMarketEnable() == 1 ;
            if (flag) {


                List<String> wordsList = toWordsList(goodsName);

                // 分词入库
                this.wordsToDb(wordsList);
            }

            elasticsearchOperations.index(indexQuery);
        } catch (Exception e) {
            logger.error("为商品["+goodsName+"]生成索引异常",e);
            debugger.log("为商品["+goodsName+"]生成索引异常", StringUtil.getStackTrace(e));
            throw new RuntimeException("为商品["+goodsName+"]生成索引异常", e);
        }

    }

    @Override
    public void updateIndex(Map goods) {

        //删除
        this.deleteIndex(goods);
        //添加
        this.addIndex(goods);

    }

    @Override
    public void deleteIndex(Map goods) {

        //配置文件中定义的索引名字
        String indexName = esConfig.getIndexName()+"_"+ EsSettings.GOODS_INDEX_NAME;
        elasticsearchOperations.delete(indexName, EsSettings.GOODS_TYPE_NAME, goods.get("goods_id").toString());

        String goodsName = goods.get("goods_name").toString();
        List<String> wordsList = toWordsList(goodsName);
        this.deleteWords(wordsList);

    }

    /**
     * 将list中的分词减一
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
     * 封装成内存需要格式数据
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

        //参数维度,已填写参数
        List<Map> params = (List<Map>) goods.get("params");
        List<Param> paramsList = this.convertParam(params);
        goodsIndex.setParams(paramsList);

        return goodsIndex;
    }

    /**
     * 获取分词结果
     *
     * @param txt
     * @return 分词list
     */
    protected List<String> toWordsList(String txt) {


        //配置文件中定义的索引名字
        String indexName = esConfig.getIndexName()+"_"+ EsSettings.GOODS_INDEX_NAME;

        List<String> list = new ArrayList<String>();

        IndicesAdminClient indicesAdminClient = elasticsearchOperations.getClient().admin().indices();
        AnalyzeRequestBuilder request = new AnalyzeRequestBuilder(indicesAdminClient, AnalyzeAction.INSTANCE, indexName, txt);
        //	分词
        request.setAnalyzer("ik_max_word");
        request.setTokenizer("ik_max_word");
        List<AnalyzeResponse.AnalyzeToken> listAnalysis = request.execute().actionGet().getTokens();
        for (AnalyzeResponse.AnalyzeToken token : listAnalysis) {
            list.add(token.getTerm());
        }
        return list;
    }

    /**
     * 转换参数
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
     * 将分词结果写入数据库
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
        //配置文件中定义的索引名字
        String indexName = esConfig.getIndexName()+"_"+ EsSettings.GOODS_INDEX_NAME;
        //删除所有的索引
        if (index == 1) {
            if (elasticsearchOperations.indexExists(indexName)) {
                //删除goods的所有索引
                DeleteQuery deleteQuery = new DeleteQuery();
                deleteQuery.setIndex(indexName);
                deleteQuery.setType(EsSettings.GOODS_TYPE_NAME);
                //删除索引
                elasticsearchOperations.delete(deleteQuery);
                //删除分词
                goodsWordsClient.delete();
            }
        }

        boolean hasError =false;

        //循环生成索引
        for (Map goods : list) {

            //如果任务停止则停止生成索引
            TaskProgress tk = progressManager.getProgress(TaskProgressConstant.GOODS_INDEX);

            if (tk != null) {

                try {
                    /** 生成索引消息 */
                    progressManager.taskUpdate(TaskProgressConstant.GOODS_INDEX, "正在生成[" + StringUtil.toString(goods.get("goods_name")) + "]");
                    /** 生成优惠价格索引 */
                    goods.put("discount_price", 0L);
                    this.addIndex(goods);
                } catch (Exception e) {
                    hasError =true;
                    logger.error( StringUtil.toString(goods.get("goods_name"))+"索引生成异常" ,e);
                }


            } else {
                return true;
            }
        }

        return hasError;
    }

    /**
     * list去重
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
