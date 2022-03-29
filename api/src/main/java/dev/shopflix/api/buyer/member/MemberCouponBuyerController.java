/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.member;

import dev.shopflix.core.client.member.MemberCouponClient;
import dev.shopflix.core.member.model.dos.MemberCoupon;
import dev.shopflix.core.member.model.dto.MemberCouponQueryParam;
import dev.shopflix.core.member.model.vo.MemberCouponNumVO;
import dev.shopflix.core.member.service.MemberCouponManager;
import dev.shopflix.framework.context.UserContext;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.security.model.Buyer;
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
