/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.deploy.service.impl;

import com.enation.app.javashop.framework.elasticsearch.DefaultEsTemplateBuilder;
import com.enation.app.javashop.framework.elasticsearch.EsTemplateBuilder;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.database.Page;
import com.enation.app.javashop.deploy.model.Elasticsearch;
import com.enation.app.javashop.deploy.service.ElasticsearchManager;

/**
 * elasticsearch业务类
 *
 * @author admin
 * @version v1.0
 * @since v1.0
 * 2019-02-13 10:39:25
 */
@Service
public class ElasticsearchManagerImpl implements ElasticsearchManager {

    @Autowired
    private DaoSupport daoSupport;

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_elasticsearch  ";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, Elasticsearch.class);

        return webPage;
    }

    @Override
    @Transactional(value = "", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Elasticsearch add(Elasticsearch elasticsearch) {
        this.daoSupport.insert(elasticsearch);

        return elasticsearch;
    }

    @Override
    @Transactional(value = "", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Elasticsearch edit(Elasticsearch elasticsearch, Integer id) {
        this.daoSupport.update(elasticsearch, id);
        return elasticsearch;
    }

    @Override
    @Transactional(value = "", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        this.daoSupport.delete(Elasticsearch.class, id);
    }

    @Override
    public Elasticsearch getModel(Integer id) {
        return this.daoSupport.queryForObject(Elasticsearch.class, id);
    }

    @Override
    public Elasticsearch getByDeployId(Integer deployId) {
        String sql = "select * from es_elasticsearch  where deploy_id=?";
        return daoSupport.queryForObject(sql, Elasticsearch.class, deployId);
    }

    @Override
    public void initElasticsearch(Integer deployId) {
        Elasticsearch elasticsearch = new Elasticsearch();
        elasticsearch.setClusterName("elasticsearch-cluster");
        elasticsearch.setClusterNodes("192.168.2.2:9300,192.168.2.3:9300");
        elasticsearch.setDeployId(deployId);
        add(elasticsearch);
    }

    @Override
    public boolean testConnection(Elasticsearch elasticsearch) {
        try {
            //连接失败，则重新尝试5次
            for (int i = 0; i <= 4; i++) {
                if (testEs(elasticsearch)) {

                    return true;
                } else {

                    Thread.sleep(1000);
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 封装连接，用于多次尝试连接
     * @param elasticsearch
     * @return
     */
    private boolean testEs(Elasticsearch elasticsearch) {

        try {
            EsTemplateBuilder esTemplateBuilder = new DefaultEsTemplateBuilder().setClusterName(elasticsearch.getClusterName()).setClusterNodes(elasticsearch.getClusterNodes());
            ElasticsearchTemplate esTemplate = esTemplateBuilder.build();

            esTemplate.indexExists("test");
            return true;
        } catch (Exception e) {

            return false;
        }

    }


}
