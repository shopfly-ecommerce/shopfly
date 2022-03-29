/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.promotion;

import dev.shopflix.core.promotion.coupon.model.dos.CouponDO;
import dev.shopflix.core.promotion.coupon.service.CouponManager;
import dev.shopflix.framework.database.Page;
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
 * 优惠券相关API
 * @author Snow create in 2018/7/13
 * @version v2.0
 * @since v7.0.0
 */
@RestController
@RequestMapping("/promotions/coupons")
@Api(description = "优惠券相关API")
@Validated
public class CouponBuyerController {

    @Autowired
    private CouponManager couponManager;

    @ApiOperation(value = "查询优惠券列表")
    @GetMapping()
    public List<CouponDO> getList(){

        List<CouponDO>  couponDOList = this.couponManager.getList();
        return couponDOList;
    }


    @ApiOperation(value = "查询所有优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(name	= "page_no", value = "页码", dataType = "int",	paramType =	"query"),
            @ApiImplicitParam(name	= "page_size", value = "条数", dataType = "int",	paramType =	"query"),
    })
    @GetMapping(value = "/all")
    public Page<CouponDO> getPage(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize){
        Page<CouponDO> page = this.couponManager.all(pageNo,pageSize);
        return page;
    }


}
