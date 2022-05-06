/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.payment.plugin.alipay.executor;

import cloud.shopfly.b2c.core.payment.model.vo.RefundBill;
import cloud.shopfly.b2c.core.payment.plugin.alipay.AlipayPluginConfig;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundStatusEnum;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.logs.Debugger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fk
 * @version v1.0
 * @Description: 支付宝退款
 * @date 2018/4/17 14:55
 * @since v7.0.0
 */
@Service
public class AlipayRefundExcutor extends AlipayPluginConfig {

	@Autowired
	private Cache cache;

	@Autowired
	private Debugger debugger;


	/**
	 * 退款
	 * @param bill
	 * @return
	 */
	public boolean refundPay(RefundBill bill) {
		try {

			Map<String, String> config = bill.getConfigMap();

			debugger.log("基础参数为", config.toString());

			//获得初始化的AlipayClient
			AlipayClient alipayClient = buildClient(config);

			//设置请求参数
			AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();

			//需要退款的金额，该金额不能大于订单金额，必填
			Double refundAmount = bill.getRefundPrice();
			//退款的原因说明
			String refundReason = "正常退款";
			//标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
			String outRequestNo = bill.getRefundSn();
			
			Map<String, String> sParaTemp = new HashMap<>(16);
			sParaTemp.put("trade_no", bill.getReturnTradeNo());
			sParaTemp.put("refund_amount", refundAmount+"");
			sParaTemp.put("refund_reason", refundReason);
			sParaTemp.put("out_request_no", outRequestNo);

			debugger.log("请求参数为：", sParaTemp.toString());

			ObjectMapper json = new ObjectMapper();
			//填充业务参数
		    alipayRequest.setBizContent(json.writeValueAsString(sParaTemp));

			debugger.log("向支付宝发出请求");
		    AlipayTradeRefundResponse response = alipayClient.execute(alipayRequest);

		    debugger.log("请求结果："+ response.isSuccess());
		    if(response.isSuccess()){
		    	return true;
		    } else {
		    	cache.put(REFUND_ERROR_MESSAGE+"_"+bill.getRefundSn(),response.getCode()+":"+response.getSubMsg() );
		    	return false;
		    }
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 查询退款进度状态
	 * @param bill
	 * @return
	 */
	public String queryRefundStatus(RefundBill bill) {

		try {

			Map<String, String> config = bill.getConfigMap();

			//获得初始化的AlipayClient
			AlipayClient alipayClient = buildClient(config);

			//设置请求参数
			AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();

			//商户订单号，商户网站订单系统中唯一订单号
			String tradeNo = bill.getReturnTradeNo();
			//请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号，必填
			String refundSn = bill.getRefundSn();

			Map<String, String> sParaTemp = new HashMap<String, String>(16);
			sParaTemp.put("trade_no", tradeNo);
			sParaTemp.put("out_request_no", refundSn);

			ObjectMapper json = new ObjectMapper();
			alipayRequest.setBizContent(json.writeValueAsString(sParaTemp));

			AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(alipayRequest);

			if(response.isSuccess()){
				if(response.getOutTradeNo()!=null){
					return RefundStatusEnum.COMPLETED.value();
				}else{
					return RefundStatusEnum.REFUNDING.value();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return RefundStatusEnum.REFUNDING.value();
	}



}
