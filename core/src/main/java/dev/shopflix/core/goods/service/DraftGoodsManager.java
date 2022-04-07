/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.service;

import dev.shopflix.core.goods.model.dos.DraftGoodsDO;
import dev.shopflix.core.goods.model.dos.GoodsDO;
import dev.shopflix.core.goods.model.dto.GoodsDTO;
import dev.shopflix.core.goods.model.vo.DraftGoodsVO;
import dev.shopflix.framework.database.Page;

/**
 * 草稿商品业务层
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-26 10:40:34
 */
public interface DraftGoodsManager {

    /**
     * 查询草稿商品列表
     *
     * @param page        页码
     * @param pageSize    每页数量
     * @param keyword
     * @param shopCatPath
     * @return Page
     */
    Page list(int page, int pageSize, String keyword, String shopCatPath);

    /**
     * 添加草稿商品
     *
     * @param goodsVO 草稿商品
     * @return DraftGoods 草稿商品
     */
    DraftGoodsDO add(GoodsDTO goodsVO);

    /**
     * 修改草稿商品
     *
     * @param goodsVo 草稿商品
     * @param id      草稿商品主键
     * @return DraftGoods 草稿商品
     */
    DraftGoodsDO edit(GoodsDTO goodsVo, Integer id);

    /**
     * 删除草稿商品
     *
     * @param draftGoodsIds 草稿商品主键
     */
    void delete(Integer[] draftGoodsIds);

    /**
     * 获取草稿商品
     *
     * @param id 草稿商品主键
     * @return DraftGoods  草稿商品
     */
    DraftGoodsDO getModel(Integer id);

    /**
     * 获取草稿商品
     *
     * @param id 草稿商品主键
     * @return DraftGoods  草稿商品
     */
    DraftGoodsVO getVO(Integer id);

    /**
     * 草稿商品上架
     *
     * @param goodsVO
     * @param draftGoodsId
     * @return
     */
    GoodsDO addMarket(GoodsDTO goodsVO, Integer draftGoodsId);

}