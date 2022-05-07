/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
 * Realization of member points
 * Calling this interface requires that each attribute be completely filled out
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
        // Adding history
        Member member = memberManager.getModel(memberPointHistory.getMemberId());
        if (member == null) {
            throw new ResourceNotFoundException("This member does not exist");
        }
        memberPointHistory.setTime(DateUtil.getDateline());
        memberPointHistory.setMemberId(member.getMemberId());
        memberPointHistoryManager.add(memberPointHistory);
        // Add value to member, grade points processing
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
        // Add value to member, consumption points processing
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
        // Treatment of consumption credit types
        Integer consumPointType = memberPointHistory.getConsumPointType();
        boolean bool = consumPointType == null || (consumPointType != 1 && consumPointType != 0);
        if (bool) {
            throw new ServiceException(MemberErrorCode.E106.code(), "Incorrect consumption credit type");
        }
        // The treatment of consumption points
        Integer consumPoint = memberPointHistory.getConsumPoint();
        bool = consumPoint == null || consumPoint < 0;
        if (bool) {
            throw new ServiceException(MemberErrorCode.E106.code(), "Consumption points are incorrect");
        }
        // The treatment of grade integral types
        Integer gadePointType = memberPointHistory.getGradePointType();
        bool = gadePointType == null || (gadePointType != 1 && gadePointType != 0);
        if (bool) {
            throw new ServiceException(MemberErrorCode.E106.code(), "The grade integral type is incorrect");
        }
        // The treatment of consumption points
        Integer gadePoint = memberPointHistory.getGradePoint();
        bool = gadePoint == null || gadePoint < 0;
        if (bool) {
            throw new ServiceException(MemberErrorCode.E106.code(), "The grade integral is not correct");
        }
        return memberPointHistory;

    }

    @Override
    public boolean checkPointSettingOpen() {
        return false;
    }
}
