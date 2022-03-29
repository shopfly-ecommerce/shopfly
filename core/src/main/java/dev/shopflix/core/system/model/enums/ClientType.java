/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.model.enums;

/**
 * 支付客户端类型
 * 
 * @author fk
 * @version v6.4
 * @since v6.4 2017年10月17日 上午10:49:25
 */
public enum ClientType {

	// pc端，移动wap端，移动app端
	PC("pc_enable", "PC"), WAP("wap_enable", "WAP"), APP("app_enable", "APP");

	private String description;
	private String client;

	ClientType(String description, String client) {
		this.description = description;
		this.client = client;

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
	public String description() {
		return this.description;
	}

	public String value() {
		return this.name();
	}
	

}
