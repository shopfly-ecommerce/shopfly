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
package cloud.shopfly.b2c.core.goods.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 商品参数vo
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月26日 下午4:17:03
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsParamsGroupVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1450550797436233753L;
	@ApiModelProperty("参数组关联的参数集合")
	private List<GoodsParamsVO> params;

	@ApiModelProperty("参数组名称")
	private String groupName;

	@ApiModelProperty("参数组id")
	private Integer groupId;
	
	
	public List<GoodsParamsVO> getParams() {
		return params;
	}
	public void setParams(List<GoodsParamsVO> params) {
		this.params = params;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	@Override
	public String toString() {
		return "GoodsParamsGroupVO [params=" + params + ", groupName=" + groupName + ", groupId=" + groupId + "]";
	}
	
}
