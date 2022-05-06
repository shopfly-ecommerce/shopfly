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
package cloud.shopfly.b2c.api.seller.promotion;

import cloud.shopfly.b2c.core.promotion.PromotionErrorCode;
import cloud.shopfly.b2c.core.promotion.coupon.model.dos.CouponDO;
import cloud.shopfly.b2c.core.promotion.coupon.service.CouponManager;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.NoPermissionException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.exception.SystemErrorCodeV1;
import cloud.shopfly.b2c.framework.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 优惠券控制器
 *
 * @author Snow
 * @version v2.0
 * @since v7.0.0
 * 2018-04-17 23:19:39
 */
@RestController
@RequestMapping("/seller/promotion/coupons")
@Api(description = "优惠券相关API")
@Validated
public class CouponSellerController {

    @Autowired
    private CouponManager couponManager;


    @ApiOperation(value = "查询优惠券列表", response = CouponDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "start_time", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "end_time", value = "截止时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "关键字", dataType = "String", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize,
                     @ApiIgnore Long startTime, @ApiIgnore Long endTime, @ApiIgnore String keyword) {

        return this.couponManager.list(pageNo, pageSize, startTime, endTime, keyword);
    }


    @ApiOperation(value = "添加优惠券", response = CouponDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "优惠券名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "coupon_price", value = "优惠券面额", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "coupon_threshold_price", value = "优惠券门槛价格", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "start_time", value = "使用起始时间", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "end_time", value = "使用截止时间", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "create_num", value = "发行量", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit_num", value = "每人限领数量", required = true, dataType = "int", paramType = "query"),

    })
    @PostMapping
    public CouponDO add(@ApiIgnore @NotEmpty(message = "请填写优惠券名称") String title,
                        @ApiIgnore @NotNull(message = "请填写优惠券面额") @Max(value = 99999999, message = "优惠券面额不能超过99999999") Double couponPrice,
                        @ApiIgnore @NotNull(message = "请填写优惠券门槛价格") @Max(value = 99999999, message = "优惠券门槛价格不能超过99999999") Double couponThresholdPrice,
                        @ApiIgnore @NotNull(message = "请填写起始时间") Long startTime,
                        @ApiIgnore @NotNull(message = "请填写截止时间") Long endTime,
                        @ApiIgnore @NotNull(message = "请填写发行量") Integer createNum,
                        @ApiIgnore @NotNull(message = "请填写每人限领数量") Integer limitNum) {
        if (limitNum < 0) {
            throw new ServiceException(PromotionErrorCode.E406.code(), "限领数量不能为负数");
        }
        //校验每人限领数是都大于发行量
        if (limitNum > createNum) {
            throw new ServiceException(PromotionErrorCode.E405.code(), "限领数量超出发行量");
        }
        //校验优惠券面额是否小于门槛价格
        if (couponPrice >= couponThresholdPrice) {
            throw new ServiceException(PromotionErrorCode.E409.code(), "优惠券面额必须小于优惠券门槛价格");
        }

        CouponDO couponDO = new CouponDO();
        couponDO.setTitle(title);
        couponDO.setCouponPrice(couponPrice);
        couponDO.setCouponThresholdPrice(couponThresholdPrice);
        couponDO.setCreateNum(createNum);
        couponDO.setLimitNum(limitNum);


        //开始时间取前段+00:00:00 结束时间取前段+23:59:59
        String startStr = DateUtil.toString(startTime, "yyyy-MM-dd");
        String endStr = DateUtil.toString(endTime, "yyyy-MM-dd");

        couponDO.setStartTime(DateUtil.getDateline(startStr + " 00:00:00"));
        couponDO.setEndTime(DateUtil.getDateline(endStr + " 23:59:59", "yyyy-MM-dd hh:mm:ss"));

        this.paramValid(startTime, endTime);

        couponDO.setReceivedNum(0);
        couponDO.setUsedNum(0);
        this.couponManager.add(couponDO);

        return couponDO;
    }

    @PutMapping(value = "/{coupon_id}")
    @ApiOperation(value = "修改优惠券", response = CouponDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coupon_id", value = "优惠券id", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "title", value = "优惠券名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "coupon_price", value = "优惠券面额", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "coupon_threshold_price", value = "优惠券门槛价格", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "start_time", value = "使用起始时间", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "end_time", value = "使用截止时间", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "create_num", value = "发行量", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit_num", value = "每人限领数量", required = true, dataType = "int", paramType = "query"),

    })
    @PostMapping
    public CouponDO add(@ApiIgnore @PathVariable("coupon_id") @NotNull(message = "请填写优惠券id") Integer couponId,
                        @ApiIgnore @NotEmpty(message = "请填写优惠券名称") String title,
                        @ApiIgnore @NotNull(message = "请填写优惠券面额") Double couponPrice,
                        @ApiIgnore @NotNull(message = "请填写优惠券门槛价格") Double couponThresholdPrice,
                        @ApiIgnore @NotNull(message = "请填写起始时间") Long startTime,
                        @ApiIgnore @NotNull(message = "请填写截止时间") Long endTime,
                        @ApiIgnore @NotNull(message = "请填写发行量") Integer createNum,
                        @ApiIgnore @NotNull(message = "请填写每人限领数量") Integer limitNum) {
        //校验每人限领数是都大于发行量
        if (limitNum > createNum) {
            throw new ServiceException(PromotionErrorCode.E405.code(), "限领数量超出发行量");
        }

        CouponDO oldCoupon = this.couponManager.getModel(couponId);
        long currTime = DateUtil.getDateline();
        if (oldCoupon.getStartTime() <= currTime && oldCoupon.getEndTime() >= currTime) {
            throw new ServiceException(PromotionErrorCode.E405.code(), "优惠券已生效，不可进行编辑操作");
        }

        CouponDO couponDO = new CouponDO();
        couponDO.setCouponId(couponId);
        couponDO.setTitle(title);
        couponDO.setCouponPrice(couponPrice);
        couponDO.setCouponThresholdPrice(couponThresholdPrice);
        couponDO.setStartTime(startTime);
        couponDO.setEndTime(endTime);
        couponDO.setCreateNum(createNum);
        couponDO.setLimitNum(limitNum);

        this.paramValid(couponDO.getStartTime(), couponDO.getEndTime());
        this.couponManager.verifyAuth(couponId);
        this.couponManager.edit(couponDO, couponId);
        return couponDO;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要删除的优惠券主键", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.couponManager.verifyAuth(id);
        this.couponManager.delete(id);

        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询一个优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的优惠券主键", required = true, dataType = "int", paramType = "path")
    })
    public CouponDO get(@PathVariable Integer id) {

        CouponDO coupon = this.couponManager.getModel(id);
        if (coupon == null) {
            throw new NoPermissionException("数据不存在");
        }

        return coupon;
    }

    @GetMapping(value = "/{status}/list")
    @ApiOperation(value = "根据状态获取优惠券数据集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "优惠券状态 0：全部，1：有效，2：失效", required = true, dataType = "int", paramType = "path", allowableValues = "0,1,2", example = "0：全部，1：有效，2：失效")
    })
    public List<CouponDO> getByStatus(@PathVariable Integer status) {

        return this.couponManager.getByStatus(status);
    }

    /**
     * 参数验证
     *
     * @param startTime
     * @param endTime
     */
    private void paramValid(Long startTime, Long endTime) {

        long nowTime = DateUtil.getDateline();

        //如果活动起始时间小于现在时间
        if (startTime.longValue() < nowTime) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "活动起始时间必须大于当前时间");
        }

        // 开始时间不能大于结束时间
        if (startTime.longValue() > endTime.longValue()) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "活动起始时间不能大于活动结束时间");
        }
    }

}
