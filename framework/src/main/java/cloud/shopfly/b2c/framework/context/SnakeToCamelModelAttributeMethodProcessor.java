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
package cloud.shopfly.b2c.framework.context;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.servlet.ServletRequest;

/**
 * 蛇形转驼峰参数转换器
 * 用于非基本类型
 * Created by kingapex on 2018/3/20.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/20
 */
public class SnakeToCamelModelAttributeMethodProcessor extends ServletModelAttributeMethodProcessor {


    /**
     * 构造函数
     *
     * @param annotationNotRequired 注解是否必须
     */
    public SnakeToCamelModelAttributeMethodProcessor(boolean annotationNotRequired) {
        super(annotationNotRequired);
    }

    /**
     * 绑定蛇形转驼峰Binder
     *
     * @param binder  spring 机制传琛来的binder
     * @param request spring机制的web request
     */
    @Override
    @InitBinder
    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
        SnakeToCamelRequestDataBinder camelBinder = new SnakeToCamelRequestDataBinder(binder.getTarget(), binder.getObjectName());
        camelBinder.bind(request.getNativeRequest(ServletRequest.class));
        super.bindRequestParameters(binder, request);
    }


}
