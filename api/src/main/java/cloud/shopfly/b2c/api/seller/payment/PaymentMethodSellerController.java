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
package cloud.shopfly.b2c.api.seller.payment;

import cloud.shopfly.b2c.core.payment.model.dos.PaymentMethodDO;
import cloud.shopfly.b2c.core.payment.model.vo.PaymentPluginVO;
import cloud.shopfly.b2c.core.payment.service.PaymentMethodManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * Payment mode table controller
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-04-11 16:06:57
 */
@RestController
@RequestMapping("/seller/payment/payment-methods")
@Api(description = "Payment method table relatedAPI")
public class PaymentMethodSellerController {

    @Autowired
    private PaymentMethodManager paymentMethodManager;


    @ApiOperation(value = "Query the payment method list", response = PaymentMethodDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {

        return this.paymentMethodManager.list(pageNo, pageSize);
    }


    @ApiOperation(value = "Modification of payment method", response = PaymentMethodDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payment_plugin_id", value = "Pay the plug-inid", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "payment_method", value = "Payment Method object", required = true, dataType = "PaymentPluginVO", paramType = "body")
    })
    @PutMapping("/{payment_plugin_id}")
    public PaymentMethodDO add(@Valid @RequestBody @ApiIgnore PaymentPluginVO paymentMethod, @PathVariable("payment_plugin_id") String paymentPluginId) {

        return this.paymentMethodManager.add(paymentMethod, paymentPluginId);
    }

    @GetMapping(value = "/{plugin_id}")
    @ApiOperation(value = "Query a payment method")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "plugin_id", value = "The payment plug-in to queryid", required = true, dataType = "string", paramType = "path")
    })
    public PaymentPluginVO get(@PathVariable("plugin_id") String pluginId) {

        return this.paymentMethodManager.getByPlugin(pluginId);
    }

}
