/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.member.service;

import com.enation.app.javashop.core.member.model.dos.MemberCollectionGoods;
import com.enation.app.javashop.framework.database.Page;

/**
 * 会员商品收藏表业务层
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 10:13:41
 */
public interface MemberCollectionGoodsManager {

    /**
     * 查询会员商品收藏表列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加会员商品收藏表
     *
     * @param memberCollectionGoods 收藏商品对象
     * @return
     */
    MemberCollectionGoods add(MemberCollectionGoods memberCollectionGoods);

    /**
     * 删除会员商品收藏
     *
     * @param goodsId 商品id
     */
    void delete(Integer goodsId);

    /**
     * 获取会员商品收藏
     *
     * @param id 会员商品收藏表主键
     * @return MemberCollectionGoods  会员商品收藏表
     */
    MemberCollectionGoods getModel(Integer id);

    /**
     * 获取会员收藏商品数
     *
     * @return 收藏商品数
     */
    Integer getMemberCollectCount();

    /**
     * 某商品收藏数量
     *
     * @param goodsId
     * @return
     */
    Integer getGoodsCollectCount(Integer goodsId);

    /**
     * 是否收藏某商品
     *
     * @param id 商品id
     * @return
     */
    boolean isCollection(Integer id);
}