package cloud.shopfly.b2c.consumer.job.execute;

import org.springframework.scheduling.annotation.Async;

/**
 * @version v1.0
 * @Description: 每十分钟执行定时任务
 * @Author: gy
 * @Date: 2020/6/10 0010 10:02
 */
public interface EveryTenMinutesExecute {

    /**
     * 每十分钟执行定时任务
     */
    @Async
    void everyTenMinutes();
}
