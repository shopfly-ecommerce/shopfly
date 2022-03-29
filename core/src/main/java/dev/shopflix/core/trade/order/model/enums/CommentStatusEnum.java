/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.model.enums;


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
