/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.distribution.impl;

import cloud.shopfly.b2c.core.client.distribution.DistributionClient;
import cloud.shopfly.b2c.core.distribution.model.dos.DistributionDO;
import cloud.shopfly.b2c.core.distribution.service.DistributionManager;
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
public class DistributionClientDefaultImpl implements DistributionClient {

    @Autowired
    private DistributionManager distributionManager;

    /**
     * 新增分销商
     *
     * @param distributor
     * @return
     */
    @Override
    public DistributionDO add(DistributionDO distributor) {
        return distributionManager.add(distributor);
    }

    /**
     * 更新Distributor信息
     *
     * @param distributor
     * @return
     */
    @Override
    public DistributionDO edit(DistributionDO distributor) {
        return distributionManager.edit(distributor);
    }

    /**
     * 获取分销商
     *
     * @param memberId
     * @return
     */
    @Override
    public DistributionDO getDistributorByMemberId(Integer memberId) {
        return distributionManager.getDistributorByMemberId(memberId);
    }

    /**
     * 根据会员id设置其上级分销商（两级）
     *
     * @param memberId 会员id
     * @param parentId 上级会员的id
     * @return 设置结果， trun=成功 false=失败
     */
    @Override
    public boolean setParentDistributorId(Integer memberId, Integer parentId) {
        return distributionManager.setParentDistributorId(memberId, parentId);
    }
}
