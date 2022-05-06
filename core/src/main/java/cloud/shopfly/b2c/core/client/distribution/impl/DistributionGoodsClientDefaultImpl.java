/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.distribution.impl;

import cloud.shopfly.b2c.core.client.distribution.DistributionGoodsClient;
import cloud.shopfly.b2c.core.distribution.model.dos.DistributionGoods;
import cloud.shopfly.b2c.core.distribution.service.DistributionGoodsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * DistributionGoodsClientDefaultImpl
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-14 下午1:32
 */
@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class DistributionGoodsClientDefaultImpl implements DistributionGoodsClient {

    @Autowired
    private DistributionGoodsManager distributionGoodsManager;


    /**
     * 获取某商品设置
     *
     * @param goodsId
     * @return
     */
    @Override
    public DistributionGoods getModel(Integer goodsId) {
        return distributionGoodsManager.getModel(goodsId);
    }

    /**
     * 修改分销商品提现设置
     *
     * @param distributionGoods
     * @return
     */
    @Override
    public DistributionGoods edit(DistributionGoods distributionGoods) {
        return distributionGoodsManager.edit(distributionGoods);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        distributionGoodsManager.delete(id);
    }
}
