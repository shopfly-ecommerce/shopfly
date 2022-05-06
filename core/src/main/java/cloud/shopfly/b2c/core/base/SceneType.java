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
package cloud.shopfly.b2c.core.base;

/**
 * 图片验证码业务类型
 *
 * @author zh
 * @version v2.0
 * @since v7.0
 * 2018年3月19日 下午4:35:32
 */
public enum SceneType {

    /**
     * 验证码登录
     */
    LOGIN("验证码登录"),
    /**
     * 手机注册
     */
    REGISTER("手机注册"),
    /**
     * 找回密码
     */
    FIND_PASSWORD("找回密码"),
    /**
     * 绑定手机
     */
    BIND_MOBILE("绑定手机"),
    /**
     * 修改密码
     */
    MODIFY_PASSWORD("修改密码"),
    /**
     * 添加店员
     */
    ADD_CLERK("添加店员"),
    /**
     * 验证手机
     */
    VALIDATE_MOBILE("验证手机");

    private String description;

    SceneType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
