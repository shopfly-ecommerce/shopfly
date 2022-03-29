/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.security;

import com.enation.app.javashop.framework.util.EmojiCharacterUtil;
import com.enation.app.javashop.framework.util.XssUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
