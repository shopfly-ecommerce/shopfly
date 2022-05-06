/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goods.service;

import cloud.shopfly.b2c.core.goods.model.dos.GoodsParamsDO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsParamsGroupVO;

import java.util.List;

/**
 * 商品参数关联接口
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018年3月21日 下午5:29:09
 */
public interface GoodsParamsManager {

    /**
     * 修改商品查询分类和商品关联的参数
     *
     * @param categoryId
     * @param goodsId
     * @return
     */
    List<GoodsParamsGroupVO> queryGoodsParams(Integer categoryId, Integer goodsId);

    /**
     * 添加商品查询分类和商品关联的参数
     *
     * @param categoryId
     * @return
     */
    List<GoodsParamsGroupVO> queryGoodsParams(Integer categoryId);

    /**
     * 添加商品关联的参数
     *
     * @param goodsId   商品id
     * @param paramList 参数集合
     */
    void addParams(List<GoodsParamsDO> paramList, Integer goodsId);


}
