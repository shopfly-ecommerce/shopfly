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

/**
 * 系统级别异常码
 *
 * @author kingapex
 * @version v1.0.0
 * @since v1.0.0
 * 2017年3月27日 下午6:56:35
 */
public class SystemErrorCodeV1 {

    /**
     * 无权限异常
     */
    public final static String NO_PERMISSION = "002";
    /**
     * 资源未能找到
     */
    public final static String RESOURCE_NOT_FOUND = "003";
    /**
     * 错误的请求参数
     */
    public final static String INVALID_REQUEST_PARAMETER = "004";
    /**
     * 错误的配置参数
     */
    public final static String INVALID_CONFIG_PARAMETER = "005";
    /**
     * 错误的配置参数
     */
    public final static String INVALID_COTENT = "006";
}
