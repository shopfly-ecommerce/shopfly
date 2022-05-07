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
package cloud.shopfly.b2c.api.buyer.promotion;

import cloud.shopfly.b2c.core.promotion.coupon.model.dos.CouponDO;
import cloud.shopfly.b2c.core.promotion.coupon.service.CouponManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Coupon relatedAPI
 * @author Snow create in 2018/7/13
 * @version v2.0
 * @since v7.0.0
 */
@RestController
@RequestMapping("/promotions/coupons")
@Api(description = "Coupon relatedAPI")
@Validated
public class CouponBuyerController {

    @Autowired
    private CouponManager couponManager;

    @ApiOperation(value = "Check coupon list")
    @GetMapping()
    public List<CouponDO> getList(){

        List<CouponDO>  couponDOList = this.couponManager.getList();
        return couponDOList;
    }


    @ApiOperation(value = "Query all coupons")
    @ApiImplicitParams({
            @ApiImplicitParam(name	= "page_no", value = "The page number", dataType = "int",	paramType =	"query"),
            @ApiImplicitParam(name	= "page_size", value = "A number of", dataType = "int",	paramType =	"query"),
    })
    @GetMapping(value = "/all")
    public Page<CouponDO> getPage(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize){
        Page<CouponDO> page = this.couponManager.all(pageNo,pageSize);
        return page;
    }


}
