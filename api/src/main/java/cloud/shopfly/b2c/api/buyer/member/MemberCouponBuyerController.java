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
 * Membership coupon
 *
 * @author Snow create in 2018/6/13
 * @version v2.0
 * @since v7.0.0
 */
@RestController
@RequestMapping("/members/coupon")
@Api(description = "Membership coupon relatedAPI")
@Validated
public class MemberCouponBuyerController {

    @Autowired
    private MemberCouponManager memberCouponManager;

    @Autowired
    private MemberCouponClient memberCouponClient;


    @ApiOperation(value = "Check my coupon list", response = MemberCoupon.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "Coupon status0For all,1Is not used and available,2Is used,3Overdue for, 4Coupons are not available（Used and expired）", dataType = "int", paramType = "query", allowableValues = "0,1,2,3,4"),
            @ApiImplicitParam(name = "page_no", value = "Number of pages", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "A number of", dataType = "int", paramType = "query"),
    })
    @GetMapping
    public Page<MemberCoupon> list(@ApiIgnore Integer status, @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        MemberCouponQueryParam param = new MemberCouponQueryParam();
        param.setStatus(status);
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);
        return this.memberCouponManager.list(param);
    }


    @ApiOperation(value = "Users receive coupons")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coupon_id", value = "couponsid", required = true, dataType = "String", paramType = "path")
    })
    @PostMapping(value = "/{coupon_id}/receive")
    public String receiveBonus(@ApiIgnore @PathVariable("coupon_id") Integer couponId) {
        // Limit get detection
        this.memberCouponManager.checkLimitNum(couponId);
        Buyer buyer = UserContext.getBuyer();
        this.memberCouponClient.receiveBonus(buyer.getUid(), couponId);
        return "";
    }


    @ApiOperation(value = "The settlement page—Read the list of coupons available", response = MemberCoupon.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seller_ids", value = "merchantsIDA collection of", required = true, dataType = "int", paramType = "path", allowMultiple = true),
    })
    @GetMapping("/{seller_ids}")
    public List<MemberCoupon> listByCheckout(@ApiIgnore @PathVariable("seller_ids") @NotNull(message = "merchantsIDCant be empty") Integer[] sellerIds) {
        return this.memberCouponManager.listByCheckout(UserContext.getBuyer().getUid());
    }


    @ApiOperation(value = "coupons—Dont use,Has been used,Total number of expired states")
    @GetMapping("/num")
    public MemberCouponNumVO getStatusNum() {
        return this.memberCouponManager.statusNum();
    }

}
