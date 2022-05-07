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
package cloud.shopfly.b2c.framework.database;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Paging object. Contains current page data and page information such as total records.
 *
 * @param <T> The data type
 * @author kingapex
 * @version v1.0
 * @since v7.0.0
 * 2017years8month15The morning of10:55:08
 */
@ApiModel(value = "Data paging object")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Page<T> implements Serializable {

	/**
	 * Data list
	 */
	@ApiModelProperty(value = "The list of data")
	private List<T> data;

	/**
	 * The current page number
	 */
	@ApiModelProperty(value = "The current page number")
	private Integer pageNo;


	/**
	 * Page size
	 */
	@ApiModelProperty(value = "Page size")
	private Integer pageSize;

	/**
	 * A total record number
	 */
	@ApiModelProperty(value = "A total record number")
	private Long dataTotal;


	/**
	 * A constructor
	 *
	 * @param data      Data list
	 * @param pageNo    The current page number
	 * @param pageSize  Page size
	 * @param dataTotal A total record number
	 */
	public Page(Integer pageNo, Long dataTotal, Integer pageSize, List<T> data) {
		this.data = data;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.dataTotal = dataTotal;
	}

	public Page() {
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getDataTotal() {
		return dataTotal;
	}

	public void setDataTotal(Long dataTotal) {
		this.dataTotal = dataTotal;
	}

	@Override
	public String toString() {
		return "Page{" +
				"data=" + data +
				", pageNo=" + pageNo +
				", pageSize=" + pageSize +
				", dataTotal=" + dataTotal +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Page<?> page = (Page<?>) o;

		if (data != null ? !data.equals(page.data) : page.data != null) {
			return false;
		}
		if (pageNo != null ? !pageNo.equals(page.pageNo) : page.pageNo != null) {
			return false;
		}
		if (pageSize != null ? !pageSize.equals(page.pageSize) : page.pageSize != null) {
			return false;
		}
		return dataTotal != null ? dataTotal.equals(page.dataTotal) : page.dataTotal == null;
	}

	@Override
	public int hashCode() {
		int result = data != null ? data.hashCode() : 0;
		result = 31 * result + (pageNo != null ? pageNo.hashCode() : 0);
		result = 31 * result + (pageSize != null ? pageSize.hashCode() : 0);
		result = 31 * result + (dataTotal != null ? dataTotal.hashCode() : 0);
		return result;
	}
}
