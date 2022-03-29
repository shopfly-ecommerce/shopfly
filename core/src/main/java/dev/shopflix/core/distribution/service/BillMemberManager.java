/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.distribution.service;

import dev.shopflix.core.distribution.model.dos.BillMemberDO;
import dev.shopflix.core.distribution.model.dos.DistributionOrderDO;
import dev.shopflix.core.distribution.model.dto.DistributionRefundDTO;
import dev.shopflix.core.distribution.model.vo.BillMemberVO;
import dev.shopflix.core.distribution.model.vo.DistributionOrderVO;
import dev.shopflix.core.distribution.model.vo.DistributionSellbackOrderVO;
import dev.shopflix.framework.database.Page;

import java.util.List;

/**
 * 用户结算单
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 上午8:51
 */

public interface BillMemberManager {

    /**
     * 获取某个会员的下线业绩
     *
     * @param memberId
     * @param billId
     * @return
     */
    List<BillMemberVO> allDown(Integer memberId, Integer billId);


    /**
     * 获取结算单
     *
     * @param totalSn
     * @param memberId
     * @return
     */
    BillMemberDO getBillByTotalSn(String totalSn, Integer memberId);

    /**
     * 分页获取会员历史业绩
     *
     * @param memberId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<BillMemberVO> getAllByMemberId(Integer memberId, Integer pageNo, Integer pageSize);

    /**
     * 新增一个结算单
     *
     * @param billMember
     * @return
     */
    BillMemberDO add(BillMemberDO billMember);

    /**
     * 查询一个总结算单的会员结算单
     *
     * @param page
     * @param pageSize
     * @param id
     * @param uname
     * @return page
     */
    Page<BillMemberVO> page(Integer page, Integer pageSize, Integer id, String uname);


    /**
     * 获取分销商结算单
     *
     * @param billId 总结算单id
     * @return do
     */
    BillMemberDO getBillMember(Integer billId);


    /**
     * 购买商品产生的结算
     *
     * @param order
     */
    void buyShop(DistributionOrderDO order);

    /**
     * 退货商品产生的结算
     *
     * @param order
     * @param distributionRefundDTO
     */
    void returnShop(DistributionOrderDO order, DistributionRefundDTO distributionRefundDTO);

    /**
     * 获取分销账单
     *
     * @param page
     * @param pageSize
     * @param id
     * @param memberId
     * @return page
     */
    Page<DistributionOrderVO> listOrder(Integer page, Integer pageSize, Integer id, Integer memberId);


    /**
     * 获取分销退款单
     *
     * @param page
     * @param pagesize
     * @param id
     * @param memberId
     * @return page
     */
    Page<DistributionSellbackOrderVO> listSellback(Integer page, Integer pagesize, Integer id, Integer memberId);

    /**
     * 获取当前分销商当月结算单
     * @param memberId
     * @param billId
     * @return
     */
    BillMemberVO getCurrentBillMember(Integer memberId, Integer billId);

    /**
     * 获取指定sn的分销商结算单
     *
     * @param memberId
     * @param sn
     * @return do
     */
    BillMemberDO getHistoryBillMember(Integer memberId, String sn);


    /**
     * 结算单会员分页查询
     *
     * @param pageNo
     * @param pageSize
     * @param memberId
     * @return page
     */
    Page<BillMemberVO> billMemberPage(Integer pageNo, Integer pageSize, Integer memberId);

}
