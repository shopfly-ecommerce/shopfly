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
package cloud.shopfly.b2c.core.trade.order.model.dto;

import cloud.shopfly.b2c.core.trade.order.model.enums.OrderDataKey;
import cloud.shopfly.b2c.framework.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kingapex on 2019-02-11.
 * 个性化数据
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-02-11
 */
public class PersonalizedData {

    private String data;

    public PersonalizedData() {
    }

    public PersonalizedData(String data) {
        this.data = data;
    }


    /**
     * 设置个性化的数据
     */
    public void setPersonalizedData(OrderDataKey key, Object personalizedData) {
        Gson gson = new GsonBuilder().create();

        //如果个性化数据为空new 一个map
        //如果不为空，用orderData转回map
        Map data;
        if (StringUtil.isEmpty(this.data)) {
            data = new HashMap(16);
        } else {
            data = gson.fromJson(this.data, HashMap.class);
        }

        //把个性化数据转为json存在map中，再转为json给orderData
        String json = gson.toJson(personalizedData);
        data.put(key.name(), json);
        this.data = gson.toJson(data);
    }

    public String getData() {
        return data;
    }

}
