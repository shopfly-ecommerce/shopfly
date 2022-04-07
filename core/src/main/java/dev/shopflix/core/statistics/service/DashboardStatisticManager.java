/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.statistics.service;

import dev.shopflix.core.statistics.model.vo.ShopDashboardVO;

/**
 * 首页仪表盘
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018/6/25 10:16
 */
public interface DashboardStatisticManager {

    /**
     * 获取仪表盘数据
     *
     * @return 仪表盘数据展示类
     */
    ShopDashboardVO getData();

}