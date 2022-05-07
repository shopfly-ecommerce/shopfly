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
package cloud.shopfly.b2c.core.goodssearch.model;

import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: Parameter property selector
 * @date 2018/8/16 9:23
 * @since v7.0.0
 */
public class PropSelector {

    private String key;

    private List<SearchSelector> value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<SearchSelector> getValue() {
        return value;
    }

    public void setValue(List<SearchSelector> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PropSelector{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
