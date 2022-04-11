/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.job.execute;

import org.springframework.scheduling.annotation.Async;

/**
 * 每日执行
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-05 下午1:19
 */
public interface EveryDayExecute {
    /**
     * 每日执行
     */
    @Async
    void everyDay();
}
