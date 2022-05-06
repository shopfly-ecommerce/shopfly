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
package cloud.shopfly.b2c.framework.security;

import cloud.shopfly.b2c.framework.util.EmojiCharacterUtil;
import cloud.shopfly.b2c.framework.util.XssUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 防xss攻击的json转换器
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/7/27
 */
public class XssStringJsonSerializer extends JsonSerializer<String> {

    @Override
    public Class<String> handledType() {
        return String.class;
    }

    /**
     * 覆写 serialize 方法，对string 值进行xss过滤
     * 使用的是Jsoup的clean方法
     * @param value
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     */
    @Override
    public void serialize(String value, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        if (value != null) {
            value = XssUtil.clean(value);
            //xss过滤同时，解码emoji表情，这里只能处理response为对象的情况，基本类型在ParameterHandleResponse中处理
            jsonGenerator.writeString(EmojiCharacterUtil.decode(value));
        }
    }

}
