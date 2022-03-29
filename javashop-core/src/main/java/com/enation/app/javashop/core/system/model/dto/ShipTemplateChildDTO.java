/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.system.model.dto;

import com.enation.app.javashop.core.system.model.dos.ShipTemplateChild;
import com.enation.app.javashop.core.system.model.vo.ShipTemplateChildBuyerVO;

/**
 * 扩展用于与商品相关的属性
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018年8月23日 下午4:02:52 
 *
 */
public class ShipTemplateChildDTO extends ShipTemplateChild {

	public ShipTemplateChildDTO(ShipTemplateChildBuyerVO shipTemplateChild) {
		this.setFirstPrice(shipTemplateChild.getFirstPrice());
		this.setFirstCompany(shipTemplateChild.getFirstCompany());
		this.setContinuedCompany(shipTemplateChild.getContinuedCompany());
		this.setContinuedPrice(shipTemplateChild.getContinuedPrice());
	}
	
	
	/**
	 * 用于存放父类的计费方式字段
	 */
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	@Override
	public String toString() {
		return "ShipTemplateChildDTO{" +
				"type=" + type +
				"} " + super.toString();
	}
}
