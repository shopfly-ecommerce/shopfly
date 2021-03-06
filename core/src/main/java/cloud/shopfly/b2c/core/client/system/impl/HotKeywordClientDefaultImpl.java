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
package cloud.shopfly.b2c.core.client.system.impl;

import cloud.shopfly.b2c.core.client.system.HotkeywordClient;
import cloud.shopfly.b2c.core.pagedata.model.HotKeyword;
import cloud.shopfly.b2c.core.pagedata.service.HotKeywordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zs
 * @version v1.0
 * @Description: Hot keywords
 * @date 2021-01-19
 * @since v7.1.0
 */
@Service
@ConditionalOnProperty(value="shopfly.product", havingValue="stand")
public class HotKeywordClientDefaultImpl implements HotkeywordClient {

    @Autowired
    private HotKeywordManager hotKeywordManager;

    @Override
    public List<HotKeyword> listByNum(Integer num) {
        return hotKeywordManager.listByNum(num);
    }
}
