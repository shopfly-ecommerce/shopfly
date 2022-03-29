/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.client.goods;

import com.enation.app.javashop.core.goods.model.dos.CategoryDO;
import com.enation.app.javashop.core.goods.model.dos.GoodsDO;
import com.enation.app.javashop.core.goods.model.vo.CacheGoods;
import com.enation.app.javashop.core.goods.model.vo.GoodsSelectLine;
import com.enation.app.javashop.core.goods.model.vo.GoodsSkuVO;
import com.enation.app.javashop.core.goods.model.vo.GoodsSnapshotVO;
import com.enation.app.javashop.core.trade.order.model.vo.OrderSkuVO;

import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v1.0
 * @Description: 商品对外的接口
 * @date 2018/7/26 10:33
 * @since v7.0.0
 */
public interface GoodsClient {
    /**
     * 缓存中查询商品的信息
     *
     * @param goodsId
     * @return
     */
    CacheGoods getFromCache(Integer goodsId);

    /**
     * 根据条件查询商品总数
     *
     * @param status 商品状态
     * @return 商品总数
     */
    Integer queryGoodsCountByParam(Integer status);

    /**
     * 查询多个商品的基本信息
     *
     * @param goodsIds
     * @return
     */
    List<GoodsSelectLine> query(Integer[] goodsIds);

    /**
     * 查询很多商品的信息和参数信息
     *
     * @param goodsIds 商品id集合
     * @return
     */
    List<Map<String, Object>> getGoodsAndParams(Integer[] goodsIds);

    /**
     * 缓存中查询sku信息
     *
     * @param skuId
     * @return
     */
    GoodsSkuVO getSkuFromCache(Integer skuId);

    /**
     * 更新商品的评论数量
     *
     * @param goodsId
     */
    void updateCommentCount(Integer goodsId);

    /**
     * 查询商品信息
     *
     * @param goodsIds 商品id集合
     * @return
     */
    List<Map<String, Object>> getGoods(Integer[] goodsIds);

    /**
     * 更新商品的购买数量
     *
     * @param list
     */
    void updateBuyCount(List<OrderSkuVO> list);

    /**
     * 查询商品总数
     *
     * @return 商品总数
     */
    Integer queryGoodsCount();

    /**
     * 查询某范围的商品信息
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<Map> queryGoodsByRange(Integer pageNo, Integer pageSize);

    /**
     * 获取商品分类
     *
     * @param id 商品分类主键
     * @return Category 商品分类
     */
    CategoryDO getCategory(Integer id);

    /**
     * 校验商品模版是否使用
     *
     * @param templateId
     * @return 商品
     */
    GoodsDO checkShipTemplate(Integer templateId);

    /**
     * 添加商品快照时使用的接口
     *
     * @param goodsId
     * @return
     */
    GoodsSnapshotVO queryGoodsSnapShotInfo(Integer goodsId);

    /**
     * 更新商品好平率
     */
    void updateGoodsGrade();

}
