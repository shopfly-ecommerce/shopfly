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
package cloud.shopfly.b2c.framework.parameter;

import cloud.shopfly.b2c.framework.util.EmojiCharacterUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Handles returns of basic types, so far onlyemojiDecoding of expression
 *
 * @author fk
 * @version 1.0
 * @since 7.2.0
 * 2020/5/8
 */
@ControllerAdvice
public class ParameterHandleResponse implements ResponseBodyAdvice {


    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        // Only basic types of parameters are handled
        return BeanUtils.isSimpleProperty(methodParameter.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        Class<?> type = methodParameter.getParameterType();
        // The character string is emoji transcoding
        if (type.equals(String.class) && o != null) {
            return EmojiCharacterUtil.encode(o.toString());
        }
        return o;
    }
}
