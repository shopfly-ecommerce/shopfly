/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.redis.configure;

import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * redis构建接口
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017年10月27日 下午2:05:40
 */
public interface IRedisBuilder {

	/**
	 * 构建连接
	 * @param  config redis配置
	 * @return
	 */
	RedisConnectionFactory buildConnectionFactory(RedisConnectionConfig config);
	
	/**
	 * 类型
	 * @return
	 */
    RedisType getType();
}
