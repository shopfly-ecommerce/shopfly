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
package cloud.shopfly.b2c.core.client.system;

/**
 * 验证码客户端
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 上午11:48
 * @since v7.0
 */

public interface CaptchaClient {
    /**
     * 图片验证
     *
     * @param uuid  uid
     * @param code  验证码
     * @param scene 业务类型
     * @return
     */
    boolean valid(String uuid, String code, String scene);

    /**
     * 清除图片验证码
     *
     * @param uuid
     * @param code
     * @param scene
     */
    void deleteCode(String uuid, String code, String scene);
}
