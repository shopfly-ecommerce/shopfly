/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.redis.configure;

/**
 * Created by kingapex on 26/12/2017.
 *
 * Redis类型
 * @author kingapex
 * @version 1.0
 * @since 6.4.0
 * 26/12/2017
 */
public enum RedisType {

    /**
     * 集群
     */
    cluster,
    /**
     * 哨兵
     */
    sentinel,
    /**
     * 独立
     */
    standalone



}
