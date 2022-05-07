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
 * shopfly Ali Pay client
 *
 * @author zh
 * @version v7.0
 * @date 18/7/19 In the afternoon3:21
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
        // Clear Security Settings
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
        // Prints the complete request packet
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
             * Pay attention to：
             *     The purpose of this class is to overwrite this, to formshopfly Front-end requiredform andform itemformat
             * why：
             *     Separation of the front and back ends,vueUnable to getformForm a jump
             *******************************************************************************/
            rsp.setBody(JsonUtil.objectToJson(this.getFormData(requestHolder)));
        }
        return rsp;
    }


    /**
     * Assemble interface parameters to handle encryption、Signature logic
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

        // Serialized bizModel populates bizContent only if the API contains the biz_CONTENT parameter and its value is null
        try {
            if (request.getClass().getMethod("getBizContent") != null
                    && StringUtils.isEmpty(appParams.get(AlipayConstants.BIZ_CONTENT_KEY))
                    && request.getBizModel() != null) {
                appParams.put(AlipayConstants.BIZ_CONTENT_KEY,
                        new JSONWriter().write(request.getBizModel(), true));
            }
        } catch (NoSuchMethodException e) {
            // Do nothing if you cant find getBizContent
        } catch (SecurityException e) {
            AlipayLogger.logBizError(e);
        }

        // Only new interfaces and set keys can support encryption
        if (request.isNeedEncrypt()) {

            if (StringUtils.isEmpty(appParams.get(AlipayConstants.BIZ_CONTENT_KEY))) {

                throw new AlipayApiException("The currentAPIEncrypted requests are not supported");
            }

            // To encrypt, you must set the key and encryption algorithm
            if (!StringUtils.areNotEmpty(this.encryptKey, this.encryptType)) {

                throw new AlipayApiException("APIIf the request requires encryption, the key and key type must be set：encryptKey=" + encryptKey
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
     * Tissue parameters
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
     * To obtainPOSTThe request ofbase url
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
     * GETGet jump link in mode
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
