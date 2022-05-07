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
 * Commodity change message
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018years3month22The morning of10:05:19
 */
public class GoodsChangeMsg implements Serializable{

	private static final long serialVersionUID = 3352769927238407770L;

	/**
	 * Change resources, goodsidA collection of
	 */
	private Integer[] goodsIds;

	/**
	 * Operation type
	 */
	private Integer operationType;

	/**
	 * add
	 */
	public final static int ADD_OPERATION = 1;

	/**
	 * Manually modify
	 */
	public final static int MANUAL_UPDATE_OPERATION = 2;

	/**
	 * Automatically change
	 */
	public final static int AUTO_UPDATE_OPERATION = 9;

	/**
	 * delete
	 */
	public final static int DEL_OPERATION = 3;

	/**
	 * off
	 */
	public final static int UNDER_OPERATION = 4;

	/**
	 * reduction
	 */
	public final static int REVERT_OPERATION = 5;

	/**
	 * Put it in the recycling bin
	 */
	public final static int INRECYCLE_OPERATION = 6;

	/**
	 * The message
	 */
	private String message;

	public GoodsChangeMsg(Integer[] goodsIds, Integer operationType) {
		super();
		this.goodsIds = goodsIds;
		this.operationType = operationType;
	}

	public GoodsChangeMsg(Integer[] goodsIds, Integer operationIype, String message) {
		super();
		this.goodsIds = goodsIds;
		this.operationType = operationIype;
		this.message = message;
	}

	public Integer[] getGoodsIds() {
		return goodsIds;
	}

	public void setGoodsIds(Integer[] goodsIds) {
		this.goodsIds = goodsIds;
	}

	public Integer getOperationType() {
		return operationType;
	}

	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



}
