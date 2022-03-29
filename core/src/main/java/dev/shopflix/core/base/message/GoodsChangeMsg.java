/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.base.message;

import java.io.Serializable;

/**
 * 商品变化消息
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月22日 上午10:05:19
 */
public class GoodsChangeMsg implements Serializable{

	private static final long serialVersionUID = 3352769927238407770L;

	/**
	 * 变更资源，商品id集合
	 */
	private Integer[] goodsIds;

	/**
	 * 操作类型
	 */
	private Integer operationType;

	/**
	 * 添加
	 */
	public final static int ADD_OPERATION = 1;

	/**
	 * 手动修改
	 */
	public final static int MANUAL_UPDATE_OPERATION = 2;

	/**
	 * 自动修改
	 */
	public final static int AUTO_UPDATE_OPERATION = 9;

	/**
	 * 删除
	 */
	public final static int DEL_OPERATION = 3;

	/**
	 * 下架
	 */
	public final static int UNDER_OPERATION = 4;

	/**
	 * 还原
	 */
	public final static int REVERT_OPERATION = 5;

	/**
	 * 放入回收站
	 */
	public final static int INRECYCLE_OPERATION = 6;

	/**
	 * 消息
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
