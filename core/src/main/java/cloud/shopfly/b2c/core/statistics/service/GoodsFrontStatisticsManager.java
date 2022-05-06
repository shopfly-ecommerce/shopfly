/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.statistics.service;

import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.base.SearchCriteria;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * 商品分析manager接口
 *
 * @author xin
 * @version v1.0, 2015-12-29
 * @since v1.0
 */
public interface GoodsFrontStatisticsManager {

    /**
     * 获取商品详情
     *
     * @param pageNo    当前页码
     * @param pageSize  每页数据量
     * @param catId     商品分类id
     * @param goodsName 商品名称
     * @return Page 分页对象
     */
    Page getGoodsDetail(Integer pageNo, Integer pageSize, Integer catId, String goodsName);

    /**
     * 获取商品价格数据，分页数据
     *
     * @param sections       区间List  格式：0 100 200
     * @param searchCriteria 时间与店铺id相关参数
     * @return SimpleChart简单图表数据
     */
    SimpleChart getGoodsPriceSales(List<Integer> sections, SearchCriteria searchCriteria);

    /**
     * 获取商品下单金额排行前30，分页数据
     *
     * @param searchCriteria 时间相关参数
     * @param topNum         top数
     * @return Page 分页对象
     */
    Page getGoodsOrderPriceTopPage(int topNum, SearchCriteria searchCriteria);

    /**
     * 获取下单商品数量排行前30，分页数据
     *
     * @param searchCriteria 时间相关参数
     * @param topNum         名次 默认为30
     * @return Page 分页对象
     */
    Page getGoodsNumTopPage(int topNum, SearchCriteria searchCriteria);

    /**
     * 获取商品下单金额排行前30，图表数据
     *
     * @param topNum         top数
     * @param searchCriteria 时间相关参数
     * @return SimpleChart 简单图表数据
     */
    SimpleChart getGoodsOrderPriceTop(Integer topNum, SearchCriteria searchCriteria);

    /**
     * 获取商品下单数量排行前30，图表数据
     *
     * @param topNum         top数
     * @param searchCriteria 时间相关参数
     * @return SimpleChart 简单图表数据
     */
    SimpleChart getGoodsNumTop(Integer topNum, SearchCriteria searchCriteria);
}
