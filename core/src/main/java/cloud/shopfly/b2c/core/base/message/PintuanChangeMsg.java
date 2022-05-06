/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.base.message;

import java.io.Serializable;

/**
 * 分类变更消息vo
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月23日 上午10:37:12
 */
public class PintuanChangeMsg implements Serializable {

	/**
	 * 拼团id
	 */
	private Integer pintuanId;

	/**
	 * 操作类型 0关闭 1开启
	 */
	private Integer optionType;

	public Integer getPintuanId() {
		return pintuanId;
	}

	public void setPintuanId(Integer pintuanId) {
		this.pintuanId = pintuanId;
	}

	public Integer getOptionType() {
		return optionType;
	}

	public void setOptionType(Integer optionType) {
		this.optionType = optionType;
	}
}
