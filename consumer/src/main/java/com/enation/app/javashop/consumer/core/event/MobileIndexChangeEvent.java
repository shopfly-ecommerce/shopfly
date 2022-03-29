/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.consumer.core.event;

import com.enation.app.javashop.core.base.message.CmsManageMsg;

/**
 * 移动端首页变化
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月23日 上午9:50:40
 */
public interface MobileIndexChangeEvent {

	/**
	 * 创建首页
	 * @param operation
	 */
    void mobileIndexChange(CmsManageMsg operation);
}
