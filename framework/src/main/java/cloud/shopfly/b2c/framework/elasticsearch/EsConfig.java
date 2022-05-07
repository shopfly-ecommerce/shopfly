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
package cloud.shopfly.b2c.framework.elasticsearch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

/**
 * Created by kingapex on 2018/7/18.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/7/18
 */
@Configuration
public class EsConfig {

    @Value("${spring.data.elasticsearch.cluster-name}")
    private String clusterName;

    @Value("${spring.data.elasticsearch.xpack.security.user:#{null}}")
    private String userPass;

    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String nodes;

    /**
     * The index name
     */
    @Value("${spring.data.elasticsearch.index-name}")
    private String indexName;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() {
        EsTemplateBuilder esTemplateBuilder = new DefaultEsTemplateBuilder().setClusterName(clusterName).setClusterNodes(nodes).setUserPass(userPass);
        return esTemplateBuilder.build();
    }


}
