/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.trade;

import dev.shopflix.core.trade.TradeErrorCode;
import dev.shopflix.core.trade.cart.model.vo.CartSkuOriginVo;
import dev.shopflix.core.trade.cart.model.vo.CartSkuVO;
import dev.shopflix.core.trade.cart.model.vo.CartView;
import dev.shopflix.core.trade.cart.model.vo.PriceDetailVO;
import dev.shopflix.core.trade.cart.service.CartOriginDataManager;
import dev.shopflix.core.trade.cart.service.CartReadManager;
import dev.shopflix.framework.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * 购物车接口
 *
 * @author Snow
 * @version v1.0
 * 2018年03月19日21:40:52
 * @since v7.0.0
 */
@Api(description = "购物车接口模块")
@RestController
@RequestMapping("/trade/carts")
@Validated
public class CartBuyerController {

    @Autowired
    private CartReadManager cartReadManager;


    @Autowired
    private CartOriginDataManager cartOriginDataManager;


    protected final Log logger = LogFactory.getLog(getClass());


    @ApiOperation(value = "向购物车中添加一个产品", response = CartSkuVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_id", value = "产品ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "num", value = "此产品的购买数量", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "activity_id", value = "默认参与的活动id", dataType = "int", paramType = "query"),
    })
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CartSkuOriginVo add(@ApiIgnore @NotNull(message = "产品id不能为空") Integer skuId,
                               @ApiIgnore @NotNull(message = "购买数量不能为空") @Min(value = 1, message = "加入购物车数量必须大于0") Integer num,
                               @ApiIgnore Integer activityId) {

        return cartOriginDataManager.add(skuId, num, activityId);
    }


    @ApiOperation(value = "立即购买", response = CartSkuVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_id", value = "产品ID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "num", value = "此产品的购买数量", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "activity_id", value = "默认参与的活动id", dataType = "int", paramType = "query"),
    })
    @ResponseBody
    @PostMapping("/buy")
    public void buy(@ApiIgnore @NotNull(message = "产品id不能为空") Integer skuId,
                    @ApiIgnore @NotNull(message = "购买数量不能为空") @Min(value = 1, message = "购买数量必须大于0") Integer num,
                    @ApiIgnore Integer activityId) {
        cartOriginDataManager.buy(skuId, num, activityId);
    }


    @ApiOperation(value = "获取购物车页面购物车详情")
    @GetMapping("/all")
    public CartView cartAll() {

        try {

            return this.cartReadManager.getCartListAndCountPrice();

        } catch (Exception e) {
            logger.error("读取购物车异常", e);
            return new CartView(new ArrayList<>(), new PriceDetailVO());
        }

    }


    @ApiOperation(value = "获取结算页面购物车详情")
    @GetMapping("/checked")
    public CartView cartChecked() {

        try {

            // 读取选中的列表
            return this.cartReadManager.getCheckedItems();

        } catch (Exception e) {
            logger.error("读取结算页的购物车异常", e);
            return new CartView(new ArrayList<>(), new PriceDetailVO());
        }


    }


    @ApiOperation(value = "更新购物车中的多个产品", notes = "更新购物车中的多个产品的数量或选中状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_id", value = "产品id数组", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "checked", value = "是否选中", dataType = "int", paramType = "query", allowableValues = "0,1"),
            @ApiImplicitParam(name = "num", value = "产品数量", dataType = "int", paramType = "query"),
    })
    @ResponseBody
    @PostMapping(value = "/sku/{sku_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@ApiIgnore @NotNull(message = "产品id不能为空") @PathVariable(name = "sku_id") Integer skuId,
                       @Min(value = 0) @Max(value = 1) Integer checked, Integer num) {
        if (checked != null) {
            cartOriginDataManager.checked(skuId, checked);

        } else if (num != null) {
            cartOriginDataManager.updateNum(skuId, num);

        }
    }


    @ApiOperation(value = "设置全部商为选中或不选中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checked", value = "是否选中", required = true, dataType = "int", paramType = "query", allowableValues = "0,1"),
    })
    @ResponseBody
    @PostMapping(value = "/checked", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateAll(@NotNull(message = "必须指定是否选中") @Min(value = 0, message = "是否选中参数异常") @Max(value = 1, message = "是否选中参数异常") Integer checked) {
        if (checked != null) {
            cartOriginDataManager.checkedAll(checked);
        }

    }


    @ApiOperation(value = "批量设置某商家的商品为选中或不选中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seller_id", value = "卖家id", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "checked", value = "是否选中", required = true, dataType = "int", paramType = "query", allowableValues = "0,1"),
    })
    @ResponseBody
    @PostMapping(value = "/seller/{seller_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateSellerAll(@NotNull(message = "卖家id不能为空") @PathVariable(name = "seller_id") Integer sellerId,
                                @NotNull(message = "必须指定是否选中") @Min(value = 0) @Max(value = 1) Integer checked) {
        if (checked != null && sellerId != null) {
            cartOriginDataManager.checkedSeller(sellerId, checked);
        }
    }


    @ApiOperation(value = "清空购物车")
    @DeleteMapping()
    public void clean() {
        cartOriginDataManager.clean();
    }


    @ApiOperation(value = "删除购物车中的一个或多个产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_ids", value = "产品id，多个产品可以用英文逗号：(,) 隔开", required = true, dataType = "int", paramType = "path", allowMultiple = true),
    })
    @DeleteMapping(value = "/{sku_ids}/sku")
    public void delete(@PathVariable(name = "sku_ids") Integer[] skuIds) {

        if (skuIds.length == 0) {
            throw new ServiceException(TradeErrorCode.E455.code(), "参数异常");
        }
        cartOriginDataManager.delete(skuIds);

    }

}
