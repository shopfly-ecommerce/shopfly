/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.statistics.impl;

import dev.shopflix.core.client.statistics.MemberDataClient;
import dev.shopflix.core.member.model.dos.Member;
import dev.shopflix.core.statistics.service.MemberDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * MemberDataClientDefaultImpl
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-14 下午2:40
 */
@Service
@ConditionalOnProperty(value="shopflix.product", havingValue="stand")
public class MemberDataClientDefaultImpl implements MemberDataClient {

    @Autowired
    private MemberDataManager memberDataManager;
    /**
     * 会员注册
     * @param member 会员
     */
    @Override
    public void register(Member member) {
        memberDataManager.register(member);
    }
}