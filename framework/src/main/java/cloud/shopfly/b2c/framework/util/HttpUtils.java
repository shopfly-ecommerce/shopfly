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
package cloud.shopfly.b2c.framework.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * Httptool
 * Created by kingapex on 2018/3/13.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/13
 */
public final class HttpUtils {

    public static final int HTTP_CONN_TIMEOUT = 100000;
    public static final int HTTP_SOCKET_TIMEOUT = 100000;

    public static String doPost(String reqUrl, Map<String, String> parameters) {
        return doPost(reqUrl, parameters, "UTF-8", HTTP_CONN_TIMEOUT, HTTP_SOCKET_TIMEOUT);
    }

    public static String doPost(String reqUrl, Map<String, String> parameters, String encoding) {
        return doPost(reqUrl, parameters, encoding, HTTP_CONN_TIMEOUT, HTTP_SOCKET_TIMEOUT);
    }

    public static String doPost(String reqUrl, Map<String, String> parameters, String encoding, int connectTimeout,
                                int readTimeout) {
        HttpURLConnection urlConn = null;
        try {
            urlConn = sendPost(reqUrl, parameters, encoding, connectTimeout, readTimeout);
            String responseContent = getContent(urlConn, encoding);
            return responseContent.trim();
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();

            }
        }
    }

    private static HttpURLConnection sendPost(String reqUrl,
                                              Map<String, String> parameters, String encoding, int connectTimeout, int readTimeout) {
        HttpURLConnection urlConn = null;
        try {
            String params = generatorParamString(parameters, encoding);
            URL url = new URL(reqUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            // urlConn
            // .setRequestProperty(
            // "User-Agent",
            // "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.3) Gecko/20100401 Firefox/3.6.3");
            // (milliseconds) JDK
            urlConn.setConnectTimeout(connectTimeout);
            // In JDK 1.5, the read timed out
            urlConn.setReadTimeout(readTimeout);
            urlConn.setDoOutput(true);
            // Its a good idea to treat strings as bytes
            byte[] b = params.getBytes(encoding);
            urlConn.getOutputStream().write(b, 0, b.length);
            urlConn.getOutputStream().flush();
            urlConn.getOutputStream().close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return urlConn;
    }

    private static String getContent(HttpURLConnection urlConn, String encoding) {
        try {
            String responseContent = null;
            InputStream in = urlConn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, encoding));
            String tempLine = rd.readLine();
            StringBuffer tempStr = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                tempStr.append(tempLine);
                tempStr.append(crlf);
                tempLine = rd.readLine();
            }
            responseContent = tempStr.toString();
            rd.close();
            in.close();
            return responseContent;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * @param link
     * @param encoding
     * @return
     */
    public static String doGet(String link, String encoding, int connectTimeout, int readTimeout) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(link);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            // conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedInputStream in = new BufferedInputStream(
                    conn.getInputStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int i = 0; (i = in.read(buf)) > 0;) {
                out.write(buf, 0, i);
            }
            out.flush();
            String s = new String(out.toByteArray(), encoding);
            return s;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
    }

    /**
     * UTF-8coding
     *
     * @param link
     * @return
     */
    public static String doGet(String link) {
        return doGet(link, "UTF-8", HTTP_CONN_TIMEOUT, HTTP_SOCKET_TIMEOUT);
    }

    /**
     * willparametersData is converted to use"&"Of the linkhttpRequest parameter form
     *
     * @param parameters
     * @return
     */
    private static String generatorParamString(Map<String, String> parameters, String encoding) {
        StringBuffer params = new StringBuffer();
        if (parameters != null) {
            for (Iterator<String> iter = parameters.keySet().iterator(); iter
                    .hasNext();) {
                String name = iter.next();
                String value = parameters.get(name);
                params.append(name + "=");
                try {
                    params.append(URLEncoder.encode(value, encoding));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e.getMessage(), e);
                } catch (Exception e) {
                    String message = String.format("'%s'='%s'", name, value);
                    throw new RuntimeException(message, e);
                }
                if (iter.hasNext()) {
                    params.append("&");
                }
            }
        }
        return params.toString();
    }

}
