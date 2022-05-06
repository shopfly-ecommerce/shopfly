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
package cloud.shopfly.b2c.core.base.model.vo;

import java.util.List;

/**
 * 配置文件映射实体
 *
 * @author dongxin
 * @version v1.0
 * @since v6.4.0
 * 2017年8月15日 上午11:35:42
 */
public class ConfigItem {
    /**
     * 配置文件name值
     */
    private String name;
    /**
     * 配置文件name映射文本值
     */
    private String text;
    /**
     * 配置文件显示在浏览器时，input的type属性
     */
    private String type;
    /**
     * 配置的值
     */
    private Object value;
    /**
     * 如果是select 是需要将可选项传递到前天
     */
    private List<RadioOption> options;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public List<RadioOption> getOptions() {
        return options;
    }

    public void setOptions(List<RadioOption> options) {
        this.options = options;
    }
}
