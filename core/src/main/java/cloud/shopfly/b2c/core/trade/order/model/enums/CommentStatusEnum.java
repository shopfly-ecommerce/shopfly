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
 * 评论状态
 * @author kingapex
 * @version 1.0
 * @since v7.0.0
 * 2017年6月5日下午9:13:55
 */
public enum CommentStatusEnum {

	/**
	 * 未完成的评论
	 */
	UNFINISHED("未完成评论"),

	/**
	 * 已经完成评论
	 */
	FINISHED("已经完成评论");

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
