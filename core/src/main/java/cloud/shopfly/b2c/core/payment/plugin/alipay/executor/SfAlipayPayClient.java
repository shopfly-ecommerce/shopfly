/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.payment.plugin.alipay.executor;

import cloud.shopfly.b2c.core.payment.model.vo.Form;
import cloud.shopfly.b2c.core.payment.model.vo.FormItem;
import com.alipay.api.*;
import com.alipay.api.internal.util.*;
import com.alipay.api.internal.util.json.JSONWriter;
import cloud.shopfly.b2c.framework.util.JsonUtil;

import java.io.IOException;
import java.security.Security;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * shopflix 阿里支付客户端
 *
 * @author zh
 * @version v7.0
 * @date 18/7/19 下午3:21
 * @since v7.0
 */

public class SfAlipayPayClient extends DefaultAlipayClient {

    private String serverUrl;
    private String appId;
    private String privateKey;
    private String prodCode;
    private String format = AlipayConstants.FORMAT_JSON;
    private String signType = AlipayConstants.SIGN_TYPE_RSA;
    private String encryptKey;
    private String encryptType = AlipayConstants.ENCRYPT_TYPE_AES;


    private String charset;

    static {
        //清除安全设置
        Security.setProperty("jdk.certpath.disabledAlgorithms", "");
    }

    public SfAlipayPayClient(String serverUrl, String appId, String privateKey) {
        super(serverUrl, appId, privateKey);

        this.serverUrl = serverUrl;
        this.appId = appId;
        this.privateKey = privateKey;

    }

    public SfAlipayPayClient(String serverUrl, String appId, String privateKey, String format) {
        super(serverUrl, appId, privateKey, format);
        this.serverUrl = serverUrl;
        this.appId = appId;
        this.privateKey = privateKey;
        this.format = format;
    }

    public SfAlipayPayClient(String serverUrl, String appId, String privateKey, String format, String charset) {
        super(serverUrl, appId, privateKey, format, charset);
        this.serverUrl = serverUrl;
        this.appId = appId;
        this.privateKey = privateKey;
        this.format = format;
        this.charset = charset;

    }

    public SfAlipayPayClient(String serverUrl, String appId, String privateKey, String format, String charset, String alipayPublicKey) {
        super(serverUrl, appId, privateKey, format, charset, alipayPublicKey);
        this.serverUrl = serverUrl;
        this.appId = appId;
        this.privateKey = privateKey;
        this.format = format;
        this.charset = charset;

    }

    public SfAlipayPayClient(String serverUrl, String appId, String privateKey, String format, String charset, String alipayPublicKey, String signType) {
        super(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
        this.serverUrl = serverUrl;
        this.appId = appId;
        this.privateKey = privateKey;
        this.format = format;
        this.charset = charset;
        this.signType = signType;
    }

    public SfAlipayPayClient(String serverUrl, String appId, String privateKey, String format, String charset, String alipayPublicKey, String signType, String proxyHost, int proxyPort) {
        super(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType, proxyHost, proxyPort);
        this.serverUrl = serverUrl;
        this.appId = appId;
        this.privateKey = privateKey;
        this.format = format;
        this.charset = charset;
        this.signType = signType;
    }

    public SfAlipayPayClient(String serverUrl, String appId, String privateKey, String format, String charset, String alipayPublicKey, String signType, String encryptKey, String encryptType) {
        super(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType, encryptKey, encryptType);
        this.serverUrl = serverUrl;
        this.appId = appId;
        this.privateKey = privateKey;
        this.format = format;
        this.charset = charset;
        this.signType = signType;
        this.encryptType = encryptType;
    }

    public SfAlipayPayClient(CertAlipayRequest certAlipayRequest) throws AlipayApiException {
        super(certAlipayRequest);
    }

    @Override
    public <T extends AlipayResponse> T pageExecute(AlipayRequest<T> request) throws AlipayApiException {
        return pageExecute(request, "POST");
    }

    @Override
    public <T extends AlipayResponse> T pageExecute(AlipayRequest<T> request,
                                                    String httpMethod) throws AlipayApiException {
        RequestParametersHolder requestHolder = getRequestHolderWithSign(request, null, null);
        // 打印完整请求报文
        if (AlipayLogger.isBizDebugEnabled()) {
            AlipayLogger.logBizDebug(getRedirectUrl(requestHolder));
        }
        T rsp = null;
        try {
            Class<T> clazz = request.getResponseClass();
            rsp = clazz.newInstance();
        } catch (InstantiationException e) {
            AlipayLogger.logBizError(e);
        } catch (IllegalAccessException e) {
            AlipayLogger.logBizError(e);
        }
        if ("GET".equalsIgnoreCase(httpMethod)) {
            rsp.setBody(getRedirectUrl(requestHolder));
        } else {
            /*******************************************************************************
             * 注意：
             *     这个类的作用就是覆写这里，目的是形成 shopflix 前端需要的form 和form item格式
             * 原因：
             *     前后端分离，vue无法通过form表单跳转
             *******************************************************************************/
            rsp.setBody(JsonUtil.objectToJson(this.getFormData(requestHolder)));
        }
        return rsp;
    }


    /**
     * 组装接口参数，处理加密、签名逻辑
     *
     * @param request
     * @param accessToken
     * @param appAuthToken
     * @return
     * @throws AlipayApiException
     */
    private <T extends AlipayResponse> RequestParametersHolder getRequestHolderWithSign(AlipayRequest<?> request,
                                                                                        String accessToken,
                                                                                        String appAuthToken) throws AlipayApiException {
        RequestParametersHolder requestHolder = new RequestParametersHolder();
        AlipayHashMap appParams = new AlipayHashMap(request.getTextParams());

        // 仅当API包含biz_content参数且值为空时，序列化bizModel填充bizContent
        try {
            if (request.getClass().getMethod("getBizContent") != null
                    && StringUtils.isEmpty(appParams.get(AlipayConstants.BIZ_CONTENT_KEY))
                    && request.getBizModel() != null) {
                appParams.put(AlipayConstants.BIZ_CONTENT_KEY,
                        new JSONWriter().write(request.getBizModel(), true));
            }
        } catch (NoSuchMethodException e) {
            // 找不到getBizContent则什么都不做
        } catch (SecurityException e) {
            AlipayLogger.logBizError(e);
        }

        // 只有新接口和设置密钥才能支持加密
        if (request.isNeedEncrypt()) {

            if (StringUtils.isEmpty(appParams.get(AlipayConstants.BIZ_CONTENT_KEY))) {

                throw new AlipayApiException("当前API不支持加密请求");
            }

            // 需要加密必须设置密钥和加密算法
            if (!StringUtils.areNotEmpty(this.encryptKey, this.encryptType)) {

                throw new AlipayApiException("API请求要求加密，则必须设置密钥和密钥类型：encryptKey=" + encryptKey
                        + ",encryptType=" + encryptType);
            }

            String encryptContent = AlipayEncrypt.encryptContent(
                    appParams.get(AlipayConstants.BIZ_CONTENT_KEY), this.encryptType, this.encryptKey,
                    this.charset);

            appParams.put(AlipayConstants.BIZ_CONTENT_KEY, encryptContent);
        }

        if (!StringUtils.isEmpty(appAuthToken)) {
            appParams.put(AlipayConstants.APP_AUTH_TOKEN, appAuthToken);
        }

        requestHolder.setApplicationParams(appParams);

        if (StringUtils.isEmpty(charset)) {
            charset = AlipayConstants.CHARSET_UTF8;
        }

        AlipayHashMap protocalMustParams = new AlipayHashMap();
        protocalMustParams.put(AlipayConstants.METHOD, request.getApiMethodName());
        protocalMustParams.put(AlipayConstants.VERSION, request.getApiVersion());
        protocalMustParams.put(AlipayConstants.APP_ID, this.appId);
        protocalMustParams.put(AlipayConstants.SIGN_TYPE, this.signType);
        protocalMustParams.put(AlipayConstants.TERMINAL_TYPE, request.getTerminalType());
        protocalMustParams.put(AlipayConstants.TERMINAL_INFO, request.getTerminalInfo());
        protocalMustParams.put(AlipayConstants.NOTIFY_URL, request.getNotifyUrl());
        protocalMustParams.put(AlipayConstants.RETURN_URL, request.getReturnUrl());
        protocalMustParams.put(AlipayConstants.CHARSET, charset);

        if (request.isNeedEncrypt()) {
            protocalMustParams.put(AlipayConstants.ENCRYPT_TYPE, this.encryptType);
        }

        Long timestamp = System.currentTimeMillis();
        DateFormat df = new SimpleDateFormat(AlipayConstants.DATE_TIME_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone(AlipayConstants.DATE_TIMEZONE));
        protocalMustParams.put(AlipayConstants.TIMESTAMP, df.format(new Date(timestamp)));
        requestHolder.setProtocalMustParams(protocalMustParams);

        AlipayHashMap protocalOptParams = new AlipayHashMap();
        protocalOptParams.put(AlipayConstants.FORMAT, format);
        protocalOptParams.put(AlipayConstants.ACCESS_TOKEN, accessToken);
        protocalOptParams.put(AlipayConstants.ALIPAY_SDK, AlipayConstants.SDK_VERSION);
        protocalOptParams.put(AlipayConstants.PROD_CODE, request.getProdCode());
        requestHolder.setProtocalOptParams(protocalOptParams);

        if (!StringUtils.isEmpty(this.signType)) {

            String signContent = AlipaySignature.getSignatureContent(requestHolder);
            protocalMustParams.put(AlipayConstants.SIGN,
                    AlipaySignature.rsaSign(signContent, privateKey, charset, this.signType));

        } else {
            protocalMustParams.put(AlipayConstants.SIGN, "");
        }
        return requestHolder;
    }

    /**
     * 组织参数
     *
     * @param requestHolder
     * @return
     * @throws AlipayApiException
     */
    private Form getFormData(RequestParametersHolder requestHolder) throws AlipayApiException {
        if (requestHolder.getApplicationParams() != null && !requestHolder.getApplicationParams().isEmpty()) {
            Form form = new Form();
            List<FormItem> formItems = new ArrayList<>();
            Set<String> keys = requestHolder.getApplicationParams().keySet();
            Iterator var3 = keys.iterator();
            while (var3.hasNext()) {
                FormItem item = new FormItem();
                String key = (String) var3.next();
                String value = requestHolder.getApplicationParams().get(key);
                item.setItemName(key);
                item.setItemValue(value.replace("\"", "&quot;"));
                formItems.add(item);
            }
            form.setFormItems(formItems);
            form.setGatewayUrl(this.getRequestUrl(requestHolder).toString());
            return form;
        }
        return null;
    }

    /**
     * 获取POST请求的base url
     *
     * @param requestHolder
     * @return
     * @throws AlipayApiException
     */
    private String getRequestUrl(RequestParametersHolder requestHolder) throws AlipayApiException {
        StringBuilder urlSb = new StringBuilder(serverUrl);
        try {
            String sysMustQuery = WebUtils.buildQuery(loadTest ?
                    LoadTestUtil.getParamsWithLoadTestFlag(requestHolder.getProtocalMustParams())
                    : requestHolder.getProtocalMustParams(), charset);
            String sysOptQuery = WebUtils.buildQuery(requestHolder.getProtocalOptParams(), charset);

            urlSb.append("?");
            urlSb.append(sysMustQuery);
            if (sysOptQuery != null && sysOptQuery.length() > 0) {
                urlSb.append("&");
                urlSb.append(sysOptQuery);
            }
        } catch (IOException e) {
            throw new AlipayApiException(e);
        }

        return urlSb.toString();
    }


    /**
     * GET模式下获取跳转链接
     *
     * @param requestHolder
     * @return
     * @throws AlipayApiException
     */
    private String getRedirectUrl(RequestParametersHolder requestHolder) throws AlipayApiException {
        StringBuilder urlSb = new StringBuilder(serverUrl);
        try {
            Map<String, String> sortedMap = AlipaySignature.getSortedMap(requestHolder);
            String sortedQuery = WebUtils.buildQuery(loadTest ?
                    LoadTestUtil.getParamsWithLoadTestFlag(sortedMap) : sortedMap, charset);
            urlSb.append("?");
            urlSb.append(sortedQuery);
        } catch (IOException e) {
            throw new AlipayApiException(e);
        }

        return urlSb.toString();
    }
}
