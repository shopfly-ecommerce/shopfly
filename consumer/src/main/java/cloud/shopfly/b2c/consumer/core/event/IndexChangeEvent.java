/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.consumer.core.event;

import cloud.shopfly.b2c.core.base.message.CmsManageMsg;

/**
 * 商品分类变化事件
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月23日 上午10:22:23
 */
public interface IndexChangeEvent {

	/**
	 * 创建首页
	 * @param cmsManageMsg
	 */
    void createIndexPage(CmsManageMsg cmsManageMsg);
}
