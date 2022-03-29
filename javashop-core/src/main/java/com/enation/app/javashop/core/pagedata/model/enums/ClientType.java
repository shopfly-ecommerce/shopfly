/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.pagedata.model.enums;

/**
 * 支付客户端类型
 * @author fk
 * @version v6.4
 * @since v6.4 2017年10月17日 上午10:49:25
 */
public enum ClientType {

	/**
	 * pc客户端
	 */
	PC,
	/**
	 * MOBILE
	 */
	MOBILE;

	ClientType() {

	}

	public String value() {
		return this.name();
	}
	

}
