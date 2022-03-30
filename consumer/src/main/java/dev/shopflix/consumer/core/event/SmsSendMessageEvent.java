/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.event;


import dev.shopflix.core.base.model.vo.SmsSendVO;

/**
 * 发送短信
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月23日 上午10:26:41
 */
public interface SmsSendMessageEvent {

	/**
	 * 发送短信
	 * @param smsSendVO	短信发送vo
	 */
	void send(SmsSendVO smsSendVO);
}
