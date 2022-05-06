/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.service;

import cloud.shopfly.b2c.core.member.model.dos.MemberAsk;
import cloud.shopfly.b2c.core.member.model.dto.CommentQueryParam;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 咨询业务层
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-04 17:41:18
 */
public interface MemberAskManager {

    /**
     * 查询咨询列表
     *
     * @param param 查询条件
     * @return Page
     */
    Page list(CommentQueryParam param);

    /**
     * 添加咨询
     *
     * @param askContent 咨询
     * @param goodsId    商品id
     * @return MemberAsk 咨询
     */
    MemberAsk add(String askContent, Integer goodsId);

    /**
     * 删除咨询
     *
     * @param id 咨询主键
     */
    void delete(Integer id);

    /**
     * 获取咨询
     *
     * @param id 咨询主键
     * @return MemberAsk  咨询
     */
    MemberAsk getModel(Integer id);

    /**
     * 回复咨询
     *
     * @param replyContent
     * @param askId
     * @return
     */
    MemberAsk reply(String replyContent, Integer askId);

    /**
     * 卖家获取未回复的咨询数量
     *
     * @return
     */
    Integer getNoReplyCount();

    /**
     * 管理端审核会员咨询信息
     *
     * @param askId
     * @param authStatus WAIT_AUDIT("待审核"),PASS_AUDIT("审核通过"),REFUSE_AUDIT("审核拒绝");
     */
    void auth(Integer askId, String authStatus);
}