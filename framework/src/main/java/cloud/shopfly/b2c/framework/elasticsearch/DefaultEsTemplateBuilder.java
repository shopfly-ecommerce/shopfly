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

import cloud.shopfly.b2c.framework.util.StringUtil;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kingapex on 2019-02-13.
 * defaultelasticsearchThe builder
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-02-13
 */
public class DefaultEsTemplateBuilder implements  EsTemplateBuilder {

    private String clusterName;
    private String clusterNodes;
    private String userPass;

    public DefaultEsTemplateBuilder() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    @Override
    public ElasticsearchTemplate build() {

        return new ElasticsearchTemplate(transportClient());
    }

    private TransportClient transportClient() {

        try {
            Settings.Builder settings = Settings.builder().put("cluster.name", clusterName);

            if(!StringUtil.isEmpty(userPass)&&!"''".equals(userPass)){
                settings.put("xpack.security.user", userPass);
            }

            TransportClient client = new PreBuiltXPackTransportClient(settings.build());

            Map<String, Integer> nodeMap = parseNodeIpInfo();
            for (Map.Entry<String, Integer> entry : nodeMap.entrySet()) {

                client.addTransportAddress(
                        new InetSocketTransportAddress(InetAddress.getByName(entry.getKey()), entry.getValue()));
            }

            return client;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Parsing the nodeIPinformation,Multiple nodes are separated by commas,IPAnd ports are separated by colons
     *
     * @return
     */
    private Map<String, Integer> parseNodeIpInfo() {
        String[] nodeIpInfoArr = clusterNodes.split(",");
        Map<String, Integer> map = new HashMap<>(nodeIpInfoArr.length);
        for (String ipInfo : nodeIpInfoArr) {
            String[] ipInfoArr = ipInfo.split(":");
            map.put(ipInfoArr[0], Integer.parseInt(ipInfoArr[1]));
        }
        return map;
    }



    public DefaultEsTemplateBuilder setClusterName(String clusterName) {
        this.clusterName = clusterName;
        return  this;
    }

    public DefaultEsTemplateBuilder setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
        return  this;
    }

    public DefaultEsTemplateBuilder setUserPass(String userPass) {
        this.userPass = userPass;
        return this;

    }
}
