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

/**
 * Service exception class,Each service exception needs to inherit this exception
 * A business class throws an exception if it cannot handle it
 *
 * @author kingapex
 * @version v1.0.0
 * @since v7.0.0
 * 2017years3month7The morning of11:09:29
 */
public class ServiceException extends RuntimeException {

    protected HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

    private String code;
    /**
     * To return data from the front end
     */
    private Object data;


    public ServiceException(String code, String message) {
        super(message);
        this.code = code;

    }

    public ServiceException(String code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getCode() {
        return code;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
