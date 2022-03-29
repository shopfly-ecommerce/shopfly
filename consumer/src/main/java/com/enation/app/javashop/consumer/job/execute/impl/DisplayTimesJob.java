/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.consumer.job.execute.impl;

import com.enation.app.javashop.consumer.job.execute.EveryDayExecute;
import com.enation.app.javashop.core.statistics.service.DisplayTimesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 流量如果不能达到阙值，则每天消费掉积攒掉流量
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-08 上午8:48
 */
@Component
public class DisplayTimesJob implements EveryDayExecute {

    @Autowired
    private DisplayTimesManager displayTimesManager;

    /**
     * 每日执行
     */
    @Override
    public void everyDay() {
        displayTimesManager.countNow();
    }
}
