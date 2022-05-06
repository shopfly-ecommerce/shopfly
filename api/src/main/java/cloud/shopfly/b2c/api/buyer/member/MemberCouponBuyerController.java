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
package cloud.shopfly.b2c.api.buyer.member;

import cloud.shopfly.b2c.core.client.member.MemberCouponClient;
import cloud.shopfly.b2c.core.member.model.dos.MemberCoupon;
import cloud.shopfly.b2c.core.member.model.dto.MemberCouponQueryParam;
import cloud.shopfly.b2c.core.member.model.vo.MemberCouponNumVO;
import cloud.shopfly.b2c.core.member.service.MemberCouponManager;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 会员优惠券
 *
 * @author Snow create in 2018/6/13
 * @version v2.0
 * @since v7.0.0
 */
@RestController
@RequestMapping("/members/coupon")
@Api(description = "会员优惠券相关API")
@Validated
public class MemberCouponBuyerController {

    @Autowired
    private MemberCouponManager memberCouponManager;

    @Autowired
    private MemberCouponClient memberCouponClient;


    @ApiOperation(value = "查询我的优惠券列表", response = MemberCoupon.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "优惠券状态 0为全部，1为未使用且可用，2为已使用，3为已过期, 4为不可用优惠券（已使用和已过期）", dataType = "int", paramType = "query", allowableValues = "0,1,2,3,4"),
            @ApiImplicitParam(name = "page_no", value = "页数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "条数", dataType = "int", paramType = "query"),
    })
    @GetMapping
    public Page<MemberCoupon> list(@ApiIgnore Integer status, @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        MemberCouponQueryParam param = new MemberCouponQueryParam();
        param.setStatus(status);
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);
        return this.memberCouponManager.list(param);
    }


    @ApiOperation(value = "用户领取优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coupon_id", value = "优惠券id", required = true, dataType = "String", paramType = "path")
    })
    @PostMapping(value = "/{coupon_id}/receive")
    public String receiveBonus(@ApiIgnore @PathVariable("coupon_id") Integer couponId) {
        //限领检测
        this.memberCouponManager.checkLimitNum(couponId);
        Buyer buyer = UserContext.getBuyer();
        this.memberCouponClient.receiveBonus(buyer.getUid(), couponId);
        return "";
    }


    @ApiOperation(value = "结算页—读取可用的优惠券列表", response = MemberCoupon.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seller_ids", value = "商家ID集合", required = true, dataType = "int", paramType = "path", allowMultiple = true),
    })
    @GetMapping("/{seller_ids}")
    public List<MemberCoupon> listByCheckout(@ApiIgnore @PathVariable("seller_ids") @NotNull(message = "商家ID不能为空") Integer[] sellerIds) {
        return this.memberCouponManager.listByCheckout(UserContext.getBuyer().getUid());
    }


    @ApiOperation(value = "优惠券—未使用,已使用,已过期状态总数量")
    @GetMapping("/num")
    public MemberCouponNumVO getStatusNum() {
        return this.memberCouponManager.statusNum();
    }

}
