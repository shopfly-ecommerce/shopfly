/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.client.member;

import com.enation.app.javashop.core.member.model.dos.MemberAddress;

/**
 * 会员地址客户端
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 下午3:51
 * @since v7.0
 */

public interface MemberAddressClient {

    /**
     * 获取会员地址
     *
     * @param id 会员地址主键
     * @return MemberAddress  会员地址
     */
    MemberAddress getModel(Integer id);

    /**
     * 获取会员默认地址
     *
     * @param memberId 会员id
     * @return 会员默认地址
     */
    MemberAddress getDefaultAddress(Integer memberId);
}
