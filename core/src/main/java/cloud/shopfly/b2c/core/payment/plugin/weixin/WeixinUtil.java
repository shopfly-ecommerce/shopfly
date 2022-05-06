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
package cloud.shopfly.b2c.core.payment.plugin.weixin;

import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.*;


/**
 * @author fk
 * @version v2.0
 * @Description: 微信支付使用工具
 * @date 2018/4/12 10:25
 * @since v7.0.0
 */
public class WeixinUtil {


    /**
     * 生成签名
     *
     * @param params 参数map
     * @param key    支付key(API密钥)
     * @return
     */
    public static String createSign(Map params, String key) {

        String url = createLinkString(params);
        url = url + "&key=" + key;
        String sign = DigestUtils.md5Hex(url).toUpperCase();
        return sign;
    }


    /**
     * 生成签名，没有key
     *
     * @param params
     * @return
     */
    public static String createSign(Map params) {

        String url = createLinkString(params);
        String sign = DigestUtils.md5Hex(url).toUpperCase();
        return sign;
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        org.w3c.dom.Document document = WXPayXmlUtil.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key : data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString();
        try {
            writer.close();
        } catch (Exception ex) {
        }
        return output;
    }


    /**
     * 将一个xml转为map
     *
     * @param document
     * @return
     */
    public static Map xmlToMap(Document document) {


        Element rootEl = document.getRootElement();
        List<Element> elList = rootEl.elements();
        Map map = new HashMap(elList.size());
        for (Element el : elList) {
            String name = el.getName();
            String text = el.getText();
            map.put(name, text);
        }
        return map;
    }

    public static String doc2String(Document document) {
        String s = "";
        try {
            // 使用输出流来进行转化
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 使用UTF-8编码

            OutputFormat format = new OutputFormat("   ", true, "UTF-8");
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            s = out.toString("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return s;
    }

    public static Document post(String wsPart, String docStr) {

        BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory())
                        .build(),
                null,
                null,
                null
        );
        try {

            HttpClient httpClient = HttpClientBuilder.create()
                    .setConnectionManager(connManager)
                    .build();
            HttpPost httppost = new HttpPost(wsPart);
            HttpEntity requestEntity = new StringEntity(docStr, "UTF-8");

            httppost.setEntity(requestEntity);
            httppost.addHeader("Content-Type", "text/xml");
            HttpResponse httpresponse = httpClient.execute(httppost);
            HttpEntity rentity = httpresponse.getEntity();
            InputStream in = rentity.getContent();
            if (in != null) {
                SAXReader reader = new SAXReader();
                reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                Document doc = reader.read(in);
                return doc;
            } else {
                throw new RuntimeException("post uri[" + wsPart
                        + "]发生错误:[stream 返回Null]");
            }
        } catch (Throwable e) {

            throw new RuntimeException("post uri [" + wsPart + "]发生错误", e);

        }
    }


    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        StringBuffer prestr = new StringBuffer();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if ("sign".equals(key)) {
                continue;
            }
            // 拼接时，不包括最后一个&字符
            if ("".equals(prestr.toString())) {
                prestr.append(key + "=" + value);
            } else {
                prestr.append("&" + key + "=" + value);
            }
        }
        return prestr.toString();
    }

    public static Document verifyCertPost(String wsPart, String docStr, String mchid, String p12Path) {

        try {
            // 证书
            char[] password = mchid.toCharArray();
            KeyStore ks = KeyStore.getInstance("PKCS12");
            FileInputStream certStream = new FileInputStream(new File(p12Path));
            ks.load(certStream, password);

            // 实例化密钥库 & 初始化密钥工厂
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, password);

            // 创建 SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    new DefaultHostnameVerifier());

            BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", sslConnectionSocketFactory)
                            .build(),
                    null,
                    null,
                    null
            );


            HttpClient httpClient = HttpClientBuilder.create()
                    .setConnectionManager(connManager)
                    .build();

            HttpPost httpPost = new HttpPost(wsPart);


            StringEntity postEntity = new StringEntity(docStr, "UTF-8");
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.addHeader("User-Agent", "wxpay sdk java v1.0 " + mchid);
            httpPost.setEntity(postEntity);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream in = httpEntity.getContent();
            if (in != null) {
                SAXReader reader = new SAXReader();
                reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                Document doc = reader.read(in);
                return doc;
            } else {
                throw new RuntimeException("post uri[" + wsPart + "]发生错误:[stream 返回Null]");
            }
        } catch (Throwable e) {
            throw new RuntimeException("post uri [" + wsPart + "]发生错误", e);

        }
    }

    /**
     * 是否是微信打开
     *
     * @return
     */
    public static int isWeChat() {
        String agent = ThreadContextHolder.getHttpRequest().getHeader("User-agent");
        if (agent.toLowerCase().indexOf("micromessenger") > 0) {
            return 1;
        }
        return 0;
    }


    /**
     * 获取session中的openId
     * 这个方法不会主动去获取openId，需要页面中去获取，放到session当中
     *
     * @return
     * @throws IOException
     */
    public static String getUnionId() throws IOException {
        String unionId = (String) ThreadContextHolder.getHttpRequest().getSession().getAttribute(WeixinPuginConfig.UNIONID_SESSION_KEY);
        if (!StringUtil.isEmpty(unionId)) {
            return unionId;
        } else {
            //kingapex 注释于2016年9月22日，解决微信注册时，没有正确的配置微信时，会报404的错误
            return "程序错误";
        }

    }

    /**
     * 获取session中的openId
     * 这个方法不会主动去获取openId，需要页面中去获取，放到session当中
     *
     * @return
     * @throws IOException
     */
    public static String getOpenId() throws IOException {
        String openid = (String) ThreadContextHolder.getHttpRequest().getSession().getAttribute(WeixinPuginConfig.OPENID_SESSION_KEY);
        if (!StringUtil.isEmpty(openid)) {
            return openid;
        } else {
            //kingapex 注释于2016年9月22日，解决微信注册时，没有正确的配置微信时，会报404的错误
            return "程序错误";
        }

    }
}
