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
package cloud.shopfly.b2c.core.goods.model.enums;

/**
 * 商品类型枚举
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月22日 下午2:04:37
 */
public enum GoodsType {
	/**
	 * 正常商品
	 */
	NORMAL("正常商品"), 
	/**
	 * 积分商品
	 */
	POINT("积分商品");

	private String description;

	GoodsType(String description) {
		this.description = description;

	}
}
