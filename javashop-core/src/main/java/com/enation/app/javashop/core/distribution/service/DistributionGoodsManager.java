/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.distribution.service;

import com.enation.app.javashop.core.distribution.model.dos.DistributionGoods;

import java.util.List;

/**
 * 分销商品接口
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/14 上午12:37
 */
public interface DistributionGoodsManager {

    /**
     * 设置分销商品提现设置
     *
     * @param distributionGoods
     * @return
     */
    DistributionGoods edit(DistributionGoods distributionGoods);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 获取model
     *
     * @param goodsId
     * @return
     */
    DistributionGoods getModel(Integer goodsId);


    /**
     * 获取分销返佣商品
     * @param goodsIds
     * @return
     */
    List<DistributionGoods> getGoodsIds(List<Integer> goodsIds);
}
