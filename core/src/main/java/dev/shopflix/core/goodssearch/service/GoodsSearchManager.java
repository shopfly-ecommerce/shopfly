/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goodssearch.service;

import dev.shopflix.core.goodssearch.model.GoodsSearchDTO;
import dev.shopflix.core.goodssearch.model.GoodsWords;
import dev.shopflix.framework.database.Page;

import java.util.List;
import java.util.Map;

/**
 * 商品搜索
 *
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017年9月14日 上午10:52:20
 */
public interface GoodsSearchManager {

    /**
     * 搜索
     *
     * @param goodsSearch 搜索条件
     * @return 商品分页
     */
    Page search(GoodsSearchDTO goodsSearch);

    /**
     * 获取筛选器
     *
     * @param goodsSearch 搜索条件
     * @return Map
     */
    Map<String, Object> getSelector(GoodsSearchDTO goodsSearch);

    /**
     * 通过关键字获取商品分词索引
     *
     * @param keyword
     * @return
     */
    List<GoodsWords> getGoodsWords(String keyword);

    /**
     * 获取'为你推荐'商品列表
     * @param goodsSearch 查询参数
     * @return 分页数据
     */
    Page recommendGoodsList(GoodsSearchDTO goodsSearch);
}
