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
 * 系统设置分组枚举
 * Created by kingapex on 2018/3/19.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/19
 */
public enum SettingGroup {

    /**
     * 系统设置
     */
    SYSTEM,

    /**
     * 站点设置
     */
    SITE,

    /**
     * 商品设置
     */
    GOODS,

    /**
     * 平台联系方式
     */
    INFO,
    /**
     * 商品图片尺寸
     */
    PHOTO_SIZE,
    /**
     * 交易设置
     */
    TRADE,

    /**
     * 积分设置
     */
    POINT,
    /**
     * 分销设置
     */
    DISTRIBUTION,
    /**
     * 测试设置
     */
    TEST,
    /**
     * app推送设置
     */
    PUSH,
    /**
     * page
     */

    PAGE,
    /**
     * ES_SIGN 分词词库秘钥
     */
    ES_SIGN;

}
