/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.api.buyer.distribution;

import cloud.shopfly.b2c.core.distribution.exception.DistributionErrorCode;
import cloud.shopfly.b2c.core.distribution.exception.DistributionException;
import cloud.shopfly.b2c.core.distribution.model.vo.BillMemberVO;
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionOrderVO;
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionSellbackOrderVO;
import cloud.shopfly.b2c.core.distribution.service.BillMemberManager;
import cloud.shopfly.b2c.core.distribution.service.DistributionOrderManager;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.security.model.Buyer;
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
