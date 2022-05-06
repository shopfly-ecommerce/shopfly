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
package cloud.shopfly.b2c.core.goodssearch.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;

/**
 * 商品分词po
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-16 16:32:45
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsWords {

	/**
	 * 分词名称
	 */
	private String words;
	/**
	 * 约计商品数量
	 */
	private int goodsNum;
	
	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}

	public int getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}

	@Override
	public String toString() {
		return "GoodsWords{" +
				"words='" + words + '\'' +
				", goodsNum=" + goodsNum +
				'}';
	}
}
