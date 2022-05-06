/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.statistics.service;

import cloud.shopfly.b2c.core.statistics.model.dto.GoodsData;

/**
 * 商品收集manager
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/8 下午4:11
 */

public interface GoodsDataManager {
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
     * 获取商品
     * @param goodsId
     * @return
     */
    GoodsData get(Integer goodsId);

    /**
     * 下架所有商品
     */
    void underAllGoods();

}
