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
 * Coupon controller
 *
 * @author Snow
 * @version v2.0
 * @since v7.0.0
 * 2018-04-17 23:19:39
 */
@RestController
@RequestMapping("/seller/promotion/coupons")
@Api(description = "Coupon relatedAPI")
@Validated
public class CouponSellerController {

    @Autowired
    private CouponManager couponManager;


    @ApiOperation(value = "Check coupon list", response = CouponDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "start_time", value = "The start time", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "end_time", value = "By the time", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "keyword", dataType = "String", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize,
                     @ApiIgnore Long startTime, @ApiIgnore Long endTime, @ApiIgnore String keyword) {

        return this.couponManager.list(pageNo, pageSize, startTime, endTime, keyword);
    }


    @ApiOperation(value = "Add coupons", response = CouponDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "Coupon name", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "coupon_price", value = "Coupon face value", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "coupon_threshold_price", value = "Coupon threshold price", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "start_time", value = "Start time of use", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "end_time", value = "Use deadline", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "create_num", value = "circulation", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit_num", value = "Limit the amount per person", required = true, dataType = "int", paramType = "query"),

    })
    @PostMapping
    public CouponDO add(@ApiIgnore @NotEmpty(message = "Please fill in the name of coupon") String title,
                        @ApiIgnore @NotNull(message = "Please fill in the coupon value") @Max(value = 99999999, message = "The coupon value cannot exceed99999999") Double couponPrice,
                        @ApiIgnore @NotNull(message = "Please fill in the coupon threshold price") @Max(value = 99999999, message = "The coupon threshold price cannot be exceeded99999999") Double couponThresholdPrice,
                        @ApiIgnore @NotNull(message = "Please fill in the start time") Long startTime,
                        @ApiIgnore @NotNull(message = "Please fill in the deadline") Long endTime,
                        @ApiIgnore @NotNull(message = "Please fill in the circulation") Integer createNum,
                        @ApiIgnore @NotNull(message = "Please fill in the limit per person") Integer limitNum) {
        if (limitNum < 0) {
            throw new ServiceException(PromotionErrorCode.E406.code(), "The limit quantity cannot be negative");
        }
        // Check each person limit is greater than the circulation
        if (limitNum > createNum) {
            throw new ServiceException(PromotionErrorCode.E405.code(), "The quantity limit exceeds the circulation");
        }
        // Verify the coupon face value is less than the threshold price
        if (couponPrice >= couponThresholdPrice) {
            throw new ServiceException(PromotionErrorCode.E409.code(), "The coupon face value must be less than the coupon threshold price");
        }

        CouponDO couponDO = new CouponDO();
        couponDO.setTitle(title);
        couponDO.setCouponPrice(couponPrice);
        couponDO.setCouponThresholdPrice(couponThresholdPrice);
        couponDO.setCreateNum(createNum);
        couponDO.setLimitNum(limitNum);


        // Start time Start time +00:00:00 End time Start time +23:59:59
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
    @ApiOperation(value = "Modify coupons", response = CouponDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coupon_id", value = "couponsid", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "title", value = "Coupon name", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "coupon_price", value = "Coupon face value", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "coupon_threshold_price", value = "Coupon threshold price", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "start_time", value = "Start time of use", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "end_time", value = "Use deadline", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "create_num", value = "circulation", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit_num", value = "Limit the amount per person", required = true, dataType = "int", paramType = "query"),

    })
    @PostMapping
    public CouponDO add(@ApiIgnore @PathVariable("coupon_id") @NotNull(message = "Please fill in the couponid") Integer couponId,
                        @ApiIgnore @NotEmpty(message = "Please fill in the name of coupon") String title,
                        @ApiIgnore @NotNull(message = "Please fill in the coupon value") Double couponPrice,
                        @ApiIgnore @NotNull(message = "Please fill in the coupon threshold price") Double couponThresholdPrice,
                        @ApiIgnore @NotNull(message = "Please fill in the start time") Long startTime,
                        @ApiIgnore @NotNull(message = "Please fill in the deadline") Long endTime,
                        @ApiIgnore @NotNull(message = "Please fill in the circulation") Integer createNum,
                        @ApiIgnore @NotNull(message = "Please fill in the limit per person") Integer limitNum) {
        // Check each person limit is greater than the circulation
        if (limitNum > createNum) {
            throw new ServiceException(PromotionErrorCode.E405.code(), "The quantity limit exceeds the circulation");
        }

        CouponDO oldCoupon = this.couponManager.getModel(couponId);
        long currTime = DateUtil.getDateline();
        if (oldCoupon.getStartTime() <= currTime && oldCoupon.getEndTime() >= currTime) {
            throw new ServiceException(PromotionErrorCode.E405.code(), "The coupon is valid and cannot be edited");
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
    @ApiOperation(value = "Delete coupons")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "The coupon primary key to delete", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.couponManager.verifyAuth(id);
        this.couponManager.delete(id);

        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Query a coupon")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "The coupon primary key to query", required = true, dataType = "int", paramType = "path")
    })
    public CouponDO get(@PathVariable Integer id) {

        CouponDO coupon = this.couponManager.getModel(id);
        if (coupon == null) {
            throw new NoPermissionException("Data does not exist");
        }

        return coupon;
    }

    @GetMapping(value = "/{status}/list")
    @ApiOperation(value = "Get the coupon data set based on the state")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "Coupon status0：All,1：Effective,2：failure", required = true, dataType = "int", paramType = "path", allowableValues = "0,1,2", example = "0：All,1：Effective,2：failure")
    })
    public List<CouponDO> getByStatus(@PathVariable Integer status) {

        return this.couponManager.getByStatus(status);
    }

    /**
     * Parameter validation
     *
     * @param startTime
     * @param endTime
     */
    private void paramValid(Long startTime, Long endTime) {

        long nowTime = DateUtil.getDateline();

        // If the activity start time is less than the present time
        if (startTime.longValue() < nowTime) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "The start time of the activity must be greater than the current time");
        }

        // The start time cannot be later than the end time
        if (startTime.longValue() > endTime.longValue()) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "The start time cannot be later than the end time");
        }
    }

}
