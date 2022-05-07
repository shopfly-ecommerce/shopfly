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
package cloud.shopfly.b2c.core.trade.order.model.enums;


/**
 * Review status
 * @author kingapex
 * @version 1.0
 * @since v7.0.0
 * 2017years6month5On the afternoon9:13:55
 */
public enum CommentStatusEnum {

	/**
	 * Unfinished comments
	 */
	UNFINISHED("Unfinished comment"),

	/**
	 * Comments have been completed
	 */
	FINISHED("Comments have been completed");

	private String description;

	CommentStatusEnum(String description){
		  this.description=description;

	}

	public String description(){
		return this.description;
	}

	public String value(){
		return this.name();
	}

}
