/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goods.service;

import cloud.shopfly.b2c.core.goods.model.dos.GoodsGalleryDO;

import java.util.List;

/**
 * 商品相册业务层
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-21 11:39:54
 */
public interface GoodsGalleryManager {

    /**
     * 查询某商品的相册
     *
     * @param goodsId
     * @return
     */
    List<GoodsGalleryDO> list(Integer goodsId);

    /**
     * 使用原始图片得到商品的其他规格的图片格式
     *
     * @param origin
     * @return
     */
    GoodsGalleryDO getGoodsGallery(String origin);

    /**
     * 添加商品相册
     *
     * @param goodsGallery 商品相册
     * @return GoodsGallery 商品相册
     */
    GoodsGalleryDO add(GoodsGalleryDO goodsGallery);

    /**
     * 添加商品的相册
     *
     * @param goodsGalleryList
     * @param goodsId
     */
    void add(List<GoodsGalleryDO> goodsGalleryList, Integer goodsId);

    /**
     * 修改某商品的相册
     * @param goodsGalleryList
     * @param goodsId
     */
    void edit(List<GoodsGalleryDO> goodsGalleryList, Integer goodsId);

    /**
     * 获取商品相册
     *
     * @param id
     *            商品相册主键
     * @return GoodsGallery 商品相册
     */
    GoodsGalleryDO getModel(Integer id);


}