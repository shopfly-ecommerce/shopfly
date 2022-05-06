/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.statistics.service;

import cloud.shopfly.b2c.core.member.model.dos.Member;

/**
 * 会员信息收集manager
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/8 下午4:10
 */

public interface MemberDataManager {


    /**
     * 会员注册
     *
     * @param member 会员
     */
    void register(Member member);


}
