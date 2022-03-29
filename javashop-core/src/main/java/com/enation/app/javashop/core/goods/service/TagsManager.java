/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.goods.service;

import com.enation.app.javashop.core.goods.model.dos.TagsDO;
import com.enation.app.javashop.core.goods.model.vo.GoodsSelectLine;
import com.enation.app.javashop.framework.database.Page;

import java.util.List;

/**
 * 商品标签业务层
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-28 14:49:36
 */
public interface TagsManager {


    /**
     * 查询某个标签下固定数量的商品
     *
     * @param num
     * @param mark
     * @return
     */
    List<GoodsSelectLine> queryTagGoods(Integer num, String mark);

    /**
     * 查询商品标签列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 查询某标签下的商品
     *
     * @param tagId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page queryTagGoods(Integer tagId, Integer pageNo, Integer pageSize);

    /**
     * 保存标签商品
     *
     * @param tagId
     * @param goodsIds
     * @return
     */
    void saveTagGoods(Integer tagId, Integer[] goodsIds);


    /**
     * 查询一个标签
     * @param id
     * @return
     */
    TagsDO getModel(Integer id);
}