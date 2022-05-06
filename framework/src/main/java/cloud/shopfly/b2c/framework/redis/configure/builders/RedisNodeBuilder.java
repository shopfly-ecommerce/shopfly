/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
