/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goodssearch.model;

import cloud.shopfly.b2c.framework.util.StringUtil;

import java.util.List;

/**
 * 选器实体
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-16 16:32:45
 */
public class SearchSelector {

	private String name;
	private String url;
	private boolean isSelected;
	private String value;
	private List<SearchSelector> otherOptions;


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		if(!StringUtil.isEmpty(url) && url.startsWith("/")){
			url= url.substring(1, url.length());
		}
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}
	public boolean getIsSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<SearchSelector> getOtherOptions() {
		return otherOptions;
	}
	public void setOtherOptions(List<SearchSelector> otherOptions) {
		this.otherOptions = otherOptions;
	}

	@Override
	public String toString() {
		return "SearchSelector{" +
				"name='" + name + '\'' +
				", url='" + url + '\'' +
				", isSelected=" + isSelected +
				", value='" + value + '\'' +
				", otherOptions=" + otherOptions +
				'}';
	}
}
