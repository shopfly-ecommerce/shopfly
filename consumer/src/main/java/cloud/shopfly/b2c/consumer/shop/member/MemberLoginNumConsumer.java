/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.consumer.shop.member;

import cloud.shopfly.b2c.consumer.core.event.MemberLoginEvent;
import cloud.shopfly.b2c.core.client.member.MemberClient;
import cloud.shopfly.b2c.core.member.model.vo.MemberLoginMsg;
import cloud.shopfly.b2c.framework.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会员登录后登录次数进行处理
 *
 * @author zh
 * @version v7.0
 * @date 18/4/12 下午5:38
 * @since v7.0
 */
@Service
public class MemberLoginNumConsumer implements MemberLoginEvent {

    @Autowired
    private MemberClient memberClient;

    /**
     * 会员登录后对会员某些字段进行更新 例如 上次登录时间、登录次数
     *
     * @param memberLoginMsg
     */
    @Override
    public void memberLogin(MemberLoginMsg memberLoginMsg) {
        this.memberClient.updateLoginNum(memberLoginMsg.getMemberId(), DateUtil.getDateline());
    }
}
