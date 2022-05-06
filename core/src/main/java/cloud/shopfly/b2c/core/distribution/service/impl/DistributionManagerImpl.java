/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.distribution.service.impl;

import cloud.shopfly.b2c.core.distribution.exception.DistributionException;
import cloud.shopfly.b2c.core.distribution.model.dos.CommissionTpl;
import cloud.shopfly.b2c.core.distribution.model.dos.DistributionDO;
import cloud.shopfly.b2c.core.distribution.model.enums.UpgradeTypeEnum;
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionVO;
import cloud.shopfly.b2c.core.distribution.service.CommissionTplManager;
import cloud.shopfly.b2c.core.distribution.service.DistributionManager;
import cloud.shopfly.b2c.core.distribution.service.UpgradeLogManager;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.distribution.exception.DistributionErrorCode;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分销管理实现类
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/21 下午3:27
 */

@Component
public class DistributionManagerImpl implements DistributionManager {

    @Autowired
    private CommissionTplManager commissionTplManager;
    @Autowired
    private UpgradeLogManager upgradeLogManager;
    @Autowired
    private MemberManager memberManager;
    @Autowired

    private DaoSupport daoSupport;

    @Override
    public DistributionDO add(DistributionDO distributor) {
        // 如果分销商值有效
        if (distributor != null) {
            distributor.setCreateTime(DateUtil.getDateline());
            this.daoSupport.insert("es_distribution", distributor);
        }
        distributor.setId(daoSupport.getLastId("es_distribution"));
        return distributor;
    }

    /**
     * 所有下线
     *
     * @param memberId
     * @return
     */
    @Override
    public List<DistributionVO> allDown(Integer memberId) {
        List<DistributionDO> dos = this.daoSupport.queryForList("select * from es_distribution where member_id_lv1 =? or member_id_lv2 = ?", DistributionDO.class, memberId, memberId);


        List<DistributionVO> vos = new ArrayList<>();
        //填充一级
        for (DistributionDO ddo : dos) {
            if (ddo.getMemberIdLv1().equals(memberId)) {
                vos.add(new DistributionVO(ddo));
            }
        }
        //填充二级

        if (vos != null) {
            for (DistributionDO ddo : dos) {
                for (DistributionVO vo : vos) {
                    if (ddo.getMemberIdLv1().equals(vo.getId())) {
                        List<DistributionVO> item = vo.getItem();
                        if (item == null) {
                            item = new ArrayList<>();
                        }
                        item.add(new DistributionVO(ddo));
                        vo.setItem(item);
                    }
                }
            }
        }

        return vos;


    }

    @Override
    public Page page(Integer pageNo, Integer pageSize, String memberName) {

        List<String> params = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select * from es_distribution");
        if (!StringUtil.isEmpty(memberName)) {
            sql.append(" where member_name like ?");
            params.add("%" + memberName + "%");
        }
        sql.append(" order by id desc");
        Page<DistributionDO> page = this.daoSupport.queryForPage(sql.toString(), pageNo, pageSize, DistributionDO.class, params.toArray());

        Page result = new Page();
        result.setPageNo(page.getPageNo());
        result.setPageSize(page.getPageSize());
        result.setDataTotal(page.getDataTotal());

        List<DistributionVO> vos = new ArrayList<>();
        for (DistributionDO ddo : page.getData()) {
            vos.add(new DistributionVO(ddo));
        }
        result.setData(vos);
        return result;
    }

    @Override
    public DistributionDO getDistributorByMemberId(Integer memberId) {

        String sql = "SELECT * FROM es_distribution where member_id = ?";
        DistributionDO distributor = this.daoSupport.queryForObject(sql, DistributionDO.class, memberId);

        return distributor;
    }

    @Override
    public DistributionDO getDistributor(Integer id) {

        String sql = "SELECT * FROM es_distribution where id = ?";
        DistributionDO distributor = this.daoSupport.queryForObject(sql, DistributionDO.class, id);
        return distributor;
    }

    @Override
    public DistributionDO edit(DistributionDO distributor) {

        Map map = new HashMap(16);
        map.put("id", distributor.getId());
        this.daoSupport.update("es_distribution", distributor, map);
        return distributor;

    }


    @Override
    public boolean setParentDistributorId(Integer memberId, Integer parentId) {

        // 如果会员id有效
        if (memberId != 0) {
            // 1.得到父级会员信息 就是当前会员的lv1
            DistributionDO lv1Distributor = this.getDistributorByMemberId(parentId);

            // 2.得到 他的lv1级 （当前会员的lv2级 是他的lv1级的lv1级）
            Integer lv2MemberId = lv1Distributor.getMemberIdLv1();

            // 3.准备拼接sql
            StringBuffer sql = new StringBuffer("UPDATE es_distribution SET member_id_lv1 = ?");

            List<Object> params = new ArrayList<>();
            params.add(lv1Distributor.getMemberId());

            // 如果lv2会员id存在
            if (null != lv2MemberId) {
                sql.append(",member_id_lv2 = ?");
                params.add(lv2MemberId);
            }

            // 4.添加where 并执行
            sql.append(" WHERE member_id = ?");
            params.add(memberId);
            this.daoSupport.execute(sql.toString(), params.toArray());


            this.countDown(lv1Distributor.getMemberId());
            // 如果lv2会员id存在
            if (null != lv2MemberId) {
                this.countDown(lv2MemberId);
            }
            return true;
        }
        return false;
    }

    @Override
    public void addFrozenCommission(Double price, Integer memberId) {
        String sql = "UPDATE es_distribution SET commission_frozen = commission_frozen + ? WHERE member_id = ?";
        this.daoSupport.execute(sql, price, memberId);
    }

    @Override
    public void addTotalPrice(Double orderPrice, Double rebate, Integer memberId) {
        String sql = "UPDATE es_distribution SET turnover_price = turnover_price + ?,rebate_total = rebate_total + ? WHERE member_id = ?";
        this.daoSupport.execute(sql, orderPrice, rebate, memberId);
    }

    @Override
    public void subTotalPrice(Double orderPrice, Double rebate, Integer memberId) {
        String sql = "UPDATE es_distribution SET turnover_price = turnover_price - ?,rebate_total = rebate_total - ? WHERE member_id = ?";
        this.daoSupport.execute(sql, orderPrice, rebate, memberId);
    }

    @Override
    public Double getCanRebate(Integer memberId) {
        try {
            return this.daoSupport
                    .queryForObject("select * from es_distribution where member_id = ?", DistributionDO.class, memberId)
                    .getCanRebate();
        } catch (Exception e) {
            // 如果用户没有提现金额 返回0
            return 0d;
        }
    }

    @Override
    public String getUpMember() {

        String path = this.getDistributorByMemberId(
                UserContext.getBuyer().getUid())
                .getPath();
        String[] up = path.split("\\|");
        Integer upMember = Integer.parseInt(up[up.length - 2]);
        if (upMember == 0) {
            return "没有推荐人";
        }
        try {
            DistributionDO distributor = this.getDistributorByMemberId(upMember);
            Member member = memberManager.getModel(distributor.getMemberId());
            if (member == null) {
                return "没有推荐人";
            }
            return member.getUname();
        } catch (Exception e) {
            return "没有推荐人";
        }
    }

    @Override
    public List<DistributionVO> getLowerDistributorTree(Integer memberId) {
        List<DistributionDO> list = this.daoSupport.queryForList("select * from es_distribution where member_id_lv2 = ? or member_id_lv1 = ? ", DistributionDO.class, memberId, memberId);
        List<DistributionVO> vos = new ArrayList<DistributionVO>();
        for (DistributionDO ddo : list) {
            vos.add(new DistributionVO(ddo));
        }
        List<DistributionVO> result = new ArrayList<>();
        //第一层关系构造
        for (DistributionVO vo : vos) {
            if (vo.getLv1Id().equals(memberId)) {
                result.add(vo);
            }
        }
        //第二层关系构造 循环第一层构造
        for (DistributionVO rs : result) {
            List<DistributionVO> items = new ArrayList<>();
            for (DistributionVO vo : vos) {
                if (rs.getId().equals(vo.getLv1Id())) {
                    items.add(vo);
                }
            }
            rs.setItem(items);
        }
        return result;
    }


    @Override
    public void changeTpl(Integer memberId, Integer tplId) {
        CommissionTpl tpl = commissionTplManager.getModel(tplId);
        DistributionDO ddo = this.getDistributorByMemberId(memberId);
        if (tpl == null || ddo == null) {
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
        this.upgradeLogManager.addUpgradeLog(memberId, tplId, UpgradeTypeEnum.MANUAL);
        this.daoSupport.execute("update es_distribution set current_tpl_id = ? ,current_tpl_name = ? where member_id = ?", tplId, commissionTplManager.getModel(tplId).getTplName(), memberId);
    }

    /**
     * 统计下线人数
     */
    @Override
    public void countDown(Integer memberId) {
        this.daoSupport.execute("UPDATE es_distribution SET downline =(SELECT count from( SELECT count(0) count FROM es_distribution a WHERE a.member_id_lv2 = ? OR a.member_id_lv1 = ? ) counttable ) WHERE member_id = ?", memberId, memberId, memberId);
    }

}
