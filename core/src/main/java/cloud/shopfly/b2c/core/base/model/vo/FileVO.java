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
 * File upload return value encapsulation
 * @author zh
 * @version v2.0
 * @since v7.0
 * 2018years3month19On the afternoon4:42:51
 */
@ApiModel
public class FileVO {
	/** The file name*/
	@ApiModelProperty(name="name",value="The file name",required=true)
	private String name;
	/** The file suffix*/
	@ApiModelProperty(name="ext",value="The file suffix",required=true)
	private String ext;
	/** url */
	@ApiModelProperty(name="url",value="Picture address",required=true)
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
