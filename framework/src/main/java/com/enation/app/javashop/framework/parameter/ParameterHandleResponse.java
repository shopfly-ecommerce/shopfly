/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.parameter;

import com.enation.app.javashop.framework.util.EmojiCharacterUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 处理基本类型的返回，目前只处理了emoji表情的解码
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
        //只处理基本类型的参数
        return BeanUtils.isSimpleProperty(methodParameter.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        Class<?> type = methodParameter.getParameterType();
        //字符串类型，emoji表情转码
        if (type.equals(String.class) && o != null) {
            return EmojiCharacterUtil.encode(o.toString());
        }
        return o;
    }
}
