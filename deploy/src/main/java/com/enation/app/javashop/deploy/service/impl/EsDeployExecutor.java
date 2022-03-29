/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.service.impl;

import com.enation.app.javashop.deploy.model.Elasticsearch;
import com.enation.app.javashop.deploy.service.DeployExecutor;
import com.enation.app.javashop.deploy.service.ElasticsearchManager;
import com.enation.app.javashop.framework.elasticsearch.DefaultEsTemplateBuilder;
import com.enation.app.javashop.framework.elasticsearch.EsSettings;
import com.enation.app.javashop.framework.elasticsearch.EsTemplateBuilder;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kingapex on 2019-02-13.
 * elasticsearch部署执行器
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-02-13
 */
@Service
public class EsDeployExecutor implements DeployExecutor {

    @Autowired
    private ElasticsearchManager elasticsearchManager;


    @Override
    public void deploy(Integer deployId) {
        for (int i = 0; i <= 4; i++) {
            try {
                Elasticsearch elasticsearch = elasticsearchManager.getByDeployId(deployId);
                index(elasticsearch);
                break;
            } catch (NoNodeAvailableException e) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

        }

    }

    public void index( Elasticsearch elasticsearch) {
        EsTemplateBuilder esTemplateBuilder = new DefaultEsTemplateBuilder().setClusterName(elasticsearch.getClusterName()).setClusterNodes(elasticsearch.getClusterNodes());
        ElasticsearchTemplate esTemplate = esTemplateBuilder.build();

        String indexName = elasticsearch.getIndexName();

        String ptIndexName = indexName + "_" + EsSettings.PINTUAN_INDEX_NAME;
        String goodsIndexName = indexName + "_" + EsSettings.GOODS_INDEX_NAME;
        esTemplate.deleteIndex(ptIndexName);
        esTemplate.createIndex(ptIndexName);

        esTemplate.deleteIndex(goodsIndexName);
        esTemplate.createIndex(goodsIndexName);

        Map goodsMapping = createGoodsMapping();
        Map pinTuanMapping = createPingTuanMapping();


        //创建类型
        esTemplate.putMapping(goodsIndexName, EsSettings.GOODS_TYPE_NAME, goodsMapping);
        esTemplate.putMapping(ptIndexName, EsSettings.PINTUAN_TYPE_NAME, pinTuanMapping);
    }

    @Override
    public String getType() {
        return "elasticsearch";
    }


    /**
     * 创建商品mapping
     *
     * @return
     */
    private Map createGoodsMapping() {

        Map goodsMap = new HashMap();

        goodsMap.put("goodsId", new MyMap().put("type", "long").getMap());
        goodsMap.put("goodsName", new MyMap().put("type", "text").put("analyzer", "ik_max_word").getMap());
        goodsMap.put("thumbnail", new MyMap().put("type", "text").getMap());
        goodsMap.put("small", new MyMap().put("type", "text").getMap());
        goodsMap.put("buyCount", new MyMap().put("type", "integer").getMap());
        goodsMap.put("sellerId", new MyMap().put("type", "integer").getMap());
        goodsMap.put("sellerName", new MyMap().put("type", "text").getMap());
        goodsMap.put("shopCatId", new MyMap().put("type", "integer").getMap());
        goodsMap.put("shopCatPath", new MyMap().put("type", "text").getMap());
        goodsMap.put("commentNum", new MyMap().put("type", "integer").getMap());
        goodsMap.put("grade", new MyMap().put("type", "double").getMap());
        goodsMap.put("price", new MyMap().put("type", "double").getMap());
        goodsMap.put("brand", new MyMap().put("type", "integer").getMap());
        goodsMap.put("categoryId", new MyMap().put("type", "integer").getMap());
        goodsMap.put("categoryPath", new MyMap().put("type", "text").getMap());
        goodsMap.put("disabled", new MyMap().put("type", "integer").getMap());
        goodsMap.put("marketEnable", new MyMap().put("type", "integer").getMap());
        goodsMap.put("isAuth", new MyMap().put("type", "integer").getMap());
        goodsMap.put("intro", new MyMap().put("type", "text").getMap());
        goodsMap.put("selfOperated", new MyMap().put("type", "integer").getMap());

        Map paramPro = new MyMap().put("name", new MyMap().put("type", "keyword").getMap()).put("value", new MyMap().put("type", "keyword").getMap()).getMap();
        goodsMap.put("params", new MyMap().put("type", "nested").put("properties", paramPro).getMap());

        return new MyMap().put("properties", goodsMap).getMap();
    }

    /**
     * 创建拼团mapping
     *
     * @return
     */
    private Map createPingTuanMapping() {

        Map pingTuanMap = new HashMap();
        pingTuanMap.put("buyCount", new MyMap().put("type", "integer").getMap());
        pingTuanMap.put("categoryId", new MyMap().put("type", "integer").getMap());
        pingTuanMap.put("categoryPath", new MyMap().put("type", "text").getMap());
        pingTuanMap.put("goodsId", new MyMap().put("type", "long").getMap());
        pingTuanMap.put("goodsName", new MyMap().put("type", "text").put("analyzer", "ik_max_word").getMap());
        pingTuanMap.put("originPrice", new MyMap().put("type", "double").getMap());
        pingTuanMap.put("salesPrice", new MyMap().put("type", "double").getMap());
        pingTuanMap.put("skuId", new MyMap().put("type", "long").getMap());
        pingTuanMap.put("thumbnail", new MyMap().put("type", "text").getMap());
        return new MyMap().put("properties", pingTuanMap).getMap();
    }


}
