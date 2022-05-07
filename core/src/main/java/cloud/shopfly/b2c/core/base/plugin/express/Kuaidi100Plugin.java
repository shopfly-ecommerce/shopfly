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
package cloud.shopfly.b2c.core.base.plugin.express;

import cloud.shopfly.b2c.core.base.model.vo.ConfigItem;
import cloud.shopfly.b2c.core.base.model.vo.RadioOption;
import cloud.shopfly.b2c.core.base.plugin.express.util.HttpRequest;
import cloud.shopfly.b2c.core.base.plugin.express.util.MD5;
import cloud.shopfly.b2c.core.client.system.LogiCompanyClient;
import cloud.shopfly.b2c.core.system.model.vo.ExpressDetailVO;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * showapi Express implementation
 *
 * @author zh
 * @version v7.0
 * @date 18/7/11 In the afternoon3:52
 * @since v7.0
 */
@Component
public class Kuaidi100Plugin implements ExpressPlatform {

    @Autowired
    private LogiCompanyClient logiCompanyClient;

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public List<ConfigItem> definitionConfigItem() {
        List<ConfigItem> list = new ArrayList<>();
        ConfigItem codeItem = new ConfigItem();
        codeItem.setName("code");
        codeItem.setText("Company code");
        codeItem.setType("text");

        ConfigItem secretItem = new ConfigItem();
        secretItem.setName("id");
        secretItem.setText("id");
        secretItem.setType("text");

        ConfigItem typeItem = new ConfigItem();
        typeItem.setName("user");
        typeItem.setText("The user types");
        typeItem.setType("radio");
        // Organization user type Optional
        List<RadioOption> options = new ArrayList<>();
        RadioOption radioOption = new RadioOption();
        radioOption.setLabel("The average user");
        radioOption.setValue(0);
        options.add(radioOption);
        radioOption = new RadioOption();
        radioOption.setLabel("Enterprise customers");
        radioOption.setValue(1);
        options.add(radioOption);
        typeItem.setOptions(options);

        list.add(codeItem);
        list.add(secretItem);
        list.add(typeItem);
        return list;
    }

    @Override
    public String getPluginId() {
        return "kuaidi100Plugin";
    }

    @Override
    public String getPluginName() {
        return "Courier100";
    }

    @Override
    public Integer getIsOpen() {
        return 0;
    }

    @Override
    public ExpressDetailVO getExpressDetail(String abbreviation, String num, Map config) {
        String url = "";
        // Obtain express platform parameters
        Integer user = new Double(StringUtil.toDouble(config.get("user"), false)).intValue();
        String code = StringUtil.toString(config.get("code"));
        String id = StringUtil.toString(config.get("id"));
        HashMap<String, String> parms = new HashMap<String, String>(16);
        // Select different query interfaces according to different user types
        if (user.equals(1)) {
            url = "http://poll.kuaidi100.com/poll/query.do";
            String param = "{\"com\":\"" + abbreviation + "\",\"num\":\"" + num + "\"}";
            String sign = MD5.encode(param + id + code);
            parms.put("param", param);
            parms.put("sign", sign);
            parms.put("customer", code);
        } else {
            url = "http://api.kuaidi100.com/api?id=" + id + "&nu=" + num + "&com=" + abbreviation + "&muti=1&order=asc";
        }
        try {
            String content = HttpRequest.postData(url, parms, "utf-8").toString();
            Map map = JsonUtil.toMap(content);
            ExpressDetailVO expressDetailVO = new ExpressDetailVO();
            expressDetailVO.setData((List<Map>) map.get("data"));
            expressDetailVO.setCourierNum(map.get("nu").toString());
            expressDetailVO.setName(logiCompanyClient.getLogiByCode(map.get("com").toString()).getName());
            return expressDetailVO;
        } catch (Exception e) {
            logger.error("Express query error" + e);
            e.printStackTrace();
        }
        return null;
    }
}
