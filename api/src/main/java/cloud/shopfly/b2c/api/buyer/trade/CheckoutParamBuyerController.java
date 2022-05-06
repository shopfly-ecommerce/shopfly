/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 结算参数控制器
 *
 * @author Snow create in 2018/4/8
 * @version v2.0
 * @since v7.0.0
 */
@Api(description = "结算参数接口模块")
@RestController
@RequestMapping("/trade/checkout-params")
@Validated
public class CheckoutParamBuyerController {

    @Autowired
    private CheckoutParamManager checkoutParamManager;


    @ApiOperation(value = "设置收货地址id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address_id", value = "收货地址id", required = true, dataType = "int", paramType = "path"),
    })
    @PostMapping(value = "/address-id/{address_id}")
    public void setAddressId(@NotNull(message = "必须指定收货地址id") @PathVariable(value = "address_id") Integer addressId) {

        //读取结算参数
        CheckoutParamVO checkoutParamVO = this.checkoutParamManager.getParam();

        //设置收货地址
        this.checkoutParamManager.setAddressId(addressId);
    }


    @ApiOperation(value = "设置支付类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payment_type", value = "支付类型 在线支付：ONLINE，货到付款：COD", required = true, dataType = "String", paramType = "query", allowableValues = "ONLINE,COD")
    })
    @PostMapping(value = "/payment-type")
    public void setPaymentType(@ApiIgnore @NotNull(message = "必须指定支付类型") String paymentType) {


        PaymentTypeEnum paymentTypeEnum = PaymentTypeEnum.valueOf(paymentType.toUpperCase());

        //检测是否支持货到付款
        this.checkoutParamManager.checkCod(paymentTypeEnum);

        this.checkoutParamManager.setPaymentType(paymentTypeEnum);

    }

    @ApiOperation(value = "设置发票信息")
    @PostMapping(value = "/receipt")
    public void setReceipt(@Valid ReceiptVO receiptVO) {
        if (StringUtil.isEmpty(receiptVO.getReceiptTitle())) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "发票抬头必填");
        }
        if (StringUtil.isEmpty(receiptVO.getReceiptContent())) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "发票内容必填");
        }
        //如果发票不为个人的时候 需要校验发票税号
        if (!receiptVO.getType().equals(0) && StringUtil.isEmpty(receiptVO.getTaxNo())) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "发票税号必填");
        }
        receiptVO.setReceiptType(ReceiptTypeEnum.VATORDINARY.name());
        this.checkoutParamManager.setReceipt(receiptVO);
    }

    @ApiOperation(value = "取消发票")
    @DeleteMapping(value = "/receipt")
    public void delReceipt() {
        checkoutParamManager.deleteReceipt();
    }


    @ApiOperation(value = "设置送货时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "receive_time", value = "送货时间", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping(value = "/receive-time")
    public void setReceiveTime(@ApiIgnore @NotNull(message = "必须指定送货时间") String receiveTime) {

        this.checkoutParamManager.setReceiveTime(receiveTime);

    }


    @ApiOperation(value = "设置订单备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "remark", value = "订单备注", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping(value = "/remark")
    public void setRemark(String remark) {

        this.checkoutParamManager.setRemark(remark);
    }


    @ApiOperation(value = "获取结算参数", response = CheckoutParamVO.class)
    @ResponseBody
    @GetMapping()
    public CheckoutParamVO get() {
        return this.checkoutParamManager.getParam();
    }

}
