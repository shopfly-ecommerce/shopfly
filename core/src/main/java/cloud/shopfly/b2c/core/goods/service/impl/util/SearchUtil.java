/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goods.service.impl.util;

import cloud.shopfly.b2c.core.goods.GoodsErrorCode;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsQueryParam;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * SearchUtil
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-01-25 下午4:17
 */
public class SearchUtil {


    /**
     * 分类查询
     *
     * @param goodsQueryParam
     * @param term
     * @param sqlBuffer
     * @param daoSupport
     */
    public static void categoryQuery(GoodsQueryParam goodsQueryParam, List<Object> term, StringBuffer sqlBuffer, DaoSupport daoSupport) {
        // 商城分类，同时需要查询出子分类的商品
        if (!StringUtil.isEmpty(goodsQueryParam.getCategoryPath())) {
            List<Map> list = daoSupport.queryForList(
                    "select category_id from es_category where category_path like ? ",
                    goodsQueryParam.getCategoryPath() + "%");

            if (!StringUtil.isNotEmpty(list)) {
                throw new ServiceException(GoodsErrorCode.E301.code(), "分类不存在");
            }

            String[] temp = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                temp[i] = "?";
                term.add(list.get(i).get("category_id"));
            }
            String str = StringUtil.arrayToString(temp, ",");
            sqlBuffer.append(" and g.category_id in (" + str + ")");

        }
    }

    /**
     * 基础查询
     *
     * @param goodsQueryParam
     * @param term
     * @param sqlBuffer
     */
    public static void baseQuery(GoodsQueryParam goodsQueryParam, List<Object> term, StringBuffer sqlBuffer) {
        if (goodsQueryParam.getDisabled() == null) {
            goodsQueryParam.setDisabled(1);
        }
        sqlBuffer.append(" where  g.disabled = ? ");
        term.add(goodsQueryParam.getDisabled());

        // 上下架
        if (goodsQueryParam.getMarketEnable() != null) {
            sqlBuffer.append(" and g.market_enable = ? ");
            term.add(goodsQueryParam.getMarketEnable());
        }
        // 模糊关键字
        if (!StringUtil.isEmpty(goodsQueryParam.getKeyword())) {
            sqlBuffer.append(" and (g.goods_name like ? or g.sn like ? ) ");
            term.add("%" + goodsQueryParam.getKeyword() + "%");
            term.add("%" + goodsQueryParam.getKeyword() + "%");
        }
        // 名称
        if (!StringUtil.isEmpty(goodsQueryParam.getGoodsName())) {
            sqlBuffer.append(" and g.goods_name like ?");
            term.add("%" + goodsQueryParam.getGoodsName() + "%");
        }

        // 商品编号
        if (!StringUtil.isEmpty(goodsQueryParam.getGoodsSn())) {
            sqlBuffer.append(" and g.sn like ?");
            term.add("%" + goodsQueryParam.getGoodsSn() + "%");
        }

        //商品类型
        if (!StringUtil.isEmpty(goodsQueryParam.getGoodsType())) {
            sqlBuffer.append(" and g.goods_type = ?");
            term.add(goodsQueryParam.getGoodsType());
        }

    }

}
