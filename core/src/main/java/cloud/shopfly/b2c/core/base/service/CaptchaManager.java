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
package cloud.shopfly.b2c.core.base.service;

/**
 * 图片验证码业务层
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018年3月19日 上午9:55:04
 */
public interface CaptchaManager {
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

    /**
     * 图片生成
     *
     * @param uuid  uid
     * @param scene 业务类型
     */
    void writeCode(String uuid, String scene);

}
