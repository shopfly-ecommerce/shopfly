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
package cloud.shopfly.b2c.core.statistics.service.impl;

import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.statistics.model.dto.MemberRegisterData;
import cloud.shopfly.b2c.core.statistics.service.MemberDataManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Member data injection implementation
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/22 In the afternoon10:12
 *
 */
@Service
public class MemberDataManagerImpl implements MemberDataManager {

    @Autowired

    private DaoSupport daoSupport;

    @Override
    public void register(Member member) {
        this.daoSupport.insert("es_sss_member_register_data", new MemberRegisterData(member));
    }

}
