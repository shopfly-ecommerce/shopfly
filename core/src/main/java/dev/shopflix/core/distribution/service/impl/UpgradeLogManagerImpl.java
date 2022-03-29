/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.distribution.service.impl;

import dev.shopflix.core.client.member.MemberClient;
import dev.shopflix.core.distribution.model.dos.CommissionTpl;
import dev.shopflix.core.distribution.model.dos.UpgradeLogDO;
import dev.shopflix.core.distribution.model.enums.UpgradeTypeEnum;
import dev.shopflix.core.distribution.service.CommissionTplManager;
import dev.shopflix.core.distribution.service.DistributionManager;
import dev.shopflix.core.distribution.service.UpgradeLogManager;
import dev.shopflix.core.member.model.dos.Member;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.util.DateUtil;
import dev.shopflix.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 升级日志 实现
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 下午12:58
 */

@Component
public class UpgradeLogManagerImpl implements UpgradeLogManager {

    @Autowired
    @Qualifier("distributionDaoSupport")
    private DaoSupport daoSupport;
    @Autowired
    private DistributionManager distributionManager;
    @Autowired
    private CommissionTplManager commissionTplManager;

    @Autowired
    private MemberClient memberClient;

    @Override
    public Page page(int page, int pageSize, String memberName) {
        String sql = "SELECT * FROM es_upgrade_log";

        List<String> params = new ArrayList<>();
        // 只传入了搜索的名字
        if (!StringUtil.isEmpty(memberName)) {
            sql += " WHERE member_name LIKE ?";
            params.add("%" + memberName + "%");
        }
        sql += " ORDER BY create_time DESC";
        Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, params.toArray());
        return webpage;
    }

    @Override
    public UpgradeLogDO add(UpgradeLogDO upgradeLog) {

        // 非空
        if (upgradeLog != null) {
            this.daoSupport.insert("es_upgrade_log", upgradeLog);
        }
        return upgradeLog;
    }

    @Override
    public void addUpgradeLog(int memberId, int newTplId, UpgradeTypeEnum upgradeType) {
        UpgradeLogDO upgradelog = new UpgradeLogDO();
        Member member = this.memberClient.getModel(memberId);
        int oldTplId = this.distributionManager.getDistributorByMemberId(memberId).getCurrentTplId();
        CommissionTpl oldTpl = this.commissionTplManager.getModel(oldTplId);
        CommissionTpl newTpl = this.commissionTplManager.getModel(newTplId);

        //set数据
        upgradelog.setMemberId(memberId);
        if (member != null) {
            upgradelog.setMemberName(member.getUname());
        } else {
            upgradelog.setMemberName("无名");
        }

        // 如果有 就记录
        if (oldTpl != null) {
            upgradelog.setOldTplId(oldTplId);
            upgradelog.setOldTplName(oldTpl.getTplName());
        }

        upgradelog.setNewTplId(newTplId);
        upgradelog.setNewTplName(newTpl.getTplName());
        upgradelog.setType(upgradeType.getName());
        upgradelog.setCreateTime(DateUtil.getDateline());
        this.add(upgradelog);
    }
}
