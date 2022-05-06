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
 * 资源找不到异常
 *
 * @author Kingapex
 * @version v1.0
 * @since v7.0.0
 * 2017年8月15日 下午1:02:50
 */
public class ResourceNotFoundException extends ServiceException {


    private static final long serialVersionUID = -6945068834935110333L;


    public ResourceNotFoundException(String message) {

        super(SystemErrorCodeV1.RESOURCE_NOT_FOUND, message);
        this.statusCode = HttpStatus.NOT_FOUND;

    }
}
