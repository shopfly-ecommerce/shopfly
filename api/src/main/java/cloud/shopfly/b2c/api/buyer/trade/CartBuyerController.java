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
package cloud.shopfly.b2c.api.buyer.trade;

import cloud.shopfly.b2c.core.trade.TradeErrorCode;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuOriginVo;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartView;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PriceDetailVO;
import cloud.shopfly.b2c.core.trade.cart.service.CartOriginDataManager;
import cloud.shopfly.b2c.core.trade.cart.service.CartReadManager;
import cloud.shopfly.b2c.framework.exception.ServiceException;
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
 * Shopping cart interface
 *
 * @author Snow
 * @version v1.0
 * 2018years03month19day21:40:52
 * @since v7.0.0
 */
@Api(description = "Shopping cart interface module")
@RestController
@RequestMapping("/trade/carts")
@Validated
public class CartBuyerController {

    @Autowired
    private CartReadManager cartReadManager;


    @Autowired
    private CartOriginDataManager cartOriginDataManager;


    protected final Log logger = LogFactory.getLog(getClass());


    @ApiOperation(value = "Add a product to your shopping cart", response = CartSkuVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_id", value = "productID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "num", value = "Purchase quantity of this product", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "activity_id", value = "Default participation activitiesid", dataType = "int", paramType = "query"),
    })
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CartSkuOriginVo add(@ApiIgnore @NotNull(message = "productidCant be empty") Integer skuId,
                               @ApiIgnore @NotNull(message = "Purchase quantity cannot be empty") @Min(value = 1, message = "The number of added carts must be greater than0") Integer num,
                               @ApiIgnore Integer activityId) {

        return cartOriginDataManager.add(skuId, num, activityId);
    }


    @ApiOperation(value = "Buy now", response = CartSkuVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_id", value = "productID", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "num", value = "Purchase quantity of this product", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "activity_id", value = "Default participation activitiesid", dataType = "int", paramType = "query"),
    })
    @ResponseBody
    @PostMapping("/buy")
    public void buy(@ApiIgnore @NotNull(message = "productidCant be empty") Integer skuId,
                    @ApiIgnore @NotNull(message = "Purchase quantity cannot be empty") @Min(value = 1, message = "Purchase quantity must be greater than0") Integer num,
                    @ApiIgnore Integer activityId) {
        cartOriginDataManager.buy(skuId, num, activityId);
    }


    @ApiOperation(value = "Get shopping cart details from shopping cart page")
    @GetMapping("/all")
    public CartView cartAll() {

        try {

            return this.cartReadManager.getCartListAndCountPrice();

        } catch (Exception e) {
            logger.error("Shopping cart reading exception", e);
            return new CartView(new ArrayList<>(), new PriceDetailVO());
        }

    }


    @ApiOperation(value = "Get cart details on the settlement page")
    @GetMapping("/checked")
    public CartView cartChecked() {

        try {

            // Reads the selected list
            return this.cartReadManager.getCheckedItems();

        } catch (Exception e) {
            logger.error("The cart reading settlement page is abnormal", e);
            return new CartView(new ArrayList<>(), new PriceDetailVO());
        }


    }


    @ApiOperation(value = "Update multiple products in shopping cart", notes = "Update multiple products in shopping cart的数量或选中状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_id", value = "productidAn array of", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "checked", value = "If the selected", dataType = "int", paramType = "query", allowableValues = "0,1"),
            @ApiImplicitParam(name = "num", value = "Product quantity", dataType = "int", paramType = "query"),
    })
    @ResponseBody
    @PostMapping(value = "/sku/{sku_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@ApiIgnore @NotNull(message = "productidCant be empty") @PathVariable(name = "sku_id") Integer skuId,
                       @Min(value = 0) @Max(value = 1) Integer checked, Integer num) {
        if (checked != null) {
            cartOriginDataManager.checked(skuId, checked);

        } else if (num != null) {
            cartOriginDataManager.updateNum(skuId, num);

        }
    }


    @ApiOperation(value = "Sets all vendors to selected or unselected")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checked", value = "If the selected", required = true, dataType = "int", paramType = "query", allowableValues = "0,1"),
    })
    @ResponseBody
    @PostMapping(value = "/checked", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateAll(@NotNull(message = "You must specify whether it is selected") @Min(value = 0, message = "Check whether the parameter is abnormal") @Max(value = 1, message = "Check whether the parameter is abnormal") Integer checked) {
        if (checked != null) {
            cartOriginDataManager.checkedAll(checked);
        }

    }


    @ApiOperation(value = "Batch sets selected or unselected items of a merchant")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seller_id", value = "The sellerid", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "checked", value = "If the selected", required = true, dataType = "int", paramType = "query", allowableValues = "0,1"),
    })
    @ResponseBody
    @PostMapping(value = "/seller/{seller_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateSellerAll(@NotNull(message = "The selleridCant be empty") @PathVariable(name = "seller_id") Integer sellerId,
                                @NotNull(message = "You must specify whether it is selected") @Min(value = 0) @Max(value = 1) Integer checked) {
        if (checked != null && sellerId != null) {
            cartOriginDataManager.checkedSeller(sellerId, checked);
        }
    }


    @ApiOperation(value = "Empty shopping cart")
    @DeleteMapping()
    public void clean() {
        cartOriginDataManager.clean();
    }


    @ApiOperation(value = "Remove one or more products from the shopping cart")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_ids", value = "productid，多个product可以用英文逗号：(,) separated", required = true, dataType = "int", paramType = "path", allowMultiple = true),
    })
    @DeleteMapping(value = "/{sku_ids}/sku")
    public void delete(@PathVariable(name = "sku_ids") Integer[] skuIds) {

        if (skuIds.length == 0) {
            throw new ServiceException(TradeErrorCode.E455.code(), "Parameters of the abnormal");
        }
        cartOriginDataManager.delete(skuIds);

    }

}
