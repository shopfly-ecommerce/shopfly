/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.payment.service;

import dev.shopflix.core.base.CachePrefix;
import dev.shopflix.core.base.DomainHelper;
import dev.shopflix.core.payment.PaymentErrorCode;
import dev.shopflix.core.payment.model.enums.ClientType;
import dev.shopflix.core.payment.model.enums.TradeType;
import dev.shopflix.core.payment.model.vo.PayBill;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.JsonUtil;
import dev.shopflix.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付插件父类<br>
 * 具有读取配置的能力
 *
 * @author kingapex
 * @version 1.0
 * @since pangu1.0
 * 2017年4月3日下午11:38:38
 */
public abstract class AbstractPaymentPlugin {

    protected final Log logger = LogFactory.getLog(getClass());
    /**
     * 测试环境 0  生产环境  1
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
     * 获取插件的配置方式
     *
     * @return
     */
    protected Map<String, String> getConfig(ClientType clientType) {
        //获取当前支付插件的id
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
            throw new ServiceException(PaymentErrorCode.E502.code(), "支付方式未开启");
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
     * 获取插件id
     *
     * @return
     */
    protected abstract String getPluginId();


    /**
     * 获取同步通知url
     *
     * @param bill 交易
     * @return
     */
    protected String getReturnUrl(PayBill bill) {

        String tradeType = bill.getTradeType().name();
        String payMode = bill.getPayMode();
        String client = bill.getClientType().name();

        return domainHelper.getCallback() + "/order/pay/return/" + tradeType + "/" + payMode + "/"+ client +"/"+bill.getSn()+"/"+ this.getPluginId();
    }


    /**
     * 获取异步通知url
     *
     * @param tradeType
     * @return
     */
    protected String getCallBackUrl(TradeType tradeType, ClientType clientType) {
        return domainHelper.getCallback() + "/order/pay/callback/" + tradeType + "/" + this.getPluginId() + "/" + clientType;
    }

    /**
     * 支付回调后执行方法
     *
     * @param billSn        支付账号单
     * @param returnTradeNo 第三方平台回传支付单号
     * @param tradeType
     * @param payPrice
     */
    protected void paySuccess(String billSn, String returnTradeNo, TradeType tradeType, double payPrice) {
        //调用账单接口完成相关交易及流程的状态变更
        this.paymentBillManager.paySuccess(billSn, returnTradeNo, tradeType, payPrice);
    }


}
