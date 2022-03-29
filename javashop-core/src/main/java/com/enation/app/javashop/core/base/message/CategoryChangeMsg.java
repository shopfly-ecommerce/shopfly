/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.base.message;

import java.io.Serializable;

/**
 * 分类变更消息vo
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月23日 上午10:37:12
 */
public class CategoryChangeMsg implements Serializable{

	private static final long serialVersionUID = 6042345706426853823L;
	
	/**
	 * 分类id
	 */
	private Integer categoryId;

	/**
	 * 操作类型
	 */
	private Integer operationType;

	/**
	 * 添加
	 */
	public final static int ADD_OPERATION = 1;

	/**
	 * 修改
	 */
	public final static int UPDATE_OPERATION = 2;

	/**
	 * 删除
	 */
	public final static int DEL_OPERATION = 3;


	public CategoryChangeMsg(Integer categoryId, Integer operationType) {
		super();
		this.categoryId = categoryId;
		this.operationType = operationType;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getOperationType() {
		return operationType;
	}

	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}

	@Override
	public String toString() {
		return "CategoryChangeMsg [categoryId=" + categoryId + ", operationType=" + operationType + "]";
	}

}
