/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.service;

import dev.shopflix.core.goods.model.dos.GoodsDO;
import dev.shopflix.core.goods.model.dos.GoodsSkuDO;
import dev.shopflix.core.goods.model.dto.GoodsQueryParam;
import dev.shopflix.core.goods.model.vo.GoodsSkuVO;
import dev.shopflix.framework.database.Page;

import java.util.List;

/**
 * 商品sku业务层
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-21 11:48:40
 */
public interface GoodsSkuManager {

    /**
     * 查询SKU列表
     * @param param
     * @return
     */
    Page list(GoodsQueryParam param);

    /**
     * 查询某商品的sku
     *
     * @param goodsId
     * @return
     */
    List<GoodsSkuVO> listByGoodsId(Integer goodsId);

    /**
     * 添加商品sku
     *
     * @param skuList
     * @param goods
     */
    void add(List<GoodsSkuVO> skuList, GoodsDO goods);

    /**
     * 修改商品sku
     *
     * @param skuList
     * @param goods
     */
    void edit(List<GoodsSkuVO> skuList, GoodsDO goods);

    /**
     * 根据商品sku主键id集合获取商品信息
     * @param skuIds
     * @return
     */
    List<GoodsSkuVO> query(Integer[] skuIds);

    /**
     * 缓存中查询sku信息
     *
     * @param skuId
     * @return
     */
    GoodsSkuVO getSkuFromCache(Integer skuId);

    /**
     * 查询单个sku
     *
     * @param id
     * @return
     */
    GoodsSkuDO getModel(Integer id);

}