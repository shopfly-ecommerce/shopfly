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
package cloud.shopfly.b2c.framework.redis.configure.builders;

import cloud.shopfly.b2c.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2019/4/17 0017 16:30
 */
public class RedisNodeBuilder {

    public static List<String> build(String nodes){
        if(StringUtil.isEmpty(nodes)){
            throw new RuntimeException("redis 配置错误：集群节点为空");
        }

        List<String> nodeList  = new ArrayList<>();

        String[] nodeAr =  nodes.split(",");
        for (String node : nodeAr ){
            String[] ipAndPort = node.split(":");
            if (ipAndPort.length < 2) {
                continue;
            }
            nodeList.add(node);
        }

        return nodeList;
    }
}
