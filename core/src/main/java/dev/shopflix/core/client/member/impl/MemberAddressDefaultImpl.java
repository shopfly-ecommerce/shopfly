/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.member.impl;

import dev.shopflix.core.client.member.MemberAddressClient;
import dev.shopflix.core.member.model.dos.MemberAddress;
import dev.shopflix.core.member.service.MemberAddressManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 会员地址默认实现
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 下午3:54
 * @since v7.0
 */
@Service
@ConditionalOnProperty(value = "javashop.product", havingValue = "stand")
public class MemberAddressDefaultImpl implements MemberAddressClient {

    @Autowired
    private MemberAddressManager memberAddressManager;

    @Override
    public MemberAddress getModel(Integer id) {
        return memberAddressManager.getModel(id);
    }

    @Override
    public MemberAddress getDefaultAddress(Integer memberId) {
        return memberAddressManager.getDefaultAddress(memberId);
    }
}
