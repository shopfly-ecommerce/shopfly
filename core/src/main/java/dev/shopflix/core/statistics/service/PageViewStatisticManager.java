/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.statistics.service;

import dev.shopflix.core.base.SearchCriteria;
import dev.shopflix.core.statistics.model.vo.SimpleChart;

/**
 * 商家中心与平台后台，流量分析
 * 
 * @author mengyuanming
 * @version 2.0
 * @since 7.0 
 * 2018年3月19日上午8:36:28
 */
public interface PageViewStatisticManager {

	/**
	 * 平台后台 查询商品访问量
	 * 
	 * @param searchCriteria，流量参数类
	 * @return 访问流量前30的商品名及流量数据
	 */
	SimpleChart countGoods(SearchCriteria searchCriteria);

}
