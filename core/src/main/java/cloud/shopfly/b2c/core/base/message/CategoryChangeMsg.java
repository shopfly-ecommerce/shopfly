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
 * Classified change messagevo
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018years3month23The morning of10:37:12
 */
public class CategoryChangeMsg implements Serializable{

	private static final long serialVersionUID = 6042345706426853823L;
	
	/**
	 * Categoriesid
	 */
	private Integer categoryId;

	/**
	 * Operation type
	 */
	private Integer operationType;

	/**
	 * add
	 */
	public final static int ADD_OPERATION = 1;

	/**
	 * edit
	 */
	public final static int UPDATE_OPERATION = 2;

	/**
	 * delete
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
