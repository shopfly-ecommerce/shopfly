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
 * 会员相关统计
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/4/16 下午1:54
 */

public interface MemberStatisticManager {

    /**
     * 获取新增会员数量
     *
     * @param searchCriteria
     * @return
     */
    SimpleChart getIncreaseMember(SearchCriteria searchCriteria);

    /**
     * 获取新增会员数量 表格
     *
     * @param searchCriteria
     * @return
     */
    Page getIncreaseMemberPage(SearchCriteria searchCriteria);

    /**
     * 获取会员下单量
     *
     * @param searchCriteria
     * @return
     */
    SimpleChart getMemberOrderQuantity(SearchCriteria searchCriteria);

    /**
     * 获取会员下单量 表格
     *
     * @param searchCriteria
     * @return
     */
    Page getMemberOrderQuantityPage(SearchCriteria searchCriteria);

    /**
     * 获取下单商品数量 表格
     *
     * @param searchCriteria
     * @return
     */
    SimpleChart getMemberGoodsNum(SearchCriteria searchCriteria);

    /**
     * 获取下单商品数量 表格
     *
     * @param searchCriteria
     * @return
     */
    Page getMemberGoodsNumPage(SearchCriteria searchCriteria);

    /**
     * 获取下单总金额
     *
     * @param searchCriteria
     * @return
     */
    SimpleChart getMemberMoney(SearchCriteria searchCriteria);

    /**
     * 获取下单总金额 表格
     *
     * @param searchCriteria
     * @return
     */
    Page getMemberMoneyPage(SearchCriteria searchCriteria);


}
