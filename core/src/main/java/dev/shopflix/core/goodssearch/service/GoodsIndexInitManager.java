package dev.shopflix.core.goodssearch.service;

import org.springframework.scheduling.annotation.Async;

/**
 * 商品索引初始化
 * @author kingapex
 * @data 2022/4/11 11:55
 * @version 1.0
 **/
public interface GoodsIndexInitManager {

    /**
     * 如果索引不存在，初始化索引
     */
    @Async
    void initIndex();
}
