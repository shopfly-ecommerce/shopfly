/**
 * 
 */
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
package cloud.shopfly.b2c.core.goodssearch.service;

import java.util.List;
import java.util.Map;

/**
 * 商品索引管理接口
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 14:11:46
 */
public interface GoodsIndexManager {
	
	/**
	 * 将某个商品加入索引<br>
	 * @param goods
	 */
	void addIndex(Map goods);
	
	/**
	 * 更新某个商品的索引
	 * @param goods
	 */
	void updateIndex(Map goods);

	
	/**
	 * 更新
	 * @param goods
	 */
	void deleteIndex(Map goods);
	
	/**
	 * 初始化索引
	 * @param list
	 * @param index
	 * @return  是否是生成成功
	 */
	boolean addAll(List<Map<String, Object>> list, int index);


}
