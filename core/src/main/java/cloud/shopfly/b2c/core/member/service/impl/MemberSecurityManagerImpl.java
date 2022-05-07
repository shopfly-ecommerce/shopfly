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
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.member.service.MemberSecurityManager;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.SceneType;
import cloud.shopfly.b2c.core.base.service.SmsManager;
import cloud.shopfly.b2c.core.passport.service.PassportManager;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.util.StringUtil;
import cloud.shopfly.b2c.framework.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Member security business implementation
 *
 * @author zh
 * @version v7.0
 * @date 18/4/23 In the afternoon3:24
 * @since v7.0
 */
@Service
public class MemberSecurityManagerImpl implements MemberSecurityManager {

    @Autowired
    private MemberManager memberManager;
    @Autowired
    private SmsManager smsManager;
    @Autowired
    private PassportManager passportManager;
    @Autowired
    private Cache cache;
    @Autowired
    
    private DaoSupport memberDaoSupport;

    @Override
    public void sendBindSmsCode(String mobile) {
        if (!Validator.isMobile(mobile)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The mobile phone number format is incorrect");
        }
        // Verify membership exists
        Member member = memberManager.getMemberByMobile(mobile);
        if (member != null) {
            throw new ServiceException(MemberErrorCode.E111.code(), "The mobile phone number has been bound to another user");
        }
        smsManager.sendSmsMessage("Mobile phone Binding", mobile, SceneType.BIND_MOBILE);
    }

    @Override
    public void sendValidateSmsCode(String mobile) {
        if (!Validator.isMobile(mobile)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The mobile phone number format is incorrect");
        }
        // Verify membership exists
        Member member = memberManager.getMemberByMobile(mobile);
        if (member == null) {
            throw new ResourceNotFoundException("Current member does not exist");
        }
        smsManager.sendSmsMessage("Mobile phone verification code authentication", mobile, SceneType.VALIDATE_MOBILE);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updatePassword(Integer memberId, String password) {
        // Verify whether this step is performed after mobile phone authentication
        Member member = memberManager.getModel(memberId);
        // Check whether the current member exists
        if (member == null) {
            throw new ResourceNotFoundException("Current member does not exist");
        }
        // Verify that the current membership is disabled
        if (!member.getDisabled().equals(0)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "The current account is disabled");
        }
        // Verify password length
        String newPassword = StringUtil.md5(password + member.getUname().toLowerCase());
        String sql = "update es_member set password = ? where member_id =? ";
        this.memberDaoSupport.execute(sql, newPassword, memberId);
        // Clear step marker cache
        passportManager.clearSign(member.getMobile(), SceneType.VALIDATE_MOBILE.name());
    }

    @Override
    public void bindMobile(String mobile) {
        Buyer buyer = UserContext.getBuyer();
        // Check whether the mobile phone number is occupied
        Member member = memberManager.getModel(buyer.getUid());
        if (member != null && !StringUtil.isEmpty(member.getMobile())) {
            throw new ServiceException(MemberErrorCode.E111.code(), "The current member has a mobile phone number bound");
        }
        List list = memberDaoSupport.queryForList("select * from es_member where mobile = ?", mobile);
        if (list.size() > 0) {
            throw new ServiceException(MemberErrorCode.E111.code(), "The current mobile phone number is occupied");
        }
        String sql = "update es_member set mobile = ? where member_id = ?";
        this.memberDaoSupport.execute(sql, mobile, buyer.getUid());
    }

    @Override
    public void changeBindMobile(String mobile) {
        Buyer buyer = UserContext.getBuyer();
        if (buyer != null) {
            // Verify whether this step is performed after mobile phone authentication
            Member member = memberManager.getModel(buyer.getUid());
            String str = StringUtil.toString(cache.get(CachePrefix.MOBILE_VALIDATE.getPrefix() + SceneType.VALIDATE_MOBILE.name() + "_" + member.getMobile()));
            if (StringUtil.isEmpty(str)) {
                throw new ServiceException(MemberErrorCode.E115.code(), "Verification of bound mobile phones is invalid");
            }
            List list = memberDaoSupport.queryForList("select * from es_member where mobile = ?", mobile);
            if (list.size() > 0) {
                throw new ServiceException(MemberErrorCode.E111.code(), "The current mobile phone number is occupied");
            }
            String sql = "update es_member set mobile = ? where member_id = ?";
            this.memberDaoSupport.execute(sql, mobile, buyer.getUid());
            // Clear step marker cache
            passportManager.clearSign(member.getMobile(), SceneType.VALIDATE_MOBILE.name());
        }
    }
}
