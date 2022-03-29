/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.statistics.service;

import com.enation.app.javashop.core.statistics.model.vo.ShopProfileVO;
import com.enation.app.javashop.core.statistics.model.vo.SimpleChart;

/**
 * 自营店概况
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018/5/11 19:58
 */
public interface ShopProfileStatisticsManager {

    /**
     * 店铺近30天概况
     *
     * @return ShopProfileVO 店铺概况数据
     */
    ShopProfileVO data();

    /**
     * 店铺近30天销售额
     *
     * @return SimpleChart 简单图表数据
     */
    SimpleChart chart();

}
