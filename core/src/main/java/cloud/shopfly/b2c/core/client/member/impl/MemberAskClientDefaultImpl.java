/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.member.impl;

import cloud.shopfly.b2c.core.client.member.MemberAskClient;
import cloud.shopfly.b2c.core.member.service.MemberAskManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author fk
 * @version v1.0
 * @Description: 评论对外接口实现
 * @date 2018/7/26 11:30
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class MemberAskClientDefaultImpl implements MemberAskClient {

    @Autowired
    private MemberAskManager memberAskManager;

    @Override
    public Integer getNoReplyCount() {

        return memberAskManager.getNoReplyCount();
    }
}
