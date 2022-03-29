/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.client.distribution;

import com.enation.app.javashop.core.distribution.model.dos.DistributionDO;

/**
 * DistributionClient
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-14 下午1:54
 */
public interface DistributionClient {

    /**
     * 新增分销商
     *
     * @param distributor
     * @return
     */
    DistributionDO add(DistributionDO distributor);

    /**
     * 更新Distributor信息
     *
     * @param distributor
     * @return
     */
    DistributionDO edit(DistributionDO distributor);

    /**
     * 获取分销商
     *
     * @param memberId
     * @return
     */
    DistributionDO getDistributorByMemberId(Integer memberId);

    /**
     * 根据会员id设置其上级分销商（两级）
     *
     * @param memberId 会员id
     * @param parentId 上级会员的id
     * @return 设置结果， trun=成功 false=失败
     */
    boolean setParentDistributorId(Integer memberId, Integer parentId);
}
