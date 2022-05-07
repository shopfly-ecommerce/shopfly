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

import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountDO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.model.vo.FullDiscountVO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.service.FullDiscountManager;
import cloud.shopfly.b2c.core.promotion.tool.support.PromotionValid;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.NoPermissionException;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.exception.SystemErrorCodeV1;
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
 * Full discount activity controller
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-30 17:34:32
 */
@RestController
@RequestMapping("/seller/promotion/full-discounts")
@Api(description = "Full discount activities relatedAPI")
@Validated
public class FullDiscountSellerController {

    @Autowired
    private FullDiscountManager fullDiscountManager;


    @ApiOperation(value = "Check the list of full offers", response = FullDiscountDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keywords", value = "keyword", dataType = "String", paramType = "query"),
    })
    @GetMapping
    public Page<FullDiscountVO> list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @ApiIgnore String keywords) {

        return this.fullDiscountManager.list(pageNo, pageSize, keywords);
    }


    @ApiOperation(value = "Add full discount activities", response = FullDiscountVO.class)
    @PostMapping
    public FullDiscountVO add(@Valid @RequestBody FullDiscountVO fullDiscountVO) {

        this.verifyFullDiscountParam(fullDiscountVO);
        this.fullDiscountManager.add(fullDiscountVO);

        return fullDiscountVO;
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Modify full discount activities", response = FullDiscountDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "A primary key", required = true, dataType = "int", paramType = "path")
    })
    public FullDiscountVO edit(@Valid @RequestBody FullDiscountVO fullDiscountVO, @PathVariable Integer id) {

        fullDiscountVO.setFdId(id);
        this.verifyFullDiscountParam(fullDiscountVO);
        this.fullDiscountManager.verifyAuth(id);
        this.fullDiscountManager.edit(fullDiscountVO, id);

        return fullDiscountVO;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete full discount activities")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Primary key for full offers to delete", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.fullDiscountManager.verifyAuth(id);
        this.fullDiscountManager.delete(id);

        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Query a full discount event")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "To query the full discount activity key", required = true, dataType = "int", paramType = "path")
    })
    public FullDiscountVO get(@PathVariable Integer id) {

        FullDiscountVO fullDiscount = this.fullDiscountManager.getModel(id);
        // Verify unauthorized operations
        if (fullDiscount == null) {
            throw new NoPermissionException("Have the right to operate");
        }

        return fullDiscount;
    }


    /**
     * Verify parameter information of full discount activity
     *
     * @param fullDiscountVO
     */
    private void verifyFullDiscountParam(FullDiscountVO fullDiscountVO) {

        PromotionValid.paramValid(fullDiscountVO.getStartTime(), fullDiscountVO.getEndTime(),
                fullDiscountVO.getRangeType(), fullDiscountVO.getGoodsList());

        // You should choose at least one of the promotional offers, not all of them
        if (fullDiscountVO.getIsFullMinus() == null && fullDiscountVO.getIsFreeShip() == null && fullDiscountVO.getIsSendGift() == null
                && fullDiscountVO.getIsSendBonus() == null && fullDiscountVO.getIsDiscount() == null) {

            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "Please select a discount option");
        }

        // If the promotional offer details are included full minus not empty
        if (fullDiscountVO.getIsFullMinus() != null && fullDiscountVO.getIsFullMinus() == 1) {

            if (fullDiscountVO.getMinusValue() == null || fullDiscountVO.getMinusValue() == 0) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "Please fill in the amount deducted");
            }

            // The reduced cash must be less than the preferential threshold
            if (fullDiscountVO.getMinusValue() > fullDiscountVO.getFullMoney()) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "The reduction amount cannot be greater than the threshold amount");
            }

        }

        // If the promotional activity offers detailed whether to include full giveaway is not empty
        if (fullDiscountVO.getIsSendGift() != null && fullDiscountVO.getIsSendGift() == 1) {
            // Gift ID cannot be 0
            if (fullDiscountVO.getGiftId() == null || fullDiscountVO.getGiftId() == 0) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "Please select freebies");
            }
        }

        // If the promotional offer details are included full send coupons are not empty
        if (fullDiscountVO.getIsSendBonus() != null && fullDiscountVO.getIsSendBonus() == 1) {
            // The coupon ID cannot be 0
            if (fullDiscountVO.getBonusId() == null || fullDiscountVO.getBonusId() == 0) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "Please select coupons");
            }
        }

        // If the promotion offers details whether to include a discount is not empty
        if (fullDiscountVO.getIsDiscount() != null && fullDiscountVO.getIsDiscount() == 1) {
            // Discounted values cannot be empty or 0
            if (fullDiscountVO.getDiscountValue() == null || fullDiscountVO.getDiscountValue() == 0) {

                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "Please fill in the discount value");
            }
            // The discount value should be between 0 and 10
            if (fullDiscountVO.getDiscountValue() >= 10 || fullDiscountVO.getDiscountValue() <= 0) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "The discount value should be between0to10between");
            }
        }

    }
}
