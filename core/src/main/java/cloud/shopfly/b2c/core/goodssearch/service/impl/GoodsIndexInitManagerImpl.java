package cloud.shopfly.b2c.core.goodssearch.service.impl;

import cloud.shopfly.b2c.core.goodssearch.model.MyMap;
import cloud.shopfly.b2c.core.ShopflyRunner;
import cloud.shopfly.b2c.core.goodssearch.service.GoodsIndexInitManager;
import cloud.shopfly.b2c.framework.elasticsearch.EsConfig;
import cloud.shopfly.b2c.framework.elasticsearch.EsSettings;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kingapex
 * @version 1.0
 * @description TODO
 * @data 2022/4/11 11:57
 **/
@Service
public class GoodsIndexInitManagerImpl implements GoodsIndexInitManager {

    @Autowired
    protected ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    protected EsConfig esConfig;

    @Autowired
    private MessageSender messageSender;

    protected final Logger logger = LoggerFactory.getLogger(ShopflyRunner.class);

    @Override
    public void initIndex()  {

        String indexName = esConfig.getIndexName() + "_" + EsSettings.GOODS_INDEX_NAME;

        boolean exists = elasticsearchTemplate.indexExists(indexName);
        if (!exists) {
            if (logger.isDebugEnabled()) {
                logger.debug("index not exists,create index and mapping");
            }

            elasticsearchTemplate.createIndex(indexName);
            Map goodsMapping = createGoodsMapping();
            elasticsearchTemplate.putMapping(indexName, EsSettings.GOODS_TYPE_NAME, goodsMapping);

            /** Send index generation message*/
//            this.messageSender.send(new MqMessage(AmqpExchange.INDEX_CREATE, AmqpExchange.INDEX_CREATE+"_ROUTING","1"));
//            CmsManageMsg cmsManageMsg = new CmsManageMsg();

        }else {
            if (logger.isDebugEnabled()) {
                logger.debug("index exists");
            }

        }


    }


    /**
     * Create goodsmapping
     *
     * @return
     */
    private Map createGoodsMapping() {

        Map goodsMap = new HashMap();

        goodsMap.put("goodsId", new MyMap().put("type", "long").getMap());
        goodsMap.put("goodsName", new MyMap().put("type", "text").getMap());
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


}
