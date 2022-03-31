/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.statistics.service.impl;

import dev.shopflix.core.member.model.dos.Member;
import dev.shopflix.core.statistics.model.dto.MemberRegisterData;
import dev.shopflix.core.statistics.service.MemberDataManager;
import dev.shopflix.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 会员数据注入实现
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/22 下午10:12
 *
 */
@Service
public class MemberDataManagerImpl implements MemberDataManager {

    @Autowired

    private DaoSupport daoSupport;

    @Override
    public void register(Member member) {
        this.daoSupport.insert("es_sss_member_register_data", new MemberRegisterData(member));
    }

}
