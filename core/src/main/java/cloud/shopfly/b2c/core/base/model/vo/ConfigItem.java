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
 * Configuration files map entities
 *
 * @author dongxin
 * @version v1.0
 * @since v6.4.0
 * 2017years8month15The morning of11:35:42
 */
public class ConfigItem {
    /**
     * The configuration filenamevalue
     */
    private String name;
    /**
     * The configuration filenameMapped text value
     */
    private String text;
    /**
     * When the configuration file is displayed in the browser,inputthetypeattribute
     */
    private String type;
    /**
     * The value of the configuration
     */
    private Object value;
    /**
     * If it isselect Yes you need to pass the option to the day before yesterday
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
