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

import cloud.shopfly.b2c.core.promotion.halfprice.model.dos.HalfPriceDO;
import cloud.shopfly.b2c.core.promotion.halfprice.model.vo.HalfPriceVO;
import cloud.shopfly.b2c.core.promotion.halfprice.service.HalfPriceManager;
import cloud.shopfly.b2c.core.promotion.tool.support.PromotionValid;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.NoPermissionException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * Second half price controller
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-23 19:53:42
 */
@RestController
@RequestMapping("/seller/promotion/half-prices")
@Api(description = "The second one is half priceAPI")
@Validated
public class HalfPriceSellerController {

    @Autowired
    private HalfPriceManager halfPriceManager;


    @ApiOperation(value = "Check the second half price list", response = HalfPriceDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keywords", value = "keyword", dataType = "String", paramType = "query"),
    })
    @GetMapping
    public Page<HalfPriceVO> list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @ApiIgnore String keywords) {

        return this.halfPriceManager.list(pageNo, pageSize, keywords);
    }


    @ApiOperation(value = "Add a second piece for half price", response = HalfPriceVO.class)
    @PostMapping
    public HalfPriceVO add(@Valid @RequestBody HalfPriceVO halfPrice) {

        PromotionValid.paramValid(halfPrice.getStartTime(), halfPrice.getEndTime(),
                halfPrice.getRangeType(), halfPrice.getGoodsList());
        this.halfPriceManager.add(halfPrice);
        return halfPrice;
    }


    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Modify the second half price", response = HalfPriceDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "A primary key", required = true, dataType = "int", paramType = "path")
    })
    public HalfPriceVO edit(@Valid @RequestBody HalfPriceVO halfPrice, @PathVariable Integer id) {

        PromotionValid.paramValid(halfPrice.getStartTime(), halfPrice.getEndTime(),
                halfPrice.getRangeType(), halfPrice.getGoodsList());

        halfPrice.setHpId(id);
        this.halfPriceManager.verifyAuth(id);
        this.halfPriceManager.edit(halfPrice, id);

        return halfPrice;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete the second piece at half price")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "The second half-price primary key to delete", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.halfPriceManager.verifyAuth(id);
        this.halfPriceManager.delete(id);

        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Enquire a second piece half price")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "The second half-price primary key to query", required = true, dataType = "int", paramType = "path")
    })
    public HalfPriceVO get(@PathVariable Integer id) {
        HalfPriceVO halfPrice = this.halfPriceManager.getFromDB(id);
        // Verify unauthorized operations
        if (halfPrice == null) {
            throw new NoPermissionException("Have the right to operate");
        }
        return halfPrice;
    }

}
