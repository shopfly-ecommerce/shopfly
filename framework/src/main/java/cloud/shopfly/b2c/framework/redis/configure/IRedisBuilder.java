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
package cloud.shopfly.b2c.framework.redis.configure;

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
