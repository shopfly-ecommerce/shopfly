/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.distribution;

import dev.shopflix.core.distribution.model.dos.DistributionGoods;

/**
 * 分销商品客户端
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/8/14 下午1:31
 */
public interface DistributionGoodsClient {


    /**
     * 修改分销商品提现设置
     *
     * @param distributionGoods
     * @return
     */
    DistributionGoods edit(DistributionGoods distributionGoods);

    /**
     * 获取分销设置
     *
     * @param goodsId
     * @return
     */
    DistributionGoods getModel(Integer goodsId);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Integer id);

}
