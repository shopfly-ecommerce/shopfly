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
 * @Description: Alipay refund
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
	 * A refund
	 * @param bill
	 * @return
	 */
	public boolean refundPay(RefundBill bill) {
		try {

			Map<String, String> config = bill.getConfigMap();

			debugger.log("Basic parameters are", config.toString());

			// Get the initialized AlipayClient
			AlipayClient alipayClient = buildClient(config);

			// Setting request Parameters
			AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();

			// The amount to be refunded must not be greater than the order amount
			Double refundAmount = bill.getRefundPrice();
			// Explanation of reasons for refund
			String refundReason = "Normal refund";
			// Identifies a refund request. Multiple refunds for the same transaction must be guaranteed to be unique. If partial refunds are required, this parameter is mandatory
			String outRequestNo = bill.getRefundSn();
			
			Map<String, String> sParaTemp = new HashMap<>(16);
			sParaTemp.put("trade_no", bill.getReturnTradeNo());
			sParaTemp.put("refund_amount", refundAmount+"");
			sParaTemp.put("refund_reason", refundReason);
			sParaTemp.put("out_request_no", outRequestNo);

			debugger.log("Request parameters are：", sParaTemp.toString());

			ObjectMapper json = new ObjectMapper();
			// Populate business parameters
		    alipayRequest.setBizContent(json.writeValueAsString(sParaTemp));

			debugger.log("Send a request to Alipay");
		    AlipayTradeRefundResponse response = alipayClient.execute(alipayRequest);

		    debugger.log("Request the results："+ response.isSuccess());
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
	 * Query the refund progress
	 * @param bill
	 * @return
	 */
	public String queryRefundStatus(RefundBill bill) {

		try {

			Map<String, String> config = bill.getConfigMap();

			// Get the initialized AlipayClient
			AlipayClient alipayClient = buildClient(config);

			// Setting request Parameters
			AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();

			// Merchant order number, the unique order number in the order system of merchant website
			String tradeNo = bill.getReturnTradeNo();
			// This parameter is mandatory. If the refund request interface is not passed in the refund request, the value is the external transaction number when the transaction is created
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
