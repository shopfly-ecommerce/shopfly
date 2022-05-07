package cloud.shopfly.b2c.consumer.job.execute;

import org.springframework.scheduling.annotation.Async;

/**
 * @version v1.0
 * @Description: Perform scheduled tasks every 10 minutes
 * @Author: gy
 * @Date: 2020/6/10 0010 10:02
 */
public interface EveryTenMinutesExecute {

    /**
     * Perform scheduled tasks every 10 minutes
     */
    @Async
    void everyTenMinutes();
}
