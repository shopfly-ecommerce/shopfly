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
import cloud.shopfly.b2c.core.system.model.vo.ExpressDetailVO;
import com.alibaba.fastjson.JSON;
import com.easypost.EasyPost;
import com.easypost.exception.EasyPostException;
import com.easypost.model.Tracker;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EasyPost 快递实现
 *
 * @author cs
 * @version v7.2.3
 * @date 22/4/26 下午3:52
 * @since v7.0
 */
@Component
public class EasyPostPlugin implements ExpressPlatform {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public List<ConfigItem> definitionConfigItem() {
        List<ConfigItem> list = new ArrayList<>();
        ConfigItem keyItem = new ConfigItem();
        keyItem.setName("apikey");
        keyItem.setText("apikey");
        keyItem.setType("text");
        list.add(keyItem);
        return list;
    }

    @Override
    public String getPluginId() {
        return "EasyPostPlugin";
    }

    @Override
    public String getPluginName() {
        return "EasyPost";
    }

    @Override
    public Integer getIsOpen() {
        return 0;
    }

    @Override
    public ExpressDetailVO getExpressDetail(String abbreviation, String num, Map config) {
        EasyPost.apiKey = config.get("apikey").toString();
        Map<String, Object> trackerMap = new HashMap();
        trackerMap.put("tracking_code",num);
        trackerMap.put("carrier", abbreviation);

        try {
            Tracker tracker = Tracker.create(trackerMap);
            ExpressDetailVO expressDetailVO = new ExpressDetailVO();
            expressDetailVO.setData(tracker.getTrackingDetails());
            expressDetailVO.setCourierNum(tracker.getTrackingCode());
            expressDetailVO.setName(tracker.getCarrier());
            return expressDetailVO;
        } catch (EasyPostException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        EasyPost.apiKey = "EZTK6cca1d234b7c4e948ac4cd2d0c1487a4KaXeG4kBUMHzP5uIEM3Flw";
        Map<String, Object> trackerMap = new HashMap();
        trackerMap.put("tracking_code","EZ2000000002");
        trackerMap.put("carrier", "USPS");

        try {
            Tracker tracker = Tracker.create(trackerMap);
            System.out.println(JSON.toJSONString(tracker));

        } catch (EasyPostException e) {
            e.printStackTrace();

        }
    }
}
