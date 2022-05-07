package cloud.shopfly.b2c.core.goodssearch.service;

import org.springframework.scheduling.annotation.Async;

/**
 * The commodity index is initialized
 * @author kingapex
 * @data 2022/4/11 11:55
 * @version 1.0
 **/
public interface GoodsIndexInitManager {

    /**
     * If the index does not exist, initialize it
     */
    @Async
    void initIndex();
}
