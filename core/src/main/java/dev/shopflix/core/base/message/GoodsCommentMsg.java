/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.base.message;

import dev.shopflix.core.member.model.dos.MemberComment;

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
