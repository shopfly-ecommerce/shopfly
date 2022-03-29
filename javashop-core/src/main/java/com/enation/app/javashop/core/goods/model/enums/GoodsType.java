/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.goods.model.enums;

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
