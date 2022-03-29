/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.redis.configure;

/**
 * Created by kingapex on 26/12/2017.
 *
 * Redis 配置类型
 * @author kingapex
 * @version 1.0
 * @since 6.4.0
 * 26/12/2017
 */
public enum RedisConfigType {


    //手动指定
    manual,

    //通过cache cloud 的rest api来获取配置
    rest



}
