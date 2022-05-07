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
import cloud.shopfly.b2c.framework.security.model.Seller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * The default userholder
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-05-28
 */
@Component
public class DefaultUserHolder implements  UserHolder{

    @Override
    public Seller getSeller() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object someOne = authentication.getDetails();
        if (someOne != null && someOne instanceof Seller) {
            return (Seller) someOne;
        }
        return null;
    }

    @Override
    public Buyer getBuyer() {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        // If it contains buyer permission, it reads seller information and returns
        Object someOne = authentication.getDetails();
        if (someOne != null && someOne instanceof Buyer) {
            return (Buyer) someOne;
        }

        return null;
    }
}
