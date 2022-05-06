/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.statistics.service;

import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.framework.database.Page;

/**
* 商家中心，商品收藏统计
*
* @author mengyuanming
* @version 2.0
* @since 7.0 
* 2018年4月20日下午4:23:58
*/
public interface CollectFrontStatisticsManager {
	
	/**
	 * 商品收藏图表数据
	 *
	 * @return SimpleChart，简单图表数据
	 */
	SimpleChart getChart();
	
	/**
	 * 商品收藏列表数据
	 * @param pageNo，页码
	 * @param pageSize，页面数据量
	 * @return Page，分页数据
	 */
	Page getPage(Integer pageNo, Integer pageSize);

}
