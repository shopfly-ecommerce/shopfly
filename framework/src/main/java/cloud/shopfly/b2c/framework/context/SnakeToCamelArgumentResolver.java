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

import cloud.shopfly.b2c.framework.util.XssUtil;
import com.google.common.base.CaseFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Snake-to-hump parameter converter
 * As well as thexssParameter filtering of
 * For basic types
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2017years5month19The morning of12:45:33
 */
public class SnakeToCamelArgumentResolver implements HandlerMethodArgumentResolver {

	/**
	 * Default page size
	 */
	private static final int DEFAULT_SIZE = 10;

	/**
	 * Default start page number
	 */
	private static final int DEFAULT_NO = 1;
	
	/**
	 * Defines that only primitive type parameters are converted
	 * @param parameter springThe current parameter to process passed by the mechanism
	 * @return Only basic type parameters are returnedtrue,Otherwise returnsfalse
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// Only basic types of parameters are handled
		return  BeanUtils.isSimpleProperty(parameter.getParameterType());
	}


	/**
	 * Converts the snake parameter value to the hump parameter value
	 * @param parameter Parameters to process
	 * @param mavContainer spring mavContainer
	 * @param webRequest  spring  webRequest
	 * @param binderFactory spring binderFactory
	 * @return Parameter value after conversion
	 * @throws Exception
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		// Convert the snake parameter name to the hump parameter name and return the value
		String paramName  = parameter.getParameterName();
		String camelName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, paramName);
		SimpleTypeConverter converter = new SimpleTypeConverter();

		Object param = converter.convertIfNecessary(webRequest.getParameter(camelName),parameter.getParameterType());

		// If it is a string parameter, XSS filtering is performed
		if( param instanceof String){
			param = XssUtil.clean((String)param);
		}

		// Paging related, adding default paging
		if(param == null ){
			if ("pageNo".equals(paramName)) {

				return DEFAULT_NO;
			}

			if ("pageSize".equals(paramName)) {

				return DEFAULT_SIZE;
			}
		}

		return 	param;

	}


}
