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
package cloud.shopfly.b2c.framework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Eop 参数校验处理类
 *
 * @author jianghongyan
 * @version v1.0
 * @since v6.2
 * 2016年12月9日 上午12:00:53
 */
@ControllerAdvice
public class ShopflyExceptionHandler {
    /**
     * 处理单个参数校验
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleValidationException(ConstraintViolationException e) {
        for (ConstraintViolation<?> s : e.getConstraintViolations()) {
            return new ErrorMessage(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, s.getMessage());
        }
        return new ErrorMessage(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "未知参数错误");
    }

    /**
     * 处理参数异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleValidationBodyException(MethodArgumentNotValidException e) {
        for (ObjectError s : e.getBindingResult().getAllErrors()) {
            return new ErrorMessage(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, s.getDefaultMessage());
        }
        return new ErrorMessage(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "未知参数错误");
    }

    /**
     * 处理实体类校验
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleValidationBeanException(BindException e) {

        for (ObjectError s : e.getAllErrors()) {

            String msg = s.getDefaultMessage();
            //地区不合法formatter会进入该异常
            if(msg.contains("IllegalArgumentException")){
                return new ErrorMessage(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, msg.substring(msg.lastIndexOf(":")+1));
            }

            return new ErrorMessage(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, s.getDefaultMessage());
        }
        return new ErrorMessage(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "未知参数错误");
    }

    /**
     * 处理ServiceExcepiton：业务类抛出来的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ErrorMessage handleServiceException(ServiceException e, HttpServletResponse response) {
        response.setStatus(e.getStatusCode().value());
        Object data = e.getData();
        if (data == null) {
            return new ErrorMessage(e.getCode(), e.getMessage());
        } else {
            return new ErrorMessageWithData(e.getCode(), e.getMessage(), data);
        }

    }

    /**
     * 处理参数传递异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ErrorMessage handleUnProccessableServiceException(IllegalArgumentException e, HttpServletResponse response) {

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ErrorMessage(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, e.getMessage());
    }


}
