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
package cloud.shopfly.b2c.core.payment.service;

import cloud.shopfly.b2c.core.payment.PaymentErrorCode;
import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.base.DomainHelper;
import cloud.shopfly.b2c.framework.cache.Cache;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Payment plug-in parent class<br>
 * The ability to read configurations
 *
 * @author kingapex
 * @version 1.0
 * @since pangu1.0
 * 2017years4month3On the afternoon11:38:38
 */
public abstract class AbstractPaymentPlugin {

    protected final Log logger = LogFactory.getLog(getClass());
    /**
     * The test environment0  The production environment1
     */
    protected int isTest = 0;


    public static final String SUCCESS = "SUCCESS";

    public static final String REFUND_ERROR_MESSAGE = "{REFUND_ERROR_MESSAGE}";

    @Autowired
    
    private DaoSupport daoSupport;

    @Autowired
    private PaymentBillManager paymentBillManager;

    @Autowired
    private DomainHelper domainHelper;

    @Autowired
    private Cache cache;


    /**
     * Gets how the plug-in is configured
     *
     * @return
     */
    protected Map<String, String> getConfig(ClientType clientType) {
        // Gets the ID of the current payment plug-in
        String paymentMethodId = this.getPluginId();

        String config = (String) cache.get(CachePrefix.PAYMENT_CONFIG.getPrefix() + clientType.getDbColumn() + paymentMethodId);

        if (config == null) {
            config = daoSupport.queryForString("select " + clientType.getDbColumn() + " from es_payment_method where plugin_id=?", paymentMethodId);
            cache.put(CachePrefix.PAYMENT_CONFIG.getPrefix() + clientType.getDbColumn() + paymentMethodId, config);
        }

        if (StringUtil.isEmpty(config)) {
            return new HashMap<>(16);
        }

        Map map = JsonUtil.jsonToObject(config, Map.class);
        List<Map> list = (List<Map>) map.get("config_list");
        if (!"1".equals(map.get("is_open").toString())) {
            throw new ServiceException(PaymentErrorCode.E502.code(), "The payment method is not enabled");
        }

        Map<String, String> result = new HashMap<>(list.size());
        if (list != null) {
            for (Map item : list) {
                result.put(item.get("name").toString(), item.get("value").toString());
            }
        }


        return result;
    }


    /**
     * To get the pluginid
     *
     * @return
     */
    protected abstract String getPluginId();


    /**
     * Getting synchronization Notificationsurl
     *
     * @param bill trading
     * @return
     */
    protected String getReturnUrl(PayBill bill) {

        String tradeType = bill.getTradeType().name();
        String payMode = bill.getPayMode();
        String client = bill.getClientType().name();

        return domainHelper.getCallback() + "/order/pay/return/" + tradeType + "/" + payMode + "/"+ client +"/"+bill.getSn()+"/"+ this.getPluginId();
    }


    /**
     * Getting asynchronous notificationsurl
     *
     * @param tradeType
     * @return
     */
    protected String getCallBackUrl(TradeType tradeType, ClientType clientType) {
        return domainHelper.getCallback() + "/order/pay/callback/" + tradeType + "/" + this.getPluginId() + "/" + clientType;
    }

    /**
     * Execute method after payment callback
     *
     * @param billSn        Payment account slip
     * @param returnTradeNo The third-party platform sends back the payment order number
     * @param tradeType
     * @param payPrice
     */
    protected void paySuccess(String billSn, String returnTradeNo, TradeType tradeType, double payPrice) {
        // Invokes the billing interface to complete state changes for related transactions and processes
        this.paymentBillManager.paySuccess(billSn, returnTradeNo, tradeType, payPrice);
    }


}
