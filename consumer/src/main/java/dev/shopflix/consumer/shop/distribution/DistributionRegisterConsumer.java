/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.distribution;

import dev.shopflix.consumer.core.event.MemberRegisterEvent;
import com.enation.app.javashop.core.base.CachePrefix;
import com.enation.app.javashop.core.base.message.MemberRegisterMsg;
import com.enation.app.javashop.core.client.distribution.CommissionTplClient;
import com.enation.app.javashop.core.client.distribution.DistributionClient;
import com.enation.app.javashop.core.distribution.model.dos.CommissionTpl;
import com.enation.app.javashop.core.distribution.model.dos.DistributionDO;
import com.enation.app.javashop.framework.cache.Cache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 注册后添加分销商
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/13 下午11:33
 */
@Component
public class DistributionRegisterConsumer implements MemberRegisterEvent {
    @Autowired
    private DistributionClient distributionClient;

    @Autowired
    private CommissionTplClient commissionTplClient;

    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private Cache cache;

    @Transactional(value = "distributionTransactionManager", rollbackFor = Exception.class)
    @Override
    public void memberRegister(MemberRegisterMsg memberRegisterMsg) {
        // 注册完毕后 在分销商表中添加会员信息
        DistributionDO distributor = new DistributionDO();
        try {
            distributor.setMemberId(memberRegisterMsg.getMember().getMemberId());
            //默认模版设置
            CommissionTpl commissionTpl = commissionTplClient.getDefaultCommission();
            distributor.setCurrentTplId(commissionTpl.getId());
            distributor.setCurrentTplName(commissionTpl.getTplName());
            distributor.setMemberName(memberRegisterMsg.getMember().getUname());
            distributor = this.distributionClient.add(distributor);
            distributor.setPath("0|" + distributor.getMemberId() + "|");
            this.distributionClient.edit(distributor);
        } catch (RuntimeException e) {
            logger.error(e);
        }

        //注册完毕后，给注册会员添加他的上级分销商id
        Object upMemberId = cache.get(CachePrefix.DISTRIBUTION_UP.getPrefix() + memberRegisterMsg.getUuid());

        try {
            //如果推广的会员id存在
            if (upMemberId != null) {
                int lv1MemberId = Integer.parseInt(upMemberId.toString());
                DistributionDO parentDistributor = this.distributionClient.getDistributorByMemberId(lv1MemberId);
                distributor.setPath(parentDistributor.getPath() + distributor.getMemberId() + "|");

                //先更新 path
                this.distributionClient.edit(distributor);
                // 再更新parentId
                this.distributionClient.setParentDistributorId(memberRegisterMsg.getMember().getMemberId(), lv1MemberId);

            } else {
                this.distributionClient.edit(distributor);
            }
            cache.remove(CachePrefix.DISTRIBUTION_UP.getPrefix() + memberRegisterMsg.getUuid());
        } catch (Exception e) {
            logger.error(e);
        }
    }


}
