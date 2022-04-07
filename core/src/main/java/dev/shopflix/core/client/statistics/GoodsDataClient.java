/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.statistics;

import dev.shopflix.core.statistics.model.dto.GoodsData;

/**
 * 商品收集manager
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/8 下午4:11
 */
public interface GoodsDataClient {
    /**
     * 新增商品
     *
     * @param goodsIds 商品id
     */
    void addGoods(Integer[] goodsIds);

    /**
     * 修改商品
     *
     * @param goodsIds 商品id
     */
    void updateGoods(Integer[] goodsIds);

    /**
     * 删除商品
     *
     * @param goodsIds 商品id
     */
    void deleteGoods(Integer[] goodsIds);

    /**
     * 修改商品收藏数量
     * @param goodsData
     */
    void updateCollection(GoodsData goodsData);

    /**
     * 下架所有商品
     */
    void underAllGoods();


}