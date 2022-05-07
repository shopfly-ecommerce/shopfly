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
 * User context
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
     * Use this property to facilitate testing logged on in unit tests
     * If this property has a value, the buyer context will return the mock object directly
     */
    public static Buyer mockBuyer =null;

    /**
     * Get the current buyer
     *
     * @return
     */
    public static Buyer getBuyer() {

        // If there is a mock object, it is returned directly
        if (mockBuyer != null) {
            return mockBuyer;
        }

        return userHolder.getBuyer();

    }


}
