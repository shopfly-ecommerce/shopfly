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
package cloud.shopfly.b2c.core.pagecreate.exception;

import cloud.shopfly.b2c.framework.exception.ServiceException;
import org.springframework.http.HttpStatus;

/**
 * A system exception
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-05-10 In the morning7:11
 */

public class SystemException extends ServiceException {



    public SystemException(String code, String message) {

        super(code, message);
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        statusCode = HttpStatus.BAD_REQUEST;

    }

}
