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

import cloud.shopfly.b2c.core.system.SystemErrorCode;
import cloud.shopfly.b2c.core.system.service.WaybillManager;
import cloud.shopfly.b2c.core.trade.cart.model.dos.OrderPermission;
import cloud.shopfly.b2c.core.trade.order.model.enums.ShipStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.DeliveryVO;
import cloud.shopfly.b2c.core.trade.order.service.OrderOperateManager;
import cloud.shopfly.b2c.core.trade.order.service.OrderQueryManager;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The electron surface is formedAPI
 *
 * @author zh
 * @version v7.0
 * @date 18/6/11 In the afternoon4:56
 * @since v7.0
 */

@RestController
@RequestMapping("/seller/waybill")
@Api(description = "The electron surface is formedapi")
@Validated
public class WayBillSellerController {

    @Autowired
    private WaybillManager waybillManager;

    @Autowired
    private OrderOperateManager orderOperateManager;
    @Autowired
    private OrderQueryManager orderQueryManager;


    @ApiOperation(value = "The electron surface is formed")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "Order no.", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "logistics_id", value = "Logistics companyid", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping
    public String create(@RequestParam(value = "order_sn") @NotEmpty(message = "The order number cannot be blank") String orderSn, @RequestParam(value = "logistics_id") @NotNull(message = "Logistics companyidCant be empty") Integer logisticsId, @RequestParam(value = "logistics_name") String logisticsName) {
        String result = this.waybillManager.createPrintData(orderSn, logisticsId);
        // Obtain the tracking number of the electronic surface order
        JSONObject resultJson = JSONObject.fromObject(result);
        if (resultJson.get("Success").equals(false)) {
            throw new ServiceException(SystemErrorCode.E911.code(), resultJson.get("Reason").toString());
        }
        Object order = resultJson.get("Order");
        JSONObject orders = JSONObject.fromObject(order);
        String logisticCode = (String) orders.get("LogisticCode");
        String template = StringUtil.toString(resultJson.get("PrintTemplate"));

        OrderDetailDTO model = orderQueryManager.getModel(orderSn);
        if (model.getLogiId() == null || ShipStatusEnum.SHIP_NO.equals(model.getShipStatus())) {
            DeliveryVO delivery = new DeliveryVO();
            delivery.setDeliveryNo(logisticCode);
            delivery.setOrderSn(orderSn);
            delivery.setLogiId(logisticsId);
            delivery.setLogiName(logisticsName);
            orderOperateManager.ship(delivery, OrderPermission.admin);
        }
        return template;
    }

}
