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

import cloud.shopfly.b2c.core.member.model.dos.MemberPointHistory;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * Member points sheet business layer
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-03 15:44:12
 */
public interface MemberPointHistoryManager {

    /**
     * Query the list of member points
     *
     * @param page     The page number
     * @param pageSize Number each page
     * @param memberId membersid
     * @return Page
     */
    Page list(int page, int pageSize, Integer memberId);

    /**
     * Add membership points table
     *
     * @param memberPointHistory Member points sheet
     * @return MemberPointHistory Member points sheet
     */
    MemberPointHistory add(MemberPointHistory memberPointHistory);


}
