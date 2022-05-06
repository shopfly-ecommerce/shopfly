/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.service.impl;

import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.dos.MemberPointHistory;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.member.service.MemberPointHistoryManager;
import cloud.shopfly.b2c.core.member.service.MemberPointManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

/**
 * 会员积分实现
 * 调用此接口需要完整填写每项属性
 *
 * @author zh
 * @version v7.0
 * @since v7.0
 * 2018-04-03 15:44:12
 */
@Service
public class MemberPointManagerImpl implements MemberPointManager {

    @Autowired
    private MemberManager memberManager;
    @Autowired
    private MemberPointHistoryManager memberPointHistoryManager;

    @Autowired

    private DaoSupport memberDaoSupport;

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void pointOperation(@Valid MemberPointHistory memberPointHistory) {
        this.checkMemberPoint(memberPointHistory);
        //添加历史记录
        Member member = memberManager.getModel(memberPointHistory.getMemberId());
        if (member == null) {
            throw new ResourceNotFoundException("此会员不存在");
        }
        memberPointHistory.setTime(DateUtil.getDateline());
        memberPointHistory.setMemberId(member.getMemberId());
        memberPointHistoryManager.add(memberPointHistory);
        //给会员添加值,等级积分的处理
        if (member.getGradePoint() == null) {
            member.setGradePoint(0);
        }
        if (memberPointHistory.getGradePointType().equals(1)) {
            this.memberDaoSupport.execute("update es_member set grade_point=grade_point+? where member_id=?", memberPointHistory.getGradePoint(), member.getMemberId());
        } else {
            if (member.getGradePoint() - memberPointHistory.getGradePoint() > 0) {
                this.memberDaoSupport.execute("update es_member set grade_point=grade_point-? where member_id=?", memberPointHistory.getGradePoint(), member.getMemberId());
            } else {
                this.memberDaoSupport.execute("update es_member set grade_point=0 where member_id=?", member.getMemberId());
            }
        }
        //给会员添加值,消费积分的处理
        if (member.getConsumPoint() == null) {
            member.setConsumPoint(0);
        }
        if (memberPointHistory.getConsumPointType().equals(1)) {
            this.memberDaoSupport.execute("update es_member set consum_point=consum_point+? where member_id=?", memberPointHistory.getConsumPoint(), member.getMemberId());
        } else {
            if (member.getConsumPoint() - memberPointHistory.getConsumPoint() > 0) {
                this.memberDaoSupport.execute("update es_member set consum_point=consum_point-? where member_id=?", memberPointHistory.getConsumPoint(), member.getMemberId());
            } else {
                this.memberDaoSupport.execute("update es_member set consum_point=0 where member_id=?", member.getMemberId());
            }
        }


    }


    private MemberPointHistory checkMemberPoint(MemberPointHistory memberPointHistory) {
        //对消费积分类型的处理
        Integer consumPointType = memberPointHistory.getConsumPointType();
        boolean bool = consumPointType == null || (consumPointType != 1 && consumPointType != 0);
        if (bool) {
            throw new ServiceException(MemberErrorCode.E106.code(), "消费积分类型不正确");
        }
        //对消费积分的处理
        Integer consumPoint = memberPointHistory.getConsumPoint();
        bool = consumPoint == null || consumPoint < 0;
        if (bool) {
            throw new ServiceException(MemberErrorCode.E106.code(), "消费积分不正确");
        }
        //对等级积分类型的处理
        Integer gadePointType = memberPointHistory.getGradePointType();
        bool = gadePointType == null || (gadePointType != 1 && gadePointType != 0);
        if (bool) {
            throw new ServiceException(MemberErrorCode.E106.code(), "等级积分类型不正确");
        }
        //对消费积分的处理
        Integer gadePoint = memberPointHistory.getGradePoint();
        bool = gadePoint == null || gadePoint < 0;
        if (bool) {
            throw new ServiceException(MemberErrorCode.E106.code(), "等级积分不正确");
        }
        return memberPointHistory;

    }

    @Override
    public boolean checkPointSettingOpen() {
        return false;
    }
}
