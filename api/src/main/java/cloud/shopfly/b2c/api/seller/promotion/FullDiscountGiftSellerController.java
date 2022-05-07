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

import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.service.FullDiscountGiftManager;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.NoPermissionException;
import cloud.shopfly.b2c.framework.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Full discount gift controller
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-30 17:34:46
 */
@RestController
@RequestMapping("/seller/promotion/full-discount-gifts")
@Api(description = "Full discount related giftsAPI")
@Validated
public class FullDiscountGiftSellerController {

    @Autowired
    private FullDiscountGiftManager fullDiscountGiftManager;


    @ApiOperation(value = "Check the full discount gift list", response = FullDiscountGiftDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "Keyword query", dataType = "String", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, String keyword) {

        return this.fullDiscountGiftManager.list(pageNo, pageSize, keyword);
    }


    @ApiOperation(value = "Add full discount gift", response = FullDiscountGiftDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gift_name", value = "Name of gift", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gift_price", value = "Gift amount", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "gift_img", value = "Gifts pictures", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "actual_store", value = "Inventory", required = true, dataType = "int", paramType = "query"),
    })
    @PostMapping
    public FullDiscountGiftDO add(@ApiIgnore @NotEmpty(message = "Please fill in the name of the gift") String giftName,
                                  @ApiIgnore @NotNull(message = "Please fill in the amount of gift") Double giftPrice,
                                  @ApiIgnore @NotEmpty(message = "Please upload pictures of the gifts") String giftImg,
                                  @ApiIgnore @NotNull(message = "Please fill in the inventory") Integer actualStore) {

        FullDiscountGiftDO giftDO = new FullDiscountGiftDO();
        giftDO.setGiftName(giftName);
        giftDO.setGiftPrice(giftPrice);
        giftDO.setGiftImg(giftImg);
        giftDO.setActualStore(actualStore);

        // Set available inventory for giveaways (Available inventory when adding = actual inventory)
        giftDO.setEnableStore(actualStore);

        // Added the creation time of giveaways
        giftDO.setCreateTime(DateUtil.getDateline());

        // Set the gift type (version 1.0 gift type defaults to 0: normal gift)
        giftDO.setGiftType(0);

        // Disable by default
        giftDO.setDisabled(0);

        this.fullDiscountGiftManager.add(giftDO);

        return giftDO;
    }

    @PutMapping(value = "/{gift_id}")
    @ApiOperation(value = "Modify full discount gifts", response = FullDiscountGiftDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gift_id", value = "The giftsid", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "gift_name", value = "Name of gift", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gift_name", value = "Name of gift", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gift_price", value = "Gift amount", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "gift_img", value = "Gifts pictures", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "actual_store", value = "Inventory", required = true, dataType = "int", paramType = "query"),
    })
    public FullDiscountGiftDO edit(@ApiIgnore @PathVariable("gift_id") Integer giftId,
                                   @ApiIgnore @NotEmpty(message = "Please fill in the name of the gift") String giftName,
                                   @ApiIgnore @NotNull(message = "Please fill in the amount of gift") Double giftPrice,
                                   @ApiIgnore @NotEmpty(message = "Please upload pictures of the gifts") String giftImg,
                                   @ApiIgnore @NotNull(message = "Please fill in the inventory") Integer actualStore) {

        this.fullDiscountGiftManager.verifyAuth(giftId);
        FullDiscountGiftDO giftDO = new FullDiscountGiftDO();
        giftDO.setGiftId(giftId);
        giftDO.setGiftName(giftName);
        giftDO.setGiftPrice(giftPrice);
        giftDO.setGiftImg(giftImg);
        giftDO.setActualStore(actualStore);

        // Set available inventory for giveaways (Available inventory when adding = actual inventory)
        giftDO.setEnableStore(actualStore);

        // Added the creation time of giveaways
        giftDO.setCreateTime(DateUtil.getDateline());

        // Set the gift type (version 1.0 gift type defaults to 0: normal gift)
        giftDO.setGiftType(0);

        // Disable by default
        giftDO.setDisabled(0);

        this.fullDiscountGiftManager.edit(giftDO, giftId);

        return giftDO;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete full discount gifts")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "To remove the full discount giveaway main key", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.fullDiscountGiftManager.verifyAuth(id);
        this.fullDiscountGiftManager.delete(id);

        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Query for a full discount giveaway")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "To query the full discount gift master key", required = true, dataType = "int", paramType = "path")
    })
    public FullDiscountGiftDO get(@PathVariable Integer id) {

        FullDiscountGiftDO fullDiscountGift = this.fullDiscountGiftManager.getModel(id);
        // Verify unauthorized operations
        if (fullDiscountGift == null) {
            throw new NoPermissionException("Have the right to operate");
        }
        return fullDiscountGift;
    }

    @ApiOperation(value = "Query full discount gift collection", response = FullDiscountGiftDO.class)
    @GetMapping(value = "/all")
    public List<FullDiscountGiftDO> listAll() {
        return this.fullDiscountGiftManager.listAll();
    }

}
