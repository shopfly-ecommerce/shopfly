/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.buyer.api.distribution;

import com.enation.app.javashop.core.distribution.exception.DistributionErrorCode;
import com.enation.app.javashop.core.distribution.exception.DistributionException;
import com.enation.app.javashop.core.distribution.model.vo.BillMemberVO;
import com.enation.app.javashop.core.distribution.model.vo.DistributionOrderVO;
import com.enation.app.javashop.core.distribution.model.vo.DistributionSellbackOrderVO;
import com.enation.app.javashop.core.distribution.service.BillMemberManager;
import com.enation.app.javashop.core.distribution.service.DistributionOrderManager;
import com.enation.app.javashop.framework.context.UserContext;
import com.enation.app.javashop.framework.database.Page;
import com.enation.app.javashop.framework.security.model.Buyer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 分销总结算
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/23 上午8:32
 */
@RestController
@Api(description = "分销总结算")
@RequestMapping("/distribution/bill")
public class BillMemberBuyerController {


    @Autowired
    private BillMemberManager billMemberManager;

    @Autowired
    private DistributionOrderManager distributionOrderManager;


    @ApiOperation("获取某会员当前月份结算单")
    @GetMapping("/member")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bill_id", value = "总结算单id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "member_id", value = "会员id 为空时获取当前登录会员的结算单", required = false, paramType = "query", dataType = "int")
    })
    public BillMemberVO billMemberVO(@ApiIgnore Integer billId, @ApiIgnore Integer memberId) {
        Buyer buyer = UserContext.getBuyer();
        if (buyer == null) {
            throw new DistributionException(DistributionErrorCode.E1001.code(), DistributionErrorCode.E1001.des());
        }
        if (memberId == null || memberId == 0) {
            memberId = buyer.getUid();
        }
        return billMemberManager.getCurrentBillMember(memberId, billId);
    }

    @ApiOperation("根据结算单获取订单信息")
    @GetMapping(value = "/order-list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_size", value = "分页大小", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page_no", value = "页码", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "bill_id", value = "总结算单id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "member_id", value = "会员id 为0代表查看当前会员业绩", required = false, paramType = "query", dataType = "int")
    })
    public Page<DistributionOrderVO> orderList(@ApiIgnore Integer memberId, @ApiIgnore Integer billId, @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        Buyer buyer = UserContext.getBuyer();
        if (buyer == null) {
            throw new DistributionException(DistributionErrorCode.E1001.code(), DistributionErrorCode.E1001.des());
        }
        if (memberId == null || memberId == 0) {
            return distributionOrderManager.pageDistributionTotalBillOrder(pageSize, pageNo, buyer.getUid(), billId);
        }
        return distributionOrderManager.pageDistributionTotalBillOrder(pageSize, pageNo, memberId, billId);
    }

    @ApiOperation("根据结算单获取退款订单信息")
    @GetMapping(value = "/sellback-order-list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_size", value = "分页大小", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page_no", value = "页码", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "bill_id", value = "结算单id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "member_id", value = "会员id 为0代表查看当前会员业绩", paramType = "query", dataType = "int")
    })
    public Page<DistributionSellbackOrderVO> sellbackOrderList(@ApiIgnore Integer memberId, @ApiIgnore Integer billId, @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        Buyer buyer = UserContext.getBuyer();
        if (buyer == null) {
            throw new DistributionException(DistributionErrorCode.E1001.code(), DistributionErrorCode.E1001.des());
        }
        if (memberId == null || memberId == 0) {
            return distributionOrderManager.pageSellBackOrder(pageSize, pageNo, buyer.getUid(), billId);
        }
        return distributionOrderManager.pageSellBackOrder(pageSize, pageNo, memberId, billId);
    }


    @ApiOperation("历史业绩")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page_size", value = "分页大小", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "member_id", value = "会员id 为0或空代表查看当前会员业绩", paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/history")
    public Page<BillMemberVO> historyBill(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @ApiIgnore Integer memberId) {
        Buyer buyer = UserContext.getBuyer();
        if (buyer == null) {
            throw new DistributionException(DistributionErrorCode.E1001.code(), DistributionErrorCode.E1001.des());
        }
        if (memberId == null || memberId == 0) {
            return billMemberManager.billMemberPage(pageNo, pageSize, buyer.getUid());
        }
        return billMemberManager.billMemberPage(pageNo, pageSize, memberId);
    }

}
