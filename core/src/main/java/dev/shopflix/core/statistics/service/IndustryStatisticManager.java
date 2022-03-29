/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.statistics.service;


import dev.shopflix.core.base.SearchCriteria;
import dev.shopflix.core.statistics.model.vo.SimpleChart;
import dev.shopflix.framework.database.Page;

/**
 * 后台 行业分析
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018/4/16 下午1:53
 */
public interface IndustryStatisticManager {


    /**
     * 按分类统计下单量
     *
     * @param searchCriteria
     * @return
     */
    SimpleChart getOrderQuantity(SearchCriteria searchCriteria);

    /**
     * 按分类统计下单商品数量
     *
     * @param searchCriteria
     * @return
     */
    SimpleChart getGoodsNum(SearchCriteria searchCriteria);

    /**
     * 按分类统计下单金额
     *
     * @param searchCriteria
     * @return
     */
    SimpleChart getOrderMoney(SearchCriteria searchCriteria);

    /**
     * 概括总览
     *
     * @param searchCriteria
     * @return
     */
    Page getGeneralOverview(SearchCriteria searchCriteria);

}
