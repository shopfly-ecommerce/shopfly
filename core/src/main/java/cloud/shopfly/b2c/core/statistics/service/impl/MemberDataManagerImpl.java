/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.statistics.service.impl;

import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.statistics.model.dto.MemberRegisterData;
import cloud.shopfly.b2c.core.statistics.service.MemberDataManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
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
