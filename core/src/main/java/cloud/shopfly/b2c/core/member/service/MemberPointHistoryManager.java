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
 * 会员积分表业务层
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-03 15:44:12
 */
public interface MemberPointHistoryManager {

    /**
     * 查询会员积分表列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @param memberId 会员id
     * @return Page
     */
    Page list(int page, int pageSize, Integer memberId);

    /**
     * 添加会员积分表
     *
     * @param memberPointHistory 会员积分表
     * @return MemberPointHistory 会员积分表
     */
    MemberPointHistory add(MemberPointHistory memberPointHistory);


}