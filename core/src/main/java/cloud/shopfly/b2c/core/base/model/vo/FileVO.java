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
package cloud.shopfly.b2c.core.base.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 文件上传返回值封装
 * @author zh
 * @version v2.0
 * @since v7.0
 * 2018年3月19日 下午4:42:51
 */
@ApiModel
public class FileVO {
	/** 文件名称 */
	@ApiModelProperty(name="name",value="文件名称",required=true)
	private String name;
	/** 文件后缀 */
	@ApiModelProperty(name="ext",value="文件后缀",required=true)
	private String ext;
	/** url */
	@ApiModelProperty(name="url",value="图片地址",required=true)
	private String url;


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "FileVO{" +
				"name='" + name + '\'' +
				", ext='" + ext + '\'' +
				", url='" + url + '\'' +
				'}';
	}

	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}




}
