/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.client.member;

import com.enation.app.javashop.core.member.model.vo.GoodsGrade;
import com.enation.app.javashop.core.trade.sdk.model.OrderDetailDTO;

import java.util.List;

/**
 * @author fk
 * @version v1.0
 * @Description: 会员评论对外接口
 * @date 2018/7/26 11:34
 * @since v7.0.0
 */
public interface MemberCommentClient {

    /**
     * 查询商品的好评比例
     *
     * @return
     */
    List<GoodsGrade> queryGoodsGrade();

    /**
     * 根据商品id获取评论数
     *
     * @param goodsId 商品id
     * @return 评论数
     */
    Integer getGoodsCommentCount(Integer goodsId);

    /**
     * 自动好评
     * @param detailDTOList
     */
    void autoGoodComments(List<OrderDetailDTO> detailDTOList);

    /**
     * 根据会员id修改头像信息
     *
     * @param memberId 会员id
     * @param face     头像
     */
    void editComment(Integer memberId, String face);
}
