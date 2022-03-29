/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.seller.trade;

import dev.shopflix.core.system.SystemErrorCode;
import dev.shopflix.core.system.service.WaybillManager;
import dev.shopflix.core.trade.cart.model.dos.OrderPermission;
import dev.shopflix.core.trade.order.model.enums.ShipStatusEnum;
import dev.shopflix.core.trade.order.model.vo.DeliveryVO;
import dev.shopflix.core.trade.order.service.OrderOperateManager;
import dev.shopflix.core.trade.order.service.OrderQueryManager;
import dev.shopflix.core.trade.sdk.model.OrderDetailDTO;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.StringUtil;
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
 * 电子面单生成API
 *
 * @author zh
 * @version v7.0
 * @date 18/6/11 下午4:56
 * @since v7.0
 */

@RestController
@RequestMapping("/seller/waybill")
@Api(description = "电子面单生成api")
@Validated
public class WayBillSellerController {

    @Autowired
    private WaybillManager waybillManager;

    @Autowired
    private OrderOperateManager orderOperateManager;
    @Autowired
    private OrderQueryManager orderQueryManager;


    @ApiOperation(value = "电子面单生成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "订单编号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "logistics_id", value = "物流公司id", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping
    public String create(@RequestParam(value = "order_sn") @NotEmpty(message = "订单编号不能为空") String orderSn, @RequestParam(value = "logistics_id") @NotNull(message = "物流公司id不能为空") Integer logisticsId, @RequestParam(value = "logistics_name") String logisticsName) {
        String result = this.waybillManager.createPrintData(orderSn, logisticsId);
        //获取电子面单的快递单号
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
