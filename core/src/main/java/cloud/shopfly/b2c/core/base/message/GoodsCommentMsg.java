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

import cloud.shopfly.b2c.core.member.model.dos.MemberComment;

import java.io.Serializable;

/**
 * 商品评论消息
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018年3月23日 上午10:37:41
 */
public class GoodsCommentMsg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8978542323509463579L;

	private MemberComment comment;

	public MemberComment getComment() {
		return comment;
	}

	public void setComment(MemberComment comment) {
		this.comment = comment;
	}
}
