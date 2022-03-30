/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.core.event;

import java.util.List;

/**
 * 帮助变化
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月23日 上午10:25:08
 */
public interface HelpChangeEvent {

	/**
	 * 帮助变化
	 * @param articeids
	 */
    void helpChange(List<Integer> articeids);
}
