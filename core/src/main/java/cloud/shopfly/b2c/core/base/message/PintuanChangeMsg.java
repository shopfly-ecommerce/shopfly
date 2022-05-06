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
package cloud.shopfly.b2c.core.base.message;

import java.io.Serializable;

/**
 * 分类变更消息vo
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月23日 上午10:37:12
 */
public class PintuanChangeMsg implements Serializable {

	/**
	 * 拼团id
	 */
	private Integer pintuanId;

	/**
	 * 操作类型 0关闭 1开启
	 */
	private Integer optionType;

	public Integer getPintuanId() {
		return pintuanId;
	}

	public void setPintuanId(Integer pintuanId) {
		this.pintuanId = pintuanId;
	}

	public Integer getOptionType() {
		return optionType;
	}

	public void setOptionType(Integer optionType) {
		this.optionType = optionType;
	}
}
