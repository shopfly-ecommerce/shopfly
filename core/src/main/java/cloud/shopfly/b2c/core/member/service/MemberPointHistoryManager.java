/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.service;

import cloud.shopfly.b2c.core.member.model.dos.MemberPointHistory;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 会员积分表业务层
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-03 15:44:12
 */
public interface MemberPointHistoryManager {

    /**
     * 查询会员积分表列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @param memberId 会员id
     * @return Page
     */
    Page list(int page, int pageSize, Integer memberId);

    /**
     * 添加会员积分表
     *
     * @param memberPointHistory 会员积分表
     * @return MemberPointHistory 会员积分表
     */
    MemberPointHistory add(MemberPointHistory memberPointHistory);


}