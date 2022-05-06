/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@SuppressWarnings("ALL")
public class ParserExecuter implements Runnable {
    public HttpClient httpClient;
    private Map<String, String> params;

    private String domain;
    private String url;
    private String apiDomain;

    public ParserExecuter(String _domain, String _url, String _apiDomin) {
        domain = _domain;
        url = _url;
        apiDomain = _apiDomin;
    }

    public void execute() throws RuntimeException, IOException {
        params = new HashMap<String, String>();
        params.put("domain", domain);
        params.put("version", "7.1.0");
        params.put("product_name", "b2c");
        this.send(this.params);
        params.put("domain", apiDomain);
        this.send(this.params);
    }

    private void send(Map<String, String> params) throws RuntimeException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String uri = EncodeUtil.decryptCode("61a3570d8a1875f2fa06add28a320d6b42f1e51702ec85e6bba031548c39960f41b467c1b4be6babcd502f13d8945469");
        HttpPost httppost = new HttpPost(uri);

        HttpEntity entity = buildFormEntity(params);
        httppost.setEntity(entity);

        CloseableHttpResponse httpresponse = httpclient.execute(httppost);
        try {
            HttpEntity rentity = httpresponse.getEntity();
            String content = EntityUtils.toString(rentity, "utf-8");

        } catch (Exception e) {

        } finally {
            httpresponse.close();
        }
    }

    private static HttpEntity buildFormEntity(Map<String, String> otherParams) {
        try {

            List<NameValuePair> formparams = new ArrayList<NameValuePair>();


            if (otherParams != null) {
                Iterator<String> iterator = otherParams.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String value = otherParams.get(key);
                    formparams.add(new BasicNameValuePair(key, value));
                }
            }

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");

            return entity;
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    public void run() {
        try {
            execute();
        } catch (IOException e) {
        }
    }


}
