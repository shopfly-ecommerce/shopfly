/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.base.model.vo;

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
