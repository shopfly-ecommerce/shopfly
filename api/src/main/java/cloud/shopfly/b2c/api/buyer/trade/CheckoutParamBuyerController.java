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

import cloud.shopfly.b2c.core.member.model.enums.ReceiptTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PaymentTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.CheckoutParamVO;
import cloud.shopfly.b2c.core.trade.order.model.vo.ReceiptVO;
import cloud.shopfly.b2c.core.trade.order.service.CheckoutParamManager;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.exception.SystemErrorCodeV1;
import cloud.shopfly.b2c.framework.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Settlement parameter controller
 *
 * @author Snow create in 2018/4/8
 * @version v2.0
 * @since v7.0.0
 */
@Api(description = "Clearing parameters Interface module")
@RestController
@RequestMapping("/trade/checkout-params")
@Validated
public class CheckoutParamBuyerController {

    @Autowired
    private CheckoutParamManager checkoutParamManager;


    @ApiOperation(value = "Set the shipping addressid")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address_id", value = "Shipping addressid", required = true, dataType = "int", paramType = "path"),
    })
    @PostMapping(value = "/address-id/{address_id}")
    public void setAddressId(@NotNull(message = "A receiving address must be specifiedid") @PathVariable(value = "address_id") Integer addressId) {

        // Read settlement parameters
        CheckoutParamVO checkoutParamVO = this.checkoutParamManager.getParam();

        // Set the shipping address
        this.checkoutParamManager.setAddressId(addressId);
    }


    @ApiOperation(value = "Setting the payment Type")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payment_type", value = "Payment type Online payment：ONLINECash on delivery：COD", required = true, dataType = "String", paramType = "query", allowableValues = "ONLINE,COD")
    })
    @PostMapping(value = "/payment-type")
    public void setPaymentType(@ApiIgnore @NotNull(message = "The payment type must be specified") String paymentType) {


        PaymentTypeEnum paymentTypeEnum = PaymentTypeEnum.valueOf(paymentType.toUpperCase());

        // Check whether cash on delivery is supported
        this.checkoutParamManager.checkCod(paymentTypeEnum);

        this.checkoutParamManager.setPaymentType(paymentTypeEnum);

    }

    @ApiOperation(value = "Setting invoice Information")
    @PostMapping(value = "/receipt")
    public void setReceipt(@Valid ReceiptVO receiptVO) {
        if (StringUtil.isEmpty(receiptVO.getReceiptTitle())) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "The invoice title must be filled in");
        }
        if (StringUtil.isEmpty(receiptVO.getReceiptContent())) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "The invoice content is mandatory");
        }
        // If the invoice is not personal, it needs to check the invoice tax number
        if (!receiptVO.getType().equals(0) && StringUtil.isEmpty(receiptVO.getTaxNo())) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "Tax invoice number must be filled in");
        }
        receiptVO.setReceiptType(ReceiptTypeEnum.VATORDINARY.name());
        this.checkoutParamManager.setReceipt(receiptVO);
    }

    @ApiOperation(value = "Cancel the invoice")
    @DeleteMapping(value = "/receipt")
    public void delReceipt() {
        checkoutParamManager.deleteReceipt();
    }


    @ApiOperation(value = "Set delivery time")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "receive_time", value = "Delivery time", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping(value = "/receive-time")
    public void setReceiveTime(@ApiIgnore @NotNull(message = "Delivery times must be specified") String receiveTime) {

        this.checkoutParamManager.setReceiveTime(receiveTime);

    }


    @ApiOperation(value = "Set order remarks")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "remark", value = "The order note", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping(value = "/remark")
    public void setRemark(String remark) {

        this.checkoutParamManager.setRemark(remark);
    }


    @ApiOperation(value = "Get settlement parameters", response = CheckoutParamVO.class)
    @ResponseBody
    @GetMapping()
    public CheckoutParamVO get() {
        return this.checkoutParamManager.getParam();
    }

}
