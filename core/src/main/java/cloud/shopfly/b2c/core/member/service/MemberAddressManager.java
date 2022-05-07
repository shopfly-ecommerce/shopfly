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
package cloud.shopfly.b2c.core.member.service;

import cloud.shopfly.b2c.core.member.model.dos.MemberAddress;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * User delivery address business interface
 *
 * @author dmy
 * @version v2.0
 * @since v7.0.0
 * 2018-03-18 15:37:00
 */
public interface MemberAddressManager {

    /**
     * Query the collection of user shipping address information
     *
     * @return User delivery address collection
     */
    List<MemberAddress> list();

    /**
     * Query the current user's shipping address paginated list
     *
     * @param page     Number of paginated pages
     * @param pageSize Number of paginated display
     * @param memberId User ID
     * @return User delivery address collection
     */
    Page list(int page, int pageSize, Integer memberId);

    /**
     * Add user shipping address
     *
     * @param memberAddress User shipping address parameter information
     * @return Add the user's shipping address information after successful addition
     */
    MemberAddress add(MemberAddress memberAddress);

    /**
     * Update user shipping address
     *
     * @param memberAddress User shipping address parameter information
     * @param id            primary key ID
     * @return The user's delivery address information after the modification is successful
     */
    MemberAddress edit(MemberAddress memberAddress, Integer id);

    /**
     * Delete user shipping address
     *
     * @param id primary key ID
     */
    void delete(Integer id);

    /**
     * Query the details of a user's shipping address
     *
     * @param id primary key ID
     * @return User shipping address details
     */
    MemberAddress getModel(Integer id);

    /**
     * Get the details of the user's default shipping address
     *
     * @param memberId User ID
     * @return User default shipping address details
     */
    MemberAddress getDefaultAddress(Integer memberId);

    /**
     * Set as default shipping address
     *
     * @param id primary key ID
     */
    void editDefault(Integer id);


}
