/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 付款单相关API
 * @author Snow create in 2018/7/18
 * @version v2.0
 * @since v7.0.0
 */
@Api(description = "付款单相关API")
@RestController
@RequestMapping("/seller/trade/orders")
@Validated
public class PayLogSellerController {

    @Autowired
    private PayLogManager payLogManager;

    @ApiOperation(value = "查询付款单列表")
    @GetMapping("/pay-log")
    public Page<PayLog> list(PayLogQueryParam param) {

        Page page = this.payLogManager.list(param);

        return page;
    }


    @ApiOperation(value = "收款单导出Excel")
    @ApiImplicitParams({
            @ApiImplicitParam(name	= "order_sn", value = "订单编号",  dataType = "String",	paramType =	"query"),
            @ApiImplicitParam(name	= "pay_way", value = "支付方式",  dataType = "String",	paramType =	"query"),
            @ApiImplicitParam(name	= "start_time", value = "开始时间",  dataType = "Long",	paramType =	"query"),
            @ApiImplicitParam(name	= "end_time", value = "结束时间",  dataType = "Long",	paramType =	"query"),
            @ApiImplicitParam(name	= "member_name", value = "会员名",  dataType = "String",	paramType =	"query")
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
