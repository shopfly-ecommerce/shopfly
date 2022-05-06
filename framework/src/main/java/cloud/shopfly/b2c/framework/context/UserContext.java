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
package cloud.shopfly.b2c.framework.context;

import cloud.shopfly.b2c.framework.security.model.Buyer;

/**
 * 用户上下文
 * Created by kingapex on 2018/3/12.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/12
 */
public class UserContext {

    private static UserHolder userHolder;

    public static void  setHolder(UserHolder userHodler) {
        userHolder = userHodler;
    }

    /**
     * 为了方便在单元测试中测试已登录的情况，请使用此属性
     * 如果此属性有值，买家上下文中将会直接返回此模拟对象
     */
    public static Buyer mockBuyer =null;

    /**
     * 获取当前买家
     *
     * @return
     */
    public static Buyer getBuyer() {

        //如果有模拟对象，会直接返回此模拟对象
        if (mockBuyer != null) {
            return mockBuyer;
        }

        return userHolder.getBuyer();

    }


}
