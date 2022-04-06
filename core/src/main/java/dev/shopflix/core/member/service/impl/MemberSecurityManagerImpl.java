/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.service.impl;

import dev.shopflix.core.base.CachePrefix;
import dev.shopflix.core.base.SceneType;
import dev.shopflix.core.base.service.SmsManager;
import dev.shopflix.core.member.MemberErrorCode;
import dev.shopflix.core.member.model.dos.Member;
import dev.shopflix.core.member.service.MemberManager;
import dev.shopflix.core.member.service.MemberSecurityManager;
import dev.shopflix.core.passport.service.PassportManager;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.context.UserContext;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.exception.ResourceNotFoundException;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.security.model.Buyer;
import dev.shopflix.framework.util.StringUtil;
import dev.shopflix.framework.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 会员安全业务实现
 *
 * @author zh
 * @version v7.0
 * @date 18/4/23 下午3:24
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
            throw new ServiceException(MemberErrorCode.E107.code(), "手机号码格式不正确");
        }
        //校验会员是否存在
        Member member = memberManager.getMemberByMobile(mobile);
        if (member != null) {
            throw new ServiceException(MemberErrorCode.E111.code(), "此手机号码已经绑定其他用户");
        }
        smsManager.sendSmsMessage("手机绑定操作", mobile, SceneType.BIND_MOBILE);
    }

    @Override
    public void sendValidateSmsCode(String mobile) {
        if (!Validator.isMobile(mobile)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "手机号码格式不正确");
        }
        //校验会员是否存在
        Member member = memberManager.getMemberByMobile(mobile);
        if (member == null) {
            throw new ResourceNotFoundException("当前会员不存在");
        }
        smsManager.sendSmsMessage("手机验证码验证", mobile, SceneType.VALIDATE_MOBILE);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updatePassword(Integer memberId, String password) {
        //校验是否经过手机验证而进行此步骤
        Member member = memberManager.getModel(memberId);
        //校验当前会员是否存在
        if (member == null) {
            throw new ResourceNotFoundException("当前会员不存在");
        }
        //校验当前会员是否被禁用
        if (!member.getDisabled().equals(0)) {
            throw new ServiceException(MemberErrorCode.E107.code(), "当前账号已经禁用");
        }
        //校验密码长度
        String newPassword = StringUtil.md5(password + member.getUname().toLowerCase());
        String sql = "update es_member set password = ? where member_id =? ";
        this.memberDaoSupport.execute(sql, newPassword, memberId);
        //清除步骤标记缓存
        passportManager.clearSign(member.getMobile(), SceneType.VALIDATE_MOBILE.name());
    }

    @Override
    public void bindMobile(String mobile) {
        Buyer buyer = UserContext.getBuyer();
        //校验手机号码是否已经被占用
        Member member = memberManager.getModel(buyer.getUid());
        if (member != null && !StringUtil.isEmpty(member.getMobile())) {
            throw new ServiceException(MemberErrorCode.E111.code(), "当前会员已经绑定手机号");
        }
        List list = memberDaoSupport.queryForList("select * from es_member where mobile = ?", mobile);
        if (list.size() > 0) {
            throw new ServiceException(MemberErrorCode.E111.code(), "当前手机号已经被占用");
        }
        String sql = "update es_member set mobile = ? where member_id = ?";
        this.memberDaoSupport.execute(sql, mobile, buyer.getUid());
    }

    @Override
    public void changeBindMobile(String mobile) {
        Buyer buyer = UserContext.getBuyer();
        if (buyer != null) {
            //校验是否经过手机验证而进行此步骤
            Member member = memberManager.getModel(buyer.getUid());
            String str = StringUtil.toString(cache.get(CachePrefix.MOBILE_VALIDATE.getPrefix() + SceneType.VALIDATE_MOBILE.name() + "_" + member.getMobile()));
            if (StringUtil.isEmpty(str)) {
                throw new ServiceException(MemberErrorCode.E115.code(), "对已绑定手机校验失效");
            }
            List list = memberDaoSupport.queryForList("select * from es_member where mobile = ?", mobile);
            if (list.size() > 0) {
                throw new ServiceException(MemberErrorCode.E111.code(), "当前手机号已经被占用");
            }
            String sql = "update es_member set mobile = ? where member_id = ?";
            this.memberDaoSupport.execute(sql, mobile, buyer.getUid());
            //清除步骤标记缓存
            passportManager.clearSign(member.getMobile(), SceneType.VALIDATE_MOBILE.name());
        }
    }
}
