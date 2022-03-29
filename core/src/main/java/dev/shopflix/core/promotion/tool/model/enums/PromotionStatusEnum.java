/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.tool.model.enums;

/**
 * 活动状态
 * @author liushuai
 * @version v1.0
 * @since v7.0
 * 2018/12/9 下午5:00
 * @Description:
 *
 */
public enum PromotionStatusEnum {

	/**
	 * 等待
	 */
	WAIT ("等待"),

	/**
	 * 进行中
	 */
	UNDERWAY("进行中"),

	/**
	 * 结束
	 */
	END("结束");

	private String name;

	PromotionStatusEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
