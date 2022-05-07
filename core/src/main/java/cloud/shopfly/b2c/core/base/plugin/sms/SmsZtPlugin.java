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
package cloud.shopfly.b2c.core.base.plugin.sms;

import cloud.shopfly.b2c.core.base.model.vo.ConfigItem;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cloud.shopfly.b2c.framework.logs.Debugger;

import cloud.shopfly.b2c.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * MSM plug-in
 *
 * @author zh
 * @version v1.0
 * @since v1.0
 * 2018years3month25On the afternoon2:42:20
 */
@Component
public class SmsZtPlugin implements SmsPlatform {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private Debugger debugger;

    @Override
    public boolean onSend(String phone, String content, Map param) {

        try {
            if (StringUtil.isEmpty(phone) || StringUtil.isEmpty(content) || CollUtil.isEmpty(param)) {
                logger.error("Parameters for sending SMS messages are abnormal,Please check thephone="+phone+" ,content="+content+" ,param= "+param);
                return false;
            }
            debugger.log("Tuned upSmsZtPlugin", "Parameters for：", param.toString());
            String urls = "https://api.mix2.zthysms.com/v2/sendSms";
            long tKey = System.currentTimeMillis() / 1000;
            // The user
            param.put("username", param.get("name"));
            param.remove("name");
            // Password
            param.put("password", SecureUtil.md5(SecureUtil.md5(param.get("password").toString()) + tKey));
            //tKey
            param.put("tKey", tKey + "");
            // Mobile phone no.
            param.put("mobile", phone);
            // content
            param.put("content", content);

            debugger.log("toztsms.cnMake a request, requesturlfor：", urls);
            // Return send result
            String result = HttpRequest.post(urls)
                    .header(Header.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(JSONUtil.toJsonStr(param))
                    .timeout(60000)
                    .execute()
                    .body();
            debugger.log("Received return result：", result);
            JSONObject jsonObject = JSONUtil.parseObj(result);
            if (!StringUtil.equals(jsonObject.get("code").toString(), "200")) {
                logger.error("SMS sending exception" + result);
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("SMS sending exception", e);
        }
        return false;
    }

    @Override
    public String getPluginId() {
        return "smsZtPlugin";
    }


    @Override
    public String getPluginName() {
        return "Short message of the assistant gateway";
    }


    @Override
    public List<ConfigItem> definitionConfigItem() {
        List<ConfigItem> list = new ArrayList();


        ConfigItem name = new ConfigItem();
        name.setType("text");
        name.setName("name");
        name.setText("Username");

        ConfigItem password = new ConfigItem();
        password.setType("text");
        password.setName("password");
        password.setText("Password");

        ConfigItem id = new ConfigItem();
        id.setType("text");
        id.setName("id");
        id.setText("productid");

        ConfigItem trumpet = new ConfigItem();
        trumpet.setType("text");
        trumpet.setName("trumpet");
        trumpet.setText("Extended trumpet（No please leave blank）");

        list.add(name);
        list.add(password);
        list.add(id);
        list.add(trumpet);

        return list;
    }


    @Override
    public Integer getIsOpen() {
        return 0;
    }

}
