/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.context;

import dev.shopflix.framework.util.XssUtil;
import com.google.common.base.CaseFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 蛇形转驼峰参数转换器
 * 以及防xss的参数过滤
 * 用于基本类型
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2017年5月19日上午12:45:33
 */
public class SnakeToCamelArgumentResolver implements HandlerMethodArgumentResolver {

	/**
	 * 默认分页大小
	 */
	private static final int DEFAULT_SIZE = 10;

	/**
	 * 默认开始页码
	 */
	private static final int DEFAULT_NO = 1;
	
	/**
	 * 定义只有基本类型参数才转换
	 * @param parameter spring机制传过来的当前要处理的参数
	 * @return 只有基本类型参数才返回true,否则返回false
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		//只处理基本类型的参数
		return  BeanUtils.isSimpleProperty(parameter.getParameterType());
	}


	/**
	 * 将蛇形参数值转换给驼峰参数值
	 * @param parameter 要处理的参数
	 * @param mavContainer spring mavContainer
	 * @param webRequest  spring  webRequest
	 * @param binderFactory spring binderFactory
	 * @return 转换后的参数值
	 * @throws Exception
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		//将蛇形参数名转为驼峰参数名，再读值返回
		String paramName  = parameter.getParameterName();
		String camelName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, paramName);
		SimpleTypeConverter converter = new SimpleTypeConverter();

		Object param = converter.convertIfNecessary(webRequest.getParameter(camelName),parameter.getParameterType());

		//如果是字串型参数，则进行xss过滤
		if( param instanceof String){
			param = XssUtil.clean((String)param);
		}

		//分页相关，增加默认分页
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
