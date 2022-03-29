/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.database;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象. 包含当前页数据及分页信息如总记录数.
 *
 * @param <T> 数据类型
 * @author kingapex
 * @version v1.0
 * @since v7.0.0
 * 2017年8月15日 上午10:55:08
 */
@ApiModel(value = "数据分页对象")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Page<T> implements Serializable {

	/**
	 * 数据列表
	 */
	@ApiModelProperty(value = "列表数据")
	private List<T> data;

	/**
	 * 当前页码
	 */
	@ApiModelProperty(value = "当前页码")
	private Integer pageNo;


	/**
	 * 分页大小
	 */
	@ApiModelProperty(value = "分页大小")
	private Integer pageSize;

	/**
	 * 总计录数
	 */
	@ApiModelProperty(value = "总计录数")
	private Long dataTotal;


	/**
	 * 构造方法
	 *
	 * @param data      数据列表
	 * @param pageNo    当前页码
	 * @param pageSize  页大小
	 * @param dataTotal 总计录数
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