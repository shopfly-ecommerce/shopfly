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
package cloud.shopfly.b2c.api.seller.trade;

import cloud.shopfly.b2c.core.trade.order.model.dos.PayLog;
import cloud.shopfly.b2c.core.trade.order.model.dto.PayLogQueryParam;
import cloud.shopfly.b2c.core.trade.order.service.PayLogManager;
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
 * Payment order correlationAPI
 * @author Snow create in 2018/7/18
 * @version v2.0
 * @since v7.0.0
 */
@Api(description = "Payment order correlationAPI")
@RestController
@RequestMapping("/seller/trade/orders")
@Validated
public class PayLogSellerController {

    @Autowired
    private PayLogManager payLogManager;

    @ApiOperation(value = "Query the list of payments")
    @GetMapping("/pay-log")
    public Page<PayLog> list(PayLogQueryParam param) {

        Page page = this.payLogManager.list(param);

        return page;
    }


    @ApiOperation(value = "Receipt exportExcel")
    @ApiImplicitParams({
            @ApiImplicitParam(name	= "order_sn", value = "Order no.",  dataType = "String",	paramType =	"query"),
            @ApiImplicitParam(name	= "pay_way", value = "Method of payment",  dataType = "String",	paramType =	"query"),
            @ApiImplicitParam(name	= "start_time", value = "The start time",  dataType = "Long",	paramType =	"query"),
            @ApiImplicitParam(name	= "end_time", value = "The end of time",  dataType = "Long",	paramType =	"query"),
            @ApiImplicitParam(name	= "member_name", value = "Member name",  dataType = "String",	paramType =	"query")
    })
    @GetMapping(value = "/pay-log/list")
    public List<PayLog> excel(@ApiIgnore String orderSn, @ApiIgnore String payWay, @ApiIgnore String memberName,
                              @ApiIgnore Long startTime, @ApiIgnore Long endTime) {

        PayLogQueryParam param = new PayLogQueryParam();
        param.setOrderSn(orderSn);
        param.setPayWay(payWay);
        param.setMemberName(memberName);
        param.setStartTime(startTime);
        param.setEndTime(endTime);

        return payLogManager.exportExcel(param);
    }


}
